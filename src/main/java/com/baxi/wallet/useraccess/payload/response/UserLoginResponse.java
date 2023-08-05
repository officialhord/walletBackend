package com.baxi.wallet.useraccess.payload.response;


import lombok.Data;

@Data
public class UserLoginResponse {

    private String username;
    private String email;
    private long id;
    private boolean locked;
    private boolean enabled;
    private String status;
    private String statusDescription;
    private String authentication;

}
