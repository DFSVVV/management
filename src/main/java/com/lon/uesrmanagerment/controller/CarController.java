package com.lon.uesrmanagerment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lon.uesrmanagerment.common.BaseResponse;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.common.ResultUtils;
import com.lon.uesrmanagerment.contant.UserContant;
import com.lon.uesrmanagerment.exception.BusinessException;
import com.lon.uesrmanagerment.model.domain.Car;
import com.lon.uesrmanagerment.model.domain.User;
import com.lon.uesrmanagerment.model.request.CarRegisterRequest;
import com.lon.uesrmanagerment.model.request.UserRegisterRequest;
import com.lon.uesrmanagerment.model.request.UserUpdateRequest;
import com.lon.uesrmanagerment.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping("/car")
public class CarController {
    @Resource
    private CarService carService;
    @GetMapping("/search")
    public BaseResponse<List<Car>> searchCar(Car car, HttpServletRequest request){
//        if(!isAdmin(request)){
//            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
//        }
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(car.getCarname())){
            queryWrapper.like("carname",car.getCarname());
        }
        if (car.getPrice() != null){
            queryWrapper.like("carname",car.getPrice());
        }
        List<Car> list = carService.list(queryWrapper);
        return ResultUtils.success(list);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCar(@RequestBody Car car, HttpServletRequest servletRequest){
        if(!isAdmin(servletRequest)){
            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
        }
        if(car.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"车辆不存在");
        }
        boolean b = carService.removeById(car.getId());
        return ResultUtils.success(b);
    }
    @PostMapping("/update")
    public BaseResponse<Boolean> updateCar(@RequestBody Car car, HttpServletRequest request){
//         权限校验逻辑，确保是管理员操作
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean result=carService.updateCar(car);
        return ResultUtils.success(result);
    }
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody CarRegisterRequest carRegisterRequest){
        if(carRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        String carName = carRegisterRequest.getCarName();
        Integer price = carRegisterRequest.getPrice();
        if(StringUtils.isAnyBlank(carName)||price == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        long l = carService.carRegister(carName,price);
        return ResultUtils.success(l);
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
