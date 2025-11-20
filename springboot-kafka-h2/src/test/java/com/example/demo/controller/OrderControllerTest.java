package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.service.KafkaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaService kafkaService;

    @Test
    void testCreateOrder() throws Exception {

        String requestbody = "{\n" +
                "    \"orderId\": \"1236\",\n" +
                "    \"amount\":12.789,\n" +
                "    \"status\" :\"Single2\"\n" +
                "\n" +
                "}";

        Mockito.when(kafkaService.sendOrder(eq("123"), any(Order.class)))
                .thenReturn("Order sent successfully!");

        mockMvc.perform(post("/orders/123")
                        .contentType("application/json")
                        .content(requestbody))
                .andExpect(status().isOk())
                .andExpect(content().string("Order sent successfully!"));


        verify(kafkaService, times(1)).sendOrder(eq("123"),any());
    }
}