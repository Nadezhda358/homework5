package com.ludogoriesoft.inventoryService.dto;

import com.ludogoriesoft.inventoryService.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private int quantity;
    private Product product;
}
