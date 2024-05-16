package com.lon.uesrmanagerment.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class BillRegisterRequest implements Serializable {
    private static final long serialVersionUID = -6376084951292833914L;

    private String carName;

    /**
     * 租赁数量
     */
    private Integer amount;

    /**
     * 租赁天数
     */
    private Integer day;

}
