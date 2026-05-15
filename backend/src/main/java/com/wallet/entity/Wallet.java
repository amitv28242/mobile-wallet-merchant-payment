package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Wallet Entity - Represents user wallet for money management
 */
@Entity
@Table(name = "wallets", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_wallet_address", columnList = "wallet_address"),
    @Index(name = "idx_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "wallet_address", nullable = false, unique = true, length = 100)
    private String walletAddress;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "currency", length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WalletStatus status;

    @Column(name = "daily_limit", precision = 15, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "monthly_limit", precision = 15, scale = 2)
    private BigDecimal monthlyLimit;

    @Column(name = "daily_spent", precision = 15, scale = 2)
    private BigDecimal dailySpent;

    @Column(name = "monthly_spent", precision = 15, scale = 2)
    private BigDecimal monthlySpent;

    @Column(name = "last_reset_date")
    private java.time.LocalDate lastResetDate;

    @Column(name = "kyc_verified", nullable = false)
    private Boolean kycVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_level")
    private KYCLevel kycLevel;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum WalletStatus {
        ACTIVE,
        INACTIVE,
        FROZEN
    }

    public enum KYCLevel {
        LEVEL_0,
        LEVEL_1,
        LEVEL_2,
        LEVEL_3
    }

    /**
     * Add money to wallet
     */
    public void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    /**
     * Deduct money from wallet
     */
    public void deductBalance(BigDecimal amount) {
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Check if daily limit exceeded
     */
    public boolean isDailyLimitExceeded(BigDecimal amount) {
        return dailySpent.add(amount).compareTo(dailyLimit) > 0;
    }

    /**
     * Check if monthly limit exceeded
     */
    public boolean isMonthlyLimitExceeded(BigDecimal amount) {
        return monthlySpent.add(amount).compareTo(monthlyLimit) > 0;
    }
}
