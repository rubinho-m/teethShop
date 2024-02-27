package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductSectionRepository extends JpaRepository<ProductSection, Long> {
    Optional<ProductSection> findProductSectionBySectionName(String sectionName);

    @Modifying
    @Transactional
    @Query("""
            UPDATE ProductSection ps SET ps.sectionName = :sectionName WHERE ps.id = :id""")
    void changeSection(@Param("id") Long id, @Param("sectionName") String sectionName);
}

