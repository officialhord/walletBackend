package com.baxi.wallet.email;

public interface EmailSender {

    void send(String to, String email, String subject);

}
