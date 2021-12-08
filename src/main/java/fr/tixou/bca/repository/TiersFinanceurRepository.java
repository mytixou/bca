package fr.tixou.bca.repository;

import fr.tixou.bca.domain.TiersFinanceur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TiersFinanceur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TiersFinanceurRepository extends JpaRepository<TiersFinanceur, Long> {}
