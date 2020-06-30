package com.coolsoft.yongle.user.repository;

import com.coolsoft.yongle.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findUserByAccountAndPassword(String account, String password);

    User findUserById(Integer id);
}
