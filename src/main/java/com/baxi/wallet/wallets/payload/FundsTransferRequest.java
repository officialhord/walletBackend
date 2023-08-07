package com.baxi.wallet.wallets.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundsTransferRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
}
