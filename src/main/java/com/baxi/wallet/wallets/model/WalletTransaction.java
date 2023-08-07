package com.baxi.wallet.wallets.model;

import com.baxi.wallet.wallets.payload.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    long id;

    @ManyToOne
    @JoinColumn(
            name = "wallet_id"
    )
    private Wallets wallet;

    private TransactionType transactionType;

    private long toAccount;

    private long fromAccount;

    private BigDecimal transactionAmount;

    private LocalDateTime transactionDate;

    private boolean completed;

}
