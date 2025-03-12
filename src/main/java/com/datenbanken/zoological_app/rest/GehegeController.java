package com.datenbanken.zoological_app.rest;

import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/gehege")
public class GehegeController {

    @Autowired
    private GehegeRepository gehegeRepository;


    @GetMapping
    public List<Gehege> getAll() {
        return gehegeRepository.findAll();
    }

    @PostMapping
    public Gehege create(@RequestBody Gehege gehege) {
        return gehegeRepository.save(gehege);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gehege> getById(@PathVariable Long id) {
        return gehegeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gehege> update(@PathVariable Long id, @RequestBody Gehege updatedGehege) {
        return gehegeRepository.findById(id).map(existingGehege -> {
            existingGehege.setGroesse(updatedGehege.getGroesse());
            existingGehege.setFaunabereich(updatedGehege.getFaunabereich());
            existingGehege.setSaeuberungsdatum(updatedGehege.getSaeuberungsdatum());
            existingGehege.setSaeuberungszeit(updatedGehege.getSaeuberungszeit());
            existingGehege.setGroesseneinheit(updatedGehege.getGroesseneinheit());

            gehegeRepository.save(existingGehege);
            return ResponseEntity.ok(existingGehege);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (gehegeRepository.existsById(id)) {
            gehegeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

