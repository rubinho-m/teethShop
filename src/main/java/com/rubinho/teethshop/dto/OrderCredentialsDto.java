package com.rubinho.teethshop.dto;

import com.rubinho.teethshop.model.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderCredentialsDto {
    private Long userId;
    private String address;
    private String description;
    private OrderState state;
}
