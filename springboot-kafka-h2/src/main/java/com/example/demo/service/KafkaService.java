package com.example.demo.service;

import com.example.demo.entity.Order;
;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaService {


    private static final Logger log = LoggerFactory.getLogger(KafkaService.class);

    public void sendOrder(Map<String, Object>  order)
    {


        // Log the payload using parameterized logging
        log.info("Received bank event payload: {}", order);

        // Force error simulation
        if ("true".equalsIgnoreCase(String.valueOf(order.get("force-error")))) {
            log.error("Force error triggered by request payload");
            throw new RuntimeException("Forced error as requested");
        }

    }
}
