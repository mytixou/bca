package fr.tixou.bca.repository;

import fr.tixou.bca.domain.TrancheAideEnfantGed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TrancheAideEnfantGed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrancheAideEnfantGedRepository extends JpaRepository<TrancheAideEnfantGed, Long> {}
