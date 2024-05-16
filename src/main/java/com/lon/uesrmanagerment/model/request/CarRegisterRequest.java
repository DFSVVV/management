package com.lon.uesrmanagerment.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class CarRegisterRequest implements Serializable {
    private static final long serialVersionUID = -5500033605424593471L;
    private String carName;
    private Integer price;
}
