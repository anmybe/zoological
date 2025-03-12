package com.datenbanken.zoological_app.service;


import com.datenbanken.zoological_app.entity.Tier;
import com.datenbanken.zoological_app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TierService {
    @Autowired
    private final TierRepository tierRepository;

    public TierService(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }

    public List<Tier> findAll() {
        return tierRepository.findAll();
    }

    public Tier save(Tier tier) {
        return tierRepository.save(tier);
    }

    public Tier update(Tier tier) {
        return tierRepository.save(tier);
    }

    @Transactional
    public void delete(Tier tier) {
        tier.setGehege(null);
        tier.setFuetterungsplan(null);
        tierRepository.delete(tier);
    }


    public Tier findById(Long tierId) {
        return tierRepository.findById(tierId).orElse(null);
    }
}
