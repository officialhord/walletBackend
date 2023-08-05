package com.baxi.wallet.transactions.logic.controller;

import com.app.tory.transactions.logic.service.TransactionService;
import com.app.tory.transactions.model.TransactionNotification;
import com.app.tory.transactions.model.request.ReceiveCryptoRequest;
import com.app.tory.transactions.model.request.SendCryptoRequest;
import com.app.tory.transactions.model.request.UserTransactionsRequest;
import com.app.tory.utility.payload.response.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @RequestMapping(method = RequestMethod.POST, value = "/transactionNotification")
    public RequestResponse handleTransactionNotification(@RequestBody TransactionNotification transactionNotification) {
        return transactionService.handleTransactionNotification(transactionNotification);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userTransactions")
    public RequestResponse handleFetchUserTransactions(@RequestBody UserTransactionsRequest request){
        return transactionService.handleFetchAllUserTransactions(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/receiveCrypto")
    public RequestResponse handleReceiveCryptoTransaction(@RequestBody ReceiveCryptoRequest request){
        return transactionService.handleReceiveCryptoTransaction(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sendCrypto")
    public RequestResponse handleSendCryptoTransaction(@RequestBody SendCryptoRequest request){
        return transactionService.handleSendCryptoTransaction(request);
    }

}

