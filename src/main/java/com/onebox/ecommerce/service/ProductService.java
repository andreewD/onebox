package com.onebox.ecommerce.service;

import com.onebox.ecommerce.controller.dtos.ListProductsRS;
import com.onebox.ecommerce.controller.dtos.ProductRQ;
import com.onebox.ecommerce.controller.dtos.ProductRS;
import com.onebox.ecommerce.domain.Product;

import java.util.List;

public interface ProductService {
    ListProductsRS findAll();
    ProductRS findById(Integer id);
    void updateStock(Integer id, Integer stock);
    ProductRS create(ProductRQ product);
}
