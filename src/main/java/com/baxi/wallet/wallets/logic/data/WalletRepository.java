package com.baxi.wallet.wallets.logic.data;

import com.baxi.wallet.wallets.model.Wallets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallets, Long> {

    @Query(nativeQuery = true, value = "select * from wallets where app_user_id = ?1")
    Optional<Wallets> findByUserId(long userId);
}
