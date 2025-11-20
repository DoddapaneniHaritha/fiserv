package com.example.demo.consumer;

import com.example.demo.entity.Order;
import com.example.demo.repo.OrderRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private OrderRepository orderRepository;

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "orders-topics", groupId = "my-group")
    public void consumeOrder(ConsumerRecord<String, Order> record, Acknowledgment ack) {
        try {
            // Extract the Order object from the Kafka message
            Order order = record.value();

            // Save the order to the database
            Order savedOrder = orderRepository.save(order);

            // Log order details
            log.info("Order Received -> ID: {} | Amount: {} | Status: {}",
                    order.getOrderId(), order.getAmount(), order.getStatus());

            // Log Kafka message metadata and saved order details
            log.info("Key: {} | Partition: {} | OrderId: {} | Status: {} | Amount: {}",
                    record.key(),
                    record.partition(),
                    savedOrder.getOrderId(),
                    savedOrder.getStatus(),
                    savedOrder.getAmount());

            // Commit the offset after successful processing
            ack.acknowledge();
        } catch (Exception e) {
            // Log any error that occurs during processing
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }


    @KafkaListener(topics = "orders-topics", groupId = "group-B") // belongs to same Topic but with diff group name
    public void consumeGroupB(ConsumerRecord<String, String> record) {
        log.info("[Group-B] Key: {} | Value: {} | Partition: {} | Offset: {}",
                record.key(), record.value(), record.partition(), record.offset());
    }

    @KafkaListener(topics = "orders-topics", groupId = "group-C")
    public void consumeGroupC(ConsumerRecord<String, Order> record) {
        log.info("[Group-C] {}", record.value());
    }
}