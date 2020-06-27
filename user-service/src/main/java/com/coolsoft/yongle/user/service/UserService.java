package com.coolsoft.yongle.user.service;

import com.coolsoft.yongle.user.entity.User;
import com.coolsoft.yongle.user.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User loginByAccountAndPassword(String account, String password) {
        User user = userDao.findUserByAccountAndPassword(account, password);
        return user;
    }
}
