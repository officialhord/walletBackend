package com.baxi.wallet.transactions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionNotification {


    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("virtual_account")
    private String virtualAccount;

    @JsonProperty("initiated_date")
    private String initiatedDate;

    @JsonProperty("current_status_date")
    private String currentDate;
    @JsonProperty("amount_received")
    private BigDecimal amount;

    @JsonProperty("transaction_fee")
    private BigDecimal fee;
    @JsonProperty("received_from")
    private ReceivedFrom receivedFrom;

}
