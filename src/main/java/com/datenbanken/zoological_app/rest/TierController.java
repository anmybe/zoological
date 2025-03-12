package com.datenbanken.zoological_app.rest;


import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;




@RestController
@RequestMapping("/api/tier")
public class TierController {

    @Autowired
    private TierRepository repository;
    @Autowired
    private TierRepository tierRepository;

    @GetMapping
    public List<Tier> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Tier create(@RequestBody Tier tier) {
        return repository.save(tier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tier> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tier> update(@PathVariable Long id, @RequestBody Tier updatedTier) {
        return repository.findById(id).map(existingTier -> {
            existingTier.setTierart(updatedTier.getTierart());
            existingTier.setHerkunftsland(updatedTier.getHerkunftsland());
            existingTier.setGeschlecht(updatedTier.getGeschlecht());
            existingTier.setGeburtsjahr(updatedTier.getGeburtsjahr());
            existingTier.setAufnahmedatum(updatedTier.getAufnahmedatum());
            existingTier.setName(updatedTier.getName());

            Tier savedTier = repository.save(existingTier);
            return ResponseEntity.ok(savedTier);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (tierRepository.existsById(id)) {
            tierRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

