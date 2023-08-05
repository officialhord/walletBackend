package com.baxi.wallet.utility;

import com.baxi.wallet.email.EmailSender;
import com.baxi.wallet.transactions.repo.TransactionRecordRepo;
import com.baxi.wallet.useraccess.data.WalletUserRepo;
import com.baxi.wallet.useraccess.model.WalletUser;
import com.baxi.wallet.useraccess.token.ConfirmationToken;
import com.baxi.wallet.useraccess.token.ConfirmationTokenService;
import com.baxi.wallet.utility.enums.EmailSubject;
import com.baxi.wallet.utility.enums.EmailTemplate;
import com.baxi.wallet.validator.RequestValidator;
import com.baxi.wallet.validator.RequestValidatorRepo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class WalletUtil {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private RequestValidatorRepo requestValidatorRepo;

    @Autowired
    private TransactionRecordRepo transactionRecordRepo;

    @Autowired
    private WalletUserRepo userRepo;

    @Value("${default.key}")
    private String defaultKey;
    private static final String ALGORITHM = "AES";
    @Value("${key}")
    private String KEY;
    private static final String ENCODING = "UTF-8";
    @Autowired
    private EmailSender emailService;

    public String createToken(WalletUser user) {
        // Create confirmation token
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 5);
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        confirmationToken.setAppUser(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;

    }

    public String decryptRequest(String request) {

        String response = null;
        try {
            byte[] keyBytes = KEY.getBytes(ENCODING);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = Base64.getDecoder().decode(request);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            response = new String(decryptedBytes, ENCODING);
            System.out.println("Request decrypted ::: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String decryptAES(String encryptedText, String initializationVector) throws Exception {
        byte[] decodedKey = KEY.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "AES");

        byte[] decodedIV = initializationVector.getBytes("UTF-8");
        System.out.println("Decoded IV ::: " + new String(decodedIV));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedIV);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        System.out.println("Decoded Text ::: " + new String(encryptedBytes));
        return new String(cipher.doFinal(encryptedBytes));
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenService.getToken(token).get();
    }


    public String buildResetPasswordEmail(String name, String link) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n" + "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n" + "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" + "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n" + "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" + "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" + "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" + "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n" + "                  \n" + "                    </td>\n" + "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" + "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset Password</span>\n" + "                    </td>\n" + "                  </tr>\n" + "                </tbody></table>\n" + "              </a>\n" + "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n" + "        \n" + "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n" + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" + "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" + "      <td>\n" + "        \n" + "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" + "                  <tbody><tr>\n" + "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" + "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n" + "  </tbody></table>\n" + "\n" + "\n" + "\n" + "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" + "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n" + "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" + "        \n" + "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> We have received your reset password request. Please click on the below link to reset: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" + "        \n" + "      </td>\n" + "      <td width=\"10\" valign=\"middle\"><br></td>\n" + "    </tr>\n" + "    <tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
    }

    public String generateReference() {
        return RandomStringUtils.randomAlphabetic(7).toUpperCase();
    }

    public boolean authenticate(String auth) {
        if (auth.equalsIgnoreCase("no_auth"))
            return true;

        if (requestValidatorRepo.findByAuthValue(auth).isPresent()) {
            System.out.println(":::::: Authorization token found :::::::");
            RequestValidator requestValidator = requestValidatorRepo.findByAuthValue(auth).get();
            return requestValidator.isEnabled();
        } else return auth.equalsIgnoreCase(defaultKey);
    }


    public String generateAuthentication(String email) {
        String authValue = UUID.randomUUID().toString().replace("-", "").toUpperCase();

        RequestValidator requestValidator = new RequestValidator();
        requestValidator.setAuthValue(authValue);
        requestValidator.setUserEmail(email);
        requestValidator.setLastUsed(LocalDateTime.now());
        requestValidator.setEnabled(true);

        requestValidatorRepo.save(requestValidator);
        return authValue;
    }

    public void removeToken(String auth) {
        if (requestValidatorRepo.findByAuthValue(auth).isPresent()) {
            RequestValidator requestValidator = requestValidatorRepo.findByAuthValue(auth).get();
            requestValidator.setEnabled(false);
            requestValidatorRepo.save(requestValidator);
        }
    }

    public void saveToken(ConfirmationToken token) {
        confirmationTokenService.saveConfirmationToken(token);
    }

    public void sendEmail(String emailAddress, String firstname, EmailTemplate emailTemplate, EmailSubject emailSubject, Map<String, String> content) {

        try {
            InputStream resource = new ClassPathResource("email_templates/" + emailTemplate.name).getInputStream();
            byte[] htmlBytes = IOUtils.toByteArray(resource);
            String htmlContent = new String(htmlBytes, "UTF-8");

            switch (emailTemplate.name()) {
                case "VERIFICATION_MAIL":
                    htmlContent = htmlContent.replace("{token}", content.get("token"));
                    break;
                case "CRYPTO_PURCHASE":
                    htmlContent = htmlContent.replace("{customer_name}", content.get("customer_name"));
                    htmlContent = htmlContent.replace("{total_amount}", content.get("total_amount"));
                    break;

                default:
                    htmlContent = htmlContent.replace("{name}", firstname);
                    break;
            }

            emailService.send(emailAddress, htmlContent, emailSubject.name);

        } catch (Exception e) {
            System.out.println("Error occurred while sending email");
            e.printStackTrace();
        }

    }

    public void deactivateExistingAuthentication(String username) {

        List<RequestValidator> requestValidators = requestValidatorRepo.findActiveByUsername(username);
        if (requestValidators.size() > 0) {
            for (RequestValidator requestValidator : requestValidators) {
                requestValidator.setEnabled(false);
                requestValidatorRepo.save(requestValidator);
            }
        }
    }


    public WalletUser getUserFromAuthentication(String auth) {
        WalletUser user = new WalletUser();
        if (authenticate(auth)) {
            RequestValidator requestValidator = requestValidatorRepo.findByAuthValue(auth).get();
            Optional<WalletUser> checkUser = userRepo.findByEmailAddress(requestValidator.getUserEmail());
            if (checkUser.isPresent()) {
                user = checkUser.get();
            }
        }
        return user;
    }

}
