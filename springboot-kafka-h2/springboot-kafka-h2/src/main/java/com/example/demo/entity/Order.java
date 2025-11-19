package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id

    private String orderId;
    private double amount;
    private String status;

    // Getters and Setters
}
