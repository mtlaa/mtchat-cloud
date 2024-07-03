package com.mtlaa.websocket.event.listener;


import com.mtlaa.mtchat.domain.user.entity.User;
import com.mtlaa.mtchat.domain.user.enums.UserActiveStatusEnum;

import com.mtlaa.mychat.transaction.service.PushService;
import com.mtlaa.websocket.event.UserOfflineEvent;
import com.mtlaa.websocket.service.adapter.WebSocketAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserOfflineListener {
//    @Autowired
//    private UserDao userDao;  TODO 用户服务 提供 用户下线更新数据库的接口
//    @Autowired
//    private UserCache userCache;  TODO 用户服务 提供 用户下线更新缓存
    @Autowired
    private PushService pushService;


    /**
     * 更新数据库
     */
    @EventListener(UserOfflineEvent.class)
    @Async
    public void updateDB(UserOfflineEvent event){
//        User user = event.getUser();
//        User update = new User();
//        update.setId(user.getId());
//        update.setActiveStatus(UserActiveStatusEnum.OFFLINE.getStatus());
//        update.setLastOptTime(LocalDateTime.now());
//        userDao.updateById(update);
    }
    /**
     * 更新缓存数据，并把用户下线的消息推送给所有在线用户
     */
    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void saveRedisAndPush(UserOfflineEvent event) {
//        User user = event.getUser();
//        userCache.offline(user.getId(), user.getLastOptTime());
        //推送给所有在线用户，该用户下线
//        pushService.sendPushMsg(WebSocketAdapter.buildOfflineNotify(user));
    }
}
