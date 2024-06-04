package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Balance;
import demo.data.resiliency.account.balance.domain.Payment;
import demo.data.resiliency.account.balance.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * @author Gregory Green
 * Spring Cloud Stream RabbitMQ consumer to recieve payments
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer implements Consumer<Payment> {

    private final BalanceRepository repository;

    /**
     * Calculate and save the new balance based on a given payment
     * @param payment the incoming payment
     */
    @Override
    public void accept(Payment payment) {
        repository.save(calculateNewBalance(payment));
    }

    /**
     * Calculate a new balance based on the current payment
     * @param payment the current payment
     * @return the new calculated payment
     */
    Balance calculateNewBalance(Payment payment) {
      log.info("Received payment: {}",payment);

      var balance = repository.findById(payment.id()).orElse(new Balance(payment.id(), BigDecimal.ZERO));

        log.info("Currently previous: {}",balance);

      return new Balance(balance.id(),balance.amount().add(payment.amount()));
    }
}
