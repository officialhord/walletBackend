package com.baxi.wallet.validator;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
