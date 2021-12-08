package fr.tixou.bca.service;

import fr.tixou.bca.domain.TrancheAideEnfantAssmat;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TrancheAideEnfantAssmat}.
 */
public interface TrancheAideEnfantAssmatService {
    /**
     * Save a trancheAideEnfantAssmat.
     *
     * @param trancheAideEnfantAssmat the entity to save.
     * @return the persisted entity.
     */
    TrancheAideEnfantAssmat save(TrancheAideEnfantAssmat trancheAideEnfantAssmat);

    /**
     * Partially updates a trancheAideEnfantAssmat.
     *
     * @param trancheAideEnfantAssmat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrancheAideEnfantAssmat> partialUpdate(TrancheAideEnfantAssmat trancheAideEnfantAssmat);

    /**
     * Get all the trancheAideEnfantAssmats.
     *
     * @return the list of entities.
     */
    List<TrancheAideEnfantAssmat> findAll();

    /**
     * Get the "id" trancheAideEnfantAssmat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrancheAideEnfantAssmat> findOne(Long id);

    /**
     * Delete the "id" trancheAideEnfantAssmat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
