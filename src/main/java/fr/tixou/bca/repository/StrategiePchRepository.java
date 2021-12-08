package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategiePch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategiePch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrategiePchRepository extends JpaRepository<StrategiePch, Long> {}
