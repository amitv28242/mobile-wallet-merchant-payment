package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * PasswordResetToken Entity - Secure password recovery
 */
@Entity
@Table(name = "password_reset_tokens", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_token", columnList = "reset_token", unique = true),
    @Index(name = "idx_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reset_token", nullable = false, unique = true, length = 500)
    private String resetToken;

    @Column(name = "email_hash", nullable = false, length = 255)
    private String emailHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ResetStatus status;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "device_id", length = 255)
    private String deviceId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum ResetStatus {
        GENERATED,
        USED,
        EXPIRED,
        CANCELLED
    }

    /**
     * Check if token is expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Check if token can be used
     */
    public boolean canBeUsed() {
        return this.status == ResetStatus.GENERATED && !isExpired();
    }

    /**
     * Mark token as used
     */
    public void markAsUsed() {
        this.status = ResetStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    /**
     * Mark token as expired
     */
    public void markAsExpired() {
        if (this.status == ResetStatus.GENERATED) {
            this.status = ResetStatus.EXPIRED;
        }
    }
}
