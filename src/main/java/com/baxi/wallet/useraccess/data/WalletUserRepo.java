package com.baxi.wallet.useraccess.data;


import com.baxi.wallet.useraccess.model.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface WalletUserRepo extends JpaRepository<WalletUser, String> {
    Optional<WalletUser> findByUsername(String username);

    @Query(value = "Select * from wallet_user where id = ?1", nativeQuery = true)
    Optional<WalletUser> findById(long email);

    @Query(value = "Select * from wallet_user where email_address = ?1", nativeQuery = true)
    Optional<WalletUser> findByEmailAddress(String email);

    @Transactional
    @Modifying
    @Query("UPDATE WalletUser a " +
            "SET a.enabled = TRUE WHERE a.emailAddress = ?1")
    void enableAppUser(String email);
}
