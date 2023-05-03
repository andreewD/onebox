package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.controller.dtos.AddProductToCartRQ;
import com.onebox.ecommerce.controller.dtos.CartRS;
import com.onebox.ecommerce.controller.dtos.ListCartsRS;
import com.onebox.ecommerce.service.CartService;
import com.onebox.ecommerce.utils.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.onebox.ecommerce.controller.CartApi.BASE;
import static com.onebox.ecommerce.controller.CartApi.BY_ID;
import static com.onebox.ecommerce.controller.CartApi.PRODUCT;

@RestController
@AllArgsConstructor
@RequestMapping(BASE)
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(summary = "List carts", description = "List carts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",content = @Content(
                    schema = @Schema(implementation = ListCartsRS.class)))
    })
    @GetMapping
    public ResponseEntity<?> listCarts() {
        try {
            return ResponseEntity.ok(cartService.getAllCarts());
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorStatus(e.getMessage()),HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Get cart by id", description = "Get cart by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",content = @Content(
                    schema = @Schema(implementation = CartRS.class)))
    })
    @GetMapping(BY_ID)
    public ResponseEntity<?> getCartById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(cartService.getCartById(id));
        } catch (Exception e) {
              return new ResponseEntity<>(new ErrorStatus(e.getMessage()),HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Add cart", description = "Add cart")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CartRS.class)))})
    @PostMapping
    public ResponseEntity<?> addCart() {
       try{
           return ResponseEntity.ok(cartService.addNewCart());
       } catch (Exception e) {
           return new ResponseEntity<>(new ErrorStatus(e.getMessage()),HttpStatus.CONFLICT);
       }
    }

    @Operation(summary = "Delete cart", description = "Delete cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")})
    @DeleteMapping(BY_ID)
    public ResponseEntity<?> deleteCart(@PathVariable UUID id) {
        try {
            cartService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorStatus(e.getMessage()),HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Add product to cart", description = "Add product to cart")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CartRS.class)))})
    @PostMapping(PRODUCT)
    public ResponseEntity<?> addProductToCart(@PathVariable UUID id,
                                                   @RequestBody @Valid AddProductToCartRQ addProductToCartRQ) {
        try {
            return ResponseEntity.ok(cartService.addProduct(id, addProductToCartRQ.getProductId(), addProductToCartRQ.getQuantity()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorStatus(e.getMessage()),HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete product from cart", description = "Delete product from cart")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = @
            Content(schema = @Schema(implementation = CartRS.class)))})
    @DeleteMapping(PRODUCT)
    public ResponseEntity<?> deleteProductFromCart(@PathVariable UUID id,
                                                        @RequestBody @Valid AddProductToCartRQ addProductToCartRQ) {
        try {
            CartRS result = cartService.removeProduct(id, addProductToCartRQ.getProductId(),addProductToCartRQ.getQuantity());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorStatus(e.getMessage()),HttpStatus.CONFLICT);
        }
    }

}
