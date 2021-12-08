package fr.tixou.bca.repository;

import fr.tixou.bca.domain.DroitsStrategieCi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DroitsStrategieCi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitsStrategieCiRepository extends JpaRepository<DroitsStrategieCi, Long> {}
