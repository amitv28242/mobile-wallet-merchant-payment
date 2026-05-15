package com.wallet.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * User Entity - Core user account (Consumer, Merchant, Admin)
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_phone", columnList = "phone", unique = true),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_user_type", columnList = "user_type")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "phone", nullable = false, unique = true, length = 10)
    private String phone;

    @Column(name = "password_hash", nullable = false, length = 500)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "aadhar_number_encrypted", length = 500)
    private String aadharNumberEncrypted;

    @Column(name = "pan_number_encrypted", length = 500)
    private String panNumberEncrypted;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified;

    @Column(name = "is_phone_verified", nullable = false)
    private Boolean isPhoneVerified;

    @Column(name = "is_kyc_verified", nullable = false)
    private Boolean isKycVerified;

    @Column(name = "kyc_level", nullable = false)
    private Integer kycLevel;

    @Column(name = "is_two_factor_enabled", nullable = false)
    private Boolean isTwoFactorEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_login_device", length = 500)
    private String lastLoginDevice;

    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Wallet wallet;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Merchant merchant;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum UserType {
        CONSUMER,
        MERCHANT,
        ADMIN
    }

    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        BLOCKED,
        DELETED
    }

    /**
     * Get full name
     */
    public String getFullName() {
        return firstName + (lastName != null ? " " + lastName : "");
    }

    /**
     * Update last login information
     */
    public void updateLastLogin(String device, String ipAddress) {
        this.lastLoginAt = LocalDateTime.now();
        this.lastLoginDevice = device;
        this.lastLoginIp = ipAddress;
    }

    /**
     * Check if user can perform transactions
     */
    public boolean canPerformTransactions() {
        return this.status == UserStatus.ACTIVE && 
               this.isEmailVerified && 
               this.isPhoneVerified;
    }

    /**
     * Check if 2FA is enabled
     */
    public boolean isTwoFactorRequired() {
        return this.isTwoFactorEnabled;
    }
}
