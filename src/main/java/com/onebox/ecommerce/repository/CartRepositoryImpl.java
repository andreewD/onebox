package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.Cart;
import com.onebox.ecommerce.domain.CartItem;
import com.onebox.ecommerce.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CartRepositoryImpl implements CartRepository{
    @Value("${cart.inactive.time}")
    private Long cartInactiveTime;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public List<Cart> findAll() {
        return entityManager.createQuery("SELECT c FROM Cart c", Cart.class).getResultList();
    }

    @Override
    public Cart findById(UUID id) {
        return entityManager.find(Cart.class, id);
    }

    @Override
    public List<Cart> findInactiveCarts() {
        Long currentTime = System.currentTimeMillis();
        long inactivityTime = currentTime - (cartInactiveTime);
        return entityManager.createQuery("SELECT c FROM Cart c WHERE c.updatedAt < :inactivityTime", Cart.class)
                .setParameter("inactivityTime", new Date(inactivityTime))
                .getResultList();
    }

    @Override
    public void deleteAll(List<Cart> inactiveCarts) {
        for (Cart cart : inactiveCarts) {
            entityManager.remove(cart);
        }
    }

    @Override
    public UUID create() {
        Cart cart = new Cart();
        cart.setProducts("");
        cart.setTotal(0.0);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());
        entityManager.persist(cart);
        return cart.getId();
    }

    private List<CartItem> buildCartItems(String products) {
        List<CartItem> cartItems = new ArrayList<>();
        if (products != null && !products.isEmpty()) {
            List<String> productsString = Arrays.asList(products.split(","));
            cartItems = productsString.stream().map(product -> {
                List<String> productString = Arrays.asList(product.split(":"));
                return new CartItem(Integer.parseInt(productString.get(0)),
                        Integer.parseInt(productString.get(1)),
                        Double.parseDouble(productString.get(2)));
            }).collect(Collectors.toList());
        }
        return cartItems;
    }

    private String buildProductsString(List<CartItem> products) {
        List<String> productsString = new ArrayList<>();
        for (CartItem product : products) {
            productsString.add(product.getProductId() + ":" + product.getQuantity() + ":" + product.getPrice());
        }
        return String.join(",", productsString);
    }

    @Override
    public Cart addProduct(UUID id, Integer productId, Integer quantity, Double price) {
        Cart cart = entityManager.find(Cart.class, id);

        List<CartItem> products = buildCartItems(cart.getProducts());

        // Check if product is already in cart
        Optional<CartItem> itemOptional = products.stream()
                .filter(item -> item.getProductId().equals(productId)).findFirst();

        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setPrice(item.getPrice() + (price));

            products.set(products.indexOf(item), item);
        } else {
            CartItem item = new CartItem(productId, quantity,  price);
            products.add(item);
        }

        cart.setProducts(buildProductsString(products));
        cart.setTotal(cart.getTotal() + (price));
        cart.setUpdatedAt(new Date());
        entityManager.merge(cart);
        return cart;
    }

    @Override
    public boolean isProductInCart(UUID cartId, Integer productId, Integer quantity) {
        Cart cart = entityManager.find(Cart.class, cartId);

        List<CartItem> products = buildCartItems(cart.getProducts());

        // Check if product is already in cart
        Optional<CartItem> itemOptional = products.stream()
                .filter(item -> item.getProductId().equals(productId)).findFirst();

        // Check if cart quantity is higher or equal than the product than we want to delete
        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            return item.getQuantity() >= quantity;
        }

        return false;
    }

    private Double deleteCartItem(List<CartItem> products,CartItem item, Integer quantity, Double productPrice) {
        Double priceDifference;

        item.setQuantity(item.getQuantity() - quantity);
        priceDifference =  (productPrice * quantity);
        item.setPrice(item.getPrice() - priceDifference);

        if(item.getQuantity() == 0) {
            products.remove(item);
            return priceDifference;
        }

        products.set(products.indexOf(item), item);
        return priceDifference;
    }

    @Override
    public void deleteProductFromCart(UUID cartId, Integer productId, Integer quantity) {
        Cart cart = entityManager.find(Cart.class, cartId);
        Product product = entityManager.find(Product.class, productId);

        double priceDifference = 0.0;
        List<CartItem> products = buildCartItems(cart.getProducts());

        // Check if product is already in cart
        Optional<CartItem> itemOptional = products.stream()
                .filter(item -> item.getProductId().equals(productId)).findFirst();

        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            priceDifference = deleteCartItem(products,item,quantity,product.getPrice());
        }

        cart.setProducts(buildProductsString(products));
        cart.setTotal(cart.getTotal() - (priceDifference));
        cart.setUpdatedAt(new Date());
        entityManager.merge(cart);
    }


    @Override
    public Boolean delete(UUID id) {
        Cart cart = findById(id);
        entityManager.remove(cart);
        return true;
    }

    @Override
    public void update(Cart cart) {
        entityManager.merge(cart);
    }
}
