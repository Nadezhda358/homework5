package com.ludogoriesoft.orderService.controller;

import com.ludogoriesoft.orderService.dto.OrderDTO;
import com.ludogoriesoft.orderService.dto.OrderRequest;
import com.ludogoriesoft.orderService.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderRequest orderRequest, UriComponentsBuilder uriComponentsBuilder) {
        URI location = uriComponentsBuilder.path("/api/v1/orders/{id}")
                .buildAndExpand(orderService.createOrder(orderRequest).getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
