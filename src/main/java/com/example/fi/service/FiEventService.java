
package com.example.fi.service;



import com.example.fi.model.FiEventRequest;
import com.example.fi.producer.fiProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FiEventService {

   @Autowired
   fiProducer fiproducer;

    public String processEvent(FiEventRequest request) {
        // Validate FI details
        if (request.getFiId() == null || request.getFiId().isBlank()) {
            throw new IllegalArgumentException("FI ID is missing");
        }
        if  (!request.getFiId().startsWith("FI_")) {
            throw new IllegalArgumentException("Not a Valid FI Client:");
        }


        // Publish to Kafka topic
     fiproducer.sendMessage(request);
        return "FI Event Publish Successfully";
    }
}
