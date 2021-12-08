package fr.tixou.bca.service;

import fr.tixou.bca.domain.StrategieApa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link StrategieApa}.
 */
public interface StrategieApaService {
    /**
     * Save a strategieApa.
     *
     * @param strategieApa the entity to save.
     * @return the persisted entity.
     */
    StrategieApa save(StrategieApa strategieApa);

    /**
     * Partially updates a strategieApa.
     *
     * @param strategieApa the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StrategieApa> partialUpdate(StrategieApa strategieApa);

    /**
     * Get all the strategieApas.
     *
     * @return the list of entities.
     */
    List<StrategieApa> findAll();

    /**
     * Get all the strategieApas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StrategieApa> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" strategieApa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StrategieApa> findOne(Long id);

    /**
     * Delete the "id" strategieApa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
