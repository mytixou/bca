package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.StrategiePch;
import fr.tixou.bca.repository.StrategiePchRepository;
import fr.tixou.bca.service.StrategiePchService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategiePch}.
 */
@Service
@Transactional
public class StrategiePchServiceImpl implements StrategiePchService {

    private final Logger log = LoggerFactory.getLogger(StrategiePchServiceImpl.class);

    private final StrategiePchRepository strategiePchRepository;

    public StrategiePchServiceImpl(StrategiePchRepository strategiePchRepository) {
        this.strategiePchRepository = strategiePchRepository;
    }

    @Override
    public StrategiePch save(StrategiePch strategiePch) {
        log.debug("Request to save StrategiePch : {}", strategiePch);
        return strategiePchRepository.save(strategiePch);
    }

    @Override
    public Optional<StrategiePch> partialUpdate(StrategiePch strategiePch) {
        log.debug("Request to partially update StrategiePch : {}", strategiePch);

        return strategiePchRepository
            .findById(strategiePch.getId())
            .map(existingStrategiePch -> {
                if (strategiePch.getIsActif() != null) {
                    existingStrategiePch.setIsActif(strategiePch.getIsActif());
                }
                if (strategiePch.getDateMensuelleDebutValidite() != null) {
                    existingStrategiePch.setDateMensuelleDebutValidite(strategiePch.getDateMensuelleDebutValidite());
                }
                if (strategiePch.getAnne() != null) {
                    existingStrategiePch.setAnne(strategiePch.getAnne());
                }
                if (strategiePch.getMois() != null) {
                    existingStrategiePch.setMois(strategiePch.getMois());
                }
                if (strategiePch.getMontantPlafondSalaire() != null) {
                    existingStrategiePch.setMontantPlafondSalaire(strategiePch.getMontantPlafondSalaire());
                }
                if (strategiePch.getMontantPlafondCotisations() != null) {
                    existingStrategiePch.setMontantPlafondCotisations(strategiePch.getMontantPlafondCotisations());
                }
                if (strategiePch.getMontantPlafondSalairePlus() != null) {
                    existingStrategiePch.setMontantPlafondSalairePlus(strategiePch.getMontantPlafondSalairePlus());
                }
                if (strategiePch.getMontantPlafondCotisationsPlus() != null) {
                    existingStrategiePch.setMontantPlafondCotisationsPlus(strategiePch.getMontantPlafondCotisationsPlus());
                }
                if (strategiePch.getNbHeureSalairePlafond() != null) {
                    existingStrategiePch.setNbHeureSalairePlafond(strategiePch.getNbHeureSalairePlafond());
                }
                if (strategiePch.getTauxSalaire() != null) {
                    existingStrategiePch.setTauxSalaire(strategiePch.getTauxSalaire());
                }
                if (strategiePch.getTauxCotisations() != null) {
                    existingStrategiePch.setTauxCotisations(strategiePch.getTauxCotisations());
                }

                return existingStrategiePch;
            })
            .map(strategiePchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategiePch> findAll() {
        log.debug("Request to get all StrategiePches");
        return strategiePchRepository.findAllWithEagerRelationships();
    }

    public Page<StrategiePch> findAllWithEagerRelationships(Pageable pageable) {
        return strategiePchRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategiePch> findOne(Long id) {
        log.debug("Request to get StrategiePch : {}", id);
        return strategiePchRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategiePch : {}", id);
        strategiePchRepository.deleteById(id);
    }
}
