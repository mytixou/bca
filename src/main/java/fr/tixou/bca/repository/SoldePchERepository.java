package fr.tixou.bca.repository;

import fr.tixou.bca.domain.SoldePchE;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SoldePchE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoldePchERepository extends JpaRepository<SoldePchE, Long> {}
