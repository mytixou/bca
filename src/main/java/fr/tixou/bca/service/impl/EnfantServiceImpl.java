package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.Enfant;
import fr.tixou.bca.repository.EnfantRepository;
import fr.tixou.bca.service.EnfantService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Enfant}.
 */
@Service
@Transactional
public class EnfantServiceImpl implements EnfantService {

    private final Logger log = LoggerFactory.getLogger(EnfantServiceImpl.class);

    private final EnfantRepository enfantRepository;

    public EnfantServiceImpl(EnfantRepository enfantRepository) {
        this.enfantRepository = enfantRepository;
    }

    @Override
    public Enfant save(Enfant enfant) {
        log.debug("Request to save Enfant : {}", enfant);
        return enfantRepository.save(enfant);
    }

    @Override
    public Optional<Enfant> partialUpdate(Enfant enfant) {
        log.debug("Request to partially update Enfant : {}", enfant);

        return enfantRepository
            .findById(enfant.getId())
            .map(existingEnfant -> {
                if (enfant.getIsActif() != null) {
                    existingEnfant.setIsActif(enfant.getIsActif());
                }
                if (enfant.getDateNaissance() != null) {
                    existingEnfant.setDateNaissance(enfant.getDateNaissance());
                }
                if (enfant.getDateInscription() != null) {
                    existingEnfant.setDateInscription(enfant.getDateInscription());
                }
                if (enfant.getDateResiliation() != null) {
                    existingEnfant.setDateResiliation(enfant.getDateResiliation());
                }

                return existingEnfant;
            })
            .map(enfantRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enfant> findAll() {
        log.debug("Request to get all Enfants");
        return enfantRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enfant> findOne(Long id) {
        log.debug("Request to get Enfant : {}", id);
        return enfantRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enfant : {}", id);
        enfantRepository.deleteById(id);
    }
}
