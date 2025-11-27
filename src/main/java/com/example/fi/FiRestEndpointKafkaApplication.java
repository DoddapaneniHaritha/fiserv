
package com.example.fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FiRestEndpointKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(FiRestEndpointKafkaApplication.class, args);
    }
}
