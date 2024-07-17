package com.mtlaa.login.controller;

import com.mtlaa.login.service.LoginService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create 2024/7/4 20:19
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;
    /**
     * 请求登录二维码
     */
    @GetMapping("/getQrCode")
    public WxMpQrCodeTicket getQrCode(@RequestParam("code") Integer code,
                                      @RequestParam("expireSeconds") Integer expireSeconds) throws WxErrorException {
        return wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, expireSeconds);
    }

    @GetMapping("/getValidUid")
    public Long getValidUid(@RequestParam("token") String token){
        return loginService.getValidUid(token);
    }
}
