package com.mtlaa.api.client;

import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create 2024/7/4 20:18
 */
@FeignClient(value = "login-service")
public interface LoginClient {
    @GetMapping("/getQrCode")
    WxMpQrCodeTicket getQrCode(@RequestParam("code") Integer code,
                               @RequestParam("expireSeconds") Integer expireSeconds);

    @GetMapping("/getValidUid")
    Long getValidUid(@RequestParam("token") String token)
}
