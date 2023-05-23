package com.ludogoriesoft.productService.services;

import com.ludogoriesoft.productService.dto.ProductDTO;
import com.ludogoriesoft.productService.entities.Product;
import com.ludogoriesoft.productService.exeptions.ApiRequestException;
import com.ludogoriesoft.productService.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    public ProductDTO productToProductDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(this::productToProductDTO)
                .collect(Collectors.toList());
    }
    public ProductDTO createProduct(Product product) {
        productRepository.save(product);
        return productToProductDTO(product);
    }
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ApiRequestException("Product not found");
        }
        return productToProductDTO(product.get());
    }

    public ProductDTO updateProduct(Long id, Product product) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            throw new ApiRequestException("Product not found");
        }
        foundProduct.get().setName(product.getName());
        foundProduct.get().setPrice(product.getPrice());
        foundProduct.get().setManufacturer(product.getManufacturer());
        foundProduct.get().setDescription(product.getDescription());
        productRepository.save(foundProduct.get());
        return productToProductDTO(foundProduct.get());
    }
    public int deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
            return 1;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}
