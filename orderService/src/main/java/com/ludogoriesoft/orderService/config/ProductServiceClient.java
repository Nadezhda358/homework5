package com.ludogoriesoft.orderService.config;

import com.ludogoriesoft.orderService.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "product-app",url = "http://localhost:8081/api/v1/products")
public interface ProductServiceClient
{
    @GetMapping(path = "{productId}")
    Product getProductById(@PathVariable("productId") Long productId);

}
