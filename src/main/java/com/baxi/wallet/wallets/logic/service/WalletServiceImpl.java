package com.baxi.wallet.wallets.logic.service;

import com.baxi.wallet.useraccess.data.WalletUserRepo;
import com.baxi.wallet.useraccess.model.WalletUser;
import com.baxi.wallet.utility.WalletUtil;
import com.baxi.wallet.wallets.logic.data.WalletRepository;
import com.baxi.wallet.wallets.logic.data.WalletTransactionRepository;
import com.baxi.wallet.wallets.model.WalletTransaction;
import com.baxi.wallet.wallets.model.Wallets;
import com.baxi.wallet.wallets.payload.FundsTransferRequest;
import com.baxi.wallet.wallets.payload.WalletResponse;
import com.baxi.wallet.wallets.payload.WalletTransactions;
import com.baxi.wallet.wallets.payload.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletTransactionRepository walletTransactionRepository;

    @Autowired
    WalletUtil utility;
    @Autowired
    WalletUserRepo userRepo;

    @Override
    public WalletResponse handleGenerateWalletRequest(long userId, String auth) {

        WalletResponse walletResponse = new WalletResponse();

        if (utility.authenticate(auth)) {
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
        } else {
            walletResponse.setMessage("Auntorization Failed");
        }
        return walletResponse;

    }

    @Override
    public WalletResponse handleNameEnquiryRequest(String accountNumber, String auth) {
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
    public WalletResponse handleFundsTransferRequest(FundsTransferRequest fundsTransferRequest, String auth) {
        WalletResponse walletResponse = new WalletResponse();

        Optional<Wallets> fromWallet = walletRepository.findByAccountNumber(fundsTransferRequest.getFromAccount());

        Optional<Wallets> toWallet = walletRepository.findByAccountNumber(fundsTransferRequest.getToAccount());

        BigDecimal amount = fundsTransferRequest.getAmount();

        if (fromWallet.isEmpty() || toWallet.isEmpty()) {
            walletResponse.setExists(false);
            walletResponse.setMessage("Account not found!");
            return walletResponse;
        }

        walletResponse.setExists(false);
        //validate balance on sending account
        boolean balanceAvailable = fromWallet.get().getWalletBalance().compareTo(amount) >= 0;

        if (balanceAvailable) {

            Wallets updatedFromWallet = updateWalletBalance(fromWallet.get().getId(), fundsTransferRequest.getAmount(), TransactionType.DEBIT);
            Wallets updatedToWallet = updateWalletBalance(toWallet.get().getId(), fundsTransferRequest.getAmount(), TransactionType.CREDIT);

            generateWalletTransactions(updatedFromWallet, updatedToWallet, amount);
            walletResponse.setMessage("Funds Transfer successful");

        } else {
            walletResponse.setMessage("Insufficient Balance");
            return walletResponse;
        }

        return walletResponse;
    }

    private void generateWalletTransactions(Wallets updatedFromWallet, Wallets updatedToWallet, BigDecimal amount) {

        WalletTransaction fromWalletTransaction = new WalletTransaction();
        fromWalletTransaction.setTransactionAmount(amount);
        fromWalletTransaction.setTransactionType(TransactionType.DEBIT);
        fromWalletTransaction.setTransactionDate(LocalDateTime.now());
        fromWalletTransaction.setCompleted(true);
        fromWalletTransaction.setWallet(updatedFromWallet);
        fromWalletTransaction.setToAccount(updatedToWallet.getId());
        fromWalletTransaction.setFromAccount(updatedFromWallet.getId());
        walletTransactionRepository.save(fromWalletTransaction);

        WalletTransaction toWalletTransaction = new WalletTransaction();
        toWalletTransaction.setTransactionAmount(amount);
        toWalletTransaction.setTransactionType(TransactionType.CREDIT);
        toWalletTransaction.setTransactionDate(LocalDateTime.now());
        toWalletTransaction.setCompleted(true);
        toWalletTransaction.setWallet(updatedToWallet);
        toWalletTransaction.setFromAccount(updatedFromWallet.getId());
        toWalletTransaction.setToAccount(updatedToWallet.getId());
        walletTransactionRepository.save(toWalletTransaction);


    }

    private synchronized Wallets updateWalletBalance(long walletId, BigDecimal amount, TransactionType transactionType) {

        Wallets wallet = walletRepository.findById(walletId).get();

        if (transactionType.equals(TransactionType.DEBIT))
            wallet.setWalletBalance(wallet.getWalletBalance().subtract(amount));

        if (transactionType.equals(TransactionType.CREDIT))
            wallet.setWalletBalance(wallet.getWalletBalance().add(amount));

        wallet = walletRepository.save(wallet);
        return wallet;

    }

    @Override
    public BigDecimal handleFetchWalletBalanceRequest(long userId, String auth) {
        BigDecimal walletBalance = BigDecimal.ZERO;

        Optional<Wallets> optWallet = walletRepository.findByUserId(userId);

        if (optWallet.isPresent()) {
            walletBalance = optWallet.get().getWalletBalance();
        }

        return walletBalance;
    }

    @Override
    public WalletResponse handleFetchTransactionsRequest(long walletId, String auth) {
        WalletResponse walletResponse = new WalletResponse();

        List<WalletTransactions> walletTransactionsList = new ArrayList<>();

        List<WalletTransaction> walletTransactions = walletTransactionRepository.findAllByWalletId(walletId);

        if (!walletTransactions.isEmpty()) {
            walletTransactions.forEach(
                    transaction -> {
                        WalletTransactions transactionResponse = new WalletTransactions();
                        transactionResponse.setTransactionType(transaction.getTransactionType());
                        transactionResponse.setTransactionDate(transaction.getTransactionDate());
                        transactionResponse.setAmount(transaction.getTransactionAmount());
                        walletTransactionsList.add(transactionResponse);
                    }
            );

            walletResponse.setWalletTransactions(walletTransactionsList);
        }

        return walletResponse;
    }

}
