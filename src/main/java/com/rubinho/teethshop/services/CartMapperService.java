package com.rubinho.teethshop.services;

import com.rubinho.teethshop.dto.CartDto;
import com.rubinho.teethshop.exceptions.AppException;
import com.rubinho.teethshop.model.Cart;
import com.rubinho.teethshop.model.Product;
import com.rubinho.teethshop.model.User;
import com.rubinho.teethshop.repository.ProductRepository;
import com.rubinho.teethshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapperService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Cart dtoToCart(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }
        return Cart.builder()
                .product(getProductById(cartDto.getProductId()))
                .user(getUserById(cartDto.getUserId()))
                .count(cartDto.getCount())
                .build();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }
}
