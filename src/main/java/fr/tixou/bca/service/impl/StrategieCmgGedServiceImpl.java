package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.StrategieCmgGed;
import fr.tixou.bca.repository.StrategieCmgGedRepository;
import fr.tixou.bca.service.StrategieCmgGedService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrategieCmgGed}.
 */
@Service
@Transactional
public class StrategieCmgGedServiceImpl implements StrategieCmgGedService {

    private final Logger log = LoggerFactory.getLogger(StrategieCmgGedServiceImpl.class);

    private final StrategieCmgGedRepository strategieCmgGedRepository;

    public StrategieCmgGedServiceImpl(StrategieCmgGedRepository strategieCmgGedRepository) {
        this.strategieCmgGedRepository = strategieCmgGedRepository;
    }

    @Override
    public StrategieCmgGed save(StrategieCmgGed strategieCmgGed) {
        log.debug("Request to save StrategieCmgGed : {}", strategieCmgGed);
        return strategieCmgGedRepository.save(strategieCmgGed);
    }

    @Override
    public Optional<StrategieCmgGed> partialUpdate(StrategieCmgGed strategieCmgGed) {
        log.debug("Request to partially update StrategieCmgGed : {}", strategieCmgGed);

        return strategieCmgGedRepository
            .findById(strategieCmgGed.getId())
            .map(existingStrategieCmgGed -> {
                if (strategieCmgGed.getAnne() != null) {
                    existingStrategieCmgGed.setAnne(strategieCmgGed.getAnne());
                }
                if (strategieCmgGed.getMois() != null) {
                    existingStrategieCmgGed.setMois(strategieCmgGed.getMois());
                }
                if (strategieCmgGed.getNbHeureSeuilPlafond() != null) {
                    existingStrategieCmgGed.setNbHeureSeuilPlafond(strategieCmgGed.getNbHeureSeuilPlafond());
                }
                if (strategieCmgGed.getTauxSalaire() != null) {
                    existingStrategieCmgGed.setTauxSalaire(strategieCmgGed.getTauxSalaire());
                }
                if (strategieCmgGed.getTauxCotisations() != null) {
                    existingStrategieCmgGed.setTauxCotisations(strategieCmgGed.getTauxCotisations());
                }
                if (strategieCmgGed.getIsActif() != null) {
                    existingStrategieCmgGed.setIsActif(strategieCmgGed.getIsActif());
                }
                if (strategieCmgGed.getDateCreated() != null) {
                    existingStrategieCmgGed.setDateCreated(strategieCmgGed.getDateCreated());
                }
                if (strategieCmgGed.getIsUpdated() != null) {
                    existingStrategieCmgGed.setIsUpdated(strategieCmgGed.getIsUpdated());
                }
                if (strategieCmgGed.getDateModified() != null) {
                    existingStrategieCmgGed.setDateModified(strategieCmgGed.getDateModified());
                }

                return existingStrategieCmgGed;
            })
            .map(strategieCmgGedRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategieCmgGed> findAll() {
        log.debug("Request to get all StrategieCmgGeds");
        return strategieCmgGedRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrategieCmgGed> findOne(Long id) {
        log.debug("Request to get StrategieCmgGed : {}", id);
        return strategieCmgGedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrategieCmgGed : {}", id);
        strategieCmgGedRepository.deleteById(id);
    }
}
