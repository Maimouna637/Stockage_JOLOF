package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterielSurLeTerrain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaterielSurLeTerrain entity.
 */
@SuppressWarnings("unused")
//@Repository
public interface MaterielSurLeTerrainRepository extends JpaRepository<MaterielSurLeTerrain, Long> {}
