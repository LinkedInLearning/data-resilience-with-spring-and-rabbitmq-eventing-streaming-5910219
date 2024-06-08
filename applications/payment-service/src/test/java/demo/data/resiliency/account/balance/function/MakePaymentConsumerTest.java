package demo.data.resiliency.account.balance.function;

import demo.data.resiliency.account.balance.domain.Payment;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MakePaymentConsumerTest {

    private MakePaymentConsumer subject;

    @Mock
    private RabbitTemplate template;
    private final Payment payment = JavaBeanGeneratorCreator.of(Payment.class).create();

    @BeforeEach
    void setUp() {
        subject = new MakePaymentConsumer(template, "exchange");
    }

    @Test
    void accept() {

        subject.accept(payment);

        Mockito.verify(template).convertAndSend(anyString(),anyString(),any(Payment.class));
    }
}