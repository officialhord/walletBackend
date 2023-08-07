package com.baxi.wallet.wallets.logic.service;


import com.baxi.wallet.wallets.payload.WalletResponse;

import java.math.BigDecimal;

public interface WalletService {

    public WalletResponse handleGenerateWalletRequest(long val);

    public WalletResponse handleNameEnquiryRequest(String val);

    public void handleFundsTransferRequest();

    public BigDecimal handleFetchWalletBalanceRequest(long val);

    public void handleFetchTransactionsRequest();
}
