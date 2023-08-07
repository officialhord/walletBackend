package com.baxi.wallet.wallets.logic.service;


import com.baxi.wallet.wallets.payload.FundsTransferRequest;
import com.baxi.wallet.wallets.payload.WalletResponse;

import java.math.BigDecimal;

public interface WalletService {

    public WalletResponse handleGenerateWalletRequest(long val, String val1);

    public WalletResponse handleNameEnquiryRequest(String val, String val1);

    public WalletResponse handleFundsTransferRequest(FundsTransferRequest fundsTransferRequest, String val1);

    public BigDecimal handleFetchWalletBalanceRequest(long val, String val1);

    public WalletResponse handleFetchTransactionsRequest(long val, String val1);
}
