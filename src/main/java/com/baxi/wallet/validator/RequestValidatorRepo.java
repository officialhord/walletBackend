package com.baxi.wallet.validator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestValidatorRepo extends JpaRepository<RequestValidator, Long> {
    @Query("Select k from RequestValidator k where k.authValue  = ?1")
    Optional<RequestValidator> findByAuthValue(String auth);

    @Query(value = "Select * from request_validator k where k.user_email  = ?1", nativeQuery = true)
    List<RequestValidator> findByUsername(String username);

    @Query(value = "Select * from request_validator k where k.user_email  = ?1 and k.enabled = true", nativeQuery = true)
    List<RequestValidator> findActiveByUsername(String username);
}
