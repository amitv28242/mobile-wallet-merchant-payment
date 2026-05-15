package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Card Entity - Represents saved debit/credit cards
 */
@Entity
@Table(name = "cards", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @Column(name = "card_number_encrypted", nullable = false, length = 500)
    private String cardNumberEncrypted;

    @Column(name = "card_holder_name", nullable = false, length = 255)
    private String cardHolderName;

    @Column(name = "expiry_month", nullable = false)
    private Integer expiryMonth;

    @Column(name = "expiry_year", nullable = false)
    private Integer expiryYear;

    @Column(name = "cvv_encrypted", nullable = false, length = 500)
    private String cvvEncrypted;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_network", nullable = false)
    private CardNetwork cardNetwork;

    @Column(name = "last_four_digits", length = 4)
    private String lastFourDigits;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "verification_token", length = 255)
    private String verificationToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum CardType {
        DEBIT,
        CREDIT
    }

    public enum CardNetwork {
        VISA,
        MASTERCARD,
        AMEX,
        RUPAY
    }

    public enum CardStatus {
        ACTIVE,
        INACTIVE,
        BLOCKED
    }

    /**
     * Check if card is expired
     */
    public boolean isExpired() {
        int currentYear = java.time.Year.now().getValue();
        int currentMonth = java.time.YearMonth.now().getMonthValue();

        if (expiryYear < currentYear) {
            return true;
        }
        if (expiryYear == currentYear && expiryMonth < currentMonth) {
            return true;
        }
        return false;
    }
}
