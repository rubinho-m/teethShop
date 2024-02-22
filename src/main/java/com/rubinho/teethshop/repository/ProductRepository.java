package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.Product;
import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("""
    UPDATE Product p SET p.name = :name, p.code = :code, p.producer = :producer, p.type = :type, p.section = :section, p.description = :description, p.count = :count, p.price = :price, p.url = :url WHERE p.id = :id""")
    Integer changeProduct(@Param("id") Long id,
                                @Param("name") String name,
                                @Param("code") String code,
                                @Param("producer") ProductProducer producer,
                                @Param("type") ProductType type,
                                @Param("section") ProductSection section,
                                @Param("description") String description,
                                @Param("count") Integer count,
                                @Param("price") Integer price,
                                @Param("url") String url);

    Page<Product> findAllByNameContaining(Pageable pageable, String name);
}