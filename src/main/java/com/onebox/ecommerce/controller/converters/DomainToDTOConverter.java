package com.onebox.ecommerce.controller.converters;

import com.onebox.ecommerce.controller.dtos.CartItemRS;
import com.onebox.ecommerce.controller.dtos.CartRS;
import com.onebox.ecommerce.controller.dtos.ProductRS;
import com.onebox.ecommerce.domain.Cart;
import com.onebox.ecommerce.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class DomainToDTOConverter {
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
    public CartRS convert(Cart cart) {
        return CartRS.builder()
                .id(cart.getId())
                .products(convert(cart.getProducts()))
                .total(cart.getTotal())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    public ProductRS convert(Product product){
        return ProductRS.builder()
                .id(product.getProduct_seq())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

}
