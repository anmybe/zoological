package com.datenbanken.zoological_app.service;


import com.datenbanken.zoological_app.entity.Fuetterungseinheit;
import com.datenbanken.zoological_app.entity.Fuetterungsplan;
import com.datenbanken.zoological_app.entity.Gehege;
import com.datenbanken.zoological_app.repository.FuetterungsplanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
public class FuetterungsplanService {

    @Autowired
    private FuetterungsplanRepository fuetterungsplanRepository;

    public List<Fuetterungsplan> findAll() {
        return fuetterungsplanRepository.findAll();
    }

    public Fuetterungsplan addFuetterungsplan(Fuetterungsplan fuetterungsplan) {
        return fuetterungsplanRepository.save(fuetterungsplan);
    }

    public Fuetterungsplan updateFuetterungsplan(Fuetterungsplan fuetterungsplan) {
        return fuetterungsplanRepository.save(fuetterungsplan);
    }

    public void deleteFuetterungsplan(Fuetterungsplan fuetterungsplan) {
        for (Fuetterungseinheit fuetterungseinheit : fuetterungsplan.getFuetterungseinheiten()) {
            fuetterungseinheit.setFuetterungsplan(null);
        }
        fuetterungsplanRepository.delete(fuetterungsplan);
    }

    public Fuetterungsplan findById(Long fuetterungsplanId) {
        return fuetterungsplanRepository.findById(BigInteger.valueOf(fuetterungsplanId)).orElse(null);
    }
}
