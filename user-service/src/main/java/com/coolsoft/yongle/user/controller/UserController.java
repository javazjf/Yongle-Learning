package com.coolsoft.yongle.user.controller;

import com.alibaba.fastjson.JSON;
import com.coolsoft.yongle.user.dto.UserDto;
import com.coolsoft.yongle.user.entity.User;
import com.coolsoft.yongle.user.service.UserService;
import com.coolsoft.yongle.user.token.TokenUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping(value = "/user/loginByAccountAndPassword")
    public ResponseEntity<UserDto> loginByAccountAndPassword(@RequestParam("account") String account,
                                                             @RequestParam("password") String password) {
        User user = userService.loginByAccountAndPassword(account, password);
        if (user == null) {
            Map<String, String> errorMap = Maps.newHashMap();
            errorMap.put("message", "登录失败");
            return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
        } else {
            //生成Token
            String token = TokenUtil.genToken(redisTemplate, user);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto, "password");
            userDto.setToken(token);
            return new ResponseEntity(userDto, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/user/getUserInfo")
    public ResponseEntity<UserDto> getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userJson = request.getHeader("user");
        if (StringUtils.isBlank(userJson)) {
            Map<String, String> errorMap = Maps.newHashMap();
            errorMap.put("message", "用户不存在");
            return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = JSON.parseObject(userJson, UserDto.class);
        if (userDto == null) {
            Map<String, String> errorMap = Maps.newHashMap();
            errorMap.put("message", "格式错误");
            return new ResponseEntity(errorMap, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(userDto, HttpStatus.OK);
    }
}
