package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Balance;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadBalanceFunctionTest {

    private ReadBalanceFunction subject;
    private final String accountId = "test";
    private Balance expected;
    @Mock
    private BalanceRepository repository;

    @BeforeEach
    void setUp() {
        expected = JavaBeanGeneratorCreator
                .of(Balance.class).create();
    }

    @Test
    void apply() {
        when(repository.findById(anyString())).thenReturn(Optional.of(expected));

        subject = new ReadBalanceFunction(repository);

        var actual = subject.apply(accountId);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Given account id not exist WHEN apply THEN return account with ZERO balance")
    @Test
    void apply_null() {
        var expected = new Balance(accountId, BigDecimal.ZERO);
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        subject = new ReadBalanceFunction(repository);

        var actual = subject.apply(accountId);
        assertThat(actual).isEqualTo(expected);
    }
}