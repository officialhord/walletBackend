package com.baxi.wallet.wallets.logic.service;


import java.math.BigDecimal;

public interface WalletService {

    public void handleGenerateWalletRequest(long userId);

    public void handleNameEnquiryRequest();

    public void handleFundsTransferRequest();

    public BigDecimal handleFetchWalletBalanceRequest(long userId);

    public void handleFetchTransactionsRequest();
}
