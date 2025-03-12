package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT s FROM Show s WHERE s.show_datum = :datum")
    List<Show> findByShowDatum(@Param("datum") LocalDate datum);

}
