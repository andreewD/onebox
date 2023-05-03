package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.Cart;

import java.util.List;
import java.util.UUID;

public interface CartRepository {
    List<Cart> findAll();
    Cart findById(UUID id);
    UUID create();
    Cart addProduct(UUID id, Integer productId, Integer quantity);
    Cart removeProduct(UUID id, Integer productId);
    Boolean delete(UUID id);
}
