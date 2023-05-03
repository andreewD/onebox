package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.Cart;

import java.util.List;
import java.util.UUID;

public interface CartRepository {
    List<Cart> findAll();
    Cart findById(UUID id);
    UUID create();
    boolean isProductInCart(UUID cartId, Integer productId, Integer quantity);
    Cart addProduct(UUID id, Integer productId, Integer quantity,Double price);
    Boolean delete(UUID id);
    void update(Cart cart);
    void deleteProductFromCart(UUID cartId, Integer productId, Integer quantity);
    List<Cart> findInactiveCarts();
    void deleteAll(List<Cart> inactiveCarts);
}
