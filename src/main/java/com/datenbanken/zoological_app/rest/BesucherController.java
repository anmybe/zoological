package com.datenbanken.zoological_app.rest;


import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/besucher")
public class BesucherController {

    @Autowired
    private BesucherRepository besucherRepository;

    @GetMapping
    public List<Besucher> getAll() {
        return besucherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Besucher> getById(@PathVariable Long id) {
        return besucherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Besucher create(@RequestBody Besucher besucher) {
        return besucherRepository.save(besucher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Besucher> update(@PathVariable Long id, @RequestBody Besucher updated) {
        return besucherRepository.findById(id).map(existing -> {
            existing.setBesuchsdatum(updated.getBesuchsdatum());
            return ResponseEntity.ok(besucherRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (besucherRepository.existsById(id)) {
            besucherRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
