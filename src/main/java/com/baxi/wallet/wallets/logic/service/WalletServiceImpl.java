package com.baxi.wallet.wallets.logic.service;

import com.baxi.wallet.useraccess.data.WalletUserRepo;
import com.baxi.wallet.useraccess.model.WalletUser;
import com.baxi.wallet.wallets.logic.data.WalletRepository;
import com.baxi.wallet.wallets.model.Wallets;
import com.baxi.wallet.wallets.payload.WalletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletUserRepo userRepo;

    @Override
    public WalletResponse handleGenerateWalletRequest(long userId) {

        WalletResponse walletResponse = new WalletResponse();
        Optional<WalletUser> optUser = userRepo.findById(userId);

        walletResponse.setUserId(userId);
        if (optUser.isPresent()) {

            Optional<Wallets> optWallet = walletRepository.findByUserId(userId);
            Wallets wallet = null;
            walletResponse.setExists(true);

            if (optWallet.isPresent()) {
                wallet = optWallet.get();

            } else {
                wallet = new Wallets();
                wallet.setAppUser(optUser.get());
                wallet = walletRepository.save(wallet);
            }

            walletResponse.setWalletBalance(wallet.getWalletBalance());
            walletResponse.setLienBalance(wallet.getLienBalance());
            walletResponse.setAccountNumber(wallet.getAccountNumber());

        } else {
            walletResponse.setMessage("User not found");
        }

        return walletResponse;

    }

    @Override
    public WalletResponse handleNameEnquiryRequest(String accountNumber) {
        WalletResponse walletResponse = new WalletResponse();

        Optional<Wallets> optWallet = walletRepository.findByAccountNumber(accountNumber);

        if (optWallet.isPresent()) {

            Wallets wallet = optWallet.get();

            WalletUser user = wallet.getAppUser();

            walletResponse.setExists(true);
            walletResponse.setAccountNumber(accountNumber);
            walletResponse.setAccountName(user.getFirstname() + " " + user.getLastName());

        } else {
            walletResponse.setMessage("Account not found");
        }

        return walletResponse;
    }

    @Override
    public void handleFundsTransferRequest() {

    }

    @Override
    public BigDecimal handleFetchWalletBalanceRequest(long userId) {
        BigDecimal walletBalance = BigDecimal.ZERO;

        Optional<Wallets> optWallet = walletRepository.findByUserId(userId);

        if (optWallet.isPresent()) {
            walletBalance = optWallet.get().getWalletBalance();
        }

        return walletBalance;
    }

    @Override
    public void handleFetchTransactionsRequest() {

    }

}
