package com.baxi.wallet.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    @Override
    public void send(String to, String email, String subject) {

        try {

            Properties props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.host", "smtp.titan.email");
            props.put("mail.smtp.ssl.enable", true);
            props.put("mail.smtp.port", "465");
            String username = "comms@baxi.com";
            String password = "fakePassword";

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(email, "utf-8", "html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            LOGGER.error("Failed to send email:: ", e);
            throw new IllegalStateException("Failed to send");
        }
    }
}
