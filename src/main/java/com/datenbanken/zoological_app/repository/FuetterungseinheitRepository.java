package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Fuetterungseinheit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuetterungseinheitRepository extends JpaRepository<Fuetterungseinheit, Long>, JpaSpecificationExecutor<Fuetterungseinheit> {

    @Query("SELECT f FROM Fuetterungseinheit f WHERE f.mitarbeiter.mitarbeiterId = :mitarbeiterId")
    List<Fuetterungseinheit> findByMitarbeiterId(@Param("mitarbeiterId") Integer mitarbeiterId);

}
