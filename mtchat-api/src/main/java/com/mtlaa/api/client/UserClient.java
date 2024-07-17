package com.mtlaa.api.client;

import com.mtlaa.api.domain.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Create 2024/7/4 15:32
 */
@FeignClient(value = "user-service")
public interface UserClient {
    @PutMapping("/update")
    void updateUser(@RequestBody User user);

    @GetMapping("/getByUid")
    User getByUid(@RequestParam("uid") Long uid);

    @GetMapping("/getByOpenId")
    User getByOpenId(@RequestParam("openId") String openId);

    @GetMapping("/hasPower")
    Boolean hasPower(@RequestParam("uid") Long uid, @RequestParam("role") Long role);

    @PostMapping("/register")
    void register(@RequestBody User user);
}
