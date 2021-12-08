package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.SoldeCi;
import fr.tixou.bca.repository.SoldeCiRepository;
import fr.tixou.bca.service.SoldeCiService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldeCi}.
 */
@Service
@Transactional
public class SoldeCiServiceImpl implements SoldeCiService {

    private final Logger log = LoggerFactory.getLogger(SoldeCiServiceImpl.class);

    private final SoldeCiRepository soldeCiRepository;

    public SoldeCiServiceImpl(SoldeCiRepository soldeCiRepository) {
        this.soldeCiRepository = soldeCiRepository;
    }

    @Override
    public SoldeCi save(SoldeCi soldeCi) {
        log.debug("Request to save SoldeCi : {}", soldeCi);
        return soldeCiRepository.save(soldeCi);
    }

    @Override
    public Optional<SoldeCi> partialUpdate(SoldeCi soldeCi) {
        log.debug("Request to partially update SoldeCi : {}", soldeCi);

        return soldeCiRepository
            .findById(soldeCi.getId())
            .map(existingSoldeCi -> {
                if (soldeCi.getDate() != null) {
                    existingSoldeCi.setDate(soldeCi.getDate());
                }
                if (soldeCi.getIsActif() != null) {
                    existingSoldeCi.setIsActif(soldeCi.getIsActif());
                }
                if (soldeCi.getIsDernier() != null) {
                    existingSoldeCi.setIsDernier(soldeCi.getIsDernier());
                }
                if (soldeCi.getAnnee() != null) {
                    existingSoldeCi.setAnnee(soldeCi.getAnnee());
                }
                if (soldeCi.getConsoMontantCi() != null) {
                    existingSoldeCi.setConsoMontantCi(soldeCi.getConsoMontantCi());
                }
                if (soldeCi.getConsoCiRec() != null) {
                    existingSoldeCi.setConsoCiRec(soldeCi.getConsoCiRec());
                }
                if (soldeCi.getSoldeMontantCi() != null) {
                    existingSoldeCi.setSoldeMontantCi(soldeCi.getSoldeMontantCi());
                }
                if (soldeCi.getSoldeMontantCiRec() != null) {
                    existingSoldeCi.setSoldeMontantCiRec(soldeCi.getSoldeMontantCiRec());
                }

                return existingSoldeCi;
            })
            .map(soldeCiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoldeCi> findAll() {
        log.debug("Request to get all SoldeCis");
        return soldeCiRepository.findAll();
    }

    /**
     *  Get all the soldeCis where Pec is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SoldeCi> findAllWherePecIsNull() {
        log.debug("Request to get all soldeCis where Pec is null");
        return StreamSupport
            .stream(soldeCiRepository.findAll().spliterator(), false)
            .filter(soldeCi -> soldeCi.getPec() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldeCi> findOne(Long id) {
        log.debug("Request to get SoldeCi : {}", id);
        return soldeCiRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldeCi : {}", id);
        soldeCiRepository.deleteById(id);
    }
}
