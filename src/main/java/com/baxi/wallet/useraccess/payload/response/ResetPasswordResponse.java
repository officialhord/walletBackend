package com.baxi.wallet.useraccess.payload.response;


import lombok.Data;

@Data
public class ResetPasswordResponse {

    private boolean resetStatus;
    private String statusDescription;
}
