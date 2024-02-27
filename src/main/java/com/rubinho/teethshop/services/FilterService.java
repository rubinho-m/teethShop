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

    public ProductType changeType(ProductType newProductType, Long id) {
        try {
            ProductType productType = productTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException("No such type", HttpStatus.BAD_REQUEST));
            productTypeRepository.changeType(id, newProductType.getTypeName());
            return newProductType;
        } catch (Exception ex) {
            throw new AppException("This type already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteTypeById(Long id) {
        try {
            productTypeRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Couldn't delete this type because it is used in some product", HttpStatus.BAD_REQUEST);
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

    public ProductProducer changeProducer(ProductProducer newProductProducer, Long id) {
        try {
            ProductProducer productProducer = productProducerRepository.findById(id)
                    .orElseThrow(() -> new AppException("No such producer", HttpStatus.BAD_REQUEST));
            productProducerRepository.changeProducer(id, newProductProducer.getProducerName());
            return newProductProducer;
        } catch (Exception ex) {
            throw new AppException("This producer already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteProducerById(Long id) {
        try {
            productProducerRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Couldn't delete this producer because it is used in some product", HttpStatus.BAD_REQUEST);
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

    public ProductSection changeSection(ProductSection newProductSection, Long id) {
        try {
            ProductSection productSection = productSectionRepository.findById(id)
                    .orElseThrow(() -> new AppException("No such section", HttpStatus.BAD_REQUEST));
            productSectionRepository.changeSection(id, newProductSection.getSectionName());
            return newProductSection;
        } catch (Exception ex) {
            throw new AppException("This section already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteSectionById(Long id) {
        try {
            productSectionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Couldn't delete this section because it is used in some product", HttpStatus.BAD_REQUEST);
        }

    }
}
