package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Besucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BesucherRepository extends JpaRepository<Besucher, Long>, JpaSpecificationExecutor<Besucher> {

    @Query("SELECT b FROM Besucher b")
    List<Besucher> findAllBesucher();

    @Query("SELECT b.besuchsdatum, COUNT(b) AS Anzahl FROM Besucher b GROUP BY b.besuchsdatum ORDER BY COUNT(b) DESC")
    List<Object[]> findAllPopularDates();

    @Query("SELECT b FROM Besucher b WHERE b.besuchsdatum = :besuchsdatum")
    List<Besucher> findByBesuchsdatum(LocalDate besuchsdatum);
}
