package com.baxi.wallet.transactions.repo;

import com.app.tory.transactions.model.FeeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeConfigRepo extends JpaRepository<FeeConfig, Long> {

    @Query(value = "SELECT * FROM fee_config WHERE business_id = ?1 AND fee_category = ?2", nativeQuery = true)
    FeeConfig findFeeByBusinessIdAndCategory(Long id, String event);

    @Query(value = "SELECT * FROM fee_config WHERE business_id = ?1 AND fee_type = ?2 AND fee_category = ?3", nativeQuery = true)
    FeeConfig findFeeByBusinessId_FeeType_Category(Long id, String feeType, String feeCategory);

    @Query(value = "SELECT * FROM fee_config WHERE fee_type = 'DEFAULT' AND fee_category = ?1", nativeQuery = true)
    FeeConfig findDefaultFeeByCategory(String feeCategory);
}
