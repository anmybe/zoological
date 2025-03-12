package com.datenbanken.zoological_app.rest;


import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/fuetterungseinheit")
public class FuetterungseinheitController {

    @Autowired
    private FuetterungseinheitRepository fuetterungseinheitRepository;

    @GetMapping
    public List<Fuetterungseinheit> getAll() {
        return fuetterungseinheitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fuetterungseinheit> getById(@PathVariable Long id) {
        return fuetterungseinheitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Fuetterungseinheit create(@RequestBody Fuetterungseinheit fuetterungseinheit) {
        return fuetterungseinheitRepository.save(fuetterungseinheit);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Fuetterungseinheit> update(@PathVariable Long id, @RequestBody Fuetterungseinheit updated) {
        return fuetterungseinheitRepository.findById(id).map(existing -> {
            existing.setFuetterungszeit(updated.getFuetterungszeit());
            existing.setFutterart(updated.getFutterart());
            existing.setFuttermenge(updated.getFuttermenge());
            existing.setFuetterungsplan(updated.getFuetterungsplan());
            existing.setMitarbeiter(updated.getMitarbeiter());
            return ResponseEntity.ok(fuetterungseinheitRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (fuetterungseinheitRepository.existsById(id)) {
            fuetterungseinheitRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

