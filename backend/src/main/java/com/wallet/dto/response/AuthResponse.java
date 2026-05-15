package com.wallet.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * AuthResponse - API response for authentication
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private Boolean success;
    private String message;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private UserAuthInfo user;
    private LocalDateTime timestamp;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserAuthInfo {
        private Long id;
        private String email;
        private String phone;
        private String fullName;
        private String userType;
        private String profilePictureUrl;
        private Boolean emailVerified;
        private Boolean phoneVerified;
        private Boolean kycVerified;
    }
}
