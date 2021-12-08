package fr.tixou.bca.service;

import fr.tixou.bca.domain.StrategieCmgGed;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StrategieCmgGed}.
 */
public interface StrategieCmgGedService {
    /**
     * Save a strategieCmgGed.
     *
     * @param strategieCmgGed the entity to save.
     * @return the persisted entity.
     */
    StrategieCmgGed save(StrategieCmgGed strategieCmgGed);

    /**
     * Partially updates a strategieCmgGed.
     *
     * @param strategieCmgGed the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrategieCmgGed> partialUpdate(StrategieCmgGed strategieCmgGed);

    /**
     * Get all the strategieCmgGeds.
     *
     * @return the list of entities.
     */
    List<StrategieCmgGed> findAll();

    /**
     * Get the "id" strategieCmgGed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrategieCmgGed> findOne(Long id);

    /**
     * Delete the "id" strategieCmgGed.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
