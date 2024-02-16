package com.rubinho.teethshop.mappers;

import com.rubinho.teethshop.dto.ProductDto;
import com.rubinho.teethshop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "type.typeName", target = "type")
    @Mapping(source = "section.sectionName", target = "section")
    @Mapping(source = "producer.producerName", target = "producer")
    ProductDto toProductDto(Product product);
}
