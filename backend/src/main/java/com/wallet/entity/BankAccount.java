package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * BankAccount Entity - Merchant bank account for settlements
 */
@Entity
@Table(name = "bank_accounts", indexes = {
    @Index(name = "idx_merchant_id", columnList = "merchant_id"),
    @Index(name = "idx_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "account_holder_name", nullable = false, length = 255)
    private String accountHolderName;

    @Column(name = "account_number_encrypted", nullable = false, length = 500)
    private String accountNumberEncrypted;

    @Column(name = "ifsc_code", nullable = false, length = 11)
    private String ifscCode;

    @Column(name = "bank_name", nullable = false, length = 255)
    private String bankName;

    @Column(name = "branch_name", length = 255)
    private String branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "verification_attempts", nullable = false)
    private Integer verificationAttempts;

    @Column(name = "verification_token", length = 255)
    private String verificationToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BankAccountStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum AccountType {
        SAVINGS,
        CURRENT,
        SALARY
    }

    public enum BankAccountStatus {
        ACTIVE,
        INACTIVE,
        BLOCKED,
        VERIFICATION_PENDING
    }

    /**
     * Mark account as verified
     */
    public void markAsVerified() {
        this.isVerified = true;
        this.status = BankAccountStatus.ACTIVE;
        this.verificationToken = null;
    }

    /**
     * Can verify more times
     */
    public boolean canVerifyAgain() {
        return verificationAttempts < 3;
    }
}
