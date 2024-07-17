package com.mtlaa.user.event;


import com.mtlaa.api.domain.user.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Create 2023/12/22 20:00
 */
@Getter
public class UserBlackEvent extends ApplicationEvent {
    private final User user;
    public UserBlackEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
