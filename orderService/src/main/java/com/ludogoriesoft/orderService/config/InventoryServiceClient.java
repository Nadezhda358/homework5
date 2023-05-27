package com.ludogoriesoft.orderService.config;

import com.ludogoriesoft.orderService.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "inventory-app",url = "http://localhost:8082/api/v1/inventories")
public interface InventoryServiceClient
{
    @PutMapping(path = "{productId}")
    InventoryDTO updateInventoryById(@PathVariable("productId") Long productId, InventoryDTO inventoryDTO);
    @GetMapping(path = "{productId}")
    InventoryDTO getInventoryByProductId(@PathVariable("productId") Long productId);
}
