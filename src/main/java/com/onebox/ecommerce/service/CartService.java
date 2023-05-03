package com.onebox.ecommerce.service;

import com.onebox.ecommerce.controller.dtos.ListCartsRS;
import com.onebox.ecommerce.domain.Cart;
import com.onebox.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface CartService {
    UUID addNewCart();
    ListCartsRS getAllCarts();
    Cart getCartById(UUID id);
    Cart addProduct(UUID id, Integer productId, Integer quantity);
    Cart removeProduct(UUID id, Integer productId);
    Boolean delete(UUID id);

}
