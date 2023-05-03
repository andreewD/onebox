package com.onebox.ecommerce.service;

import com.onebox.ecommerce.controller.dtos.CartItemRS;
import com.onebox.ecommerce.controller.dtos.CartRS;
import com.onebox.ecommerce.controller.dtos.ListCartsRS;
import com.onebox.ecommerce.domain.Cart;
import com.onebox.ecommerce.domain.CartItem;
import com.onebox.ecommerce.repository.CartItemRepository;
import com.onebox.ecommerce.repository.CartRepository;
import com.onebox.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public UUID addNewCart() {
        return cartRepository.create();
    }

    private List<CartItemRS> convert(List<CartItem> items){
        return items.stream().map(item -> {
            return CartItemRS.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
        }).collect(Collectors.toList());
    }
    @Override
    public ListCartsRS getAllCarts() {
        List<Cart> carts =  cartRepository.findAll();

        List<CartRS> cartsConverted =  carts.stream().map(cart -> {
            List<CartItem> cartItems = cartItemRepository.getByCartId(cart.getId());
            return CartRS.builder()
                    .id(cart.getId())
                    .total(cart.getTotal())
                    .products(convert(cartItems))
                    .createdAt(cart.getCreatedAt())
                    .updatedAt(cart.getUpdatedAt())
                    .build();}).collect(Collectors.toList());

        return ListCartsRS.builder().carts(cartsConverted).build();
    }

    @Override
    public Cart getCartById(UUID id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart addProduct(UUID id, Integer productId, Integer quantity) {

        return cartRepository.addProduct(id, productId, quantity);
    }

    @Override
    public Cart removeProduct(UUID id, Integer productId) {
        return cartRepository.removeProduct(id, productId);
    }

    @Override
    public Boolean delete(UUID id) {
         return cartRepository.delete(id);

    }


}
