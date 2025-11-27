
package com.example.fi.service;


import com.example.fi.model.FiEventRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"fi-events"})
class FiEventServiceTest {

    @Autowired
    private FiEventService fiEventService;



    @Test
    void processEvent_success() {
        FiEventRequest event = new FiEventRequest();
        event.setEventId("evt-001");
        event.setEventType("PAYMENT");
        event.setFiId("FI_001");

        String result = fiEventService.processEvent(event);
        assertTrue(result.contains("FI Event Publish Successfully"));
    }
}
