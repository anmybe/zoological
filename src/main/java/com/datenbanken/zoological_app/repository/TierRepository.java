package com.datenbanken.zoological_app.repository;

import com.datenbanken.zoological_app.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long>, JpaSpecificationExecutor<Tier> {

}
