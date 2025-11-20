package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class KafkaServiceTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private KafkaService kafkaService;

    @Test
    void testSendOrder() {
        Order order = new Order();
        order.setOrderId("ORD123");
        order.setAmount(2500.50);
        order.setStatus("NEW");

        String result = kafkaService.sendOrder("123", order);

        // Assert return value
        assertEquals("Order sent successfully!", result);


// Verify interaction with KafkaProducer
        Mockito.verify(kafkaProducer, Mockito.times(1))
                .sendOrder(Mockito.eq("123"), Mockito.eq(order));
    }
}