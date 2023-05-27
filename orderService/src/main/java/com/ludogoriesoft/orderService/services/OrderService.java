package com.ludogoriesoft.orderService.services;

import com.ludogoriesoft.orderService.config.ProductServiceClient;
import com.ludogoriesoft.orderService.dto.OrderDTO;
import com.ludogoriesoft.orderService.dto.OrderRequest;
import com.ludogoriesoft.orderService.entities.Order;
import com.ludogoriesoft.orderService.exceptions.ApiRequestException;
import com.ludogoriesoft.orderService.repositories.OrderProductRepository;
import com.ludogoriesoft.orderService.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public OrderDTO createOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setCustomer(orderRequest.getCustomer());
        orderRepository.save(order);
        try{
            orderProductsService.createOrderProductsFromOrderRequest(orderRequest, order);
        }catch (Exception e){
            throw new ApiRequestException("Product not found");
        }
        return orderToOrderDTO(order);
    }
}
