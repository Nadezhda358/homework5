package com.ludogoriesoft.orderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long id;
    private String customer;
    private List<Long> productIds;
    private List<Integer> quantities;
}
