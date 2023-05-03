package com.onebox.ecommerce.repository;

import com.onebox.ecommerce.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(Integer id);
    void updateStock(Integer id, Integer stock);
    Product create(Product product);
}
