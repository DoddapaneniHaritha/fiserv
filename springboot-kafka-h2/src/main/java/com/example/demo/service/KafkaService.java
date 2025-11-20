package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    KafkaProducer kafkaproducer;

    public String sendOrder(String customerId, Order order)
    {
        // Delegate to KafkaProducer to send the order to Kafka topic
        kafkaproducer.sendOrder(customerId,order);
        return "Order sent successfully!";
    }
}
