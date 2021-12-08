package fr.tixou.bca.service;

import fr.tixou.bca.domain.DroitsStrategiePch;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroitsStrategiePch}.
 */
public interface DroitsStrategiePchService {
    /**
     * Save a droitsStrategiePch.
     *
     * @param droitsStrategiePch the entity to save.
     * @return the persisted entity.
     */
    DroitsStrategiePch save(DroitsStrategiePch droitsStrategiePch);

    /**
     * Partially updates a droitsStrategiePch.
     *
     * @param droitsStrategiePch the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DroitsStrategiePch> partialUpdate(DroitsStrategiePch droitsStrategiePch);

    /**
     * Get all the droitsStrategiePches.
     *
     * @return the list of entities.
     */
    List<DroitsStrategiePch> findAll();

    /**
     * Get the "id" droitsStrategiePch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroitsStrategiePch> findOne(Long id);

    /**
     * Delete the "id" droitsStrategiePch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
