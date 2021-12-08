package fr.tixou.bca.repository;

import fr.tixou.bca.domain.SoldeCi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SoldeCi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoldeCiRepository extends JpaRepository<SoldeCi, Long> {}
