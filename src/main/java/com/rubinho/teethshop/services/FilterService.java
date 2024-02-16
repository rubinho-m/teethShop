package com.rubinho.teethshop.services;

import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.repository.ProductProducerRepository;
import com.rubinho.teethshop.repository.ProductSectionRepository;
import com.rubinho.teethshop.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductProducerRepository productProducerRepository;
    private final ProductSectionRepository productSectionRepository;

    public List<ProductType> getAllTypes(){
        return productTypeRepository.findAll();
    }

    public List<ProductProducer> getAllProducers(){
        return productProducerRepository.findAll();
    }

    public List<ProductSection> getAllSections(){
        return productSectionRepository.findAll();
    }
}
