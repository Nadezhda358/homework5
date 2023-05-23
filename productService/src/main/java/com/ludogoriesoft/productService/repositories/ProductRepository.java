package com.ludogoriesoft.productService.repositories;

import com.ludogoriesoft.productService.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
