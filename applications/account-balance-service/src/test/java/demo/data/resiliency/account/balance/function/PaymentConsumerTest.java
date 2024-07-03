package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Balance;
import demo.data.resiliency.account.balance.domain.Payment;
import demo.data.resiliency.account.balance.repository.BalanceRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentConsumerTest {

    private PaymentConsumer subject;
    private  Payment payment;

    @Mock
    private BalanceRepository repository;


    @BeforeEach
    void setUp() {

        payment = JavaBeanGeneratorCreator.of(Payment.class).create();
        subject = new PaymentConsumer(repository);
    }

    @DisplayName("Given No Existing Balance When Accept Then Return Not Empty Balance")
    @Test
    void accept() {
        subject.accept(payment);
        verify(repository).findById(anyString());
        verify(repository).save(any(Balance.class));
    }

    @DisplayName("Given Existing Balance When Accept Then Return Not Empty Balance")
    @Test
    void accept_when_Records_Exist() {

        Balance balance = new Balance(this.payment.id(),BigDecimal.ZERO,this.payment.timestamp().minusSeconds(1));

        when(repository.findById(anyString())).thenReturn(Optional.of(balance));

        subject.accept(payment);
        verify(repository).findById(anyString());
        verify(repository).save(any(Balance.class));
    }

    @DisplayName("Given no previous balance When calculateNewBalance Then amount equals payment")
    @Test
    void noPreviousBalance() {

        var actual = subject.calculateNewBalance(payment);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().amount()).isEqualTo(payment.amount());

    }

    @DisplayName("Given 0 Balance When Payment Then Return Expected Amount")
    @Test
    void calculateNewBalance() {

        BigDecimal expected = BigDecimal.TEN;
        String id = "calculateNewBalance";
        LocalDateTime expectedTimestamp = LocalDateTime.now();
        Balance balance = new Balance(id,BigDecimal.ZERO,expectedTimestamp.minusSeconds(1));
        var payment = new Payment(balance.id(),expected,expectedTimestamp);

        when(repository.findById(anyString())).thenReturn(Optional.of(balance));

        var actual = subject.calculateNewBalance(payment);

        assertThat(actual).isNotEmpty();
        assertThat(actual.get().amount()).isEqualTo(expected);
    }

    @DisplayName("Given duplicate when accept then remains the same")
    @Test
    void duplicatePayment() {

        Payment firstPayment = JavaBeanGeneratorCreator.of(Payment.class).create();

        Balance firstBalance = new Balance(firstPayment.id(),BigDecimal.ZERO,firstPayment.timestamp().minusDays(1));
        Balance secondBalance = new Balance(firstPayment.id(),BigDecimal.ZERO,firstPayment.timestamp());
        when(repository.findById(anyString()))
                .thenReturn(Optional.of(firstBalance))
                .thenReturn(Optional.of(secondBalance));

        var first = subject.calculateNewBalance(firstPayment);
        assertThat(first).isNotEmpty();

        var second = subject.calculateNewBalance(firstPayment);
        assertThat(second).isEmpty();

    }
}