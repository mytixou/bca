package fr.tixou.bca.service;

import fr.tixou.bca.domain.StrategieCi;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StrategieCi}.
 */
public interface StrategieCiService {
    /**
     * Save a strategieCi.
     *
     * @param strategieCi the entity to save.
     * @return the persisted entity.
     */
    StrategieCi save(StrategieCi strategieCi);

    /**
     * Partially updates a strategieCi.
     *
     * @param strategieCi the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrategieCi> partialUpdate(StrategieCi strategieCi);

    /**
     * Get all the strategieCis.
     *
     * @return the list of entities.
     */
    List<StrategieCi> findAll();

    /**
     * Get all the strategieCis with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StrategieCi> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" strategieCi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrategieCi> findOne(Long id);

    /**
     * Delete the "id" strategieCi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
