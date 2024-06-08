package demo.data.resiliency.account.balance.domain;

import java.math.BigDecimal;
public record Payment(String id, BigDecimal amount) {
}
