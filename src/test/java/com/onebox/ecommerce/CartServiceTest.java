package com.onebox.ecommerce;

import com.onebox.ecommerce.domain.Cart;
import com.onebox.ecommerce.domain.Product;
import com.onebox.ecommerce.repository.CartRepository;
import com.onebox.ecommerce.repository.ProductRepository;
import com.onebox.ecommerce.service.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartServiceTest {
	private final CartRepository cartRepository = mock(CartRepository.class);
	private final ProductRepository productRepository = mock(ProductRepository.class);

	@Test
	void contextLoads() {
	}

	@Test
	public void testAddProductToCart() {
		Product product = Product.builder()
				.product_seq(1)
				.name("Product 1")
				.description("Product 1")
				.stock(10)
				.price(10.0)
				.build();

		UUID uuid = UUID.randomUUID();
		Date date = new Date();
		Cart cart = Cart.builder()
				.id(uuid)
				.total(0.0)
				.createdAt(date)
				.updatedAt(date)
				.build();

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

}
