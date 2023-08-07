package com.baxi.wallet.useraccess.logic.controller;

import com.baxi.wallet.useraccess.logic.service.WalletUserService;
import com.baxi.wallet.useraccess.payload.request.ResetPasswordRequest;
import com.baxi.wallet.useraccess.payload.request.UserVerificationRequest;
import com.baxi.wallet.useraccess.payload.response.UserVerificationResponse;
import com.baxi.wallet.useraccess.token.ConfirmationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class WalletUserController {

    //This handles all API calls for user management and sends to the services for processing
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private WalletUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(WalletUserController.class);

    @GetMapping(path = "/register/confirm")
    public String confirm(@RequestParam("token") String token) {
        return confirmationTokenService.confirmToken(token);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verifyUser")
    public UserVerificationResponse verifyUser(@RequestBody UserVerificationRequest request) {
        logger.info("Verifying request :::: " + request);
        return userService.verifyUser(request);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resetPassword/getAuthentication")
    public String resetUserPassword(@RequestBody ResetPasswordRequest request) {
        logger.info("Reset password request :::: " + request);
        return userService.sendResetEmail(request.getEmail());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout/{auth}")
    public String logoutUser(@PathVariable String auth) {
        logger.info("Logout :::: " + auth);
        return userService.logoutUser(auth);
    }



}
