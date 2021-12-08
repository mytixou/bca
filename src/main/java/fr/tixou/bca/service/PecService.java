package fr.tixou.bca.service;

import fr.tixou.bca.domain.Pec;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link Pec}.
 */
public interface PecService {
    /**
     * Save a pec.
     *
     * @param pec the entity to save.
     * @return the persisted entity.
     */
    Pec save(Pec pec);

    /**
     * Partially updates a pec.
     *
     * @param pec the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pec> partialUpdate(Pec pec);

    /**
     * Get all the pecs.
     *
     * @return the list of entities.
     */
    List<Pec> findAll();

    /**
     * Get the "id" pec.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pec> findOne(UUID id);

    /**
     * Delete the "id" pec.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
