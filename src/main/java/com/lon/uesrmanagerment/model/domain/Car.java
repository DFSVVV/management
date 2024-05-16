package com.lon.uesrmanagerment.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName car
 */
@TableName(value ="car")
@Data
public class Car implements Serializable {
    /**
     * 汽车编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 汽车名
     */
    private String carname;

    /**
     * 价格
     */
    private Integer price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}