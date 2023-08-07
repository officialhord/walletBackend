package com.baxi.wallet.wallets.payload;

import com.baxi.wallet.wallets.payload.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletTransactions {


    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;

}
