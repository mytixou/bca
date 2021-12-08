package fr.tixou.bca.repository;

import fr.tixou.bca.domain.StrategiePch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StrategiePch entity.
 */
@Repository
public interface StrategiePchRepository extends JpaRepository<StrategiePch, Long> {
    @Query(
        value = "select distinct strategiePch from StrategiePch strategiePch left join fetch strategiePch.natureActivites left join fetch strategiePch.natureMontants",
        countQuery = "select count(distinct strategiePch) from StrategiePch strategiePch"
    )
    Page<StrategiePch> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct strategiePch from StrategiePch strategiePch left join fetch strategiePch.natureActivites left join fetch strategiePch.natureMontants"
    )
    List<StrategiePch> findAllWithEagerRelationships();

    @Query(
        "select strategiePch from StrategiePch strategiePch left join fetch strategiePch.natureActivites left join fetch strategiePch.natureMontants where strategiePch.id =:id"
    )
    Optional<StrategiePch> findOneWithEagerRelationships(@Param("id") Long id);
}
