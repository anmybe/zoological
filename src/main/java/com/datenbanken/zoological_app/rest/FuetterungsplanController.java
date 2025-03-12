package com.datenbanken.zoological_app.rest;

import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;


@RestController
@RequestMapping("/api/fuetterungsplan")
public class FuetterungsplanController {

    @Autowired
    private FuetterungsplanRepository fuetterungsplanRepository;

    @GetMapping
    public List<Fuetterungsplan> getAll() {
        return fuetterungsplanRepository.findAll();
    }

    @PostMapping
    public Fuetterungsplan create(@RequestBody Fuetterungsplan plan) {
        return fuetterungsplanRepository.save(plan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fuetterungsplan> getById(@PathVariable Long id) {
        return fuetterungsplanRepository.findById(BigInteger.valueOf(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (fuetterungsplanRepository.existsById(BigInteger.valueOf(id))) {
            fuetterungsplanRepository.deleteById(BigInteger.valueOf(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

