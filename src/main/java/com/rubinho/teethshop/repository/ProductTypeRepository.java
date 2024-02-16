package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findProductTypeByTypeName(String typeName);
}
