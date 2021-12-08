package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategieApa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategieApa entity.
 */
@Repository
public interface StrategieApaRepository extends JpaRepository<StrategieApa, Long> {
    @Query(
        value = "select distinct strategieApa from StrategieApa strategieApa left join fetch strategieApa.natureActivites left join fetch strategieApa.natureMontants",
        countQuery = "select count(distinct strategieApa) from StrategieApa strategieApa"
    )
    Page<StrategieApa> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct strategieApa from StrategieApa strategieApa left join fetch strategieApa.natureActivites left join fetch strategieApa.natureMontants"
    )
    List<StrategieApa> findAllWithEagerRelationships();

    @Query(
        "select strategieApa from StrategieApa strategieApa left join fetch strategieApa.natureActivites left join fetch strategieApa.natureMontants where strategieApa.id =:id"
    )
    Optional<StrategieApa> findOneWithEagerRelationships(@Param("id") Long id);
}
