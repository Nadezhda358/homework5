package com.ludogoriesoft.orderService.services;

import com.ludogoriesoft.orderService.config.ProductServiceClient;
import com.ludogoriesoft.orderService.dto.OrderDTO;
import com.ludogoriesoft.orderService.dto.OrderRequest;
import com.ludogoriesoft.orderService.entities.Order;
import com.ludogoriesoft.orderService.repositories.OrderProductRepository;
import com.ludogoriesoft.orderService.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderProductsService orderProductsService;
    public OrderDTO orderToOrderDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }
    public List<OrderDTO> getAllOrders() {
        List<Order> inventories = orderRepository.findAll();
        return inventories
                .stream()
                .map(this::orderToOrderDTO)
                .collect(Collectors.toList());
    }
    public OrderDTO createOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setCustomer(orderRequest.getCustomer());
        orderRepository.save(order);
        orderProductsService.createOrderProductsFromOrderRequest(orderRequest, order);
        return orderToOrderDTO(order);
    }
}
