package com.onebox.ecommerce;


import com.onebox.ecommerce.domain.Product;
import com.onebox.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);

    @Test
    public void testAddProduct() {
        Product product = Product.builder().product_seq(1).name("Product 1").description("Product 1").stock(10).price(10.0).build();

        when(productRepository.create(product)).thenReturn(product);
        Product productCreated = productRepository.create(product);

        // Verify that the result is not null
        assertTrue(productCreated != null);

        // Verify data
        assertTrue(productCreated.getProduct_seq() == 1);
        assertTrue(productCreated.getName().equals("Product 1"));
        assertTrue(productCreated.getDescription().equals("Product 1"));
        assertTrue(productCreated.getStock() == 10);
        assertTrue(productCreated.getPrice() == 10.0);
    }

    @Test
    public void testEmptyListProducts() {
        // Create an empty list of products
        List<Product> products = Arrays.asList();

        // Mock the method findAll() of the productRepository
        Mockito.when(productRepository.findAll()).thenReturn(products);

        // Call the method findAll() of the productRepository
        List<Product> result = productRepository.findAll();

        // Verify that the result is not null
        assertTrue(result != null);

        // Verify that the result is empty
        assertTrue(result.isEmpty());

        // Verify that the method findAll() of the productRepository is called only once
        verify(productRepository, times(1)).findAll();


    }

    @Test
    public void testNonEmptyListProducts() {
        Product product = Product.builder()
                .product_seq(1)
                .name("Product 1")
                .description("Product 1")
                .stock(10)
                .price(10.0)
                .build();

        Product product2 = Product.builder()
                .product_seq(2)
                .name("Product 2")
                .description("Product 2")
                .stock(10)
                .price(10.0)
                .build();

        Mockito.when(productRepository.create(product)).thenReturn(product);
        Mockito.when(productRepository.create(product2)).thenReturn(product2);

        List<Product> products = Arrays.asList(product, product2);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productRepository.findAll();

        // Verify that the result is not null
        assertTrue(result != null);

        // Verify that the result is not empty
        assertFalse(result.isEmpty());

        // Verify that the result has two products
        assertTrue(result.size() == 2);

        // Verify that the method findAll() of the productRepository is called only once
        verify(productRepository, times(1)).findAll();

    }

}
