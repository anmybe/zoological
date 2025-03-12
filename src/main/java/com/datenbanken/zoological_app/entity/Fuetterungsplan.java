package com.datenbanken.zoological_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "fuetterungsplan")
public class Fuetterungsplan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fuetterungsplan_id")
    private Long fuetterungsplanId;

    @OneToMany(mappedBy = "fuetterungsplan", fetch = FetchType.EAGER, cascade = {}, orphanRemoval = false)
    @JsonIgnore
    private List<Fuetterungseinheit> fuetterungseinheiten;

    @Version
    private Long version;

    // Getter and Setter
    public Long getFuetterungsplanId() {
        return fuetterungsplanId;
    }

    public void setFuetterungsplanId(Long fuetterungsplanId) {
        this.fuetterungsplanId = fuetterungsplanId;
    }

    public List<Fuetterungseinheit> getFuetterungseinheiten() {
        return fuetterungseinheiten;
    }

    public void setFuetterungseinheiten(List<Fuetterungseinheit> fuetterungseinheiten) {
        this.fuetterungseinheiten = fuetterungseinheiten;
    }

    @Override
    public String toString() {
        return String.valueOf(fuetterungsplanId);
    }

}
