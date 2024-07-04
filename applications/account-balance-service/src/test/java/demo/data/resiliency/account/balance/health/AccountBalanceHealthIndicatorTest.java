package demo.data.resiliency.account.balance.health;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountBalanceHealthIndicatorTest {

    private AccountBalanceHealthIndicator subject;

    @Mock
    private RedisTemplate<String,String> redisTemplate;

    @Mock
    private ValueOperations<String, String> ops;
    private String key = "delete";
    private String value = "value";

    @BeforeEach
    void setUp() {
        subject = new AccountBalanceHealthIndicator(redisTemplate,key,value);
    }

    @Test
    void health() {
        when(redisTemplate.opsForValue()).thenReturn(ops);
        doThrow(new RuntimeException("")).when(ops).set(anyString(), anyString());

        assertThat(subject.health()).isEqualTo(Health.down().build());
    }
}