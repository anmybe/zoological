package com.datenbanken.zoological_app.rest;


import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowRepository showRepository;

    @GetMapping
    public List<Show> getAll() {
        return showRepository.findAll();
    }

    @PostMapping
    public Show create(@RequestBody Show show) {
        return showRepository.save(show);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getById(@PathVariable Long id) {
        return showRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> update(@PathVariable Long id, @RequestBody Show updatedShow) {
        return showRepository.findById(id)
                .map(existingShow -> {
                    existingShow.setShowart(updatedShow.getShowart());
                    existingShow.setKapazitaet(updatedShow.getKapazitaet());
                    existingShow.setShowName(updatedShow.getShowName());
                    existingShow.setShowDatum(updatedShow.getShowDatum());
                    existingShow.setShowZeit(updatedShow.getShowZeit());
                    Show savedShow = showRepository.save(existingShow);
                    return ResponseEntity.ok(savedShow);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (showRepository.existsById(id)) {
            showRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
