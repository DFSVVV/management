package com.lon.uesrmanagerment.model.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -360697119117378816L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
