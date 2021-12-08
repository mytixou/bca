package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.SoldePch;
import fr.tixou.bca.repository.SoldePchRepository;
import fr.tixou.bca.service.SoldePchService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldePch}.
 */
@Service
@Transactional
public class SoldePchServiceImpl implements SoldePchService {

    private final Logger log = LoggerFactory.getLogger(SoldePchServiceImpl.class);

    private final SoldePchRepository soldePchRepository;

    public SoldePchServiceImpl(SoldePchRepository soldePchRepository) {
        this.soldePchRepository = soldePchRepository;
    }

    @Override
    public SoldePch save(SoldePch soldePch) {
        log.debug("Request to save SoldePch : {}", soldePch);
        return soldePchRepository.save(soldePch);
    }

    @Override
    public Optional<SoldePch> partialUpdate(SoldePch soldePch) {
        log.debug("Request to partially update SoldePch : {}", soldePch);

        return soldePchRepository
            .findById(soldePch.getId())
            .map(existingSoldePch -> {
                if (soldePch.getDate() != null) {
                    existingSoldePch.setDate(soldePch.getDate());
                }
                if (soldePch.getIsActif() != null) {
                    existingSoldePch.setIsActif(soldePch.getIsActif());
                }
                if (soldePch.getIsDernier() != null) {
                    existingSoldePch.setIsDernier(soldePch.getIsDernier());
                }
                if (soldePch.getAnnee() != null) {
                    existingSoldePch.setAnnee(soldePch.getAnnee());
                }
                if (soldePch.getMois() != null) {
                    existingSoldePch.setMois(soldePch.getMois());
                }
                if (soldePch.getConsoMontantPchCotisations() != null) {
                    existingSoldePch.setConsoMontantPchCotisations(soldePch.getConsoMontantPchCotisations());
                }
                if (soldePch.getConsoMontantPchSalaire() != null) {
                    existingSoldePch.setConsoMontantPchSalaire(soldePch.getConsoMontantPchSalaire());
                }
                if (soldePch.getSoldeMontantPch() != null) {
                    existingSoldePch.setSoldeMontantPch(soldePch.getSoldeMontantPch());
                }
                if (soldePch.getConsoHeurePch() != null) {
                    existingSoldePch.setConsoHeurePch(soldePch.getConsoHeurePch());
                }
                if (soldePch.getSoldeHeurePch() != null) {
                    existingSoldePch.setSoldeHeurePch(soldePch.getSoldeHeurePch());
                }

                return existingSoldePch;
            })
            .map(soldePchRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoldePch> findAll() {
        log.debug("Request to get all SoldePches");
        return soldePchRepository.findAll();
    }

    /**
     *  Get all the soldePches where Pec is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoldePch> findAllWherePecIsNull() {
        log.debug("Request to get all soldePches where Pec is null");
        return StreamSupport
            .stream(soldePchRepository.findAll().spliterator(), false)
            .filter(soldePch -> soldePch.getPec() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldePch> findOne(Long id) {
        log.debug("Request to get SoldePch : {}", id);
        return soldePchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldePch : {}", id);
        soldePchRepository.deleteById(id);
    }
}
