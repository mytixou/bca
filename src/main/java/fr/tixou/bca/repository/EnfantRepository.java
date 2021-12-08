package fr.tixou.bca.repository;

import fr.tixou.bca.domain.Enfant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Enfant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Long> {}
