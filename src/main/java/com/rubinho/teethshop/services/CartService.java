package com.rubinho.teethshop.services;

import com.rubinho.teethshop.dto.CartDto;
import com.rubinho.teethshop.dto.ProductDto;
import com.rubinho.teethshop.exceptions.AppException;
import com.rubinho.teethshop.mappers.CartMapper;
import com.rubinho.teethshop.mappers.ProductMapper;
import com.rubinho.teethshop.model.Cart;
import com.rubinho.teethshop.model.CartId;
import com.rubinho.teethshop.model.Product;
import com.rubinho.teethshop.model.User;
import com.rubinho.teethshop.repository.CartRepository;
import com.rubinho.teethshop.repository.ProductRepository;
import com.rubinho.teethshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapperService cartMapperService;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;

    public void addProductToUsersCart(CartDto cartDto) {
        Optional<Cart> optionalCart = cartRepository.findById(cartMapper.toCartId(cartDto));
        if (optionalCart.isPresent()) {
            throw new AppException("This line in cart already exists, use PUT to change the count", HttpStatus.BAD_REQUEST);
        }
        cartRepository.save(cartMapperService.dtoToCart(cartDto));
    }

    public void changeProductInUsersCart(CartDto cartDto) {
        User user = userRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        Product product = productRepository.findById(cartDto.getProductId())
                .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));


        cartRepository.changeCartLine(cartDto.getCount(), user, product);
    }

    public void deleteCartLine(CartDto cartDto) {
        cartRepository.deleteById(cartMapper.toCartId(cartDto));
    }

    public List<ProductDto> getUsersCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));


        List<Cart> cartLines = cartRepository.findCartByUser(user);
        List<ProductDto> usersCart = new ArrayList<>();

        for (Cart line : cartLines) {
            Product product = productRepository.findById(line.getProduct().getId())
                    .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));
            product.setCount(line.getCount());
            usersCart.add(productMapper.toProductDto(product));
        }

        return usersCart;
    }

    public List<Product> getUsersCartForOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));


        List<Cart> cartLines = cartRepository.findCartByUser(user);
        List<Product> usersCart = new ArrayList<>();

        for (Cart line : cartLines) {
            Product product = productRepository.findById(line.getProduct().getId())
                    .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));
            product.setCount(line.getCount());
            usersCart.add(product);
        }

        return usersCart;
    }


    public void clear(CartDto cartDto) {
        User user = userRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        cartRepository.deleteByUser(user);
    }
}
