package fr.tixou.bca.repository;

import fr.tixou.bca.domain.NatureActivite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureActivite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureActiviteRepository extends JpaRepository<NatureActivite, Long> {}
