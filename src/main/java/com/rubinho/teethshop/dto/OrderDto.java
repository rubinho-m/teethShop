package com.rubinho.teethshop.dto;

import com.rubinho.teethshop.model.BucketItems;
import com.rubinho.teethshop.model.OrderState;
import com.rubinho.teethshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDto {
    private Long id;
    private String firstName;
    private String secondName;
    private List<ProductDto> items;
    private String address;
    private Date date;
    private String description;
    private OrderState state;

}
