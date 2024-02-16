package com.rubinho.teethshop.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartId implements Serializable {
    private Long user;
    private Long product;
}
