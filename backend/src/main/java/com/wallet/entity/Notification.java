package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Notification Entity - User notifications
 */
@Entity
@Table(name = "notifications", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_status", columnList = "is_read"),
    @Index(name = "idx_type", columnList = "notification_type")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Column(name = "related_transaction_id")
    private Long relatedTransactionId;

    @Column(name = "related_user_id")
    private Long relatedUserId;

    @Column(name = "action_url", length = 500)
    private String actionUrl;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum NotificationType {
        PAYMENT_SENT,
        PAYMENT_RECEIVED,
        PAYMENT_FAILED,
        MONEY_ADDED,
        WITHDRAWAL_SUCCESS,
        WITHDRAWAL_FAILED,
        LOW_BALANCE,
        KYC_APPROVED,
        KYC_REJECTED,
        ACCOUNT_SUSPENDED,
        ACCOUNT_BLOCKED,
        PASSWORD_CHANGED,
        LOGIN_DETECTED,
        CARD_ADDED,
        CARD_REMOVED,
        LIMIT_EXCEEDED,
        MERCHANT_NOTIFICATION,
        SETTLEMENT_COMPLETED,
        REFUND_PROCESSED,
        GENERAL
    }

    /**
     * Mark notification as read
     */
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }

    /**
     * Get notification category
     */
    public String getCategory() {
        return switch (notificationType) {
            case PAYMENT_SENT, PAYMENT_RECEIVED, PAYMENT_FAILED -> "Payment";
            case MONEY_ADDED, WITHDRAWAL_SUCCESS, WITHDRAWAL_FAILED -> "Money";
            case KYC_APPROVED, KYC_REJECTED -> "KYC";
            case ACCOUNT_SUSPENDED, ACCOUNT_BLOCKED -> "Account";
            case CARD_ADDED, CARD_REMOVED -> "Card";
            case SETTLEMENT_COMPLETED -> "Settlement";
            case REFUND_PROCESSED -> "Refund";
            default -> "General";
        };
    }
}
