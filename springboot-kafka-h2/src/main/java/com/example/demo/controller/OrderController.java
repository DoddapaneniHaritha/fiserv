package com.example.demo.controller;

import com.example.demo.entity.Order;

import com.example.demo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    KafkaService kafkaservice;

    @PostMapping("/{customerId}")
    public ResponseEntity<String> createOrder(@PathVariable String customerId, @RequestBody Order order) {

        //Delegate to KafkaService to send the order to Kafka topic
        String response = kafkaservice.sendOrder(customerId, order);
        return ResponseEntity.ok(response);

    }
}

