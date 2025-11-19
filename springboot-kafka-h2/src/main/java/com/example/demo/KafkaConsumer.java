package com.example.demo;

import com.example.demo.entity.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "orders-topics", groupId = "my-group")
    public void consumeOrder(ConsumerRecord<String, Order> record, Acknowledgment ack) {

        try {

            System.out.println("Key: " + record.key() +
                    " | Partition: " + record.partition() +
                    " | OrderId: " + record.value().getOrderId() +
                    " | Status: " + record.value().getStatus());

            // Process the message

            ack.acknowledge(); // Commit offset after successful processing
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            System.err.println("Error processing message: " + e.getMessage());
        }
    }


        public void consumeOrder(ConsumerRecord<String, Order> record) {
            System.out.println("[Group-B] Key: " + record.key() +
                    " | OrderId: " + record.value().getOrderId() +
                    " | Status: " + record.value().getStatus());
        }
    }
