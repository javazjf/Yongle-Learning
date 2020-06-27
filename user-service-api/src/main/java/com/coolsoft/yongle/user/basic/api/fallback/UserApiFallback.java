package com.coolsoft.yongle.user.basic.api.fallback;

import com.coolsoft.yongle.user.basic.api.IUserApi;
import com.coolsoft.yongle.user.entity.User;

public class UserApiFallback implements IUserApi {


    @Override
    public User loginByAccountAndPassword(String account, String password) {
        return null;
    }

    @Override
    public User loginByPhoneAndCode(String phone, String code) {
        return null;
    }
}
