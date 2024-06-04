package demo.data.resiliency.account.balance.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 *  "AccountId": "22289",
 *         "Amount": {
 *           "Amount": "1230.00",
 *           "Currency": "GBP"
 *         }
 */
public record Balance(@Id  String id, BigDecimal amount){
}
