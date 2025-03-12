package com.datenbanken.zoological_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalTime;


@Entity
@Table(name = "fuetterungseinheit")
public class Fuetterungseinheit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fuetterungseinheit_id")
    private Long fuetterungseinheitId;

    private LocalTime fuetterungszeit;

    private String futterart;

    private Double futtermenge;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {})
    @JsonIgnore
    @JoinColumn(name = "fuetterungsplan_id", nullable = true)
    private Fuetterungsplan fuetterungsplan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "mitarbeiter_id", nullable = true)
    private Mitarbeiter mitarbeiter;
    @Version
    private Long version;

    public Fuetterungseinheit() {
    }

    // Parameterized constructor for testing
    public Fuetterungseinheit(String futterart, Double futtermenge) {
        this.futterart = futterart;
        this.futtermenge = futtermenge;
    }

    // Getter and Setter
    public Long getFuetterungseinheitId() {
        return fuetterungseinheitId;
    }

    public void setFuetterungseinheitId(Long fuetterungseinheitId) {
        this.fuetterungseinheitId = fuetterungseinheitId;
    }

    public LocalTime getFuetterungszeit() {
        return fuetterungszeit;
    }

    public void setFuetterungszeit(LocalTime fuetterungszeit) {
        this.fuetterungszeit = fuetterungszeit;
    }

    public String getFutterart() {
        return futterart;
    }

    public void setFutterart(String futterart) {
        this.futterart = futterart;
    }

    public Double getFuttermenge() {
        return futtermenge;
    }

    public void setFuttermenge(Double futtermenge) {
        this.futtermenge = futtermenge;
    }

    public Fuetterungsplan getFuetterungsplan() {
        return fuetterungsplan;
    }

    public void setFuetterungsplan(Fuetterungsplan fuetterungsplan) {
        this.fuetterungsplan = fuetterungsplan;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    @Override
    public String toString() {
        String ids = String.valueOf(fuetterungseinheitId);
        return ids;
    }
}
