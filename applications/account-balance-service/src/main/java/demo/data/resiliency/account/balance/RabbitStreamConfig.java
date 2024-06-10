package demo.data.resiliency.account.balance;

import com.rabbitmq.stream.OffsetSpecification;
import demo.data.resiliency.account.balance.repository.BalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@Configuration
@Slf4j
@Profile("stream")
public class RabbitStreamConfig {

    @Value("${rabbitmq.stream.offset:last}")
    private String offset;

    @Value("${spring.application.name}")
    private String applicationName;


    @Bean
    ListenerContainerCustomizer<MessageListenerContainer> containerCustomizer(BalanceRepository repository) {
        return (cont, dest, group) -> {
            if(cont instanceof StreamListenerContainer container)
            {
                var cnt = repository.count();
                log.info("Balance count: {}",cnt);
                if(cnt == 0)
                {
                    container.setConsumerCustomizer((name, builder) -> {
                        builder.subscriptionListener(context ->{
                            log.info("Replaying from the first record in the stream");
                            context.offsetSpecification(OffsetSpecification.first());
                        });
                    });
                }
                else
                {
                    log.info("Setting offset to last with name: {}",applicationName);
                    container.setConsumerCustomizer((name, builder) -> {
                        builder.name(applicationName);
                        builder.offset(OffsetSpecification.last());
                    });

                }

            }
        };
    }
}
