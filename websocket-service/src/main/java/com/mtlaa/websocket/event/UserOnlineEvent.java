package com.mtlaa.websocket.event;

import com.mtlaa.api.domain.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Create 2023/12/14 11:09
 * 用户上线的事件
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private final User user;
    public UserOnlineEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
