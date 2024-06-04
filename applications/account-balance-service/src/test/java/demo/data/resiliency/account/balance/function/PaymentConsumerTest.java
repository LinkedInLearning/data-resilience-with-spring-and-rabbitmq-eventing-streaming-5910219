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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentConsumerTest {

    private PaymentConsumer subject;
    private final Payment payment = JavaBeanGeneratorCreator.of(Payment.class).create();

    @Mock
    private BalanceRepository repository;


    @BeforeEach
    void setUp() {
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

        Balance balance = JavaBeanGeneratorCreator.of(Balance.class).create();
        when(repository.findById(anyString())).thenReturn(Optional.of(balance));
        subject.accept(payment);
        verify(repository).findById(anyString());
        verify(repository).save(any(Balance.class));
    }

    @DisplayName("Given 0 Balance When Payment Then Return Expected Amount")
    @Test
    void calculateNewBalance() {

        BigDecimal expected = BigDecimal.TEN;
        String id = "calculateNewBalance";
        Balance balance = new Balance(id,BigDecimal.ZERO);
        var payment = new Payment(balance.id(),expected);

        when(repository.findById(anyString())).thenReturn(Optional.of(balance));

        var actual = subject.calculateNewBalance(payment);

        assertThat(actual.amount()).isEqualTo(expected);
    }
}