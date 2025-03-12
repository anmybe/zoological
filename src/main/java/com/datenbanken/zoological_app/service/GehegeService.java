package com.datenbanken.zoological_app.service;

import com.datenbanken.zoological_app.entity.*;
import com.datenbanken.zoological_app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GehegeService {

    @Autowired
    private GehegeRepository gehegeRepository;
    @Autowired
    private TierRepository tierRepository;


    public List<Gehege> findAll() {
        return gehegeRepository.findAll();
    }

    public Gehege addGehege(Gehege gehege) {
        return gehegeRepository.save(gehege);
    }

    public Gehege updateGehege(Gehege gehege) {
        return gehegeRepository.save(gehege);
    }

    @Transactional
    public void deleteGehege(Gehege gehege) {

        // Unassign Tiere from Gehege
        for (Tier tier : gehege.getTiere()) {
            tier.setGehege(null);
            tierRepository.save(tier); // Update the Tier to remove the reference
        }
        for (Mitarbeiter mitarbeiter : gehege.getMitarbeiter()) {
            mitarbeiter.getGehege().remove(gehege);
        }

        gehegeRepository.delete(gehege);
    }


    public Gehege findById(Long gehegeId) {
        return gehegeRepository.findById(gehegeId).orElse(null);
    }

}







