package com.rubinho.teethshop.controllers;


import com.rubinho.teethshop.model.ProductProducer;
import com.rubinho.teethshop.model.ProductSection;
import com.rubinho.teethshop.model.ProductType;
import com.rubinho.teethshop.services.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/sections")
    public ResponseEntity<List<ProductSection>> getAllSections() {
        return ResponseEntity.ok(filterService.getAllSections());
    }

    @GetMapping("/producers")
    public ResponseEntity<List<ProductProducer>> getAllProducers() {
        return ResponseEntity.ok(filterService.getAllProducers());
    }


}
