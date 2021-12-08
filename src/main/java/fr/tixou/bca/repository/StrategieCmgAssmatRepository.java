package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategieCmgAssmat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategieCmgAssmat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrategieCmgAssmatRepository extends JpaRepository<StrategieCmgAssmat, Long> {}
