package com.datenbanken.zoological_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "gehege")
public class Gehege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gehege_id")
    private Long gehegeId;

    private Double groesse;
    private String faunabereich;
    private LocalDate saeuberungsdatum;
    private LocalTime saeuberungszeit;
    private String groesseneinheit;

    @OneToMany(mappedBy = "gehege",fetch = FetchType.EAGER, cascade = {}, orphanRemoval = false)
    @JsonIgnore
    private List<Tier> tiere;

    @ManyToMany(mappedBy = "gehege", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Mitarbeiter> mitarbeiter;

    @Version
    private Long version;

    public Gehege() {
        this.mitarbeiter = new ArrayList<>();
    }

    // Getter and Setter

    public Long getId() {
        return gehegeId;
    }

    public void setId(Long gehegeId) {
        this.gehegeId = gehegeId;
    }

    public Double getGroesse() {
        return groesse;
    }

    public void setGroesse(Double groesse) {
        this.groesse = groesse;
    }

    public String getFaunabereich() {
        return faunabereich;
    }

    public void setFaunabereich(String faunabereich) {
        this.faunabereich = faunabereich;
    }

    public LocalDate getSaeuberungsdatum() {
        return saeuberungsdatum;
    }

    public void setSaeuberungsdatum(LocalDate saeuberungsdatum) {
        this.saeuberungsdatum = saeuberungsdatum;
    }

    public LocalTime getSaeuberungszeit() {
        return saeuberungszeit;
    }

    public void setSaeuberungszeit(LocalTime saeuberungszeit) {
        this.saeuberungszeit = saeuberungszeit;
    }

    public String getGroesseneinheit() {
        return groesseneinheit;
    }

    public void setGroesseneinheit(String groesseneinheit) {
        this.groesseneinheit = groesseneinheit;
    }

    public List<Tier> getTiere() {
        return tiere;
    }

    public void setTiere(List<Tier> tiere) {
        this.tiere = tiere;
    }

    public List<Mitarbeiter> getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(List<Mitarbeiter> mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    @Override
    public String toString() {
        return String.valueOf(gehegeId);
    }


}
