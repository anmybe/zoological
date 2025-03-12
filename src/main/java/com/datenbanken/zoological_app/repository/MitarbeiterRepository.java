package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {

    Mitarbeiter findByMitarbeiterId(Long mitarbeiterId);

}
