package com.baxi.wallet.wallets.logic.service;

import com.baxi.wallet.useraccess.data.WalletUserRepo;
import com.baxi.wallet.useraccess.model.WalletUser;
import com.baxi.wallet.wallets.logic.data.WalletRepository;
import com.baxi.wallet.wallets.model.Wallets;
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
    public void handleGenerateWalletRequest(long userId) {

        Optional<WalletUser> optUser = userRepo.findById(userId);

        if (optUser.isPresent()) {

            Optional<Wallets> optWallet = walletRepository.findByUserId(userId);
            Wallets wallet = null;
            if (optWallet.isPresent()) {
                wallet = optWallet.get();
            } else {
                wallet = new Wallets();
                wallet.setAppUser(optUser.get());
                wallet = walletRepository.save(wallet);
            }
        } else {
            System.out.println("Complaining bitterly...");
        }

    }

    @Override
    public void handleNameEnquiryRequest() {

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
