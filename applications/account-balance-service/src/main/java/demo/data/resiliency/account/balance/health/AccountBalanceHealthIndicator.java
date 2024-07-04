package demo.data.resiliency.account.balance.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Check of the health of the service based on the backing service (ValKey)
 * @author Gregory Green
 */
@Slf4j
public class AccountBalanceHealthIndicator implements HealthIndicator {

    private final RedisTemplate<String, String> redisTemplate;
    private final String healthKey;
    private final String healthValue;

    /**
     *
     * @param redisTemplate the redis template to verify ValKey
     * @param healthKey the key to use to verify ValKey
     * @param healthValue the value to use to verify ValKey
     */
    public AccountBalanceHealthIndicator(RedisTemplate<String, String> redisTemplate,
                                         @Value("${app.health.check.key:HEALTH}") String healthKey,
                                         @Value("${app.health.check.value:IGNORE}")String healthValue) {
        this.redisTemplate = redisTemplate;
        this.healthKey = healthKey;
        this.healthValue = healthValue;
    }

    /**
     *
     * @return Down if ValKey operation fails
     */
    @Override
    public Health health() {
        try{
            redisTemplate.opsForValue().set(healthKey,healthValue);
            return Health.up().build();
        }
        catch (Throwable e)
        {
            log.error("ERROR: {}",e);
            return Health.down().build();
        }

    }
}
