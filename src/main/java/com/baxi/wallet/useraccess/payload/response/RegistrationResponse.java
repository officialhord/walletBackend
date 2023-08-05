package com.baxi.wallet.useraccess.payload.response;


import com.app.tory.useraccess.model.ToryUser;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegistrationResponse {


    private String status;
    private String statusDescription;
    private ToryUser user;
}
