package com.rubinho.teethshop.controllers;

import com.rubinho.teethshop.dto.CartDto;
import com.rubinho.teethshop.dto.OrderCredentialsDto;
import com.rubinho.teethshop.dto.OrderDto;
import com.rubinho.teethshop.model.OrderState;
import com.rubinho.teethshop.services.CartService;
import com.rubinho.teethshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(OrderCredentialsDto orderCredentialsDto) {
        OrderDto orderDto = orderService.createOrder(orderCredentialsDto);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<OrderDto> changeOrder(@PathVariable Long id, OrderCredentialsDto orderCredentialsDto) {
        OrderDto orderDto = orderService.changeOrder(id, orderCredentialsDto);
        return new ResponseEntity<>(orderDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDto>> getOrdersByState(@RequestParam(value = "id") Long userId,
                                                           @RequestParam(value = "state", defaultValue = "IN_PROGRESS") OrderState state){
        return ResponseEntity.ok(orderService.getOrders(userId, state));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> deleteOrderBy(@PathVariable Long id){

        orderService.deleteById(id);

        return new ResponseEntity<>("This order doesn't exist no more", HttpStatus.ACCEPTED);
    }
}
