package com.baxi.wallet.useraccess.payload.request;


import lombok.Data;

@Data
public class UpdateUserProfileRequest {

    private String phoneNumber;
    private String address;
    private String country;
    private String authorization;
}
