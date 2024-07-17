package com.mtlaa.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mtlaa.api.domain.common.enums.NormalOrNoEnum;
import com.mtlaa.api.domain.user.entity.UserEmoji;
import com.mtlaa.user.mapper.UserEmojiMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create 2024/1/6 20:45
 */
@Service
public class UserEmojiDao extends ServiceImpl<UserEmojiMapper, UserEmoji> {
    public List<UserEmoji> listByUid(Long uid) {
        return lambdaQuery().eq(UserEmoji::getUid, uid)
                .eq(UserEmoji::getDeleteStatus, NormalOrNoEnum.NORMAL.getStatus())
                .list();
    }

    public int countByUid(Long uid) {
        return lambdaQuery().eq(UserEmoji::getUid, uid)
                .eq(UserEmoji::getDeleteStatus, NormalOrNoEnum.NORMAL.getStatus())
                .count();
    }

    public UserEmoji getByUidAndUrl(Long uid, String expressionUrl) {
        return lambdaQuery().eq(UserEmoji::getUid, uid)
                .eq(UserEmoji::getExpressionUrl, expressionUrl)
                .one();
    }
}
