package com.mtlaa.user.event;

import com.mtlaa.api.domain.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Create 2023/12/13 16:28
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {
    private final User user;

    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
