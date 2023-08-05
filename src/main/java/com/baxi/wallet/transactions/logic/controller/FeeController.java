package com.baxi.wallet.transactions.logic.controller;

import com.app.tory.transactions.logic.service.FeeService;
import com.app.tory.transactions.model.request.AddFeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @RequestMapping(method = RequestMethod.POST, value = "/addFee")
    public void addFee(@RequestBody AddFeeRequest addFeeRequest) {
        feeService.addFee(addFeeRequest);

    }


}
