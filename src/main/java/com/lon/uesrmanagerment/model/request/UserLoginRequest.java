package com.lon.uesrmanagerment.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -362576378204677181L;
    private String userAccount;
    private String userPassword;
}
