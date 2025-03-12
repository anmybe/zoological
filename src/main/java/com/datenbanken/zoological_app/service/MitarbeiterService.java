package com.datenbanken.zoological_app.service;


import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.entity.Mitarbeiter;
import com.datenbanken.zoological_app.repository.*;
import com.datenbanken.zoological_app.repository.MitarbeiterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MitarbeiterService {

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private FuetterungseinheitRepository fuetterungseinheitRepository;

    @Autowired
    public MitarbeiterService(FuetterungseinheitRepository fuetterungseinheitRepository) {
        this.fuetterungseinheitRepository = fuetterungseinheitRepository;
    }

    public Mitarbeiter login(Long mitarbeiterId) {
        return mitarbeiterRepository.findByMitarbeiterId(mitarbeiterId);
    }

    public Mitarbeiter findMitarbeiterById(Integer mitarbeiterId) {
        return mitarbeiterRepository.findById(Long.valueOf(mitarbeiterId)).orElse(null);
    }

    public List<Mitarbeiter> findAll() {
        return mitarbeiterRepository.findAll();
    }

    @Transactional
    public Mitarbeiter addMitarbeiter(Mitarbeiter mitarbeiter) {
        return mitarbeiterRepository.save(mitarbeiter);
    }

    public Mitarbeiter updateMitarbeiter(Mitarbeiter mitarbeiter) {
        return mitarbeiterRepository.save(mitarbeiter);
    }

    public void deleteMitarbeiter(Mitarbeiter mitarbeiter) {
        mitarbeiterRepository.delete(mitarbeiter);

        for (Fuetterungseinheit fuetterungseinheit : mitarbeiter.getFuetterungseinheiten()) {
            fuetterungseinheit.setMitarbeiter(null);
            fuetterungseinheitRepository.save(fuetterungseinheit);
        }

    }

    public List<Fuetterungseinheit> erzeugeTagesplan(Integer mitarbeiterId) {
        return fuetterungseinheitRepository.findByMitarbeiterId(mitarbeiterId);
    }

}
