package demo.data.resiliency.account.balance;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.payment.destination}")
    private String exchange;

    @Bean
    TopicExchange exchange()
    {
        return new TopicExchange(exchange);
    }

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }
}
