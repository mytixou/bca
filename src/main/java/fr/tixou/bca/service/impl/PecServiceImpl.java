package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.Pec;
import fr.tixou.bca.repository.PecRepository;
import fr.tixou.bca.service.PecService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pec}.
 */
@Service
@Transactional
public class PecServiceImpl implements PecService {

    private final Logger log = LoggerFactory.getLogger(PecServiceImpl.class);

    private final PecRepository pecRepository;

    public PecServiceImpl(PecRepository pecRepository) {
        this.pecRepository = pecRepository;
    }

    @Override
    public Pec save(Pec pec) {
        log.debug("Request to save Pec : {}", pec);
        return pecRepository.save(pec);
    }

    @Override
    public Optional<Pec> partialUpdate(Pec pec) {
        log.debug("Request to partially update Pec : {}", pec);

        return pecRepository
            .findById(pec.getId())
            .map(existingPec -> {
                if (pec.getIdProduit() != null) {
                    existingPec.setIdProduit(pec.getIdProduit());
                }
                if (pec.getProduit() != null) {
                    existingPec.setProduit(pec.getProduit());
                }
                if (pec.getIsPlus() != null) {
                    existingPec.setIsPlus(pec.getIsPlus());
                }
                if (pec.getDateCreated() != null) {
                    existingPec.setDateCreated(pec.getDateCreated());
                }
                if (pec.getIsUpdated() != null) {
                    existingPec.setIsUpdated(pec.getIsUpdated());
                }
                if (pec.getDateModified() != null) {
                    existingPec.setDateModified(pec.getDateModified());
                }
                if (pec.getIsActif() != null) {
                    existingPec.setIsActif(pec.getIsActif());
                }
                if (pec.getPecDetails() != null) {
                    existingPec.setPecDetails(pec.getPecDetails());
                }

                return existingPec;
            })
            .map(pecRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pec> findAll() {
        log.debug("Request to get all Pecs");
        return pecRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pec> findOne(UUID id) {
        log.debug("Request to get Pec : {}", id);
        return pecRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Pec : {}", id);
        pecRepository.deleteById(id);
    }
}
