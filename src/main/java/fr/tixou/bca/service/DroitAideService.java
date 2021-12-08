package fr.tixou.bca.service;

import fr.tixou.bca.domain.DroitAide;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroitAide}.
 */
public interface DroitAideService {
    /**
     * Save a droitAide.
     *
     * @param droitAide the entity to save.
     * @return the persisted entity.
     */
    DroitAide save(DroitAide droitAide);

    /**
     * Partially updates a droitAide.
     *
     * @param droitAide the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DroitAide> partialUpdate(DroitAide droitAide);

    /**
     * Get all the droitAides.
     *
     * @return the list of entities.
     */
    List<DroitAide> findAll();

    /**
     * Get the "id" droitAide.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroitAide> findOne(Long id);

    /**
     * Delete the "id" droitAide.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
