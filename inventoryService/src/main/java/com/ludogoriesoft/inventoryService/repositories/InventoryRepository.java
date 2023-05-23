package com.ludogoriesoft.inventoryService.repositories;

import com.ludogoriesoft.inventoryService.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.product.id=:productId")
    Inventory getInventoryByProductId(@Param("productId") Long id);
}
