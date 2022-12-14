package com.johnny.test;

import com.johnny.dao.UserDao;
import com.johnny.dao.impl.UserDaoImpl;
import com.johnny.pojo.User;
import org.junit.Test;

public class UserDaoTest {

    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {

        if (userDao.queryUserByUsername("admin1234") == null ){
            System.out.println("用戶名可用！");
        } else {
            System.out.println("用戶名已存在！");
        }
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        if ( userDao.queryUserByUsernameAndPassword("admin","admin1234") == null) {
            System.out.println("用戶名或密碼錯誤，登入失敗");
        } else {
            System.out.println("登入成功");
        }
    }

    @Test
    public void saveUser() {
        System.out.println( userDao.saveUser(new User(null,"wzg168", "123456", "wzg168@qq.com")) );
    }
}