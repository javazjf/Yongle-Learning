package com.coolsoft.yongle.user.basic.api;

import com.coolsoft.yongle.user.basic.api.configuration.UserServiceApiConfiguration;
import com.coolsoft.yongle.user.basic.api.fallback.UserApiFallback;
import com.coolsoft.yongle.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * C端用户接口
 *
 * @author zhangjianfa
 */
@FeignClient(
        value = "user-service",
        fallback = UserApiFallback.class,
        configuration = UserServiceApiConfiguration.class)
public interface IUserApi {

    @PostMapping(value = "/user/loginByAccountAndPassword")
    public User loginByAccountAndPassword(@RequestParam("account") String account,
                                          @RequestParam("password") String password);

    @PostMapping(value = "/user/loginByPhoneAndCode")
    public User loginByPhoneAndCode(@RequestParam("phone") String phone,
                                    @RequestParam("code") String code);

    @GetMapping(value = "/user/getUserInfo")
    public User getUserInfo(Integer userId);
}
