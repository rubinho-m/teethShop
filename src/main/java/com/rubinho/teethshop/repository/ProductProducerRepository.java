package com.rubinho.teethshop.repository;

import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductProducerRepository extends JpaRepository<ProductProducer, Long> {
    Optional<ProductProducer> findProductProducerByProducerName(String producerName);
}

