package com.mtlaa.user.event.listener;



import com.mtlaa.api.client.WebsocketPushClient;
import com.mtlaa.api.domain.chat.vo.response.wsMsg.WSBaseResp;
import com.mtlaa.api.domain.user.entity.User;
import com.mtlaa.api.domain.websocket.enums.WebSocketResponseTypeEnum;
import com.mtlaa.user.cache.UserCache;
import com.mtlaa.user.cache.UserInfoCache;
import com.mtlaa.user.dao.UserDao;
import com.mtlaa.user.event.UserBlackEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Create 2023/12/22 20:02
 */
@Component
public class UserBlackListener {
    @Autowired
    private WebsocketPushClient websocketPushClient;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCache userCache;
    @Autowired
    private UserInfoCache userInfoCache;

    /**
     * 拉黑人并不频繁，因此不经过mq，直接推送给所有在线用户
     */
    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendMsg(UserBlackEvent userBlackEvent){
        User user = userBlackEvent.getUser();
        websocketPushClient.sendMsgByWebsocket(
                new WSBaseResp<>(WebSocketResponseTypeEnum.BLACK.getType(), user.getId()), null);
    }

    /**
     * 拉黑后，把用户设为不可用
     */
    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void updateUserStatus(UserBlackEvent userBlackEvent){
        Long uid = userBlackEvent.getUser().getId();
        userDao.invalidUser(uid);
        userInfoCache.delete(uid);
        userCache.refreshUserModifyTime(uid);
    }

//    /**
//     * 当前新增了一个被拉黑的用户，所以删除旧的缓存
//     */
//    @Async
//    @EventListener(classes = UserBlackEvent.class)
//    public void deleteCache(UserBlackEvent userBlackEvent){
//        userCache.evictBlackMap();
//    }
}
