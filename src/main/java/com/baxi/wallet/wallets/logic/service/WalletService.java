package com.baxi.wallet.wallets.logic.service;


public interface WalletService {

    public void handleGenerateWalletRequest();

    public void handleNameEnquiryRequest();

    public void handleFundsTransferRequest();

    public void handleFetchWalletBalanceRequest();

    public void handleFetchTransactionsRequest();
}
