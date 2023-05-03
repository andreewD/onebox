package com.onebox.ecommerce.service;

import com.onebox.ecommerce.controller.dtos.ListProductsRS;
import com.onebox.ecommerce.controller.dtos.ProductRQ;
import com.onebox.ecommerce.controller.dtos.ProductRS;
import com.onebox.ecommerce.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ProductServiceImpl implements ProductService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ListProductsRS findAll() {
        List<Product> products =  entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();

        return ListProductsRS.builder()
                .products(mapProductListToProductListRS(products))
                .build();
    }

    private List<ProductRS> mapProductListToProductListRS(List<Product> products){
        return products.stream()
                .map(this::mapProductToProductRS)
                .collect(Collectors.toList());
    }
    private ProductRS mapProductToProductRS(Product product){
        return ProductRS.builder()
                .price(product.getPrice())
                .stock(product.getStock())
                .name(product.getName())
                .id(product.getProduct_seq())
                .description(product.getDescription())
                .build();    }

    @Override
    public ProductRS findById(Integer id) {
        Product product = entityManager.find(Product.class, id);
        return mapProductToProductRS(product);
    }

    @Override
    public void updateStock(Integer id, Integer stock) {
        Product product = entityManager.find(Product.class, id);
        product.setStock(stock);
        entityManager.merge(product);
    }

    @Override
    public ProductRS create(ProductRQ productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();

        entityManager.persist(product);
        return mapProductToProductRS(product);
    }
}
