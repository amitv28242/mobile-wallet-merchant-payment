package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Entity - Payment transaction recording
 */
@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_sender_id", columnList = "sender_id"),
    @Index(name = "idx_receiver_id", columnList = "receiver_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_type", columnList = "transaction_type"),
    @Index(name = "idx_ref_number", columnList = "reference_number", unique = true),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "reference_number", nullable = false, unique = true, length = 50)
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_fee", nullable = false, precision = 12, scale = 2)
    private BigDecimal transactionFee;

    @Column(name = "net_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal netAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "sender_balance_before", precision = 12, scale = 2)
    private BigDecimal senderBalanceBefore;

    @Column(name = "sender_balance_after", precision = 12, scale = 2)
    private BigDecimal senderBalanceAfter;

    @Column(name = "receiver_balance_before", precision = 12, scale = 2)
    private BigDecimal receiverBalanceBefore;

    @Column(name = "receiver_balance_after", precision = 12, scale = 2)
    private BigDecimal receiverBalanceAfter;

    @Column(name = "qr_code_id")
    private Long qrCodeId;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "reason_if_failed", length = 500)
    private String reasonIfFailed;

    @Column(name = "sender_device_id", length = 255)
    private String senderDeviceId;

    @Column(name = "sender_ip_address", length = 45)
    private String senderIpAddress;

    @Column(name = "receiver_device_id", length = 255)
    private String receiverDeviceId;

    @Column(name = "receiver_ip_address", length = 45)
    private String receiverIpAddress;

    @Column(name = "otp_verified", nullable = false)
    private Boolean otpVerified;

    @Column(name = "is_refunded", nullable = false)
    private Boolean isRefunded;

    @Column(name = "refund_transaction_id")
    private Long refundTransactionId;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public enum TransactionType {
        PAYMENT,
        TRANSFER,
        DEPOSIT,
        WITHDRAWAL,
        REFUND,
        SETTLEMENT
    }

    public enum PaymentMethod {
        WALLET,
        CARD,
        BANK_TRANSFER,
        QR_CODE,
        UPI
    }

    public enum TransactionStatus {
        INITIATED,
        PENDING,
        PROCESSING,
        SUCCESS,
        FAILED,
        CANCELLED,
        REVERSED
    }

    /**
     * Mark transaction as success
     */
    public void markAsSuccess() {
        this.status = TransactionStatus.SUCCESS;
        this.completedAt = LocalDateTime.now();
    }

    /**
     * Mark transaction as failed
     */
    public void markAsFailed(String reason) {
        this.status = TransactionStatus.FAILED;
        this.reasonIfFailed = reason;
        this.completedAt = LocalDateTime.now();
    }

    /**
     * Can be refunded
     */
    public boolean canBeRefunded() {
        return this.status == TransactionStatus.SUCCESS && 
               !this.isRefunded &&
               this.transactionType != TransactionType.REFUND;
    }

    /**
     * Mark as refunded
     */
    public void markAsRefunded(Long refundTxnId) {
        this.isRefunded = true;
        this.refundTransactionId = refundTxnId;
        this.refundedAt = LocalDateTime.now();
    }

    /**
     * Get transaction status message
     */
    public String getStatusMessage() {
        return switch (status) {
            case INITIATED -> "Transaction initiated";
            case PENDING -> "Transaction pending";
            case PROCESSING -> "Transaction processing";
            case SUCCESS -> "Transaction successful";
            case FAILED -> "Transaction failed";
            case CANCELLED -> "Transaction cancelled";
            case REVERSED -> "Transaction reversed";
        };
    }
}
