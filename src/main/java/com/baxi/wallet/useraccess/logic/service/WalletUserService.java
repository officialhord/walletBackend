package com.baxi.wallet.useraccess.logic.service;


import com.baxi.wallet.email.EmailSender;
import com.baxi.wallet.useraccess.data.WalletUserRepo;
import com.baxi.wallet.useraccess.model.AppUserRole;
import com.baxi.wallet.useraccess.model.WalletUser;
import com.baxi.wallet.useraccess.payload.request.*;
import com.baxi.wallet.useraccess.payload.response.UserLoginResponse;
import com.baxi.wallet.useraccess.payload.response.UserRegistrationResponse;
import com.baxi.wallet.useraccess.payload.response.UserVerificationResponse;
import com.baxi.wallet.useraccess.token.ConfirmationToken;
import com.baxi.wallet.utility.WalletUtil;
import com.baxi.wallet.utility.enums.EmailSubject;
import com.baxi.wallet.utility.enums.EmailTemplate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WalletUserService implements UserDetailsService {

    // All logic, computation, and pushing to DB should happen in here
    private final static String USER_NOT_FOUND_msg = "User with Email %s not found";
    @Autowired
    WalletUserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmailSender emailSender;
    @Autowired
    WalletUtil walletUtil;


    public UserRegistrationResponse registerUser(ToryUserRegistrationDTO request) {

        WalletUser walletUser = new WalletUser();
        UserRegistrationResponse response = new UserRegistrationResponse();

        if (ObjectUtils.isEmpty(request.getEmail()) || ObjectUtils.isEmpty(request.getPassword())) {
            response.setStatus("99");
            response.setStatusDescription("Please pass a valid email or password");
            return response;
        }

        // Check if the user exists already
        boolean userExists = userRepo.findByEmailAddress(request.getEmail().toLowerCase()).isPresent();

        if (userExists) {
            response.setStatus("99");
            response.setStatusDescription("Email already Exists");
            return response;
        }

        walletUser.setEmailAddress(request.getEmail().toLowerCase());
        walletUser.setAppUserRole(AppUserRole.USER);
        walletUser.setEnabled(false);
        walletUser.setFirstname(request.getFirstName());
        walletUser.setLastName(request.getLastName());

        walletUser.setLocked(false);

        String encodedPass = bCryptPasswordEncoder.encode(request.getPassword());
        walletUser.setPassword(encodedPass);
        userRepo.save(walletUser);

        // Create confirmation token
        String token = walletUtil.createToken(walletUser);
        Map<String, String> content = new HashMap<>();
        content.put("token", token);

        try {
            walletUtil.sendEmail(walletUser.getEmailAddress(), walletUser.getFirstname(), EmailTemplate.VERIFICATION_MAIL, EmailSubject.VERIFICATION_MAIL, content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatusDescription("Registration Successful :: " + token);
        response.setStatus("00");
        response.setToryUser(walletUser);

        return response;
    }

    public UserLoginResponse loginUser(ToryUserLoginDTO loginDTO) {

        UserLoginResponse response = new UserLoginResponse();
        System.out.println("Within login user");

        //Check if the user exists using the username in the loginDTO
        String username = loginDTO.getUsername().toLowerCase();
        boolean exists = userRepo.findByEmailAddress(username).isPresent() || userRepo.findByUsername(username).isPresent();
        System.out.println("username is: " + username);

        // If user exists, confirm password ---- (Encrypted)
        if (exists) {
            WalletUser walletUser = null;

            if (userRepo.findByEmailAddress(username).isPresent()) {
                walletUser = userRepo.findByEmailAddress(username).get();
            } else {
                walletUser = userRepo.findByUsername(username).get();
            }

            if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), walletUser.getPassword())) {
                if (walletUser.isEnabled()) {
                    response.setId(walletUser.getId());
                    response.setUsername(walletUser.getUsername());
                    response.setEmail(walletUser.getEmailAddress());
                    response.setEnabled(walletUser.getEnabled());
                    response.setLocked(walletUser.getLocked());
                    response.setStatus("00");
                    response.setStatusDescription("Login successful");

                    String authentication = walletUtil.generateAuthentication(username);
                    response.setAuthentication(authentication);



                } else {
                    response.setStatus("05");
                    response.setUsername(walletUser.getUsername());

                    // Create confirmation token
                    String token = walletUtil.createToken(walletUser);

                    Map<String, String> content = new HashMap<>();
                    content.put("token", token);

                    walletUtil.sendEmail(walletUser.getEmailAddress(), walletUser.getFirstname(), EmailTemplate.VERIFICATION_MAIL, EmailSubject.VERIFICATION_MAIL, content);
                    response.setStatusDescription("Email not Verified :: " + token);
                }
            } else {

                response.setStatus("06");
                response.setStatusDescription("Invalid password entered");
                response.setUsername(walletUser.getUsername());

            }
        } else {

            response.setStatus("02");
            response.setStatusDescription("User not found");
            response.setUsername(loginDTO.getUsername());
        }
        return response;

    }

    public String sendResetEmail(String email) {

        String response = "User not found";
        if (userRepo.findByEmailAddress(email).isPresent()) {

            WalletUser user = userRepo.findByEmailAddress(email).get();
            String token = walletUtil.createToken(user);

            response = "Reset email request Sent";
            Map<String, String> content = new HashMap<>();
            content.put("token", token);

            walletUtil.sendEmail(user.getEmailAddress(), user.getFirstname(), EmailTemplate.PASSWORD_RESET, EmailSubject.PASSWORD_RESET, content);

        }
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmailAddress(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_msg, email)));
    }

    public String logoutUser(String auth) {

        String response = "Logout failed";
        if (walletUtil.authenticate(auth)) {

            walletUtil.removeToken(auth);
            response = "Logout Successful";
        }
        return response;
    }

    public UserVerificationResponse verifyUser(UserVerificationRequest request) {

        UserVerificationResponse response = new UserVerificationResponse();
        response.setMessage("Verification failed");
        response.setSuccess(false);

        String requestToken = request.getToken();
        if (!ObjectUtils.isEmpty(walletUtil.getToken(requestToken))) {

            ConfirmationToken token = walletUtil.getToken(requestToken);

            if (!token.getAppUser().getEmailAddress().equals(request.getEmail())) {
                response.setMessage("Verification Failed");
                return response;
            }
            if (!ObjectUtils.isEmpty(token.getConfirmedAt())) {
                response.setMessage("Token has been used already");
                return response;
            } else if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
                response.setMessage("Token expired");
                return response;
            } else {
                token.setConfirmedAt(LocalDateTime.now());
                walletUtil.saveToken(token);
                response.setMessage("Verification Successful");
                response.setSuccess(true);
            }

            Map<String, String> content = new HashMap<>();
            String email = null;
            String name = null;

            if (!ObjectUtils.isEmpty(token.getAppUser())) {
                WalletUser user = token.getAppUser();
                user.setEnabled(true);
                userRepo.save(user);

                email = user.getEmailAddress();
                name = user.getFirstname();
                response.setUser(true);

            }
            walletUtil.sendEmail(email, name, EmailTemplate.VERIFICATION_SUCCESSFUL, EmailSubject.VERIFICATION_SUCCESSFUL, content);
        }
        return response;
    }


}
