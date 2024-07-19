package com.axr.starrybackend.model.request.User;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private String userAccount;
    private String password;
    private String confirmPassword;
}
