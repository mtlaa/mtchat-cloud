package com.mtlaa.mtchat.constant;

/**
 * @author zhongzb create on 2021/06/10
 */
public interface MQConstant {

    /**
     * 消息发送mq。（为了异步，先入库消息返回发送成功，然后消费者异步组装消息、更新会话表等。
     *              这里发送使用分布式事务框架保证最终一致性）
     */
    String SEND_MSG_TOPIC = "chat_send_msg";
    String SEND_MSG_GROUP = "chat_send_msg_group";

    /**
     * push用户（消息组装后，需要通过websocket服务（消费者）推送给用户，这里是为了异步、削峰、广播）
     */
    String PUSH_TOPIC = "websocket_push";
    String PUSH_GROUP = "websocket_push_group";

    /**
     * 通知用户，主要推送给用户的通知（拉黑、消息撤回、上下线等）
     */
    String NOTIFY_TOPIC = "user_login_send_msg";
    String NOTIFY_GROUP = "user_login_send_msg_group";
}
