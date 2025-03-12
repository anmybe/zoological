package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Fuetterungsplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface FuetterungsplanRepository extends JpaRepository<Fuetterungsplan, BigInteger> {

}
