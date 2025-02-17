package com.mtlaa.login.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mtlaa.api.client.UserClient;
import com.mtlaa.api.domain.user.entity.User;
import com.mtlaa.login.service.LoginService;
import com.mtlaa.login.service.WxMsgService;


import com.mtlaa.login.util.WxTextBuilder;
import com.mtlaa.mtchat.adapter.WebSocketAdapter;
import com.mtlaa.mychat.transaction.service.MQProducer;
import com.mtlaa.mychat.transaction.service.PushService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Create 2023/12/6 15:39
 */
@Service
@Slf4j
public class WxMsgServiceImpl implements WxMsgService {
    /**
     * 保存openId与loginCode（即eventKey）的关系
     */
    private static final Cache<String, Integer> WAIT_AUTHORIZE_MAP = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(60))
            .maximumSize(1000).build();

    private static final String URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&" +
                    "response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    @Value("${wx.mp.callback}")
    private String callback;


    @Autowired
    private UserClient userClient;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PushService pushService;
    /**
     * 用户在扫描微信二维码后，微信会发送用户的信息以及登录码，调用到这里
     */
    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService) {
        String openId = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);

        // 错误的消息，不需要返回
        if(code == null){
            return null;
        }

        User user = userClient.getByOpenId(openId);
        // 如果用户已经存在 且 昵称和头像不为空（已经授权） 说明用户已经注册成功
        if(user != null && StrUtil.isNotBlank(user.getAvatar()) && StrUtil.isNotBlank(user.getName())){
            // 登录成功 需要给前端发送登录成功的消息
            loginSuccess(code, user);
            return WxTextBuilder.build("登录成功", wxMpXmlMessage, wxMpService);
        }
        // 如果未注册，进行注册
        if(user == null){
            user = User.builder()
                    .openId(openId)
                    .build();
            userClient.register(user);
        }
        WAIT_AUTHORIZE_MAP.asMap().put(openId, code);

        // 扫码成功，发送给前端正在等待授权的消息
        // TODO code改为字符串类型，这样保证各服务唯一
        pushService.sendPushNotify(WebSocketAdapter.build(code.toString()));

        // 授权  构造微信授权登录的url
        String callbackUrl = URLEncoder.encode(callback + "/wx/portal/public/callback");
        String authorizeUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), callbackUrl);
        String content = "点击链接授权登录：<a href=\"" + authorizeUrl + "\">登录</a>";  // 使用html使用“登录”代替url
        return WxTextBuilder.build(content, wxMpXmlMessage, wxMpService);
    }

    /**
     * 点击授权链接后，微信回调callback，callback请求内获取用户信息
     * 保存用户信息
     */
    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        User user = userClient.getByOpenId(userInfo.getOpenid());
        if(StrUtil.isBlank(user.getName()) || StrUtil.isBlank(user.getAvatar())){
            user.setName(userInfo.getNickname());
            user.setAvatar(userInfo.getHeadImgUrl());
            user.setSex(userInfo.getSex());
            user.setUpdateTime(LocalDateTime.now());
            // 更新数据库
            userClient.updateUser(user);
        }
        // 获取登录码
        Integer code = WAIT_AUTHORIZE_MAP.getIfPresent(userInfo.getOpenid());
        loginSuccess(code, user);
        // 登录成功后移除map中映射
        WAIT_AUTHORIZE_MAP.invalidate(user.getOpenId());
    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage){
        try {
            String eventKey = wxMpXmlMessage.getEventKey();
            eventKey = eventKey.replace("qrscene_", "");
            return Integer.parseInt(eventKey);
        } catch (Exception e){
            log.info("getEventKey error:{}", wxMpXmlMessage.getEventKey(), e);
            return null;
        }
    }

    /**
     * 登录成功 需要给前端发送登录成功的消息
     */
    private void loginSuccess(Integer code, User user){
        String token = loginService.login(user.getId());
        // 发送消息到消息队列，由websocket服务消费，根据全局唯一的code找到channel
        // TODO 查询权限
        // TODO code改为字符串
        pushService.sendPushNotify(WebSocketAdapter.build(user, token, true, code.toString()));
    }
}
