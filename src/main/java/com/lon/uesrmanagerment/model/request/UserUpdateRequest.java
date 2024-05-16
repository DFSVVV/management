package com.lon.uesrmanagerment.model.request;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = 5120710479039036360L;
    private Integer id;
    private String userAccount;
    private String userName;
    private Integer gender;
    private String avatarUrl;
    private String phone;
    private String email;
    private Integer userRole;
    private Integer userStatus;
    private Integer pageSize;
    private Integer current;
}
