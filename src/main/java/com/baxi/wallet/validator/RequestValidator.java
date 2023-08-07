package com.baxi.wallet.validator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RequestValidator {

    @Id
    @GeneratedValue
    private Long id;

    private String authValue;

    private LocalDateTime lastUsed;

    private boolean enabled;

    private String userEmail;
}
