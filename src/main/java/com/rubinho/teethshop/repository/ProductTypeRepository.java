package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.OrderState;
import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findProductTypeByTypeName(String typeName);

    @Modifying
    @Transactional
    @Query("""
            UPDATE ProductType pt SET pt.typeName = :typeName WHERE pt.id = :id""")
    void changeType(@Param("id") Long id, @Param("typeName") String typeName);

}
