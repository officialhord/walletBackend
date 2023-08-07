package com.baxi.wallet.wallets.logic.data;

import com.baxi.wallet.wallets.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    @Query(value = "Select * from wallet_transaction where wallet_id = ?1", nativeQuery = true)
    List<WalletTransaction> findAllByWalletId(long walletId);
}
