package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.TrancheAideEnfantGed;
import fr.tixou.bca.repository.TrancheAideEnfantGedRepository;
import fr.tixou.bca.service.TrancheAideEnfantGedService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TrancheAideEnfantGed}.
 */
@Service
@Transactional
public class TrancheAideEnfantGedServiceImpl implements TrancheAideEnfantGedService {

    private final Logger log = LoggerFactory.getLogger(TrancheAideEnfantGedServiceImpl.class);

    private final TrancheAideEnfantGedRepository trancheAideEnfantGedRepository;

    public TrancheAideEnfantGedServiceImpl(TrancheAideEnfantGedRepository trancheAideEnfantGedRepository) {
        this.trancheAideEnfantGedRepository = trancheAideEnfantGedRepository;
    }

    @Override
    public TrancheAideEnfantGed save(TrancheAideEnfantGed trancheAideEnfantGed) {
        log.debug("Request to save TrancheAideEnfantGed : {}", trancheAideEnfantGed);
        return trancheAideEnfantGedRepository.save(trancheAideEnfantGed);
    }

    @Override
    public Optional<TrancheAideEnfantGed> partialUpdate(TrancheAideEnfantGed trancheAideEnfantGed) {
        log.debug("Request to partially update TrancheAideEnfantGed : {}", trancheAideEnfantGed);

        return trancheAideEnfantGedRepository
            .findById(trancheAideEnfantGed.getId())
            .map(existingTrancheAideEnfantGed -> {
                if (trancheAideEnfantGed.getAnne() != null) {
                    existingTrancheAideEnfantGed.setAnne(trancheAideEnfantGed.getAnne());
                }
                if (trancheAideEnfantGed.getMois() != null) {
                    existingTrancheAideEnfantGed.setMois(trancheAideEnfantGed.getMois());
                }
                if (trancheAideEnfantGed.getAgeEnfantRevoluSurPeriode() != null) {
                    existingTrancheAideEnfantGed.setAgeEnfantRevoluSurPeriode(trancheAideEnfantGed.getAgeEnfantRevoluSurPeriode());
                }
                if (trancheAideEnfantGed.getMontantPlafondSalaire() != null) {
                    existingTrancheAideEnfantGed.setMontantPlafondSalaire(trancheAideEnfantGed.getMontantPlafondSalaire());
                }
                if (trancheAideEnfantGed.getIsActif() != null) {
                    existingTrancheAideEnfantGed.setIsActif(trancheAideEnfantGed.getIsActif());
                }
                if (trancheAideEnfantGed.getDateCreated() != null) {
                    existingTrancheAideEnfantGed.setDateCreated(trancheAideEnfantGed.getDateCreated());
                }
                if (trancheAideEnfantGed.getIsUpdated() != null) {
                    existingTrancheAideEnfantGed.setIsUpdated(trancheAideEnfantGed.getIsUpdated());
                }
                if (trancheAideEnfantGed.getDateModified() != null) {
                    existingTrancheAideEnfantGed.setDateModified(trancheAideEnfantGed.getDateModified());
                }

                return existingTrancheAideEnfantGed;
            })
            .map(trancheAideEnfantGedRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrancheAideEnfantGed> findAll() {
        log.debug("Request to get all TrancheAideEnfantGeds");
        return trancheAideEnfantGedRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrancheAideEnfantGed> findOne(Long id) {
        log.debug("Request to get TrancheAideEnfantGed : {}", id);
        return trancheAideEnfantGedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrancheAideEnfantGed : {}", id);
        trancheAideEnfantGedRepository.deleteById(id);
    }
}
