package demo.data.resiliency.account.balance.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @param id the payment unique ID
 * @param amount the payment amount
 * @param timestamp the time stamp
 */
public record Payment(String id, BigDecimal amount,LocalDateTime timestamp) {
}
