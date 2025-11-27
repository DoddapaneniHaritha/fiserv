package com.example.fi.producer;


import com.example.fi.model.FiEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Service
public class fiProducer {

    private static final Logger log = LoggerFactory.getLogger(fiProducer.class);

    // KafkaTemplate is used to send messages to Kafka
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
     KafkaTemplate<String, String> kafkaTemplate;

    @Value("${fi.kafka.topic}")
    private String fiTopic;



    public void sendMessage(FiEventRequest request) {
        try{
            String json = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(fiTopic,json);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error sending FI event", e);
        }
    }

}

