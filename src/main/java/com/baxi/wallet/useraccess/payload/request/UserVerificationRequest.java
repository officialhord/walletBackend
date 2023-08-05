package com.baxi.wallet.useraccess.payload.request;

import lombok.Data;

@Data
public class UserVerificationRequest {

    private String email;
    private String token;
}
