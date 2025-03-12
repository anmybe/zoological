package com.datenbanken.zoological_app.entity;

import com.datenbanken.zoological_app.repository.TierRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "tier")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tier_id")
    private Long tierId;

    private String tier_name;
    private String tierart;
    private String herkunftsland;
    private String geschlecht;
    private Integer geburtsjahr;
    private LocalDate aufnahmedatum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuetterungsplan_id", nullable = true)
    @JsonIgnore
    private Fuetterungsplan fuetterungsplan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gehege_id", nullable = true)
    @JsonIgnore
    private Gehege gehege;

    @Version
    private Long version;

    // Getter und Setter
    public Long getTierId() {
        return tierId;
    }

    public void setTierId(Long id) {
        this.tierId = id;
    }

    public String getName() {
        return tier_name;
    }

    public void setName(String name) {
        this.tier_name = name;
    }

    public String getTierart() {
        return tierart;
    }

    public void setTierart(String tierart) {
        this.tierart = tierart;
    }

    public String getHerkunftsland() {
        return herkunftsland;
    }

    public void setHerkunftsland(String herkunftsland) {
        this.herkunftsland = herkunftsland;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public Integer getGeburtsjahr() {
        return geburtsjahr;
    }

    public void setGeburtsjahr(Integer geburtsjahr) {
        this.geburtsjahr = geburtsjahr;
    }

    public LocalDate getAufnahmedatum() {
        return aufnahmedatum;
    }

    public void setAufnahmedatum(LocalDate aufnahmedatum) {
        this.aufnahmedatum = aufnahmedatum;
    }

    public Fuetterungsplan getFuetterungsplan() {
        return fuetterungsplan;
    }

    public void setFuetterungsplan(Fuetterungsplan fuetterungsplan) {
        this.fuetterungsplan = fuetterungsplan;
    }

    public Gehege getGehege() {
        return gehege;
    }

    public void setGehege(Gehege gehege) {
        this.gehege = gehege;
    }

    @Override
    public String toString() {
        return String.valueOf(tierId);
    }
}
