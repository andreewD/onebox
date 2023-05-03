package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public UUID create() {
        Cart cart = new Cart();
        cart.setTotal(0.0);
        cart.setCreatedAt(new Date());
        cart.setUpdatedAt(new Date());
        entityManager.persist(cart);
        return cart.getId();
    }

    @Override
    public Cart addProduct(UUID id, Integer productId, Integer quantity) {
        return null;
    }

    @Override
    public Cart removeProduct(UUID id, Integer productId) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        Cart cart = findById(id);
        entityManager.remove(cart);
        return true;
    }
}
