package com.coolsoft.yongle.user.dto;

import com.coolsoft.yongle.user.entity.User;

public class UserDto extends User {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
