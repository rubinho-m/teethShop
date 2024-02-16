package com.rubinho.teethshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String producer;
    private String section;
    private String type;
    private String description;
    private Integer count;
    private Integer price;
}
