package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.SoldePchE;
import fr.tixou.bca.repository.SoldePchERepository;
import fr.tixou.bca.service.SoldePchEService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldePchE}.
 */
@Service
@Transactional
public class SoldePchEServiceImpl implements SoldePchEService {

    private final Logger log = LoggerFactory.getLogger(SoldePchEServiceImpl.class);

    private final SoldePchERepository soldePchERepository;

    public SoldePchEServiceImpl(SoldePchERepository soldePchERepository) {
        this.soldePchERepository = soldePchERepository;
    }

    @Override
    public SoldePchE save(SoldePchE soldePchE) {
        log.debug("Request to save SoldePchE : {}", soldePchE);
        return soldePchERepository.save(soldePchE);
    }

    @Override
    public Optional<SoldePchE> partialUpdate(SoldePchE soldePchE) {
        log.debug("Request to partially update SoldePchE : {}", soldePchE);

        return soldePchERepository
            .findById(soldePchE.getId())
            .map(existingSoldePchE -> {
                if (soldePchE.getDate() != null) {
                    existingSoldePchE.setDate(soldePchE.getDate());
                }
                if (soldePchE.getIsActif() != null) {
                    existingSoldePchE.setIsActif(soldePchE.getIsActif());
                }
                if (soldePchE.getIsDernier() != null) {
                    existingSoldePchE.setIsDernier(soldePchE.getIsDernier());
                }
                if (soldePchE.getAnnee() != null) {
                    existingSoldePchE.setAnnee(soldePchE.getAnnee());
                }
                if (soldePchE.getMois() != null) {
                    existingSoldePchE.setMois(soldePchE.getMois());
                }
                if (soldePchE.getConsoMontantPchECotisations() != null) {
                    existingSoldePchE.setConsoMontantPchECotisations(soldePchE.getConsoMontantPchECotisations());
                }
                if (soldePchE.getConsoMontantPchESalaire() != null) {
                    existingSoldePchE.setConsoMontantPchESalaire(soldePchE.getConsoMontantPchESalaire());
                }
                if (soldePchE.getSoldeMontantPchE() != null) {
                    existingSoldePchE.setSoldeMontantPchE(soldePchE.getSoldeMontantPchE());
                }
                if (soldePchE.getConsoHeurePchE() != null) {
                    existingSoldePchE.setConsoHeurePchE(soldePchE.getConsoHeurePchE());
                }
                if (soldePchE.getSoldeHeurePchE() != null) {
                    existingSoldePchE.setSoldeHeurePchE(soldePchE.getSoldeHeurePchE());
                }

                return existingSoldePchE;
            })
            .map(soldePchERepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoldePchE> findAll() {
        log.debug("Request to get all SoldePchES");
        return soldePchERepository.findAll();
    }

    /**
     *  Get all the soldePchES where Pec is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoldePchE> findAllWherePecIsNull() {
        log.debug("Request to get all soldePchES where Pec is null");
        return StreamSupport
            .stream(soldePchERepository.findAll().spliterator(), false)
            .filter(soldePchE -> soldePchE.getPec() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldePchE> findOne(Long id) {
        log.debug("Request to get SoldePchE : {}", id);
        return soldePchERepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldePchE : {}", id);
        soldePchERepository.deleteById(id);
    }
}
