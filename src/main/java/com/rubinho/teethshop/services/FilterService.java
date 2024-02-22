package com.rubinho.teethshop.services;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductProducerRepository productProducerRepository;
    private final ProductSectionRepository productSectionRepository;

    public List<ProductType> getAllTypes() {
        return productTypeRepository.findAll();
    }

    public ProductType addType(ProductType productType) {
        try {
            return productTypeRepository.save(productType);
        } catch (Exception ex) {
            throw new AppException("This type already exists", HttpStatus.BAD_REQUEST);
        }

    }

    public List<ProductProducer> getAllProducers() {
        return productProducerRepository.findAll();
    }

    public ProductProducer addProducer(ProductProducer productProducer) {
        try {
            return productProducerRepository.save(productProducer);
        } catch (Exception ex) {
            throw new AppException("This producer already exists", HttpStatus.BAD_REQUEST);
        }

    }

    public List<ProductSection> getAllSections() {
        return productSectionRepository.findAll();
    }

    public ProductSection addSection(ProductSection productSection) {
        try {
            return productSectionRepository.save(productSection);
        } catch (Exception ex) {
            throw new AppException("This section already exists", HttpStatus.BAD_REQUEST);
        }

    }
}
