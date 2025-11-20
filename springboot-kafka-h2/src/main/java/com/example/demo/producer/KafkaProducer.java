package com.example.demo.producer;

import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    // KafkaTemplate is used to send messages to Kafka
    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendOrder(String customerId, Order order) {
        kafkaTemplate.send("orders-topics", customerId, order)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        // Log success with partition and offset details
                        logger.info("Sent Key: {} | Partition: {} | Offset: {}",
                                customerId,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        // Log error with exception details
                        logger.error("Error sending message: {}", ex.getMessage(), ex);
                    }
                });
    }
}