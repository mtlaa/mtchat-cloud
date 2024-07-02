package com.mtlaa.mtchat;

import com.mtlaa.mtchat.cache.user.BlackCache;
import com.mtlaa.mtchat.cache.user.UserCache;
import com.mtlaa.mtchat.chat.dao.MessageDao;
import com.mtlaa.mtchat.domain.chat.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/capi")
@Slf4j
public class TestController {
    @Autowired
    private BlackCache blackCache;
    @Autowired
    private MessageDao messageDao;
    /**
     * 更改黑名单的接口: 删除黑名单缓存
     */
    @PostMapping("/black/update")
    public String updateBlack(){
        log.info("update black, delete cache");
        blackCache.evictBlackMap();
        return "update success!";
    }

    /**
     * 走被黑名单拦截的逻辑
     */
    @GetMapping("/black")
    public String get(){
        log.info("get--");
        return "success!";
    }

    /**
     * 查询数据库
     */
    @GetMapping("/get")
    public String getMessage(){
        log.info("get message");
        List<Message> list = messageDao.list();
        return list.toString();
    }

}