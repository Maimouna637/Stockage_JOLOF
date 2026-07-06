package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterielEnStock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaterielEnStock entity.
 */
@SuppressWarnings("unused")
//@Repository
public interface MaterielEnStockRepository extends JpaRepository<MaterielEnStock, Long> {}
