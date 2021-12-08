package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.DroitsStrategieCi;
import fr.tixou.bca.repository.DroitsStrategieCiRepository;
import fr.tixou.bca.service.DroitsStrategieCiService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DroitsStrategieCi}.
 */
@Service
@Transactional
public class DroitsStrategieCiServiceImpl implements DroitsStrategieCiService {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategieCiServiceImpl.class);

    private final DroitsStrategieCiRepository droitsStrategieCiRepository;

    public DroitsStrategieCiServiceImpl(DroitsStrategieCiRepository droitsStrategieCiRepository) {
        this.droitsStrategieCiRepository = droitsStrategieCiRepository;
    }

    @Override
    public DroitsStrategieCi save(DroitsStrategieCi droitsStrategieCi) {
        log.debug("Request to save DroitsStrategieCi : {}", droitsStrategieCi);
        return droitsStrategieCiRepository.save(droitsStrategieCi);
    }

    @Override
    public Optional<DroitsStrategieCi> partialUpdate(DroitsStrategieCi droitsStrategieCi) {
        log.debug("Request to partially update DroitsStrategieCi : {}", droitsStrategieCi);

        return droitsStrategieCiRepository
            .findById(droitsStrategieCi.getId())
            .map(existingDroitsStrategieCi -> {
                if (droitsStrategieCi.getIsActif() != null) {
                    existingDroitsStrategieCi.setIsActif(droitsStrategieCi.getIsActif());
                }
                if (droitsStrategieCi.getAnne() != null) {
                    existingDroitsStrategieCi.setAnne(droitsStrategieCi.getAnne());
                }
                if (droitsStrategieCi.getMontantPlafondDefaut() != null) {
                    existingDroitsStrategieCi.setMontantPlafondDefaut(droitsStrategieCi.getMontantPlafondDefaut());
                }
                if (droitsStrategieCi.getMontantPlafondHandicape() != null) {
                    existingDroitsStrategieCi.setMontantPlafondHandicape(droitsStrategieCi.getMontantPlafondHandicape());
                }
                if (droitsStrategieCi.getMontantPlafondDefautPlus() != null) {
                    existingDroitsStrategieCi.setMontantPlafondDefautPlus(droitsStrategieCi.getMontantPlafondDefautPlus());
                }
                if (droitsStrategieCi.getMontantPlafondHandicapePlus() != null) {
                    existingDroitsStrategieCi.setMontantPlafondHandicapePlus(droitsStrategieCi.getMontantPlafondHandicapePlus());
                }
                if (droitsStrategieCi.getTauxSalaire() != null) {
                    existingDroitsStrategieCi.setTauxSalaire(droitsStrategieCi.getTauxSalaire());
                }
                if (droitsStrategieCi.getDateOuverture() != null) {
                    existingDroitsStrategieCi.setDateOuverture(droitsStrategieCi.getDateOuverture());
                }
                if (droitsStrategieCi.getDateFermeture() != null) {
                    existingDroitsStrategieCi.setDateFermeture(droitsStrategieCi.getDateFermeture());
                }

                return existingDroitsStrategieCi;
            })
            .map(droitsStrategieCiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroitsStrategieCi> findAll() {
        log.debug("Request to get all DroitsStrategieCis");
        return droitsStrategieCiRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroitsStrategieCi> findOne(Long id) {
        log.debug("Request to get DroitsStrategieCi : {}", id);
        return droitsStrategieCiRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroitsStrategieCi : {}", id);
        droitsStrategieCiRepository.deleteById(id);
    }
}
