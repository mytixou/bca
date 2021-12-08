package fr.tixou.bca.service;

import fr.tixou.bca.domain.StrategieCmgAssmat;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StrategieCmgAssmat}.
 */
public interface StrategieCmgAssmatService {
    /**
     * Save a strategieCmgAssmat.
     *
     * @param strategieCmgAssmat the entity to save.
     * @return the persisted entity.
     */
    StrategieCmgAssmat save(StrategieCmgAssmat strategieCmgAssmat);

    /**
     * Partially updates a strategieCmgAssmat.
     *
     * @param strategieCmgAssmat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrategieCmgAssmat> partialUpdate(StrategieCmgAssmat strategieCmgAssmat);

    /**
     * Get all the strategieCmgAssmats.
     *
     * @return the list of entities.
     */
    List<StrategieCmgAssmat> findAll();

    /**
     * Get the "id" strategieCmgAssmat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrategieCmgAssmat> findOne(Long id);

    /**
     * Delete the "id" strategieCmgAssmat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
