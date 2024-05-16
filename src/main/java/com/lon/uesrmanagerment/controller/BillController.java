package com.lon.uesrmanagerment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lon.uesrmanagerment.common.BaseResponse;
import com.lon.uesrmanagerment.common.ErrorCode;
import com.lon.uesrmanagerment.common.ResultUtils;
import com.lon.uesrmanagerment.contant.UserContant;
import com.lon.uesrmanagerment.exception.BusinessException;
import com.lon.uesrmanagerment.model.domain.Bill;
import com.lon.uesrmanagerment.model.domain.Car;
import com.lon.uesrmanagerment.model.domain.User;
import com.lon.uesrmanagerment.model.request.BillRegisterRequest;
import com.lon.uesrmanagerment.model.request.CarRegisterRequest;
import com.lon.uesrmanagerment.model.request.UserSearchRequest;
import com.lon.uesrmanagerment.service.BillService;
import com.lon.uesrmanagerment.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private CarService carService;
    @GetMapping("/search")
    public BaseResponse<List<Bill>> searchBill( Bill bill, HttpServletRequest request){
//        if(!isAdmin(request)){
//            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
//        }
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(bill.getCarname())){
            queryWrapper.like("carname",bill.getCarname());
        }
        if (StringUtils.isNoneBlank(bill.getUseraccount())){
            queryWrapper.like("useraccount",bill.getUseraccount());
        }
        if (bill.getAmount()!=null){
            queryWrapper.like("amount",bill.getAmount());
        }
        if (bill.getDay()!=null){
            queryWrapper.like("day",bill.getDay());
        }
        if (bill.getAmountprice()!=null){
            queryWrapper.like("amountprice",bill.getAmountprice());
        }
        if(bill.getCreateTime() !=null ){
            queryWrapper.like("createtime",bill.getCreateTime());
        }
        List<Bill> list = billService.list(queryWrapper);
        return ResultUtils.success(list);
    }
    @GetMapping("/searchUser")
    public BaseResponse<List<Bill>> searchBillUser( Bill bill, HttpServletRequest request){
//        if(!isAdmin(request)){
//            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
//        }
        Object attribute = request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user = (User) attribute;
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNoneBlank(bill.getUseraccount())){
            queryWrapper.like("useraccount",user.getUserAccount());
        }
        if (StringUtils.isNoneBlank(bill.getCarname())){
            queryWrapper.like("carname",bill.getCarname());
        }
        if (bill.getAmount()!=null){
            queryWrapper.like("amount",bill.getAmount());
        }
        if (bill.getDay()!=null){
            queryWrapper.like("day",bill.getDay());
        }
        if (bill.getAmountprice()!=null){
            queryWrapper.like("amountprice",bill.getAmountprice());
        }
        if(bill.getCreateTime() !=null ){
            queryWrapper.like("createtime",bill.getCreateTime());
        }
        List<Bill> list = billService.list(queryWrapper);
        return ResultUtils.success(list);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBill(@RequestBody Bill bill, HttpServletRequest servletRequest){
        if(!isAdmin(servletRequest)){
            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
        }
        if(bill.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"车辆不存在");
        }
        boolean b = billService.removeById(bill.getId());
        return ResultUtils.success(b);
    }
    @PostMapping("/register")
    public BaseResponse<Long> billRegister(@RequestBody BillRegisterRequest billRegisterRequest,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user = (User)attribute;
        if(billRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        String useraccount = user.getUserAccount();
        String carname = billRegisterRequest.getCarName();
        Integer amount = billRegisterRequest.getAmount();
        Integer day = billRegisterRequest.getDay();
        Integer amountprice = null;

        if(StringUtils.isAnyBlank(useraccount,carname)||amount == null||day ==null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"输入数据为空");
        }
        long l = billService.billRegister(useraccount,carname,amount,day,amountprice);
        return ResultUtils.success(l);
    }
    @PostMapping("/update")
    public BaseResponse<Boolean> updateCar(@RequestBody Bill bill, HttpServletRequest request){
//         权限校验逻辑，确保是管理员操作
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        boolean result=billService.updateBill(bill);
        return ResultUtils.success(result);
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
