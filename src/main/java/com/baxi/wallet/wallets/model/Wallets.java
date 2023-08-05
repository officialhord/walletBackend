package com.baxi.wallet.wallets.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Wallets {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    long id;

    @Column(name = "active")
    private boolean active;

}
