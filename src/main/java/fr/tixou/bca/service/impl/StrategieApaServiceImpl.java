package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.StrategieApa;
import fr.tixou.bca.repository.StrategieApaRepository;
import fr.tixou.bca.service.StrategieApaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategieApa}.
 */
@Service
@Transactional
public class StrategieApaServiceImpl implements StrategieApaService {

    private final Logger log = LoggerFactory.getLogger(StrategieApaServiceImpl.class);

    private final StrategieApaRepository strategieApaRepository;

    public StrategieApaServiceImpl(StrategieApaRepository strategieApaRepository) {
        this.strategieApaRepository = strategieApaRepository;
    }

    @Override
    public StrategieApa save(StrategieApa strategieApa) {
        log.debug("Request to save StrategieApa : {}", strategieApa);
        return strategieApaRepository.save(strategieApa);
    }

    @Override
    public Optional<StrategieApa> partialUpdate(StrategieApa strategieApa) {
        log.debug("Request to partially update StrategieApa : {}", strategieApa);

        return strategieApaRepository
            .findById(strategieApa.getId())
            .map(existingStrategieApa -> {
                if (strategieApa.getIsActif() != null) {
                    existingStrategieApa.setIsActif(strategieApa.getIsActif());
                }
                if (strategieApa.getDateMensuelleDebutValidite() != null) {
                    existingStrategieApa.setDateMensuelleDebutValidite(strategieApa.getDateMensuelleDebutValidite());
                }
                if (strategieApa.getAnne() != null) {
                    existingStrategieApa.setAnne(strategieApa.getAnne());
                }
                if (strategieApa.getMois() != null) {
                    existingStrategieApa.setMois(strategieApa.getMois());
                }
                if (strategieApa.getMontantPlafondSalaire() != null) {
                    existingStrategieApa.setMontantPlafondSalaire(strategieApa.getMontantPlafondSalaire());
                }
                if (strategieApa.getMontantPlafondCotisations() != null) {
                    existingStrategieApa.setMontantPlafondCotisations(strategieApa.getMontantPlafondCotisations());
                }
                if (strategieApa.getMontantPlafondSalairePlus() != null) {
                    existingStrategieApa.setMontantPlafondSalairePlus(strategieApa.getMontantPlafondSalairePlus());
                }
                if (strategieApa.getMontantPlafondCotisationsPlus() != null) {
                    existingStrategieApa.setMontantPlafondCotisationsPlus(strategieApa.getMontantPlafondCotisationsPlus());
                }
                if (strategieApa.getNbHeureSalairePlafond() != null) {
                    existingStrategieApa.setNbHeureSalairePlafond(strategieApa.getNbHeureSalairePlafond());
                }
                if (strategieApa.getTauxSalaire() != null) {
                    existingStrategieApa.setTauxSalaire(strategieApa.getTauxSalaire());
                }
                if (strategieApa.getTauxCotisations() != null) {
                    existingStrategieApa.setTauxCotisations(strategieApa.getTauxCotisations());
                }

                return existingStrategieApa;
            })
            .map(strategieApaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategieApa> findAll() {
        log.debug("Request to get all StrategieApas");
        return strategieApaRepository.findAllWithEagerRelationships();
    }

    public Page<StrategieApa> findAllWithEagerRelationships(Pageable pageable) {
        return strategieApaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategieApa> findOne(Long id) {
        log.debug("Request to get StrategieApa : {}", id);
        return strategieApaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategieApa : {}", id);
        strategieApaRepository.deleteById(id);
    }
}
