package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.controller.dtos.ListProductsRS;
import com.onebox.ecommerce.controller.dtos.ProductRQ;
import com.onebox.ecommerce.controller.dtos.ProductRS;
import com.onebox.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.onebox.ecommerce.controller.ProductApi.BASE;

@RestController
@AllArgsConstructor
@RequestMapping(BASE)
public class ProductController {
    @Autowired
    private final ProductService productService;
    @Operation(summary = "List products", description = "List products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",content = @Content(
                    schema = @Schema(implementation = ListProductsRS.class)
            ))
    })
    @GetMapping
    public ResponseEntity<ListProductsRS> listProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Operation(summary = "Create product", description = "Create product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",content = @Content(
                    schema = @Schema(implementation = ProductRS.class)
            ))
    })
    @PostMapping
    public ResponseEntity<ProductRS> createProduct(ProductRQ productRQ) {
        return ResponseEntity.ok(productService.create(productRQ));
    }
}
