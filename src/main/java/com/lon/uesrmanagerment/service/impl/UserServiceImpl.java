package com.lon.uesrmanagerment.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.contant.UserContant;
import com.lon.uesrmanagerment.exception.BusinessException;
import com.lon.uesrmanagerment.model.domain.User;
import com.lon.uesrmanagerment.model.request.UserUpdateRequest;
import com.lon.uesrmanagerment.service.UserService;
import com.lon.uesrmanagerment.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Kramir
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-04-26 20:00:04
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    final int userAccountLimit = 4;
    final int userPasswordLimit = 8;
    final String SALT = "lon";
    @Resource
    private UserMapper userMapper;
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //校验格式
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        if(userAccount.length() < userAccountLimit){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号小于4位");
        }
        if(userPassword.length() < userPasswordLimit|| checkPassword.length() < userPasswordLimit){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码小于8位");
        }
        //账户不能包含特殊字符
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$";
        boolean matches = Pattern.matches(pattern, userPassword);
        if(!matches){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码包含特殊字符或没有包含字母与数字");
        }
        //密码与验证密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一样");
        }
        //账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(userQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名重复");
        }
        //密码加密
        String s = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(s);
        boolean save = this.save(user);
        if(!save){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        if(userAccount.length() < userAccountLimit){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号小于4位");
        }
        if(userPassword.length() < userPasswordLimit){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码小于8位");
        }
        //账户不能包含特殊字符
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$";
        boolean matches = Pattern.matches(pattern, userPassword);
        if(!matches){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码包含特殊字符或没有包含字母与数字");
        }
        //加密
        String s = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount",userAccount);
        userQueryWrapper.eq("userPassword",s);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null) {
            log.info("user login failed");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        //用户脱敏
        User safeUser = safeUser(user);
        //记录登录态
        request.getSession().setAttribute(UserContant.USER_LOGIN_STATE,user);

        return safeUser;
    }

    @Override
    public User safeUser(User user) {
        if(user == null){
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setUserName(user.getUserName());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        return safeUser;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserContant.USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public boolean updateUser(UserUpdateRequest userUpdateRequest) {
        // 若更新请求中包含用户名，则进行长度校验
        if (StringUtils.isNotBlank(userUpdateRequest.getUserAccount()) && userUpdateRequest.getUserAccount().length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度过短，至少需要4位。");
        }
        // 账号特殊字符校验
        if (StringUtils.isNotBlank(userUpdateRequest.getUserAccount())) {
            String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Matcher matcher = Pattern.compile(validPattern).matcher(userUpdateRequest.getUserAccount());
            if (matcher.find()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含非法字符。");
            }
        }
        // 构造更新条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userUpdateRequest.getId());
        // 准备更新的用户实体
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        // 如果提供了密码，则进行加密处理
        // 执行更新操作
        int updateCount = userMapper.update(user, updateWrapper);
        return updateCount > 0;
    }

}




