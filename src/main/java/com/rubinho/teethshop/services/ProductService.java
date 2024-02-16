package com.rubinho.teethshop.services;

import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.repository.*;
import com.rubinho.teethshop.utils.FiltersData;
import com.rubinho.teethshop.dto.ProductDto;
import com.rubinho.teethshop.exceptions.AppException;
import com.rubinho.teethshop.mappers.ProductMapper;
import com.rubinho.teethshop.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductProducerRepository productProducerRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductSectionRepository productSectionRepository;
    private final ProductMapperService productMapperService;
    private final ProductMapper productMapper;
    private final ProductFilterRepository productFilterRepository;


    public Pageable getPaging(Integer page, Integer pageSize, String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        Pageable paging;

        String[] sortedArray = sort.split(",");


        if (sortedArray[0].equals("unsorted")) {
            paging = PageRequest.of(page, pageSize);

        } else {
            for (String sortOrder : sortedArray) {
                String[] _sort = sortOrder.split(" ");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }

            paging = PageRequest.of(page, pageSize, Sort.by(orders));
        }

        return paging;
    }

    public List<ProductDto> getAllProducts(Pageable paging, FiltersData filters) {
        Page<Product> pagedResult;

        if (filters.getAllFilledInFields().isEmpty()) {
            pagedResult = productRepository.findAll(paging);
        } else {
            pagedResult = productFilterRepository.getFilteredPagedProducts(paging, filters);
        }

        if (pagedResult.hasContent()) {
            return pagedResult.getContent().stream()
                    .map(productMapper::toProductDto)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }


    }


    public List<ProductDto> searchProducts(Pageable paging, String search) {
        Page<Product> pagedResult = productRepository.findAllByNameContaining(paging, search);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent().stream()
                    .map(productMapper::toProductDto)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }


    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException("A product with this id doesn't exist", HttpStatus.NOT_FOUND));

        return productMapper.toProductDto(product);
    }

    public void changeProductById(ProductDto newProduct, Long id) {

        try{
            ProductProducer producer = productProducerRepository.findProductProducerByProducerName(newProduct.getProducer())
                    .orElseThrow(() -> new AppException("Unknown producer", HttpStatus.NOT_FOUND));

            ProductType type = productTypeRepository.findProductTypeByTypeName(newProduct.getType())
                    .orElseThrow(() -> new AppException("Unknown type", HttpStatus.NOT_FOUND));

            ProductSection section = productSectionRepository.findProductSectionBySectionName(newProduct.getSection())
                    .orElseThrow(() -> new AppException("Unknown section", HttpStatus.NOT_FOUND));


            Integer status = productRepository.changeProduct(
                    id,
                    newProduct.getName(),
                    newProduct.getCode(),
                    producer,
                    type,
                    section,
                    newProduct.getDescription(),
                    newProduct.getCount(),
                    newProduct.getPrice());
        } catch (Exception ex){
            throw new AppException("A product with this articul already exists", HttpStatus.BAD_REQUEST);
        }




    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
//        try {
//            productRepository.deleteById(id);
//        } catch (Exception ex) {
//            throw new AppException("A product with this id doesn't exist", HttpStatus.NOT_FOUND);
//        }

    }

    public Long createProduct(ProductDto productDto) {
        try {
            Product product = productMapperService.credentialToProduct(productDto);
            productRepository.save(product);
            return product.getId();
        } catch (Exception ex) {
            throw new AppException("A product with this articul already exists", HttpStatus.BAD_REQUEST);
        }

    }

    public Product buildProduct(Product newProduct) {
        return Product.builder()
                .cartList(newProduct.getCartList())
                .name(newProduct.getName())
                .code(newProduct.getCode())
                .producer(newProduct.getProducer())
                .section(newProduct.getSection())
                .type(newProduct.getType())
                .description(newProduct.getDescription())
                .count(newProduct.getCount())
                .price(newProduct.getPrice())
                .build();
    }

    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    public FiltersData getFilterMap(String types, String producers, String sections) {
        return FiltersData.builder()
                .type(types)
                .producer(producers)
                .section(sections)
                .build();

    }

    public boolean checkIfSearchRequest(String search) {
        return !search.equals("none");

    }
}

