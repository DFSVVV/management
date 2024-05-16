package com.lon.uesrmanagerment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.uesrmanagerment.model.domain.Car;
import com.lon.uesrmanagerment.model.request.UserUpdateRequest;


/**
* @author Kramir
* @description 针对表【car】的数据库操作Service
* @createDate 2024-05-14 11:43:04
*/
public interface CarService extends IService<Car> {
    boolean updateCar(Car car);

    long carRegister(String carName,Integer price);
}
