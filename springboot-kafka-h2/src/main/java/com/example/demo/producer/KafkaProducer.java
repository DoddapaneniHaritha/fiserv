package com.example.demo.producer;


import com.example.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {
    @Autowired
    KafkaTemplate<String, Order> kafkaTemplate;
    public void sendOrder(String customerId, Order order) {
        kafkaTemplate.send("orders-topics", customerId, order)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent Key: " + customerId +
                                " | Partition: " + result.getRecordMetadata().partition() +
                                " | Offset: " + result.getRecordMetadata().offset());
                    } else {
                        System.err.println("Error sending message: " + ex.getMessage());
                    }
                });
    }
}












//package com.example.demo;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//@Service
//public class KafkaProducer {
//    @Autowired
//    KafkaTemplate<String, String> kafkaTemplate;
//
////    @Value("${Kafka.topic}")
////    String topic;
//    public void sendMessage(String message)
//    {
//        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("sa-events", message);
//        producerRecord.headers().add("usertype", "Harry".getBytes());
////        kafkaTemplate.send(topic, message);
//        kafkaTemplate.send(producerRecord);
//    }
//
//}