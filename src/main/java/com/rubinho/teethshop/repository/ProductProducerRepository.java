package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProductProducerRepository extends JpaRepository<ProductProducer, Long> {
    Optional<ProductProducer> findProductProducerByProducerName(String producerName);

    @Modifying
    @Transactional
    @Query("""
            UPDATE ProductProducer pp SET pp.producerName = :producerName WHERE pp.id = :id""")
    void changeProducer(@Param("id") Long id, @Param("producerName") String producerName);
}

