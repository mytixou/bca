package fr.tixou.bca.repository;

import fr.tixou.bca.domain.DroitsStrategiePch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DroitsStrategiePch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitsStrategiePchRepository extends JpaRepository<DroitsStrategiePch, Long> {}
