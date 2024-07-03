package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * <b>MakePaymentConsumer</b> accepts a payment to publishes to
 * RabbitMQ using a configured RabbitTemplate.
 *
 * @author Gregory Green
 */
@Component
public class MakePaymentConsumer implements Consumer<Payment> {

    private final RabbitTemplate template;
    private final String exchange;

    /**
     *
     * @param template the RabbitMQ Template
     * @param exchange the Rabbit exchange topic to publish payment
     */
    public MakePaymentConsumer(RabbitTemplate template, @Value("${spring.rabbitmq.payment.destination}") String exchange) {
        this.template = template;
        this.exchange = exchange;
    }

    /**
     * This method will send the given payment to Rabbit
     * @param payment the payment to make
     */
    @Override
    public void accept(Payment payment) {
        template.convertAndSend(exchange,payment.id(),payment);
    }
}
