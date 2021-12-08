package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategieApa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategieApa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrategieApaRepository extends JpaRepository<StrategieApa, Long> {}
