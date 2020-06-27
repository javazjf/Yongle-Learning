package com.coolsoft.yongle.user.controller;

import com.coolsoft.yongle.user.entity.User;
import com.coolsoft.yongle.user.service.UserService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/user/loginByAccountAndPassword")
    public ResponseEntity<User> loginByAccountAndPassword(@RequestParam("account") String account,
                                                          @RequestParam("password") String password) {
        User user = userService.loginByAccountAndPassword(account, password);
        if (user == null) {
            Map<String, String> errorMap = Maps.newHashMap();
            errorMap.put("message", "登录失败");
            return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }
}
