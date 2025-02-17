package com.mtlaa.user.service;





import com.mtlaa.api.domain.user.vo.request.UserEmojiReq;
import com.mtlaa.api.domain.user.vo.response.UserEmojiResp;

import java.util.List;

/**
 * Create 2024/1/6 20:39
 */
public interface UserEmojiService {
    List<UserEmojiResp> listByUid(Long uid);

    Long insert(Long uid, UserEmojiReq req);

    void delete(Long uid, long id);
}
