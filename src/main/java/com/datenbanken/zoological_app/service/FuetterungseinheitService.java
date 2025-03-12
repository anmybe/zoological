package com.datenbanken.zoological_app.service;

import com.datenbanken.zoological_app.entity.Fuetterungseinheit;
import com.datenbanken.zoological_app.entity.Fuetterungsplan;
import com.datenbanken.zoological_app.repository.FuetterungseinheitRepository;
import com.datenbanken.zoological_app.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuetterungseinheitService {

    @Autowired
    private final FuetterungseinheitRepository repository;
    @Autowired
    private final ShowRepository showRepository;


    public FuetterungseinheitService(FuetterungseinheitRepository repository, ShowRepository showRepository) {
        this.repository = repository;
        this.showRepository = showRepository;
    }

    public List<Fuetterungseinheit> findAll() {
        return repository.findAll();
    }

//    public Optional<Fuetterungseinheit> findById(Long id) {
//        return repository.findById(id);
//    }

    public Fuetterungseinheit addFuetterungseinheit(Fuetterungseinheit fuetterungseinheit) {
        return repository.save(fuetterungseinheit);
    }

    public Fuetterungseinheit updateFuetterungseinheit(Fuetterungseinheit fuetterungseinheit) {
        return repository.save(fuetterungseinheit);
    }

    public void deleteFuetterungseinheit(Fuetterungseinheit fuetterungseinheit) {
        fuetterungseinheit.setFuetterungsplan(null);
        fuetterungseinheit.setMitarbeiter(null);
        repository.delete(fuetterungseinheit);
    }

    public Fuetterungseinheit findById(Long tierId) {
        return repository.findById(tierId).orElse(null);
    }


}
