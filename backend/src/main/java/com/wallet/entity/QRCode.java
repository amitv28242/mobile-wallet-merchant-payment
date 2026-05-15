package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * QRCode Entity - QR code generation and transaction tracking
 */
@Entity
@Table(name = "qr_codes", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_merchant_id", columnList = "merchant_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_qr_token", columnList = "qr_token", unique = true),
    @Index(name = "idx_expiry_at", columnList = "expiry_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Column(name = "qr_token", nullable = false, unique = true, length = 500)
    private String qrToken;

    @Column(name = "qr_code_image_url", nullable = false, length = 500)
    private String qrCodeImageUrl;

    @Column(name = "qr_data_encrypted", nullable = false, length = 1000)
    private String qrDataEncrypted;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal amount;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QRStatus status;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "scanned_at")
    private LocalDateTime scannedAt;

    @Column(name = "scanned_by_merchant_id")
    private Long scannedByMerchantId;

    @Column(name = "expiry_at", nullable = false)
    private LocalDateTime expiryAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "scan_count", nullable = false)
    private Integer scanCount;

    @Column(name = "scan_ip_address", length = 45)
    private String scanIpAddress;

    @Column(name = "scan_device_id", length = 255)
    private String scanDeviceId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum QRStatus {
        GENERATED,
        SCANNED,
        USED,
        EXPIRED,
        CANCELLED
    }

    /**
     * Check if QR code is expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryAt);
    }

    /**
     * Check if QR code can be used
     */
    public boolean canBeUsed() {
        return this.status == QRStatus.SCANNED && !isExpired();
    }

    /**
     * Mark QR as scanned
     */
    public void markAsScanned(Long merchantId, String ipAddress, String deviceId) {
        this.status = QRStatus.SCANNED;
        this.scannedAt = LocalDateTime.now();
        this.scannedByMerchantId = merchantId;
        this.scanIpAddress = ipAddress;
        this.scanDeviceId = deviceId;
        this.scanCount++;
    }

    /**
     * Mark QR as used
     */
    public void markAsUsed(Long txnId) {
        this.status = QRStatus.USED;
        this.usedAt = LocalDateTime.now();
        this.transactionId = txnId;
    }

    /**
     * Mark QR as expired
     */
    public void markAsExpired() {
        if (this.status != QRStatus.USED) {
            this.status = QRStatus.EXPIRED;
        }
    }

    /**
     * Mark QR as cancelled
     */
    public void markAsCancelled() {
        if (this.status != QRStatus.USED) {
            this.status = QRStatus.CANCELLED;
        }
    }

    /**
     * Check if QR is still valid for scanning
     */
    public boolean isValidForScanning() {
        return (this.status == QRStatus.GENERATED || this.status == QRStatus.SCANNED) && 
               !isExpired();
    }
}
