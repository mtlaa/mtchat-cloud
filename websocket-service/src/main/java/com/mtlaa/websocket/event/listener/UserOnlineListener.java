package com.mtlaa.websocket.event.listener;




import com.mtlaa.api.client.UserClient;
import com.mtlaa.api.domain.user.entity.User;
import com.mtlaa.api.domain.user.enums.UserActiveStatusEnum;
import com.mtlaa.mychat.transaction.service.PushService;
import com.mtlaa.websocket.event.UserOnlineEvent;
import com.mtlaa.mtchat.adapter.WebSocketAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 用户上线
 * Create 2023/12/15 17:14
 */
@Component
public class UserOnlineListener {
    @Autowired
    private UserClient userClient;



//    @Autowired
//    private IpService ipService;

    @Autowired
    private PushService pushService;

    /**
     * 更新数据库状态, 更新缓存数据
     */
    @EventListener(UserOnlineEvent.class)
    @Async
    public void saveDB(UserOnlineEvent userOnlineEvent){
       User user = userOnlineEvent.getUser();
       User update = User.builder()
               .id(user.getId())
               .updateTime(LocalDateTime.now())
               .lastOptTime(user.getLastOptTime())
               .ipInfo(user.getIpInfo())
               .activeStatus(UserActiveStatusEnum.ONLINE.getStatus())
               .build();
       userClient.updateUser(update);
//        // 异步解析ip详情（为什么要先入库再解析之后再入库？因为IP解析是比较慢的，还有可能失败，而用户上线比较重要，所以先保存用户上线状态）
//        ipService.refreshIpDetailAsync(user.getId());
    }

    /**
     * 把用户上线的消息推送给所有在线用户
     */
    @Async
    @EventListener(classes = UserOnlineEvent.class)
    public void pushUserOnline(UserOnlineEvent event) {
       // 推送给所有在线用户，该用户登录成功
       pushService.sendPushMsg(WebSocketAdapter.buildOnlineNotify(event.getUser()));
    }

}
