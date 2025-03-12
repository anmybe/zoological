package com.datenbanken.zoological_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;


@Entity
@Table(name = "besucher")
public class Besucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "besucher_id")
    private Long besucherId;
    @NotNull
    private LocalDate besuchsdatum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {})
    @JsonIgnore
    @JoinTable(
            name = "besucht",
            joinColumns = @JoinColumn(name = "besucher_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    private List<Show> shows;

    @Version
    private Long version;

    // Getter and Setter
    public void setBesuchsdatum(LocalDate besuchsdatum) {
        this.besuchsdatum = besuchsdatum;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public Long getBesucherId() {
        return besucherId;
    }

    public void setBesucherId(Long besucherId) {
        this.besucherId = besucherId;
    }

    public LocalDate getBesuchsdatum() {
        return besuchsdatum;
    }

    @Override
    public String toString() {
        return String.valueOf(besucherId);
    }
}
