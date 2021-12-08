package fr.tixou.bca.repository;

import fr.tixou.bca.domain.NatureMontant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureMontant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureMontantRepository extends JpaRepository<NatureMontant, Long> {}
