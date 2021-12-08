package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategiePchE;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategiePchE entity.
 */
@Repository
public interface StrategiePchERepository extends JpaRepository<StrategiePchE, Long> {
    @Query(
        value = "select distinct strategiePchE from StrategiePchE strategiePchE left join fetch strategiePchE.natureActivites left join fetch strategiePchE.natureMontants",
        countQuery = "select count(distinct strategiePchE) from StrategiePchE strategiePchE"
    )
    Page<StrategiePchE> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct strategiePchE from StrategiePchE strategiePchE left join fetch strategiePchE.natureActivites left join fetch strategiePchE.natureMontants"
    )
    List<StrategiePchE> findAllWithEagerRelationships();

    @Query(
        "select strategiePchE from StrategiePchE strategiePchE left join fetch strategiePchE.natureActivites left join fetch strategiePchE.natureMontants where strategiePchE.id =:id"
    )
    Optional<StrategiePchE> findOneWithEagerRelationships(@Param("id") Long id);
}
