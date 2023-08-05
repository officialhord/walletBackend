package com.baxi.wallet.useraccess.payload.request;


import lombok.Data;

@Data
public class KYCRequest {

    private String userEmail;
    private String idCardUrl;
    private String idValue;
    private String proofOfAddressUrl;
    private String userPhotoUrl;
    private String authorization;

}
