package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.SoldeApa;
import fr.tixou.bca.repository.SoldeApaRepository;
import fr.tixou.bca.service.SoldeApaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldeApa}.
 */
@Service
@Transactional
public class SoldeApaServiceImpl implements SoldeApaService {

    private final Logger log = LoggerFactory.getLogger(SoldeApaServiceImpl.class);

    private final SoldeApaRepository soldeApaRepository;

    public SoldeApaServiceImpl(SoldeApaRepository soldeApaRepository) {
        this.soldeApaRepository = soldeApaRepository;
    }

    @Override
    public SoldeApa save(SoldeApa soldeApa) {
        log.debug("Request to save SoldeApa : {}", soldeApa);
        return soldeApaRepository.save(soldeApa);
    }

    @Override
    public Optional<SoldeApa> partialUpdate(SoldeApa soldeApa) {
        log.debug("Request to partially update SoldeApa : {}", soldeApa);

        return soldeApaRepository
            .findById(soldeApa.getId())
            .map(existingSoldeApa -> {
                if (soldeApa.getDate() != null) {
                    existingSoldeApa.setDate(soldeApa.getDate());
                }
                if (soldeApa.getIsActif() != null) {
                    existingSoldeApa.setIsActif(soldeApa.getIsActif());
                }
                if (soldeApa.getIsDernier() != null) {
                    existingSoldeApa.setIsDernier(soldeApa.getIsDernier());
                }
                if (soldeApa.getAnnee() != null) {
                    existingSoldeApa.setAnnee(soldeApa.getAnnee());
                }
                if (soldeApa.getMois() != null) {
                    existingSoldeApa.setMois(soldeApa.getMois());
                }
                if (soldeApa.getConsoMontantApaCotisations() != null) {
                    existingSoldeApa.setConsoMontantApaCotisations(soldeApa.getConsoMontantApaCotisations());
                }
                if (soldeApa.getConsoMontantApaSalaire() != null) {
                    existingSoldeApa.setConsoMontantApaSalaire(soldeApa.getConsoMontantApaSalaire());
                }
                if (soldeApa.getSoldeMontantApa() != null) {
                    existingSoldeApa.setSoldeMontantApa(soldeApa.getSoldeMontantApa());
                }
                if (soldeApa.getConsoHeureApa() != null) {
                    existingSoldeApa.setConsoHeureApa(soldeApa.getConsoHeureApa());
                }
                if (soldeApa.getSoldeHeureApa() != null) {
                    existingSoldeApa.setSoldeHeureApa(soldeApa.getSoldeHeureApa());
                }

                return existingSoldeApa;
            })
            .map(soldeApaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoldeApa> findAll() {
        log.debug("Request to get all SoldeApas");
        return soldeApaRepository.findAll();
    }

    /**
     *  Get all the soldeApas where Pec is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoldeApa> findAllWherePecIsNull() {
        log.debug("Request to get all soldeApas where Pec is null");
        return StreamSupport
            .stream(soldeApaRepository.findAll().spliterator(), false)
            .filter(soldeApa -> soldeApa.getPec() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldeApa> findOne(Long id) {
        log.debug("Request to get SoldeApa : {}", id);
        return soldeApaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldeApa : {}", id);
        soldeApaRepository.deleteById(id);
    }
}
