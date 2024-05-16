package com.lon.uesrmanagerment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lon.uesrmanagerment.common.BaseResponse;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.common.ResultUtils;
import com.lon.uesrmanagerment.contant.UserContant;
import com.lon.uesrmanagerment.exception.BusinessException;
import com.lon.uesrmanagerment.model.domain.User;
import com.lon.uesrmanagerment.model.request.UserLoginRequest;
import com.lon.uesrmanagerment.model.request.UserRegisterRequest;
import com.lon.uesrmanagerment.model.request.UserSearchRequest;
import com.lon.uesrmanagerment.model.request.UserUpdateRequest;
import com.lon.uesrmanagerment.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user = (User)attribute;
        if(user == null){
            throw new BusinessException(ErrorCode.NO_LOGIN,"请先登录");
        }
        Long id = user.getId();
        User safeUser = userService.safeUser(userService.getById(id));
        return ResultUtils.success(safeUser);
    }
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        long l = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(l);
    }
    /**
     * 创建用户
     *
     * @param
     * @param request
     * @return
     */
//    @PostMapping("/add")
//    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
//        if (!isAdmin(request)) {
//            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
//        }
//        if (userAddRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
//        }
//        User user = new User();
//        BeanUtils.copyProperties(userAddRequest, user);
//        boolean result = userService.save(user);
//        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
//        return ResultUtils.success(user.getId());
//    }
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request){
//         权限校验逻辑，确保是管理员操作
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean result=userService.updateUser(userUpdateRequest);
        return ResultUtils.success(result);
    }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        if(userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        User user = userService.userLogin(userAccount, userPassword, httpServletRequest);
        return ResultUtils.success(user);
    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest httpServletRequest){
        if(httpServletRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        int i = userService.userLogout(httpServletRequest);
        return ResultUtils.success(i);
    }
    @GetMapping("/search")
        public BaseResponse<List<User>> searchUser( UserSearchRequest userSearchRequest, HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(userSearchRequest.getUserName())){
            queryWrapper.like("username",userSearchRequest.getUserName());
        }
        if (StringUtils.isNoneBlank(userSearchRequest.getUserAccount())){
            queryWrapper.like("useraccount",userSearchRequest.getUserAccount());
        }
        if (StringUtils.isNoneBlank(userSearchRequest.getAvatarUrl())){
            queryWrapper.like("avatarurl",userSearchRequest.getAvatarUrl());
        }
        if (userSearchRequest.getGender()!=null){
            queryWrapper.like("gender",userSearchRequest.getGender());
        }
        if (StringUtils.isNoneBlank(userSearchRequest.getPhone())){
            queryWrapper.like("phone",userSearchRequest.getPhone());
        }
        if (userSearchRequest.getUserStatus() != null){
            queryWrapper.like("userstatus",userSearchRequest.getUserStatus());
        }
        if (userSearchRequest.getUserRole() != null){
            queryWrapper.like("userrole",userSearchRequest.getUserRole());
        }
        if (userSearchRequest.getCreateTime() !=null){
            queryWrapper.like("creattime",userSearchRequest.getCreateTime());
        }

        List<User> list = userService.list(queryWrapper);
        List<User> collect = list.stream().map(user -> {
            User safeUser = userService.safeUser(user);
            return safeUser;
        }).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserUpdateRequest userUpdateRequest,HttpServletRequest servletRequest){
        if(!isAdmin(servletRequest)){
            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
        }
        if(userUpdateRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        boolean b = userService.removeById(userUpdateRequest.getId());
        return ResultUtils.success(b);
    }

    private boolean isAdmin(HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user = (User)attribute;
        if(user == null|| user.getUserRole() != UserContant.ADMIN_ROLE){
            return false;
        }
        return true;
    }
}
