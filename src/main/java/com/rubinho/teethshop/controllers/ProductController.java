package com.rubinho.teethshop.controllers;

import com.rubinho.teethshop.services.FileService;
import com.rubinho.teethshop.utils.FiltersData;
import com.rubinho.teethshop.dto.ProductDto;
import com.rubinho.teethshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;

    @GetMapping("/test")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize,
                                                           @RequestParam(value = "sort", defaultValue = "unsorted") String sort,
                                                           @RequestParam(value = "type", defaultValue = "none") String types,
                                                           @RequestParam(value = "producer", defaultValue = "none") String producers,
                                                           @RequestParam(value = "section", defaultValue = "none") String sections,
                                                           @RequestParam(value = "search", defaultValue = "none") String search) {

        Pageable paging = productService.getPaging(page, pageSize, sort);
        if (productService.checkIfSearchRequest(search)){
            return ResponseEntity.ok(productService.searchProducts(paging, search));
        }

        FiltersData filters = productService.getFilterMap(types, producers, sections);

        return ResponseEntity.ok(productService.getAllProducts(paging, filters));
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> changeProductById(ProductDto newProduct,
                                                        @PathVariable Long id,
                                                        @RequestParam("file") MultipartFile file) {
        newProduct.setUrl(file.getOriginalFilename());
        productService.changeProductById(newProduct, id);
        fileService.save(file);
        newProduct.setId(id);

        return new ResponseEntity<>(newProduct, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>("Product doesn't exist no more", HttpStatus.ACCEPTED);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createNewProduct(ProductDto productDto, @RequestParam("file") MultipartFile file) {
        fileService.save(file);
        productDto.setUrl(file.getOriginalFilename());
        productDto.setId(productService.createProduct(productDto));
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }
}
