package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategieCmgGed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategieCmgGed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrategieCmgGedRepository extends JpaRepository<StrategieCmgGed, Long> {}
