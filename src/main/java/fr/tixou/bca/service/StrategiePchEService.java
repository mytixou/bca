package fr.tixou.bca.service;

import fr.tixou.bca.domain.StrategiePchE;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StrategiePchE}.
 */
public interface StrategiePchEService {
    /**
     * Save a strategiePchE.
     *
     * @param strategiePchE the entity to save.
     * @return the persisted entity.
     */
    StrategiePchE save(StrategiePchE strategiePchE);

    /**
     * Partially updates a strategiePchE.
     *
     * @param strategiePchE the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrategiePchE> partialUpdate(StrategiePchE strategiePchE);

    /**
     * Get all the strategiePchES.
     *
     * @return the list of entities.
     */
    List<StrategiePchE> findAll();

    /**
     * Get all the strategiePchES with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StrategiePchE> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" strategiePchE.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrategiePchE> findOne(Long id);

    /**
     * Delete the "id" strategiePchE.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
