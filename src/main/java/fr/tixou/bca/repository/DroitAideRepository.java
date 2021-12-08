package fr.tixou.bca.repository;

import fr.tixou.bca.domain.DroitAide;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DroitAide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitAideRepository extends JpaRepository<DroitAide, Long> {}
