package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.TrancheAideEnfantAssmat;
import fr.tixou.bca.repository.TrancheAideEnfantAssmatRepository;
import fr.tixou.bca.service.TrancheAideEnfantAssmatService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TrancheAideEnfantAssmat}.
 */
@Service
@Transactional
public class TrancheAideEnfantAssmatServiceImpl implements TrancheAideEnfantAssmatService {

    private final Logger log = LoggerFactory.getLogger(TrancheAideEnfantAssmatServiceImpl.class);

    private final TrancheAideEnfantAssmatRepository trancheAideEnfantAssmatRepository;

    public TrancheAideEnfantAssmatServiceImpl(TrancheAideEnfantAssmatRepository trancheAideEnfantAssmatRepository) {
        this.trancheAideEnfantAssmatRepository = trancheAideEnfantAssmatRepository;
    }

    @Override
    public TrancheAideEnfantAssmat save(TrancheAideEnfantAssmat trancheAideEnfantAssmat) {
        log.debug("Request to save TrancheAideEnfantAssmat : {}", trancheAideEnfantAssmat);
        return trancheAideEnfantAssmatRepository.save(trancheAideEnfantAssmat);
    }

    @Override
    public Optional<TrancheAideEnfantAssmat> partialUpdate(TrancheAideEnfantAssmat trancheAideEnfantAssmat) {
        log.debug("Request to partially update TrancheAideEnfantAssmat : {}", trancheAideEnfantAssmat);

        return trancheAideEnfantAssmatRepository
            .findById(trancheAideEnfantAssmat.getId())
            .map(existingTrancheAideEnfantAssmat -> {
                if (trancheAideEnfantAssmat.getAnne() != null) {
                    existingTrancheAideEnfantAssmat.setAnne(trancheAideEnfantAssmat.getAnne());
                }
                if (trancheAideEnfantAssmat.getMois() != null) {
                    existingTrancheAideEnfantAssmat.setMois(trancheAideEnfantAssmat.getMois());
                }
                if (trancheAideEnfantAssmat.getAgeEnfantRevoluSurPeriode() != null) {
                    existingTrancheAideEnfantAssmat.setAgeEnfantRevoluSurPeriode(trancheAideEnfantAssmat.getAgeEnfantRevoluSurPeriode());
                }
                if (trancheAideEnfantAssmat.getMontantPlafondSalaire() != null) {
                    existingTrancheAideEnfantAssmat.setMontantPlafondSalaire(trancheAideEnfantAssmat.getMontantPlafondSalaire());
                }
                if (trancheAideEnfantAssmat.getIsActif() != null) {
                    existingTrancheAideEnfantAssmat.setIsActif(trancheAideEnfantAssmat.getIsActif());
                }
                if (trancheAideEnfantAssmat.getDateCreated() != null) {
                    existingTrancheAideEnfantAssmat.setDateCreated(trancheAideEnfantAssmat.getDateCreated());
                }
                if (trancheAideEnfantAssmat.getIsUpdated() != null) {
                    existingTrancheAideEnfantAssmat.setIsUpdated(trancheAideEnfantAssmat.getIsUpdated());
                }
                if (trancheAideEnfantAssmat.getDateModified() != null) {
                    existingTrancheAideEnfantAssmat.setDateModified(trancheAideEnfantAssmat.getDateModified());
                }

                return existingTrancheAideEnfantAssmat;
            })
            .map(trancheAideEnfantAssmatRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrancheAideEnfantAssmat> findAll() {
        log.debug("Request to get all TrancheAideEnfantAssmats");
        return trancheAideEnfantAssmatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrancheAideEnfantAssmat> findOne(Long id) {
        log.debug("Request to get TrancheAideEnfantAssmat : {}", id);
        return trancheAideEnfantAssmatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrancheAideEnfantAssmat : {}", id);
        trancheAideEnfantAssmatRepository.deleteById(id);
    }
}
