package com.rubinho.teethshop.controllers;

import com.rubinho.teethshop.dto.CartDto;
import com.rubinho.teethshop.dto.ProductDto;
import com.rubinho.teethshop.model.Cart;
import com.rubinho.teethshop.model.CartId;
import com.rubinho.teethshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class CartController {
    private final CartService cartService;


    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<ProductDto>> getUsersCart(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getUsersCart(userId));
    }

    @PostMapping("/cart")
    public ResponseEntity<CartDto> addProduct(CartDto cartDto) {
        cartService.addProductToUsersCart(cartDto);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> deleteCartLine(CartDto cartDto) { // productId, userId
        cartService.deleteCartLine(cartDto);
        return new ResponseEntity<>("This cart line doesn't exist no more", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/cart/clear")
    public ResponseEntity<String> clearCart(CartDto cartDto) { // userId
        cartService.clear(cartDto);
        return new ResponseEntity<>("This cart is empty", HttpStatus.ACCEPTED);
    }


    @PutMapping("/cart")
    public ResponseEntity<CartDto> changeCart(CartDto newCartDto) {
        cartService.changeProductInUsersCart(newCartDto);
        return new ResponseEntity<>(newCartDto, HttpStatus.ACCEPTED);
    }

}
