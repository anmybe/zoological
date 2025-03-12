package com.datenbanken.zoological_app.service;

import com.datenbanken.zoological_app.entity.Besucher;
import com.datenbanken.zoological_app.entity.Show;
import com.datenbanken.zoological_app.repository.FuetterungseinheitRepository;
import com.datenbanken.zoological_app.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ShowService {

    @Autowired
    private final ShowRepository showRepository;
    @Autowired
    private final FuetterungseinheitRepository fuetterungseinheitRepository;

    @Autowired
    public ShowService(ShowRepository showRepository, FuetterungseinheitRepository fuetterungseinheitRepository) {
        this.showRepository = showRepository;
        this.fuetterungseinheitRepository = fuetterungseinheitRepository;
    }

    public List<Show> findAll() {
        return showRepository.findAll();
    }

    public Show addShow(Show show) {
        return showRepository.save(show);
    }

    public Show updateShow(Show show) {
        return showRepository.save(show);
    }

    public void deleteShow(Show show) {
        for(Besucher besucher : show.getBesucher()) {
            besucher.getShows().remove(show);
        }
        showRepository.delete(show);
    }

    public Show findById(Long showId) {
        return showRepository.findById(showId).orElse(null);
    }

    public List<Show> findShowsByDate(LocalDate showDatum) {
        return showRepository.findByShowDatum(showDatum);
    }

}

