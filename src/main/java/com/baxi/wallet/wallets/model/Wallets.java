package com.baxi.wallet.wallets.model;

import com.baxi.wallet.useraccess.model.WalletUser;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Wallets {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    long id;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(
            name = "app_user_id"
    )
    private WalletUser appUser;

    private BigDecimal walletBalance;

    private BigDecimal lienBalance;

    private long transactionPin;

}
