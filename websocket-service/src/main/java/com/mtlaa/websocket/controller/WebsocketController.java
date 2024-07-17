package com.mtlaa.websocket.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mtlaa.api.domain.chat.vo.response.wsMsg.WSBaseResp;
import com.mtlaa.api.domain.user.entity.User;
import com.mtlaa.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create 2024/7/4 15:24
 */
@RestController
@RequestMapping("")
public class WebsocketController {
    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/send")
    public void sendMsgByWebsocket(@RequestBody WSBaseResp<?> msg, @RequestParam("uids") List<Long> uids){
        if (CollectionUtil.isEmpty(uids)){
            webSocketService.sendMsgToAll(msg);
        } else {
            uids.forEach(uid -> webSocketService.sendMsgToUid(msg, uid));
        }
    }

    @PostMapping("/loginSuccess")
    public void loginSuccess(@RequestParam("code") Integer code,
                             @RequestBody() User user,
                             @RequestParam("token") String token){
        webSocketService.loginSuccess(code, user, token);
    }

    @PostMapping("/sendWaitAuthorizeMsg")
    public void sendWaitAuthorizeMsg(@RequestParam("code") Integer code){
        webSocketService.sendWaitAuthorizeMsg(code);
    }

}
