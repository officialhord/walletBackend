package com.baxi.wallet.transactions.logic.service;

import com.app.tory.transactions.model.FeeRecord;
import com.app.tory.transactions.model.TransactionNotification;
import com.app.tory.transactions.model.TransactionRecord;
import com.app.tory.transactions.model.request.ReceiveCryptoRequest;
import com.app.tory.transactions.model.request.SendCryptoRequest;
import com.app.tory.transactions.model.request.UserTransactionsRequest;
import com.app.tory.transactions.repo.TransactionFeeRepo;
import com.app.tory.transactions.repo.TransactionRecordRepo;
import com.app.tory.utility.ToryUtil;
import com.app.tory.utility.payload.response.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRecordRepo transactionRecordRepo;

    @Autowired
    private ToryUtil util;

    @Autowired
    private TransactionFeeRepo transactionFeeRepo;

    public void saveTransaction(TransactionRecord transactionRecord) {
        transactionRecordRepo.save(transactionRecord);
    }

    public List<TransactionRecord> findAllTransactions() {
        return transactionRecordRepo.findAll();
    }


    public TransactionRecord findTransactionById(String transactionId) {
        boolean exists = transactionRecordRepo.findById(transactionId).isPresent();

        if (exists)
            return transactionRecordRepo.findById(transactionId).get();

        return new TransactionRecord();
    }

    public List<TransactionRecord> findTransactionByCustomerId(long customerId) {
        return transactionRecordRepo.findByCustomerId(customerId);
    }

    public List<TransactionRecord> findTransactionBySessionRef(String sessionRef) {
        return transactionRecordRepo.findBySessionRef(sessionRef);
    }


    public List<TransactionRecord> findTransactionByCompleted(boolean completed) {
        return transactionRecordRepo.findByCompleted(completed);
    }

    public List<TransactionRecord> findTransactionByCompletedAndCustomerId(boolean completed, long customerId) {
        return transactionRecordRepo.findByCompletedAndCustomerId(completed, customerId);
    }


    public RequestResponse handleTransactionNotification(TransactionNotification transactionNotification) {

        RequestResponse requestResponse = new RequestResponse();

        System.out.println("Notification came in::: " + transactionNotification);

        String transactionRef = transactionNotification.getTransactionReference();
        if (!ObjectUtils.isEmpty(transactionRecordRepo.findByTransactionReference(transactionRef))) {

            TransactionRecord transactionRecord = transactionRecordRepo.findByTransactionReference(transactionRef);
            if (transactionRecord.isCompleted()) {
                requestResponse.setDescription("Transaction already completed");
                requestResponse.setSuccessful(false);

                System.out.println("Creating a new transaction as initial transaction has been completed.....");
                TransactionRecord newRecord = new TransactionRecord();
                transactionRecord.setTransactionReference(transactionRef + "-DUP");
                transactionRecord.setTransactionAmount(transactionNotification.getAmount());
                transactionRecord.setCompleted(false);
                transactionRecordRepo.save(newRecord);

            } else {
                transactionRecord.setCompleted(true);
                transactionRecordRepo.save(transactionRecord);

                FeeRecord feeRecord = new FeeRecord();
                feeRecord.setTransactionReference(String.valueOf(transactionRef));
                feeRecord.setFeeAmount(transactionRecord.getFee());
                transactionFeeRepo.save(feeRecord);

                requestResponse.setDescription("Transaction completed");
                requestResponse.setSuccessful(true);

            }

        } else {
            requestResponse.setDescription("Transaction not found");
            requestResponse.setSuccessful(false);
        }


        return requestResponse;
    }

    public RequestResponse handleFetchAllUserTransactions(UserTransactionsRequest request) {
        return null;
    }

    public RequestResponse handleReceiveCryptoTransaction(ReceiveCryptoRequest request) {

        return null;
    }

    public RequestResponse handleSendCryptoTransaction(SendCryptoRequest request) {
        return null;
    }
}
