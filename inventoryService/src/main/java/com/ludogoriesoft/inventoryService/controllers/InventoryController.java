package com.ludogoriesoft.inventoryService.controllers;

import com.ludogoriesoft.inventoryService.dto.InventoryDTO;
import com.ludogoriesoft.inventoryService.services.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }
}
