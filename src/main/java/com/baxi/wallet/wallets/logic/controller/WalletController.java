package com.baxi.wallet.wallets.logic.controller;

import com.baxi.wallet.useraccess.logic.controller.WalletUserController;
import com.baxi.wallet.useraccess.payload.request.UserVerificationRequest;
import com.baxi.wallet.useraccess.payload.response.UserVerificationResponse;
import com.baxi.wallet.wallets.logic.service.WalletService;
import com.baxi.wallet.wallets.payload.FundsTransferRequest;
import com.baxi.wallet.wallets.payload.WalletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {


    @Autowired
    WalletService walletService;
    private static final Logger logger = LoggerFactory.getLogger(WalletUserController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/createUserWallet")
    public WalletResponse handleCreateUserWallet(@PathVariable long userId, @RequestHeader("Authorization") String auth) {
        return walletService.handleGenerateWalletRequest(userId, auth);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/fundsTransfer")
    public WalletResponse handleFundsTransfer(@RequestBody FundsTransferRequest request, @RequestHeader("Authorization") String auth) {
        return walletService.handleFundsTransferRequest(request, auth);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/nameEnquiry")
    public WalletResponse handleNameEnquiry(@PathVariable String accountNumber, @RequestHeader("Authorization") String auth) {
        return walletService.handleNameEnquiryRequest(accountNumber, auth);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fetchBalance")
    public BigDecimal handleFetchBalance(@RequestBody long userId, @RequestHeader("Authorization") String auth) {
        return walletService.handleFetchWalletBalanceRequest(userId, auth);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/fetchTransactions")
    public WalletResponse handleFetchTransactions(@PathVariable long walletId,
                                                  @RequestHeader("Authorization") String auth) {
        return walletService.handleFetchTransactionsRequest(walletId, auth);
    }

}
