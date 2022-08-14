package com.johnny.service.impl;

import com.johnny.dao.UserDao;
import com.johnny.dao.impl.UserDaoImpl;
import com.johnny.pojo.User;
import com.johnny.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        /**
         * 此登入只需要查詢資料庫即可
         */
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {

        if (userDao.queryUserByUsername(username) == null) {
           // 等於null,說明沒查到，沒查到表示可用
           return false;
        }

        return true;

    }
}
