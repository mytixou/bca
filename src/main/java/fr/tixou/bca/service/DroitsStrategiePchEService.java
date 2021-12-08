package fr.tixou.bca.service;

import fr.tixou.bca.domain.DroitsStrategiePchE;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroitsStrategiePchE}.
 */
public interface DroitsStrategiePchEService {
    /**
     * Save a droitsStrategiePchE.
     *
     * @param droitsStrategiePchE the entity to save.
     * @return the persisted entity.
     */
    DroitsStrategiePchE save(DroitsStrategiePchE droitsStrategiePchE);

    /**
     * Partially updates a droitsStrategiePchE.
     *
     * @param droitsStrategiePchE the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DroitsStrategiePchE> partialUpdate(DroitsStrategiePchE droitsStrategiePchE);

    /**
     * Get all the droitsStrategiePchES.
     *
     * @return the list of entities.
     */
    List<DroitsStrategiePchE> findAll();

    /**
     * Get the "id" droitsStrategiePchE.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroitsStrategiePchE> findOne(Long id);

    /**
     * Delete the "id" droitsStrategiePchE.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
