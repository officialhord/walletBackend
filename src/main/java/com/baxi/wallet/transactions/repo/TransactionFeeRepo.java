package com.baxi.wallet.transactions.repo;


import com.app.tory.transactions.model.FeeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionFeeRepo extends JpaRepository<FeeRecord, Long> {
}
