package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    private Long OrgId;
    private double amount;
    private String EventType;

    // Getters and Setters
}

