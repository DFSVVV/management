package com.lon.uesrmanagerment.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserSearchRequest implements Serializable {
    private static final long serialVersionUID = 4747999128306998822L;
    private Integer id;
    private String userAccount;
    private String userName;
    private Integer gender;
    private String avatarUrl;
    private String phone;
    private String email;
    private Integer userRole;
    private Integer userStatus;
    private Date createTime;
}
