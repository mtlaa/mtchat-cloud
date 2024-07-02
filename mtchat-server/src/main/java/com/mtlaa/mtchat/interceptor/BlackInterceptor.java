package com.mtlaa.mtchat.interceptor;

import cn.hutool.core.collection.CollectionUtil;

import com.mtlaa.mtchat.cache.user.BlackCache;
import com.mtlaa.mtchat.domain.common.entity.RequestInfo;
import com.mtlaa.mtchat.domain.user.enums.BlackTypeEnum;
import com.mtlaa.mtchat.exception.HttpErrorEnum;
import com.mtlaa.mtchat.cache.user.UserCache;
import com.mtlaa.mtchat.utils.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 黑名单拦截
 */
@Order(2)
@Slf4j
@Component
public class BlackInterceptor implements HandlerInterceptor {

    @Autowired
    private BlackCache blackCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<Integer, Set<String>> blackMap = blackCache.getBlackMap();
        RequestInfo requestInfo = RequestHolder.get();
        if (inBlackList(requestInfo.getUid(), blackMap.get(BlackTypeEnum.UID.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        if (inBlackList(requestInfo.getIp(), blackMap.get(BlackTypeEnum.IP.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        return true;
    }

    private boolean inBlackList(Object target, Set<String> blackSet) {
        if (Objects.isNull(target) || CollectionUtil.isEmpty(blackSet)) {
            return false;
        }
        return blackSet.contains(target.toString());
    }

}