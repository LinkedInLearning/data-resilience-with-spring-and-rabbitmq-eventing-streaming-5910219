package demo.data.resiliency.account.balance.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class RabbitValKeyHealthIndicator implements HealthIndicator {

    private final RedisTemplate<String, String> redisTemplate;
    private final String healthKey;
    private final String healthValue;

    public RabbitValKeyHealthIndicator(RedisTemplate<String, String> redisTemplate,
                                       @Value("${app.health.check.key:HEALTH}") String healthKey,
                                       @Value("${app.health.check.value:IGNORE}")String healthValue) {
        this.redisTemplate = redisTemplate;
        this.healthKey = healthKey;
        this.healthValue = healthValue;
    }

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
