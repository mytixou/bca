package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.StrategieCi;
import fr.tixou.bca.repository.StrategieCiRepository;
import fr.tixou.bca.service.StrategieCiService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategieCi}.
 */
@Service
@Transactional
public class StrategieCiServiceImpl implements StrategieCiService {

    private final Logger log = LoggerFactory.getLogger(StrategieCiServiceImpl.class);

    private final StrategieCiRepository strategieCiRepository;

    public StrategieCiServiceImpl(StrategieCiRepository strategieCiRepository) {
        this.strategieCiRepository = strategieCiRepository;
    }

    @Override
    public StrategieCi save(StrategieCi strategieCi) {
        log.debug("Request to save StrategieCi : {}", strategieCi);
        return strategieCiRepository.save(strategieCi);
    }

    @Override
    public Optional<StrategieCi> partialUpdate(StrategieCi strategieCi) {
        log.debug("Request to partially update StrategieCi : {}", strategieCi);

        return strategieCiRepository
            .findById(strategieCi.getId())
            .map(existingStrategieCi -> {
                if (strategieCi.getIsActif() != null) {
                    existingStrategieCi.setIsActif(strategieCi.getIsActif());
                }
                if (strategieCi.getDateAnnuelleDebutValidite() != null) {
                    existingStrategieCi.setDateAnnuelleDebutValidite(strategieCi.getDateAnnuelleDebutValidite());
                }
                if (strategieCi.getAnne() != null) {
                    existingStrategieCi.setAnne(strategieCi.getAnne());
                }
                if (strategieCi.getMontantPlafondDefaut() != null) {
                    existingStrategieCi.setMontantPlafondDefaut(strategieCi.getMontantPlafondDefaut());
                }
                if (strategieCi.getMontantPlafondHandicape() != null) {
                    existingStrategieCi.setMontantPlafondHandicape(strategieCi.getMontantPlafondHandicape());
                }
                if (strategieCi.getMontantPlafondDefautPlus() != null) {
                    existingStrategieCi.setMontantPlafondDefautPlus(strategieCi.getMontantPlafondDefautPlus());
                }
                if (strategieCi.getMontantPlafondHandicapePlus() != null) {
                    existingStrategieCi.setMontantPlafondHandicapePlus(strategieCi.getMontantPlafondHandicapePlus());
                }
                if (strategieCi.getTauxSalaire() != null) {
                    existingStrategieCi.setTauxSalaire(strategieCi.getTauxSalaire());
                }

                return existingStrategieCi;
            })
            .map(strategieCiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategieCi> findAll() {
        log.debug("Request to get all StrategieCis");
        return strategieCiRepository.findAllWithEagerRelationships();
    }

    public Page<StrategieCi> findAllWithEagerRelationships(Pageable pageable) {
        return strategieCiRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategieCi> findOne(Long id) {
        log.debug("Request to get StrategieCi : {}", id);
        return strategieCiRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategieCi : {}", id);
        strategieCiRepository.deleteById(id);
    }
}
