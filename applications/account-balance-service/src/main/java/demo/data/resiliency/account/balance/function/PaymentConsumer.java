package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Balance;
import demo.data.resiliency.account.balance.domain.Payment;
import demo.data.resiliency.account.balance.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Gregory Green
 * Spring Cloud Stream RabbitMQ consumer to process payments
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

        var balance = calculateNewBalance(payment);
        balance.ifPresent(repository::save);
    }

    /**
     * Calculate a new balance based on the current payment
     * @param payment the current payment
     * @return the new calculated payment
     */
    Optional<Balance> calculateNewBalance(Payment payment) {
      log.info("Received payment: {}",payment);

      var balanceOptional = repository.findById(payment.id());

      if(balanceOptional.isEmpty()){
          log.info("Previous balance NOT found for payment: {}",payment);
          return Optional.of(new Balance(payment.id(), payment.amount(),payment.timestamp()));
      }

      var balance = balanceOptional.get();

        log.info("balance: {}",balance);
        log.info("Checking if duplicate payment balance.lastPaymentTimestamp:{}, is after payment {}",
                balance.lastPaymentTimestamp(),
                payment.timestamp());

        if(!balance.lastPaymentTimestamp().isBefore(payment.timestamp()))
            return Optional.empty();
        else
            return Optional.of(new Balance(balance.id(),
              balance.amount().add(payment.amount()), //add payment
              balance.lastPaymentTimestamp()));
    }
}
