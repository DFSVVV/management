package com.lon.uesrmanagerment.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName bill
 */
@TableName(value ="bill")
@Data
public class Bill implements Serializable {
    /**
     * 订单编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 购买账户
     */
    private String useraccount;

    /**
     * 车名
     */
    private String carname;

    /**
     * 租赁数量
     */
    private Integer amount;

    /**
     * 租赁天数
     */
    private Integer day;

    /**
     * 总价
     */
    private Integer amountprice;
    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}