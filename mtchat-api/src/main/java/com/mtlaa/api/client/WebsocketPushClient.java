package com.mtlaa.api.client;

import com.mtlaa.api.domain.chat.vo.response.wsMsg.WSBaseResp;
import com.mtlaa.api.domain.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Create 2024/7/4 15:05
 */
@FeignClient(value = "websocket-service")
public interface WebsocketPushClient {
    @PostMapping("/send")
    void sendMsgByWebsocket(@RequestBody WSBaseResp<?> msg, @RequestParam("uids") List<Long> uids);

    @PostMapping("/loginSuccess")
    void loginSuccess(@RequestParam("code") Integer code,
                             @RequestBody() User user,
                             @RequestParam("token") String token);

    @PostMapping("/sendWaitAuthorizeMsg")
    void sendWaitAuthorizeMsg(@RequestParam("code") Integer code);
}
