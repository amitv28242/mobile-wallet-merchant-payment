package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * OTPVerification Entity - OTP management for two-factor authentication
 */
@Entity
@Table(name = "otp_verifications", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_otp_code", columnList = "otp_code"),
    @Index(name = "idx_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "otp_code", nullable = false, length = 6)
    private String otpCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "otp_type", nullable = false)
    private OTPType otpType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OTPStatus status;

    @Column(name = "attempts", nullable = false)
    private Integer attempts;

    @Column(name = "max_attempts", nullable = false)
    private Integer maxAttempts;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "purpose", length = 255)
    private String purpose;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum OTPType {
        EMAIL_VERIFICATION,
        PHONE_VERIFICATION,
        LOGIN_2FA,
        PAYMENT_CONFIRMATION,
        PASSWORD_RESET,
        ACCOUNT_RECOVERY
    }

    public enum OTPStatus {
        GENERATED,
        VERIFIED,
        EXPIRED,
        FAILED,
        CANCELLED
    }

    /**
     * Check if OTP is expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Check if OTP can be verified
     */
    public boolean canBeVerified() {
        return this.status == OTPStatus.GENERATED && 
               !isExpired() && 
               this.attempts < this.maxAttempts;
    }

    /**
     * Increment attempt count
     */
    public void incrementAttempt() {
        this.attempts++;
        if (this.attempts >= this.maxAttempts) {
            this.status = OTPStatus.FAILED;
        }
    }

    /**
     * Mark OTP as verified
     */
    public void markAsVerified() {
        this.status = OTPStatus.VERIFIED;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * Mark OTP as expired
     */
    public void markAsExpired() {
        if (this.status == OTPStatus.GENERATED) {
            this.status = OTPStatus.EXPIRED;
        }
    }

    /**
     * Check remaining attempts
     */
    public Integer getRemainingAttempts() {
        return Math.max(0, this.maxAttempts - this.attempts);
    }
}
