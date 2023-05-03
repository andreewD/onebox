package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.controller.dtos.CartRS;
import com.onebox.ecommerce.controller.dtos.ListCartsRS;
import com.onebox.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.onebox.ecommerce.controller.CartApi.BASE;
import static com.onebox.ecommerce.controller.CartApi.BY_ID;

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
    public ResponseEntity<ListCartsRS> listCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @Operation(summary = "Get cart by id", description = "Get cart by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",content = @Content(
                    schema = @Schema(implementation = CartRS.class)))
    })
    @GetMapping(BY_ID)
    public ResponseEntity<CartRS> getCartById(@PathVariable UUID id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @Operation(summary = "Add cart", description = "Add cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",content = @Content(
                    schema = @Schema(implementation = CartRS.class)))
    })
    @PostMapping
    public ResponseEntity<CartRS> addCart() {
        return ResponseEntity.ok(cartService.addNewCart());
    }

}
