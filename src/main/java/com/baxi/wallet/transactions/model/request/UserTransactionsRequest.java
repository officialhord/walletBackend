package com.baxi.wallet.transactions.model.request;


import lombok.Data;

@Data
public class UserTransactionsRequest {

    private String userEmail;
    private String authentication;
}
