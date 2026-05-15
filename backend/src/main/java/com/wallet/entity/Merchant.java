package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Merchant Entity - Business account for merchants
 */
@Entity
@Table(name = "merchants", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id", unique = true),
    @Index(name = "idx_status", columnList = "registration_status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "business_name", nullable = false, length = 255)
    private String businessName;

    @Column(name = "business_category", nullable = false, length = 100)
    private String businessCategory;

    @Column(name = "gst_number_encrypted", nullable = false, length = 500)
    private String gstNumberEncrypted;

    @Column(name = "business_address", nullable = false, length = 500)
    private String businessAddress;

    @Column(name = "business_city", length = 100)
    private String businessCity;

    @Column(name = "business_state", length = 100)
    private String businessState;

    @Column(name = "business_pincode", length = 10)
    private String businessPincode;

    @Column(name = "business_latitude", precision = 10, scale = 6)
    private Double businessLatitude;

    @Column(name = "business_longitude", precision = 10, scale = 6)
    private Double businessLongitude;

    @Column(name = "business_phone", length = 10)
    private String businessPhone;

    @Column(name = "business_email", length = 255)
    private String businessEmail;

    @Column(name = "business_website", length = 255)
    private String businessWebsite;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status", nullable = false)
    private RegistrationStatus registrationStatus;

    @Column(name = "kyc_document_url", length = 500)
    private String kycDocumentUrl;

    @Column(name = "is_kyc_verified", nullable = false)
    private Boolean isKycVerified;

    @Column(name = "kyc_verified_at")
    private LocalDateTime kycVerifiedAt;

    @Column(name = "bank_account_count", nullable = false)
    private Integer bankAccountCount;

    @Column(name = "transaction_count", nullable = false)
    private Integer transactionCount;

    @Column(name = "total_volume_processed", precision = 15, scale = 2)
    private java.math.BigDecimal totalVolumeProcessed;

    @Column(name = "settlement_cycle_days", nullable = false)
    private Integer settlementCycleDays;

    @Column(name = "next_settlement_date")
    private LocalDateTime nextSettlementDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum RegistrationStatus {
        PENDING,
        VERIFICATION_PENDING,
        APPROVED,
        REJECTED,
        SUSPENDED
    }

    /**
     * Mark KYC as verified
     */
    public void markKycAsVerified() {
        this.isKycVerified = true;
        this.kycVerifiedAt = LocalDateTime.now();
        this.registrationStatus = RegistrationStatus.APPROVED;
    }

    /**
     * Can receive payments
     */
    public boolean canReceivePayments() {
        return this.registrationStatus == RegistrationStatus.APPROVED && 
               this.isKycVerified &&
               this.bankAccountCount > 0;
    }

    /**
     * Get business location
     */
    public String getBusinessLocation() {
        return businessCity + ", " + businessState + " - " + businessPincode;
    }
}
