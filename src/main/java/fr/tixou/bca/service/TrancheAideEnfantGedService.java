package fr.tixou.bca.service;

import fr.tixou.bca.domain.TrancheAideEnfantGed;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TrancheAideEnfantGed}.
 */
public interface TrancheAideEnfantGedService {
    /**
     * Save a trancheAideEnfantGed.
     *
     * @param trancheAideEnfantGed the entity to save.
     * @return the persisted entity.
     */
    TrancheAideEnfantGed save(TrancheAideEnfantGed trancheAideEnfantGed);

    /**
     * Partially updates a trancheAideEnfantGed.
     *
     * @param trancheAideEnfantGed the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrancheAideEnfantGed> partialUpdate(TrancheAideEnfantGed trancheAideEnfantGed);

    /**
     * Get all the trancheAideEnfantGeds.
     *
     * @return the list of entities.
     */
    List<TrancheAideEnfantGed> findAll();

    /**
     * Get the "id" trancheAideEnfantGed.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrancheAideEnfantGed> findOne(Long id);

    /**
     * Delete the "id" trancheAideEnfantGed.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
