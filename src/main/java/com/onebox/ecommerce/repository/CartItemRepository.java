package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository {
    CartItem create(CartItem cartItem);
    CartItem update(CartItem cartItem);
    void delete(Integer id);
    List<CartItem> getByCartId(UUID cartId);
}
