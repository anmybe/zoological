package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Gehege;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface GehegeRepository extends JpaRepository<Gehege, Long> {

}




