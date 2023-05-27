package com.ludogoriesoft.orderService.services;

import com.ludogoriesoft.orderService.config.ProductServiceClient;
import com.ludogoriesoft.orderService.dto.OrderRequest;
import com.ludogoriesoft.orderService.entities.Order;
import com.ludogoriesoft.orderService.entities.OrderProduct;
import com.ludogoriesoft.orderService.entities.Product;
import com.ludogoriesoft.orderService.exceptions.ApiRequestException;
import com.ludogoriesoft.orderService.repositories.OrderProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderProductsService {
    private final OrderProductRepository orderProductRepository;
    private final ProductServiceClient productServiceClient;

    public void createOrderProductsFromOrderRequest(OrderRequest orderRequest, Order order){
        for (int i = 0; i < orderRequest.getProductIds().size(); i++) {
            Long productId = orderRequest.getProductIds().get(i);
            Integer quantity;
            try{
                quantity = orderRequest.getQuantities().get(i);
            }catch (Exception e){
                quantity = 1;
            }
            Product product = productServiceClient.getProductById(productId);
            if (product == null) {
                throw new ApiRequestException("Product not found");
            }
            OrderProduct orderProduct = new OrderProduct(null, order, product, quantity);
            orderProductRepository.save(orderProduct);
        }
    }
}
