package com.rubinho.teethshop.services;

import com.rubinho.teethshop.dto.OrderCredentialsDto;
import com.rubinho.teethshop.dto.OrderDto;
import com.rubinho.teethshop.exceptions.AppException;
import com.rubinho.teethshop.mappers.ProductMapper;
import com.rubinho.teethshop.model.*;
import com.rubinho.teethshop.repository.OrderRepository;
import com.rubinho.teethshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    public OrderDto createOrder(OrderCredentialsDto orderCredentialsDto) {
        List<Product> productList = cartService.getUsersCartForOrder(orderCredentialsDto.getUserId());
        if (productList.isEmpty()) {
            throw new AppException("Order cannot be created with an empty shopping cart", HttpStatus.BAD_REQUEST);
        }

        List<BucketItems> bucketItems = new ArrayList<>();

        for (Product product : productList) {

            BucketItems item = BucketItems
                    .builder()
                    .product(product)
                    .count(product.getCount())
                    .build();

            bucketItems.add(item);
        }

        User user = userRepository.findById(orderCredentialsDto.getUserId())
                .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));

        Order order = Order
                .builder()
                .address(orderCredentialsDto.getAddress())
                .description(orderCredentialsDto.getDescription())
                .items(bucketItems)
                .date(new Date())
                .user(user)
                .state(OrderState.IN_PROGRESS)
                .build();

        Order savedOrder = orderRepository.save(order);

        return toOrderDto(savedOrder, productList);


    }

    public List<OrderDto> getOrders(Long userId, OrderState state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        List<Order> orders = orderRepository.findAllByUserAndState(user, state);
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            List<Product> products = getProductsFromBucketItems(order.getItems());
            OrderDto orderDto = toOrderDto(order, products);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }


    public OrderDto changeOrder(Long id, OrderCredentialsDto orderCredentialsDto) {
        orderRepository.changeOrder(
                id,
                orderCredentialsDto.getDescription(),
                orderCredentialsDto.getAddress(),
                orderCredentialsDto.getState()
        );

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException("Unknown order", HttpStatus.NOT_FOUND));

        order.setAddress(orderCredentialsDto.getAddress());
        order.setDescription(orderCredentialsDto.getDescription());
        order.setState(orderCredentialsDto.getState());

        List<Product> products = getProductsFromBucketItems(order.getItems());

        return toOrderDto(order, products);



    }


    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }


    private List<Product> getProductsFromBucketItems(List<BucketItems> items) {
        List<Product> products = new ArrayList<>();

        for (BucketItems item : items) {
            Product product = item.getProduct();
            product.setCount(item.getCount());
            products.add(product);
        }

        return products;
    }

    private OrderDto toOrderDto(Order order, List<Product> products) {
        return OrderDto
                .builder()
                .id(order.getId())
                .description(order.getDescription())
                .date(order.getDate())
                .address(order.getAddress())
                .state(order.getState())
                .firstName(order.getUser().getFirstName())
                .secondName(order.getUser().getSecondName())
                .items(products.stream()
                        .map(productMapper::toProductDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
