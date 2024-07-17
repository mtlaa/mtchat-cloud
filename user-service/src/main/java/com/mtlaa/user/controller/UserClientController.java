package com.mtlaa.user.controller;

import com.mtlaa.api.domain.user.entity.User;
import com.mtlaa.api.domain.user.enums.RoleEnum;
import com.mtlaa.api.domain.user.enums.UserActiveStatusEnum;
import com.mtlaa.user.cache.UserCache;
import com.mtlaa.user.cache.UserInfoCache;
import com.mtlaa.user.dao.UserDao;
import com.mtlaa.user.service.IpService;
import com.mtlaa.user.service.RoleService;
import com.mtlaa.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create 2024/7/4 15:33
 */
@RestController
@RequestMapping()
public class UserClientController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserCache userCache;
    @Autowired
    private UserInfoCache userInfoCache;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private IpService ipService;

    @PutMapping("/update")
    public void updateUser(@RequestBody User user){
        userDao.updateById(user);
        switch (UserActiveStatusEnum.valueOf(String.valueOf(user.getActiveStatus()))){
            case ONLINE:
                userCache.online(user.getId(), user.getLastOptTime());
                break;
            case OFFLINE:
                userCache.offline(user.getId(), user.getLastOptTime());
                break;
        }
    }

    @GetMapping("/getByUid")
    public User getByUid(@RequestParam("uid") Long uid){
        return userInfoCache.get(uid);
    }

    @GetMapping("/getByOpenId")
    public User getByOpenId(@RequestParam("openId") String openId){
        return userDao.getByOpenId(openId);
    }

    @GetMapping("/hasPower")
    public Boolean hasPower(@RequestParam("uid") Long uid, @RequestParam("role") Long role){
        return roleService.hasPower(uid, RoleEnum.of(role));
    }

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userService.register(user);
    }


}
