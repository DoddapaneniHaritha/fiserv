package com.example.demo.controller;

import com.example.demo.entity.Order;

import com.example.demo.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cards")
public class CardEventController {
@Autowired
KafkaService service;

    private static final Logger log = LoggerFactory.getLogger(CardEventController.class);

    @PostMapping("/events")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> order) {


      service.sendOrder(order);


        return ResponseEntity.status(HttpStatus.CREATED).body("Event saved successfully");



    }
}

