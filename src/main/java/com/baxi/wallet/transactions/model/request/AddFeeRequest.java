package com.baxi.wallet.transactions.model.request;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddFeeRequest {

    private String feeType;
    private String feeCategory;
    private BigDecimal feeAmount;
    private boolean percentage;
    private long businessId;
    private boolean defaultFee;
}
