package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.StrategiePchE;
import fr.tixou.bca.repository.StrategiePchERepository;
import fr.tixou.bca.service.StrategiePchEService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategiePchE}.
 */
@Service
@Transactional
public class StrategiePchEServiceImpl implements StrategiePchEService {

    private final Logger log = LoggerFactory.getLogger(StrategiePchEServiceImpl.class);

    private final StrategiePchERepository strategiePchERepository;

    public StrategiePchEServiceImpl(StrategiePchERepository strategiePchERepository) {
        this.strategiePchERepository = strategiePchERepository;
    }

    @Override
    public StrategiePchE save(StrategiePchE strategiePchE) {
        log.debug("Request to save StrategiePchE : {}", strategiePchE);
        return strategiePchERepository.save(strategiePchE);
    }

    @Override
    public Optional<StrategiePchE> partialUpdate(StrategiePchE strategiePchE) {
        log.debug("Request to partially update StrategiePchE : {}", strategiePchE);

        return strategiePchERepository
            .findById(strategiePchE.getId())
            .map(existingStrategiePchE -> {
                if (strategiePchE.getIsActif() != null) {
                    existingStrategiePchE.setIsActif(strategiePchE.getIsActif());
                }
                if (strategiePchE.getDateMensuelleDebutValidite() != null) {
                    existingStrategiePchE.setDateMensuelleDebutValidite(strategiePchE.getDateMensuelleDebutValidite());
                }
                if (strategiePchE.getAnne() != null) {
                    existingStrategiePchE.setAnne(strategiePchE.getAnne());
                }
                if (strategiePchE.getMois() != null) {
                    existingStrategiePchE.setMois(strategiePchE.getMois());
                }
                if (strategiePchE.getMontantPlafondSalaire() != null) {
                    existingStrategiePchE.setMontantPlafondSalaire(strategiePchE.getMontantPlafondSalaire());
                }
                if (strategiePchE.getMontantPlafondCotisations() != null) {
                    existingStrategiePchE.setMontantPlafondCotisations(strategiePchE.getMontantPlafondCotisations());
                }
                if (strategiePchE.getMontantPlafondSalairePlus() != null) {
                    existingStrategiePchE.setMontantPlafondSalairePlus(strategiePchE.getMontantPlafondSalairePlus());
                }
                if (strategiePchE.getMontantPlafondCotisationsPlus() != null) {
                    existingStrategiePchE.setMontantPlafondCotisationsPlus(strategiePchE.getMontantPlafondCotisationsPlus());
                }
                if (strategiePchE.getNbHeureSalairePlafond() != null) {
                    existingStrategiePchE.setNbHeureSalairePlafond(strategiePchE.getNbHeureSalairePlafond());
                }
                if (strategiePchE.getTauxSalaire() != null) {
                    existingStrategiePchE.setTauxSalaire(strategiePchE.getTauxSalaire());
                }
                if (strategiePchE.getTauxCotisations() != null) {
                    existingStrategiePchE.setTauxCotisations(strategiePchE.getTauxCotisations());
                }

                return existingStrategiePchE;
            })
            .map(strategiePchERepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategiePchE> findAll() {
        log.debug("Request to get all StrategiePchES");
        return strategiePchERepository.findAllWithEagerRelationships();
    }

    public Page<StrategiePchE> findAllWithEagerRelationships(Pageable pageable) {
        return strategiePchERepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategiePchE> findOne(Long id) {
        log.debug("Request to get StrategiePchE : {}", id);
        return strategiePchERepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategiePchE : {}", id);
        strategiePchERepository.deleteById(id);
    }
}
