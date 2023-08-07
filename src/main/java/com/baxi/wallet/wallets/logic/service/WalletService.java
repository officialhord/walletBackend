package com.baxi.wallet.wallets.logic.service;


import com.baxi.wallet.wallets.payload.FundsTransferRequest;
import com.baxi.wallet.wallets.payload.WalletResponse;

import java.math.BigDecimal;

public interface WalletService {

    public WalletResponse handleGenerateWalletRequest(long val);

    public WalletResponse handleNameEnquiryRequest(String val);

    public WalletResponse handleFundsTransferRequest(FundsTransferRequest fundsTransferRequest);

    public BigDecimal handleFetchWalletBalanceRequest(long val);

    public WalletResponse handleFetchTransactionsRequest(long val);
}
