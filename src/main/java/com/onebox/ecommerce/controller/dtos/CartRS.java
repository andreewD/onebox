package com.onebox.ecommerce.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRS {
    private UUID id;
    private List<CartItemRS> products;
    private Double total;
    private Date createdAt;
    private Date updatedAt;
}
