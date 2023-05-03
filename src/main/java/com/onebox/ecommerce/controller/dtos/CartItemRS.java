package com.onebox.ecommerce.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRS {
    private Integer productId;
    private Integer quantity;
    private Double price;
}
