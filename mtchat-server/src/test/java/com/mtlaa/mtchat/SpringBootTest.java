package com.mtlaa.mtchat;

import com.mtlaa.mtchat.annotation.RateLimiter;
import com.mtlaa.mtchat.config.RedisConfig;
import com.mtlaa.mtchat.config.ThreadPoolConfig;
import com.mtlaa.mtchat.domain.user.enums.IdempotentEnum;
import com.mtlaa.mtchat.user.service.UserBackpackService;
import com.mtlaa.mtchat.utils.LockService;
import com.mtlaa.mtchat.utils.SpELUtil;
import com.mtlaa.mtchat.utils.redis.RedisUtils;
import com.mtlaa.mychat.transaction.annotation.SecureInvoke;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@org.springframework.boot.test.context.SpringBootTest
@Slf4j
public class SpringBootTest {
    @Autowired
    private UserBackpackService userBackpackService;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() throws Throwable {

    }

}

