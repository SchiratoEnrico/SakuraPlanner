package com.sakuraplanner.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a temporary token used to verify a user's email address during registration.
 * Mapped to a dedicated table with a One-to-One relationship to the User entity.
 */
@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@NoArgsConstructor // Automatically generates the default constructor required by JPA
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique token string (usually a UUID) sent to the user via email.
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Unidirectional One-to-One relationship with the User.
     * If a user is deleted, their verification token is deleted automatically (CascadeType.REMOVE).
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Custom constructor to easily create a token with a specific lifespan.
     */
    public VerificationToken(String token, User user, int expiryTimeInMinutes) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }

    /**
     * Helper method to quickly check if the token has expired.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}