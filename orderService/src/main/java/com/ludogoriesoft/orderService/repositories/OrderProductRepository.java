package com.ludogoriesoft.orderService.repositories;

import com.ludogoriesoft.orderService.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
