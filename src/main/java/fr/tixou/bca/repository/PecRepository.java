package fr.tixou.bca.repository;

import fr.tixou.bca.domain.Pec;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pec entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PecRepository extends JpaRepository<Pec, UUID> {}
