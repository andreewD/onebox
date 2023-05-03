package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.CartItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.UUID;

public class CartItemRepositoryImpl implements CartItemRepository {
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public CartItem create(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<CartItem> getByCartId(UUID cartId) {
        return entityManager.createQuery("SELECT c FROM CartItem c WHERE c.cart_id = :cartId", CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }
}
