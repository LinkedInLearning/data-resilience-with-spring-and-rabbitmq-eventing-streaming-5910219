package demo.data.resiliency.account.balance;

import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    ConnectionNameStrategy connectionNameStrategy()
    {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }
}
