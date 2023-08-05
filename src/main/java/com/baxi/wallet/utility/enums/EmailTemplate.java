package com.baxi.wallet.utility.enums;

public enum EmailTemplate {

    VERIFICATION_SUCCESSFUL("verification_successful.html"),
    VERIFICATION_FAILED("verification_failed.html"),
    PASSWORD_RESET("password_reset.html"),
    PASSWORD_RESET_SUCCESSFUL("password_reset_successful.html"),
    PASSWORD_RESET_FAILED("password_reset_failed.html"),
    CRYPTO_PURCHASE("crypto_purchase.html"),
    CRYPTO_SALE("crypto_sale.html"),

    BANK_ACCOUNT_ADDED("bank_account_added.html"),
    VERIFICATION_MAIL("verification_mail.html");

    public final String name;


    EmailTemplate(String templateName) {
        this.name = templateName;
    }


}
