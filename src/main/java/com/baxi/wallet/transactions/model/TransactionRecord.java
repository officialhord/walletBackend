package com.baxi.wallet.transactions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String transactionReference;
//    private String eventReference;
    private long quantity;
    private String soldTicketIds;
    private long customerId;
    private BigDecimal transactionAmount;
    private BigDecimal fee;
    private BigDecimal remitAmount;
    private boolean completed;
    private String sessionRef;
    private long businessId;
    private String currency;
    private LocalDateTime transactionDate;
    private LocalDateTime completionDate;

    private boolean cancelled;

}
