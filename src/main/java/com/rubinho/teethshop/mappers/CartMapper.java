package com.rubinho.teethshop.mappers;

import com.rubinho.teethshop.dto.CartDto;
import com.rubinho.teethshop.model.Cart;
import com.rubinho.teethshop.model.CartId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "user", source = "userId")
    @Mapping(target = "product", source = "productId")
    CartId toCartId(CartDto cartDto);
}
