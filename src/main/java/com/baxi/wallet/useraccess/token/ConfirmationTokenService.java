package com.baxi.wallet.useraccess.token;

import com.app.tory.useraccess.data.ToryUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;
    @Autowired
    private ToryUserRepo userRepo;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepo.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {

        return confirmationTokenRepo.findByToken(token);
    }

    public int setConfirmedAt(String token) {

        ConfirmationToken confirmationToken = null;

        if (confirmationTokenRepo.findByToken(token).isPresent()) {
            confirmationToken = confirmationTokenRepo.findByToken(token).get();
            if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                confirmationTokenRepo.delete(confirmationToken);
                return 2;
            }

            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepo.save(confirmationToken);

            return 1;
        }

        return 0;

    }

    public Optional<ConfirmationToken> getTokenByUserId(Long id) {
        return confirmationTokenRepo.findByAppUserId(id);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            return "Email already confirmed";
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
           return "Token Expired";
        }

        int confirmToken = setConfirmedAt(token);

        if (confirmToken == 0) {
            return "Token not found";
        } else if (confirmToken == 1) {

            if (!ObjectUtils.isEmpty(confirmationToken.getAppUser())) {
                enableAppUser(confirmationToken.getAppUser().getEmailAddress());
            }

            return "Email verified";
        } else if (confirmToken == 2) {
            return "Token Expired";
        }

        return "unable to verify user";

    }

    private void enableAppUser(String email) {
        userRepo.enableAppUser(email);
    }


}
