package com.lon.uesrmanagerment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lon.uesrmanagerment.model.domain.Bill;
import com.lon.uesrmanagerment.model.request.UserUpdateRequest;

import javax.xml.crypto.Data;
import java.util.Date;


/**
* @author Kramir
* @description 针对表【bill】的数据库操作Service
* @createDate 2024-05-14 11:31:47
*/
public interface BillService extends IService<Bill> {
    boolean updateBill(Bill bill);
    long billRegister(String userAccount, String carName, Integer amount, Integer day, Integer amountPrice);
}
