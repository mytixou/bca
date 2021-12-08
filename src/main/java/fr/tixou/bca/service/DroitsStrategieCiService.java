package fr.tixou.bca.service;

import fr.tixou.bca.domain.DroitsStrategieCi;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroitsStrategieCi}.
 */
public interface DroitsStrategieCiService {
    /**
     * Save a droitsStrategieCi.
     *
     * @param droitsStrategieCi the entity to save.
     * @return the persisted entity.
     */
    DroitsStrategieCi save(DroitsStrategieCi droitsStrategieCi);

    /**
     * Partially updates a droitsStrategieCi.
     *
     * @param droitsStrategieCi the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DroitsStrategieCi> partialUpdate(DroitsStrategieCi droitsStrategieCi);

    /**
     * Get all the droitsStrategieCis.
     *
     * @return the list of entities.
     */
    List<DroitsStrategieCi> findAll();

    /**
     * Get the "id" droitsStrategieCi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroitsStrategieCi> findOne(Long id);

    /**
     * Delete the "id" droitsStrategieCi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
