package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategieCi;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategieCi entity.
 */
@Repository
public interface StrategieCiRepository extends JpaRepository<StrategieCi, Long> {
    @Query(
        value = "select distinct strategieCi from StrategieCi strategieCi left join fetch strategieCi.natureActivites left join fetch strategieCi.natureMontants",
        countQuery = "select count(distinct strategieCi) from StrategieCi strategieCi"
    )
    Page<StrategieCi> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct strategieCi from StrategieCi strategieCi left join fetch strategieCi.natureActivites left join fetch strategieCi.natureMontants"
    )
    List<StrategieCi> findAllWithEagerRelationships();

    @Query(
        "select strategieCi from StrategieCi strategieCi left join fetch strategieCi.natureActivites left join fetch strategieCi.natureMontants where strategieCi.id =:id"
    )
    Optional<StrategieCi> findOneWithEagerRelationships(@Param("id") Long id);
}
