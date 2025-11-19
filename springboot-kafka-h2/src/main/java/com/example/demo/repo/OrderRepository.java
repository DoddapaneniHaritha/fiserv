package com.example.demo.repo;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public class OrderRepository {


    public interface UserRepository extends JpaRepository<Order, Long> {}

}
