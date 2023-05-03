package com.onebox.ecommerce.service;

import com.onebox.ecommerce.controller.dtos.CartItemRS;
import com.onebox.ecommerce.controller.dtos.CartRS;
import com.onebox.ecommerce.controller.dtos.ListCartsRS;
import com.onebox.ecommerce.domain.Cart;
import com.onebox.ecommerce.domain.CartItem;
import com.onebox.ecommerce.domain.Product;
import com.onebox.ecommerce.repository.CartRepository;
import com.onebox.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartRS addNewCart() {
        UUID id =  cartRepository.create();
        Cart cart = cartRepository.findById(id);
        return convert(cart);
    }

    private List<CartItemRS> convert(List<CartItem> items){

         if(items.isEmpty()){
              return List.of();
         }

        return items.stream().map(item -> CartItemRS.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build()).collect(Collectors.toList());
    }

    @Override
    public ListCartsRS getAllCarts() {
        List<Cart> carts =  cartRepository.findAll();
        List<CartRS> cartsConverted = carts.stream().map(this::convert).collect(Collectors.toList());

        return ListCartsRS.builder().carts(cartsConverted).build();
    }

    public List<CartItemRS> convert(String cartProducts) {
        List<CartItemRS> products = new ArrayList<>();
        if (cartProducts != null && !cartProducts.isEmpty()) {
            String[] productsData = cartProducts.split(",");
            for (String productData : productsData) {
                String[] productDataArray = productData.split(":");
                CartItemRS item = new CartItemRS();
                item.setProductId(Integer.parseInt(productDataArray[0]));
                item.setQuantity(Integer.parseInt(productDataArray[1]));
                item.setPrice(Double.parseDouble(productDataArray[2]));
                products.add(item);
            }
        }
        return products;
    }

    private CartRS convert(Cart cart){
        return CartRS.builder()
                .id(cart.getId())
                .products(convert(cart.getProducts()))
                .total(cart.getTotal())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    @Override
    public CartRS getCartById(UUID id) {
        Cart cart =  cartRepository.findById(id);
        return convert(cart);
    }

    @Override
    public CartRS addProduct(UUID id, Integer productId, Integer quantity) throws IllegalStateException {
        if(quantity<=0) {
               throw new IllegalStateException("Invalid quantity");
        }

        Product product = productRepository.findById(productId);

        if (Objects.isNull(product)) {
            throw new IllegalStateException("Product not found");
        }

        if(product.getStock() < quantity) {
            throw new IllegalStateException("Product out of stock");
        }

        productRepository.updateStock(product.getProduct_seq(), product.getStock() - quantity);
        cartRepository.addProduct(id, productId,quantity, product.getPrice() * quantity);
        Cart cart = cartRepository.findById(id);
        return convert(cart);
    }

    @Override
    public CartRS removeProduct(UUID id, Integer productId,Integer quantity) throws IllegalStateException{
        if(quantity<=0) {
            throw new IllegalStateException("Invalid quantity");
        }

        Cart cart = cartRepository.findById(id);

        if(Objects.isNull(cart)) {
               throw new IllegalStateException("Cart not found");
        }

        if(cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        if(!cartRepository.isProductInCart(id,productId, quantity)) {
            throw new IllegalStateException("Product not found in cart");
        }

        Product product = productRepository.findById(productId);

        if(Objects.isNull(product)) {
              throw new IllegalStateException("Product not found");
        }

        if(product.getStock() < quantity) {
               throw new IllegalStateException("Product out of stock");
        }

        productRepository.updateStock(product.getProduct_seq(), product.getStock() + quantity);
        cartRepository.deleteProductFromCart(id, productId, quantity);

        return convert(cartRepository.findById(id));
    }

    @Override
    public Boolean delete(UUID id) throws IllegalStateException{
         Cart cart = cartRepository.findById(id);
         if(cart == null){
             throw new IllegalStateException("Cart not found");
         }

         return cartRepository.delete(id);
    }


}
