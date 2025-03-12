package com.datenbanken.zoological_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "mitarbeiter")
public class Mitarbeiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mitarbeiter_id")
    private Integer mitarbeiterId;

    private String vorname;
    private String nachname;
    private String position;
    private String verantwortungsbereich;
    private String arbeitsschicht;


    @OneToMany(mappedBy = "mitarbeiter", fetch = FetchType.EAGER, cascade = {}, orphanRemoval = false)
    @JsonIgnore
    private List<Fuetterungseinheit> fuetterungseinheiten;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "istzugewiesen",
            joinColumns = @JoinColumn(name = "mitarbeiter_id"),
            inverseJoinColumns = @JoinColumn(name = "gehege_id")
    )
    private Set<Gehege> gehege;

    @Version
    private Long version;


    // Getter and Setter

    public Integer getId() {
        return mitarbeiterId;
    }

    public void setId(Integer mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getVerantwortungsbereich() {
        return verantwortungsbereich;
    }

    public void setVerantwortungsbereich(String verantwortungsbereich) {
        this.verantwortungsbereich = verantwortungsbereich;
    }

    public String getArbeitsschicht() {
        return arbeitsschicht;
    }

    public void setArbeitsschicht(String arbeitsschicht) {
        this.arbeitsschicht = arbeitsschicht;
    }

    public List<Fuetterungseinheit> getFuetterungseinheiten() {
        return fuetterungseinheiten;
    }

    public void setFuetterungseinheiten(List<Fuetterungseinheit> fuetterungseinheiten) {
        this.fuetterungseinheiten = fuetterungseinheiten;
    }

    public Set<Gehege> getGehege() {
        return gehege;
    }

    public void setGehege(Set<Gehege> gehege) {
        this.gehege = gehege;
    }

    @Override
    public String toString() {
        return String.valueOf(mitarbeiterId);
    }
}
