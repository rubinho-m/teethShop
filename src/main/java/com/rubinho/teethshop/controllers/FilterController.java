package com.rubinho.teethshop.controllers;


import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.services.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class FilterController {
    private final FilterService filterService;

    @GetMapping("/types")
    public ResponseEntity<List<ProductType>> getAllTypes() {
        return ResponseEntity.ok(filterService.getAllTypes());
    }


    @PostMapping("/types")
    public ResponseEntity<ProductType> addNewType(ProductType productType) {
        return ResponseEntity.ok(filterService.addType(productType));

    }

    @PutMapping("/types/{id}")
    public ResponseEntity<ProductType> changeType(ProductType newProductType, @PathVariable Long id) {

        return ResponseEntity.ok(filterService.changeType(newProductType, id));

    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<String> deleteTypeById(@PathVariable Long id) {
        filterService.deleteTypeById(id);
        return new ResponseEntity<>("Type doesn't exist no more", HttpStatus.ACCEPTED);
    }

    @GetMapping("/sections")
    public ResponseEntity<List<ProductSection>> getAllSections() {
        return ResponseEntity.ok(filterService.getAllSections());
    }

    @PostMapping("/sections")
    public ResponseEntity<ProductSection> addNewSection(ProductSection productSection) {
        return ResponseEntity.ok(filterService.addSection(productSection));

    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<ProductSection> changeSection(ProductSection newProductSection, @PathVariable Long id) {
        return ResponseEntity.ok(filterService.changeSection(newProductSection, id));

    }

    @DeleteMapping("/sections/{id}")
    public ResponseEntity<String> deleteSectionById(@PathVariable Long id) {
        filterService.deleteSectionById(id);
        return new ResponseEntity<>("Section doesn't exist no more", HttpStatus.ACCEPTED);
    }

    @GetMapping("/producers")
    public ResponseEntity<List<ProductProducer>> getAllProducers() {
        return ResponseEntity.ok(filterService.getAllProducers());
    }

    @PostMapping("/producers")
    public ResponseEntity<ProductProducer> addNewProducer(ProductProducer productProducer) {
        return ResponseEntity.ok(filterService.addProducer(productProducer));

    }

    @PutMapping("/producers/{id}")
    public ResponseEntity<ProductProducer> changeProducer(ProductProducer newProductProducer, @PathVariable Long id) {
        return ResponseEntity.ok(filterService.changeProducer(newProductProducer, id));

    }

    @DeleteMapping("/producers/{id}")
    public ResponseEntity<String> deleteProducerById(@PathVariable Long id) {
        filterService.deleteProducerById(id);
        return new ResponseEntity<>("Producer doesn't exist no more", HttpStatus.ACCEPTED);
    }


}
