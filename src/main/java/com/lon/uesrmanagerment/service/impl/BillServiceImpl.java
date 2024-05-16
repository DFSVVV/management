package com.lon.uesrmanagerment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.exception.BusinessException;
import com.lon.uesrmanagerment.mapper.BillMapper;
import com.lon.uesrmanagerment.mapper.CarMapper;
import com.lon.uesrmanagerment.model.domain.Bill;
import com.lon.uesrmanagerment.model.domain.Car;
import com.lon.uesrmanagerment.model.domain.User;
import com.lon.uesrmanagerment.service.BillService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author Kramir
* @description 针对表【bill】的数据库操作Service实现
* @createDate 2024-05-14 11:31:47
*/
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill>
    implements BillService {
    @Resource
    private BillMapper billMapper;
    @Resource
    private CarMapper carMapper;
    @Override
    public boolean updateBill(Bill bill) {
        UpdateWrapper<Bill> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", bill.getId());
        // 准备更新的用户实体
        Bill bill1 = new Bill();
        BeanUtils.copyProperties(bill, bill1);
        int updateCount = billMapper.update(bill1, updateWrapper);
        return updateCount > 0;
    }

    @Override
    public long billRegister(String userAccount, String carName, Integer amount, Integer day, Integer amountPrice) {
        QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
        carQueryWrapper.eq("carName",carName);
        Car car = carMapper.selectOne(carQueryWrapper);
        if(car == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"汽车名不存在");
        }

        Bill bill = new Bill();
        bill.setUseraccount(userAccount);
        bill.setCarname(carName);
        bill.setAmount(amount);
        bill.setDay(day);
        if(amountPrice == null){
            amountPrice = day* amount * car.getPrice();
            if(amount >= 10){
                amountPrice -= 100;
            }
            if(day >= 10){
                amountPrice -= 100;
            }
        }
        bill.setAmountprice(amountPrice);
        boolean save = this.save(bill);
        if(!save){
            return -1;
        }
        return bill.getId();
    }
}




