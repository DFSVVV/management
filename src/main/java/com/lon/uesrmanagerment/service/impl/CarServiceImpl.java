package com.lon.uesrmanagerment.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.exception.BusinessException;
import com.lon.uesrmanagerment.mapper.CarMapper;
import com.lon.uesrmanagerment.model.domain.Car;
import com.lon.uesrmanagerment.model.domain.User;
import com.lon.uesrmanagerment.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Kramir
* @description 针对表【car】的数据库操作Service实现
* @createDate 2024-05-14 11:43:04
*/
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car>
    implements CarService {
    @Resource
    private CarMapper carMapper;
    @Override
    public boolean updateCar(Car car) {
        // 构造更新条件
        UpdateWrapper<Car> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", car.getId());
        // 准备更新的用户实体
        Car car1 = new Car();
        BeanUtils.copyProperties(car, car1);
        int updateCount = carMapper.update(car1, updateWrapper);
        return updateCount > 0;
    }
    @Override
    public long carRegister(String carName, Integer price) {

        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("carName",carName);
        Long aLong = carMapper.selectCount(carQueryWrapper);
        if(aLong > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"汽车名重复");
        }
        Car car = new Car();
        car.setCarname(carName);
        car.setPrice(price);
        boolean save = this.save(car);
        if(!save){
            return -1;
        }
        return car.getId();
    }
}




