package fr.tixou.bca.repository;

import fr.tixou.bca.domain.TrancheAideEnfantAssmat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TrancheAideEnfantAssmat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrancheAideEnfantAssmatRepository extends JpaRepository<TrancheAideEnfantAssmat, Long> {}
