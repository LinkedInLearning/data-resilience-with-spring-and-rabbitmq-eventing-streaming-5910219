package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MakePaymentConsumer implements Consumer<Payment> {

    private final RabbitTemplate template;
    private final String exchange;

    public MakePaymentConsumer(RabbitTemplate template, @Value("${spring.rabbitmq.payment.destination}") String exchange) {
        this.template = template;
        this.exchange = exchange;
    }

    @Override
    public void accept(Payment payment) {
        template.convertAndSend(exchange,payment.id(),payment);
    }
}
