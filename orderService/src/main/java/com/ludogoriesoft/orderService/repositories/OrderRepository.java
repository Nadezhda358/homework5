package com.ludogoriesoft.orderService.repositories;

import com.ludogoriesoft.orderService.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
