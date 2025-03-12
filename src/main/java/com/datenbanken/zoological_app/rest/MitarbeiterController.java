package com.datenbanken.zoological_app.rest;


import com.datenbanken.zoological_app.entity.Mitarbeiter;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@RestController
@RequestMapping("/api/mitarbeiter")
public class MitarbeiterController {

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;


    @GetMapping
    public List<Mitarbeiter> getAll() {
        return mitarbeiterRepository.findAll();
    }

    @PostMapping
    public Mitarbeiter create(@RequestBody Mitarbeiter mitarbeiter) {
        return mitarbeiterRepository.save(mitarbeiter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mitarbeiter> getById(@PathVariable Integer id) {
        return mitarbeiterRepository.findById(Long.valueOf(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mitarbeiter> update(@PathVariable Long id, @RequestBody Mitarbeiter updatedMitarbeiter) {
        return mitarbeiterRepository.findById(id).map(existingMitarbeiter -> {

            existingMitarbeiter.setVorname(updatedMitarbeiter.getVorname());
            existingMitarbeiter.setNachname(updatedMitarbeiter.getNachname());
            existingMitarbeiter.setPosition(updatedMitarbeiter.getPosition());
            existingMitarbeiter.setVerantwortungsbereich(updatedMitarbeiter.getVerantwortungsbereich());
            existingMitarbeiter.setArbeitsschicht(updatedMitarbeiter.getArbeitsschicht());

            mitarbeiterRepository.save(existingMitarbeiter);
            return ResponseEntity.ok(existingMitarbeiter);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (mitarbeiterRepository.existsById(id)) {
            mitarbeiterRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

