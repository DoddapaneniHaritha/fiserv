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
            Order order = record.value(); // ✅ Get the actual Order object
            Order savedOrder = orderRepository.save(order);

            log.info("Order Received -> ID: {} | Amount: {} | Status: {}",
                    order.getOrderId(), order.getAmount(), order.getStatus());

            System.out.println("Key: " + record.key() +
                    " | Partition: " + record.partition() +
                    " | OrderId: " + savedOrder.getOrderId() +
                    " | Status: " + savedOrder.getStatus()+
                    " | Amount :"  + savedOrder.getAmount());



            ack.acknowledge(); // Commit offset after successful processing
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "orders-topics", groupId = "group-B")
    public void consumeGroupB(ConsumerRecord<String, String> record) {
        log.info("[Group-B] Key: {} | Value: {} | Partition: {} | Offset: {}",
                record.key(), record.value(), record.partition(), record.offset());
    }

    @KafkaListener(topics = "orders-topics", groupId = "group-C")
    public void consumeGroupC(ConsumerRecord<String, Order> record) {
        log.info("[Group-C] {}", record.value());
    }

}