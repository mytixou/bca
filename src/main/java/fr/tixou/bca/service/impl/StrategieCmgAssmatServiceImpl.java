package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.StrategieCmgAssmat;
import fr.tixou.bca.repository.StrategieCmgAssmatRepository;
import fr.tixou.bca.service.StrategieCmgAssmatService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategieCmgAssmat}.
 */
@Service
@Transactional
public class StrategieCmgAssmatServiceImpl implements StrategieCmgAssmatService {

    private final Logger log = LoggerFactory.getLogger(StrategieCmgAssmatServiceImpl.class);

    private final StrategieCmgAssmatRepository strategieCmgAssmatRepository;

    public StrategieCmgAssmatServiceImpl(StrategieCmgAssmatRepository strategieCmgAssmatRepository) {
        this.strategieCmgAssmatRepository = strategieCmgAssmatRepository;
    }

    @Override
    public StrategieCmgAssmat save(StrategieCmgAssmat strategieCmgAssmat) {
        log.debug("Request to save StrategieCmgAssmat : {}", strategieCmgAssmat);
        return strategieCmgAssmatRepository.save(strategieCmgAssmat);
    }

    @Override
    public Optional<StrategieCmgAssmat> partialUpdate(StrategieCmgAssmat strategieCmgAssmat) {
        log.debug("Request to partially update StrategieCmgAssmat : {}", strategieCmgAssmat);

        return strategieCmgAssmatRepository
            .findById(strategieCmgAssmat.getId())
            .map(existingStrategieCmgAssmat -> {
                if (strategieCmgAssmat.getAnne() != null) {
                    existingStrategieCmgAssmat.setAnne(strategieCmgAssmat.getAnne());
                }
                if (strategieCmgAssmat.getMois() != null) {
                    existingStrategieCmgAssmat.setMois(strategieCmgAssmat.getMois());
                }
                if (strategieCmgAssmat.getNbHeureSeuilPlafond() != null) {
                    existingStrategieCmgAssmat.setNbHeureSeuilPlafond(strategieCmgAssmat.getNbHeureSeuilPlafond());
                }
                if (strategieCmgAssmat.getTauxSalaire() != null) {
                    existingStrategieCmgAssmat.setTauxSalaire(strategieCmgAssmat.getTauxSalaire());
                }
                if (strategieCmgAssmat.getTauxCotisations() != null) {
                    existingStrategieCmgAssmat.setTauxCotisations(strategieCmgAssmat.getTauxCotisations());
                }
                if (strategieCmgAssmat.getIsActif() != null) {
                    existingStrategieCmgAssmat.setIsActif(strategieCmgAssmat.getIsActif());
                }
                if (strategieCmgAssmat.getDateCreated() != null) {
                    existingStrategieCmgAssmat.setDateCreated(strategieCmgAssmat.getDateCreated());
                }
                if (strategieCmgAssmat.getIsUpdated() != null) {
                    existingStrategieCmgAssmat.setIsUpdated(strategieCmgAssmat.getIsUpdated());
                }
                if (strategieCmgAssmat.getDateModified() != null) {
                    existingStrategieCmgAssmat.setDateModified(strategieCmgAssmat.getDateModified());
                }

                return existingStrategieCmgAssmat;
            })
            .map(strategieCmgAssmatRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategieCmgAssmat> findAll() {
        log.debug("Request to get all StrategieCmgAssmats");
        return strategieCmgAssmatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategieCmgAssmat> findOne(Long id) {
        log.debug("Request to get StrategieCmgAssmat : {}", id);
        return strategieCmgAssmatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategieCmgAssmat : {}", id);
        strategieCmgAssmatRepository.deleteById(id);
    }
}
