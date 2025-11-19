package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


        @Autowired
        KafkaProducer producer;

        @PostMapping("/{customerId}")
        public String createOrder(@PathVariable String customerId, @RequestBody Order order) {
            producer.sendOrder(customerId, order);
            return "Order sent successfully!";
        }
    }

