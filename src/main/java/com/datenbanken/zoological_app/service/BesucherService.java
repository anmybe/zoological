package com.datenbanken.zoological_app.service;


import com.datenbanken.zoological_app.entity.Besucher;
import com.datenbanken.zoological_app.entity.Show;
import com.datenbanken.zoological_app.repository.BesucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class BesucherService {

    @Autowired
    private final BesucherRepository besucherRepository;


    @Autowired
    public BesucherService(BesucherRepository besucherRepository) {
        this.besucherRepository = besucherRepository;
    }

    public List<Besucher> findAll() {
        return besucherRepository.findAllBesucher();
    }

    public Besucher findById(Long id) {
        return besucherRepository.findById(id).orElse(null);
    }


    public String findAllPopularDates() {
        List<Object[]> popularDates = besucherRepository.findAllPopularDates();

        if (popularDates.isEmpty()) {
            return "";
        }

        Object[] mostPopularDate = popularDates.get(0);
        LocalDate date = (LocalDate) mostPopularDate[0];
        Long count = (Long) mostPopularDate[1];

        return date + ", Besucher: " + count;
    }

    public void addBesucher(Besucher besucher) {
        besucherRepository.save(besucher);
    }

    public Besucher updateBesucher(Besucher besucher) {
        return besucherRepository.save(besucher);
    }

    public Besucher deleteBesucher(Besucher besucher) {
        for (Show show : besucher.getShows()) {
            show.getBesucher().remove(besucher);
        }
        besucherRepository.delete(besucher);
        return besucher;
    }

    public List<Besucher> findByBesuchsdatum(LocalDate besuchsdatum) {
        return besucherRepository.findByBesuchsdatum(besuchsdatum);
    }


}
