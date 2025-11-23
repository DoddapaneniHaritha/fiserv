This is a Spring Boot application that demonstrates order management integrated with Apache Kafka for messaging.
It provides REST endpoints to create orders and publish them to a Kafka topic (order-topics). The application uses KafkaProducer and KafkaConsumer classes for message handling.
Sample REST API:
POST /orders/{customerId}
Content-Type: application/json

{
   "orderId": "1236",
    "amount":12.789,
    "status" :"Single2"
}
Response:
Order sent successfully!

Flow:

The request is sent to the Kafka Producer with the topic name order-topics.
Orders with the same customerId are sent to the same partition.
Consumers read and log data from the same topic. There are three consumers in different groups; if one consumer goes down, another takes over.
Finally, the data is stored in the database.

Git Link: git clone https://github.com/DoddapaneniHaritha/fiserv.git

Run zookeeper:
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties


Run Kafka server
.\bin\windows\kafka-server-start.bat .\config\server.properties


Create a topic:
.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092   


Curl: curl --location 'http://localhost:8081/orders/Customer6' \
--header 'Content-Type: application/json' \
--data '{
    "orderId": "1236",
    "amount":12.789,
    "status" :"Single2"

}'





package com.example.kafkademo.producer;

import com.example.kafkademo.exception.CustomerNotFoundException;
import com.example.kafkademo.exception.OrderNotFoundException;
import com.example.kafkademo.service.CustomerService;
import com.example.kafkademo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomerService customerService;
    private final OrderService orderService;

    public void processOrder(String orderId, String customerId, String payload) {

        if (!orderService.orderExists(orderId)) {
            log.error("❌ Order does NOT exist: {}", orderId);
            throw new OrderNotFoundException("Order does not exist: " + orderId);
        }

        if (!customerService.customerExists(customerId)) {
            log.error("❌ Customer does NOT exist: {}", customerId);
            throw new CustomerNotFoundException("Customer does not exist: " + customerId);
        }

        String topic = "non_existing_topic";

        kafkaTemplate.send(topic, orderId, payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("❌ Failed to send message to topic '{}' | ERROR: {}", topic, ex.getMessage());
                    } else {
                        log.info("✅ Successfully sent message to topic={}", topic);
                    }
                });

        log.info("✔ Order processing COMPLETED | orderId={} | customerId={}", orderId, customerId);
    }
}
package com.example.kafkademo.controller;

import com.example.kafkademo.model.OrderResponse;
import com.example.kafkademo.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer producer;

    @PostMapping("/send")
    public OrderResponse send(@RequestParam String orderId,
                              @RequestParam String customerId) {
        try {
            producer.processOrder(orderId, customerId, "Sample Order Payload");
            return new OrderResponse(orderId, customerId, true, "Order processed successfully");
        } catch (Exception e) {
            return new OrderResponse(orderId, customerId, false, e.getMessage());
        }
    }
}
package com.example.kafkademo.service;

import com.example.kafkademo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public boolean customerExists(String customerId) {
        return repository.existsById(customerId);
    }
}
package com.example.kafkademo.repository;

import com.example.kafkademo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsById(String id);
}
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/kafka_demo
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
