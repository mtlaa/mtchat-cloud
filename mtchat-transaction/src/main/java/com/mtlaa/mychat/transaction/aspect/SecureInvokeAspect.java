package com.mtlaa.mychat.transaction.aspect;

import cn.hutool.core.date.DateUtil;
import com.mtlaa.mtchat.utils.redis.JsonUtils;
import com.mtlaa.mychat.transaction.annotation.SecureInvoke;
import com.mtlaa.mychat.transaction.domain.dto.SecureInvokeDTO;
import com.mtlaa.mychat.transaction.domain.entity.SecureInvokeRecord;
import com.mtlaa.mychat.transaction.service.SecureInvokeHolder;
import com.mtlaa.mychat.transaction.service.SecureInvokeService;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 安全执行切面
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-04-20
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)//确保最先执行
@Component
public class SecureInvokeAspect {
    @Autowired
    private SecureInvokeService secureInvokeService;

    @Around("@annotation(secureInvoke)")
    public Object around(ProceedingJoinPoint joinPoint, SecureInvoke secureInvoke) throws Throwable {
        boolean async = secureInvoke.async();
        boolean inTransaction = TransactionSynchronizationManager.isActualTransactionActive();
        // 非事务状态，直接执行，不做任何保证。（即当前方法不是第一次执行，是后续重试，不需要再把方法执行记录入库）
        if (SecureInvokeHolder.isInvoking() || !inTransaction) {
            return joinPoint.proceed();
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        List<String> parameters = Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList());
        // 构造方法快照
        SecureInvokeDTO dto = SecureInvokeDTO.builder()
                .args(JsonUtils.toStr(joinPoint.getArgs()))
                .className(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(JsonUtils.toStr(parameters))
                .build();
        // 构造本地消息表记录
        SecureInvokeRecord record = SecureInvokeRecord.builder()
                .secureInvokeDTO(dto)
                .maxRetryTimes(secureInvoke.maxRetryTimes())
                .nextRetryTime(DateUtil.offsetMinute(new Date(), (int) SecureInvokeService.RETRY_INTERVAL_MINUTES))
                .build();
        secureInvokeService.invoke(record, async);
        return null;
    }
}
