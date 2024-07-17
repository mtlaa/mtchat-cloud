package com.mtlaa.websocket.event.listener;




import com.mtlaa.api.client.UserClient;
import com.mtlaa.api.domain.user.entity.User;
import com.mtlaa.api.domain.user.enums.UserActiveStatusEnum;
import com.mtlaa.mychat.transaction.service.PushService;
import com.mtlaa.websocket.event.UserOfflineEvent;
import com.mtlaa.mtchat.adapter.WebSocketAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserOfflineListener {
    @Autowired
    private UserClient userClient;

    @Autowired
    private PushService pushService;


    /**
     * 更新数据库, 更新缓存数据
     */
    @EventListener(UserOfflineEvent.class)
    @Async
    public void updateDB(UserOfflineEvent event){
       User user = event.getUser();
       User update = new User();
       update.setId(user.getId());
       update.setActiveStatus(UserActiveStatusEnum.OFFLINE.getStatus());
       update.setLastOptTime(LocalDateTime.now());
       userClient.updateUser(update);
    }
    /**
     * 把用户下线的消息推送给所有在线用户
     */
    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void pushUserOffline(UserOfflineEvent event) {
       User user = event.getUser();
        //推送给所有在线用户，该用户下线
       pushService.sendPushMsg(WebSocketAdapter.buildOfflineNotify(user));
    }
}
