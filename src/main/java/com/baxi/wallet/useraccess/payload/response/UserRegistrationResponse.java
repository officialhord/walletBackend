package com.baxi.wallet.useraccess.payload.response;

import com.baxi.wallet.useraccess.model.WalletUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationResponse {

    private String status;
    private String statusDescription;
    private WalletUser toryUser;


}
