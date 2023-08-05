package com.baxi.wallet.utility.enums;

public enum EmailSubject {

    VERIFICATION_SUCCESSFUL("Verification Successful"),
    VERIFICATION_MAIL("Verification Mail"),

    BANK_ACCOUNT_ADDED("Bank account added !!!"),
    PASSWORD_RESET("Password Reset Requested");

    public final String name;

    EmailSubject(String name) {
        this.name = name;
    }
}
