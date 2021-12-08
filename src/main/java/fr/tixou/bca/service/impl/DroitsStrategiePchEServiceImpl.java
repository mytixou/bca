package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.DroitsStrategiePchE;
import fr.tixou.bca.repository.DroitsStrategiePchERepository;
import fr.tixou.bca.service.DroitsStrategiePchEService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DroitsStrategiePchE}.
 */
@Service
@Transactional
public class DroitsStrategiePchEServiceImpl implements DroitsStrategiePchEService {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategiePchEServiceImpl.class);

    private final DroitsStrategiePchERepository droitsStrategiePchERepository;

    public DroitsStrategiePchEServiceImpl(DroitsStrategiePchERepository droitsStrategiePchERepository) {
        this.droitsStrategiePchERepository = droitsStrategiePchERepository;
    }

    @Override
    public DroitsStrategiePchE save(DroitsStrategiePchE droitsStrategiePchE) {
        log.debug("Request to save DroitsStrategiePchE : {}", droitsStrategiePchE);
        return droitsStrategiePchERepository.save(droitsStrategiePchE);
    }

    @Override
    public Optional<DroitsStrategiePchE> partialUpdate(DroitsStrategiePchE droitsStrategiePchE) {
        log.debug("Request to partially update DroitsStrategiePchE : {}", droitsStrategiePchE);

        return droitsStrategiePchERepository
            .findById(droitsStrategiePchE.getId())
            .map(existingDroitsStrategiePchE -> {
                if (droitsStrategiePchE.getIsActif() != null) {
                    existingDroitsStrategiePchE.setIsActif(droitsStrategiePchE.getIsActif());
                }
                if (droitsStrategiePchE.getAnne() != null) {
                    existingDroitsStrategiePchE.setAnne(droitsStrategiePchE.getAnne());
                }
                if (droitsStrategiePchE.getMois() != null) {
                    existingDroitsStrategiePchE.setMois(droitsStrategiePchE.getMois());
                }
                if (droitsStrategiePchE.getMontantPlafond() != null) {
                    existingDroitsStrategiePchE.setMontantPlafond(droitsStrategiePchE.getMontantPlafond());
                }
                if (droitsStrategiePchE.getMontantPlafondPlus() != null) {
                    existingDroitsStrategiePchE.setMontantPlafondPlus(droitsStrategiePchE.getMontantPlafondPlus());
                }
                if (droitsStrategiePchE.getNbHeurePlafond() != null) {
                    existingDroitsStrategiePchE.setNbHeurePlafond(droitsStrategiePchE.getNbHeurePlafond());
                }
                if (droitsStrategiePchE.getTauxCotisations() != null) {
                    existingDroitsStrategiePchE.setTauxCotisations(droitsStrategiePchE.getTauxCotisations());
                }
                if (droitsStrategiePchE.getDateOuverture() != null) {
                    existingDroitsStrategiePchE.setDateOuverture(droitsStrategiePchE.getDateOuverture());
                }
                if (droitsStrategiePchE.getDateFermeture() != null) {
                    existingDroitsStrategiePchE.setDateFermeture(droitsStrategiePchE.getDateFermeture());
                }

                return existingDroitsStrategiePchE;
            })
            .map(droitsStrategiePchERepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroitsStrategiePchE> findAll() {
        log.debug("Request to get all DroitsStrategiePchES");
        return droitsStrategiePchERepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroitsStrategiePchE> findOne(Long id) {
        log.debug("Request to get DroitsStrategiePchE : {}", id);
        return droitsStrategiePchERepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroitsStrategiePchE : {}", id);
        droitsStrategiePchERepository.deleteById(id);
    }
}
