package fr.tixou.bca.repository;

import fr.tixou.bca.domain.DroitsStrategiePchE;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DroitsStrategiePchE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitsStrategiePchERepository extends JpaRepository<DroitsStrategiePchE, Long> {}
