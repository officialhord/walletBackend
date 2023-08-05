package com.baxi.wallet.transactions.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {

    private BigDecimal transactionAmount;
    private BigDecimal transactionFee;
    private BigDecimal totalAmount;
    private String transactionReference;

//    private ConversionResponse conversionResponse;
}
