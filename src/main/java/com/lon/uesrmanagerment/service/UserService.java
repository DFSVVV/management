package com.lon.uesrmanagerment.service;

import com.lon.uesrmanagerment.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.uesrmanagerment.model.request.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author Kramir
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-04-26 20:00:04
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账户
     * @param userPassword 用户密码
     * @param checkPassword 验证码
     * @return long
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     * @param userAccount 账户
     * @param userPassword 用户密码
     * @return User
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    User safeUser(User user);

    int userLogout(HttpServletRequest request);

    boolean updateUser(UserUpdateRequest userUpdateRequest);
}
