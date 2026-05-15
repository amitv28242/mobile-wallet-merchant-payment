package com.wallet.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentResponse - API response for payment transaction
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long transactionId;
    private String transactionRef;
    private Boolean success;
    private String status;
    private String message;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal netAmount;
    private LocalDateTime completedAt;
    private String receiverName;
    private BigDecimal senderNewBalance;
    private BigDecimal receiverNewBalance;
}
