package com.baxi.wallet.transactions.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceivedFrom {

    @JsonProperty("account_name")
    private String transactionReference;

    @JsonProperty("account_number")
    private String transactionStatus;

    @JsonProperty("bank_name")
    private String virtualAccount;
}
