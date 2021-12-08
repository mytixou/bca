package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.DroitsStrategieApa;
import fr.tixou.bca.repository.DroitsStrategieApaRepository;
import fr.tixou.bca.service.DroitsStrategieApaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DroitsStrategieApa}.
 */
@Service
@Transactional
public class DroitsStrategieApaServiceImpl implements DroitsStrategieApaService {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategieApaServiceImpl.class);

    private final DroitsStrategieApaRepository droitsStrategieApaRepository;

    public DroitsStrategieApaServiceImpl(DroitsStrategieApaRepository droitsStrategieApaRepository) {
        this.droitsStrategieApaRepository = droitsStrategieApaRepository;
    }

    @Override
    public DroitsStrategieApa save(DroitsStrategieApa droitsStrategieApa) {
        log.debug("Request to save DroitsStrategieApa : {}", droitsStrategieApa);
        return droitsStrategieApaRepository.save(droitsStrategieApa);
    }

    @Override
    public Optional<DroitsStrategieApa> partialUpdate(DroitsStrategieApa droitsStrategieApa) {
        log.debug("Request to partially update DroitsStrategieApa : {}", droitsStrategieApa);

        return droitsStrategieApaRepository
            .findById(droitsStrategieApa.getId())
            .map(existingDroitsStrategieApa -> {
                if (droitsStrategieApa.getIsActif() != null) {
                    existingDroitsStrategieApa.setIsActif(droitsStrategieApa.getIsActif());
                }
                if (droitsStrategieApa.getAnne() != null) {
                    existingDroitsStrategieApa.setAnne(droitsStrategieApa.getAnne());
                }
                if (droitsStrategieApa.getMois() != null) {
                    existingDroitsStrategieApa.setMois(droitsStrategieApa.getMois());
                }
                if (droitsStrategieApa.getMontantPlafond() != null) {
                    existingDroitsStrategieApa.setMontantPlafond(droitsStrategieApa.getMontantPlafond());
                }
                if (droitsStrategieApa.getMontantPlafondPlus() != null) {
                    existingDroitsStrategieApa.setMontantPlafondPlus(droitsStrategieApa.getMontantPlafondPlus());
                }
                if (droitsStrategieApa.getNbHeurePlafond() != null) {
                    existingDroitsStrategieApa.setNbHeurePlafond(droitsStrategieApa.getNbHeurePlafond());
                }
                if (droitsStrategieApa.getTauxCotisations() != null) {
                    existingDroitsStrategieApa.setTauxCotisations(droitsStrategieApa.getTauxCotisations());
                }
                if (droitsStrategieApa.getDateOuverture() != null) {
                    existingDroitsStrategieApa.setDateOuverture(droitsStrategieApa.getDateOuverture());
                }
                if (droitsStrategieApa.getDateFermeture() != null) {
                    existingDroitsStrategieApa.setDateFermeture(droitsStrategieApa.getDateFermeture());
                }

                return existingDroitsStrategieApa;
            })
            .map(droitsStrategieApaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroitsStrategieApa> findAll() {
        log.debug("Request to get all DroitsStrategieApas");
        return droitsStrategieApaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroitsStrategieApa> findOne(Long id) {
        log.debug("Request to get DroitsStrategieApa : {}", id);
        return droitsStrategieApaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroitsStrategieApa : {}", id);
        droitsStrategieApaRepository.deleteById(id);
    }
}
