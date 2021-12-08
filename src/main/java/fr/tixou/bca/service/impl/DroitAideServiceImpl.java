package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.DroitAide;
import fr.tixou.bca.repository.DroitAideRepository;
import fr.tixou.bca.service.DroitAideService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DroitAide}.
 */
@Service
@Transactional
public class DroitAideServiceImpl implements DroitAideService {

    private final Logger log = LoggerFactory.getLogger(DroitAideServiceImpl.class);

    private final DroitAideRepository droitAideRepository;

    public DroitAideServiceImpl(DroitAideRepository droitAideRepository) {
        this.droitAideRepository = droitAideRepository;
    }

    @Override
    public DroitAide save(DroitAide droitAide) {
        log.debug("Request to save DroitAide : {}", droitAide);
        return droitAideRepository.save(droitAide);
    }

    @Override
    public Optional<DroitAide> partialUpdate(DroitAide droitAide) {
        log.debug("Request to partially update DroitAide : {}", droitAide);

        return droitAideRepository
            .findById(droitAide.getId())
            .map(existingDroitAide -> {
                if (droitAide.getIsActif() != null) {
                    existingDroitAide.setIsActif(droitAide.getIsActif());
                }
                if (droitAide.getAnne() != null) {
                    existingDroitAide.setAnne(droitAide.getAnne());
                }
                if (droitAide.getDateOuverture() != null) {
                    existingDroitAide.setDateOuverture(droitAide.getDateOuverture());
                }
                if (droitAide.getDateFermeture() != null) {
                    existingDroitAide.setDateFermeture(droitAide.getDateFermeture());
                }

                return existingDroitAide;
            })
            .map(droitAideRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroitAide> findAll() {
        log.debug("Request to get all DroitAides");
        return droitAideRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroitAide> findOne(Long id) {
        log.debug("Request to get DroitAide : {}", id);
        return droitAideRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroitAide : {}", id);
        droitAideRepository.deleteById(id);
    }
}
