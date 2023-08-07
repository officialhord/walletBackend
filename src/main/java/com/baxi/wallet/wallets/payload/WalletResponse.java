package com.baxi.wallet.wallets.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletResponse {

    @JsonProperty("exists")
    private boolean exists;

    @JsonProperty("walletId")
    private long walletId;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountName")
    private String accountName;

    @JsonProperty("walletBalance")
    private BigDecimal walletBalance;

    @JsonProperty("lienBalance")
    private BigDecimal lienBalance;

    @JsonProperty("message")
    private String message;
}
