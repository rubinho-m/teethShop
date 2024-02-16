package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSectionRepository extends JpaRepository<ProductSection, Long> {
    Optional<ProductSection> findProductSectionBySectionName(String sectionName);
}

