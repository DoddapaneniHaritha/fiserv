package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootApplication
//@EmbeddedKafka(partitions = 1, topics = { "sample-events" })
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}