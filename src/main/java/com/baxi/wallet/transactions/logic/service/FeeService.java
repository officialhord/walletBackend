package com.baxi.wallet.transactions.logic.service;

import com.app.tory.transactions.model.FeeConfig;
import com.app.tory.transactions.model.request.AddFeeRequest;
import com.app.tory.transactions.repo.FeeConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    private FeeConfigRepo feeConfigRepo;

    public void addFee(AddFeeRequest addFeeRequest) {

        FeeConfig fee = new FeeConfig();
        fee.setFeeAmount(addFeeRequest.getFeeAmount());
        fee.setPercentage(addFeeRequest.isPercentage());
        fee.setFeeCategory(addFeeRequest.getFeeCategory());
        fee.setFeeType(addFeeRequest.getFeeType());
        fee.setBusinessId(addFeeRequest.getBusinessId());
        fee.setActive(true);

        feeConfigRepo.save(fee);
    }

}
