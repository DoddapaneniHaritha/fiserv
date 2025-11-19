package com.example.demo;
import com.example.demo.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.beans.factory.annotation.Autowired;
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
public class KafkaIntegrationTest {
    @Autowired
    private KafkaProducer producer;
//    @Test
//    public void testKafka() { producer.sendOrder( "Hello Kafka"); }
}