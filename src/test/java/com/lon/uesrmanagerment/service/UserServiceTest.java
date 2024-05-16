package com.lon.uesrmanagerment.service;
import java.util.Date;

import com.lon.uesrmanagerment.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户测试
 */
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUserAccount("123");
        user.setUserName("long");
        user.setAvatarUrl("https://profile-avatar.csdnimg.cn/default.jpg!1");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("173");
        user.setEmail("322");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        assertTrue(save);
    }

    @Test
    void userRegister() {
        String userAccount = "yuanshen100";
        String userPassword = "123456789qazw";
        String checkPassword = "123456789qazw";
        long l = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(0,l);
    }
}