package fr.tixou.bca.repository;

import fr.tixou.bca.domain.DroitsStrategieApa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DroitsStrategieApa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitsStrategieApaRepository extends JpaRepository<DroitsStrategieApa, Long> {}
