package com.rubinho.teethshop.services;

import com.rubinho.teethshop.dto.ProductDto;
import com.rubinho.teethshop.exceptions.AppException;
import com.rubinho.teethshop.model.Product;
import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.repository.ProductProducerRepository;
import com.rubinho.teethshop.repository.ProductSectionRepository;
import com.rubinho.teethshop.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


//@Mapper(componentModel = "spring")
//public interface ProductMapper {
//    @Mapping(target = "type.type_name", source = "type")
//    @Mapping(target = "section.section_name", source = "section")
//    @Mapping(target = "producer.producer_name", source = "producer")
//    Product credentialToProduct(ProductDto productDto);
//}

@Component
@RequiredArgsConstructor
public class ProductMapperService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductSectionRepository productSectionRepository;
    private final ProductProducerRepository productProducerRepository;

    public Product credentialToProduct(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return Product.builder()
                .name(productDto.getName())
                .code(productDto.getCode())
                .description(productDto.getDescription())
                .count(productDto.getCount())
                .url(productDto.getUrl())
                .price(productDto.getPrice())
                .type(mapProductType(productDto.getType()))
                .section(mapProductSection(productDto.getSection()))
                .code(productDto.getCode())
                .producer(mapProductProducer(productDto.getProducer()))
                .build();
    }

    private ProductType mapProductType(String type) {
        return productTypeRepository.findProductTypeByTypeName(type)
                .orElseThrow(() -> new AppException("Unknown type", HttpStatus.NOT_FOUND));
    }

    private ProductSection mapProductSection(String section) {
        return productSectionRepository.findProductSectionBySectionName(section)
                .orElseThrow(() -> new AppException("Unknown section", HttpStatus.NOT_FOUND));
    }

    private ProductProducer mapProductProducer(String producer) {
        return productProducerRepository.findProductProducerByProducerName(producer)
                .orElseThrow(() -> new AppException("Unknown producer", HttpStatus.NOT_FOUND));
    }


}