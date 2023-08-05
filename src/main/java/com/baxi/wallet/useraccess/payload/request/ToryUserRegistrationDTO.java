package com.baxi.wallet.useraccess.payload.request;

import lombok.Data;

@Data
public class ToryUserRegistrationDTO {

    private String password, email, country, phoneNumber;

}
