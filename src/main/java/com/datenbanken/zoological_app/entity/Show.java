package com.datenbanken.zoological_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "show_id")
    private Long showId;

    private String show_name;
    private String showart;
    @Column(name = "show_datum", columnDefinition = "DATE")
    private LocalDate show_datum;
    private LocalTime show_zeit;
    private Integer kapazitaet;

    @ManyToMany(mappedBy = "shows", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Besucher> besucher;

    public Show() {
    }


    public Show(Long showId, String showName, LocalDate showDatum, String showZeit, String showArt, Integer kapazitaet) {
        this.showId = showId;
        this.show_name = showName;
        this.show_datum = showDatum;
        this.show_zeit = LocalTime.parse(showZeit);
        this.showart = showArt;
        this.kapazitaet = kapazitaet;
    }


    // Getter and Setter methods

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return show_name;
    }

    public void setShowName(String show_name) {
        this.show_name = show_name;
    }

    public String getShowart() {
        return showart;
    }

    public void setShowart(String showart) {
        this.showart = showart;
    }

    public LocalDate getShowDatum() {
        return show_datum;
    }

    public void setShowDatum(LocalDate datum) {
        this.show_datum = datum;
    }

    public LocalTime getShowZeit() {
        return show_zeit;
    }

    public void setShowZeit(LocalTime showzeit) {
        this.show_zeit = showzeit;
    }

    public Integer getKapazitaet() {
        return kapazitaet;
    }

    public void setKapazitaet(Integer kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public List<Besucher> getBesucher() {
        return besucher;
    }

    public void setBesucher(List<Besucher> besucher) {
        this.besucher = besucher;
    }

    @JsonIgnore
    public String getAuslastung() {
        return besucher.size() + "/" + kapazitaet;
    }

    @Override
    public String toString() {
        return String.valueOf(showId);
    }
}
