package fr.tixou.bca.service;

import fr.tixou.bca.domain.DroitsStrategieApa;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroitsStrategieApa}.
 */
public interface DroitsStrategieApaService {
    /**
     * Save a droitsStrategieApa.
     *
     * @param droitsStrategieApa the entity to save.
     * @return the persisted entity.
     */
    DroitsStrategieApa save(DroitsStrategieApa droitsStrategieApa);

    /**
     * Partially updates a droitsStrategieApa.
     *
     * @param droitsStrategieApa the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DroitsStrategieApa> partialUpdate(DroitsStrategieApa droitsStrategieApa);

    /**
     * Get all the droitsStrategieApas.
     *
     * @return the list of entities.
     */
    List<DroitsStrategieApa> findAll();

    /**
     * Get the "id" droitsStrategieApa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroitsStrategieApa> findOne(Long id);

    /**
     * Delete the "id" droitsStrategieApa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
