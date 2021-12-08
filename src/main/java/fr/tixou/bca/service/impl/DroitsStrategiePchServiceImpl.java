package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.DroitsStrategiePch;
import fr.tixou.bca.repository.DroitsStrategiePchRepository;
import fr.tixou.bca.service.DroitsStrategiePchService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DroitsStrategiePch}.
 */
@Service
@Transactional
public class DroitsStrategiePchServiceImpl implements DroitsStrategiePchService {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategiePchServiceImpl.class);

    private final DroitsStrategiePchRepository droitsStrategiePchRepository;

    public DroitsStrategiePchServiceImpl(DroitsStrategiePchRepository droitsStrategiePchRepository) {
        this.droitsStrategiePchRepository = droitsStrategiePchRepository;
    }

    @Override
    public DroitsStrategiePch save(DroitsStrategiePch droitsStrategiePch) {
        log.debug("Request to save DroitsStrategiePch : {}", droitsStrategiePch);
        return droitsStrategiePchRepository.save(droitsStrategiePch);
    }

    @Override
    public Optional<DroitsStrategiePch> partialUpdate(DroitsStrategiePch droitsStrategiePch) {
        log.debug("Request to partially update DroitsStrategiePch : {}", droitsStrategiePch);

        return droitsStrategiePchRepository
            .findById(droitsStrategiePch.getId())
            .map(existingDroitsStrategiePch -> {
                if (droitsStrategiePch.getIsActif() != null) {
                    existingDroitsStrategiePch.setIsActif(droitsStrategiePch.getIsActif());
                }
                if (droitsStrategiePch.getAnne() != null) {
                    existingDroitsStrategiePch.setAnne(droitsStrategiePch.getAnne());
                }
                if (droitsStrategiePch.getMois() != null) {
                    existingDroitsStrategiePch.setMois(droitsStrategiePch.getMois());
                }
                if (droitsStrategiePch.getMontantPlafond() != null) {
                    existingDroitsStrategiePch.setMontantPlafond(droitsStrategiePch.getMontantPlafond());
                }
                if (droitsStrategiePch.getMontantPlafondPlus() != null) {
                    existingDroitsStrategiePch.setMontantPlafondPlus(droitsStrategiePch.getMontantPlafondPlus());
                }
                if (droitsStrategiePch.getNbHeurePlafond() != null) {
                    existingDroitsStrategiePch.setNbHeurePlafond(droitsStrategiePch.getNbHeurePlafond());
                }
                if (droitsStrategiePch.getTauxCotisations() != null) {
                    existingDroitsStrategiePch.setTauxCotisations(droitsStrategiePch.getTauxCotisations());
                }
                if (droitsStrategiePch.getDateOuverture() != null) {
                    existingDroitsStrategiePch.setDateOuverture(droitsStrategiePch.getDateOuverture());
                }
                if (droitsStrategiePch.getDateFermeture() != null) {
                    existingDroitsStrategiePch.setDateFermeture(droitsStrategiePch.getDateFermeture());
                }

                return existingDroitsStrategiePch;
            })
            .map(droitsStrategiePchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroitsStrategiePch> findAll() {
        log.debug("Request to get all DroitsStrategiePches");
        return droitsStrategiePchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroitsStrategiePch> findOne(Long id) {
        log.debug("Request to get DroitsStrategiePch : {}", id);
        return droitsStrategiePchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroitsStrategiePch : {}", id);
        droitsStrategiePchRepository.deleteById(id);
    }
}
