package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.Beneficiaire;
import fr.tixou.bca.repository.BeneficiaireRepository;
import fr.tixou.bca.service.BeneficiaireService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Beneficiaire}.
 */
@Service
@Transactional
public class BeneficiaireServiceImpl implements BeneficiaireService {

    private final Logger log = LoggerFactory.getLogger(BeneficiaireServiceImpl.class);

    private final BeneficiaireRepository beneficiaireRepository;

    public BeneficiaireServiceImpl(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    @Override
    public Beneficiaire save(Beneficiaire beneficiaire) {
        log.debug("Request to save Beneficiaire : {}", beneficiaire);
        return beneficiaireRepository.save(beneficiaire);
    }

    @Override
    public Optional<Beneficiaire> partialUpdate(Beneficiaire beneficiaire) {
        log.debug("Request to partially update Beneficiaire : {}", beneficiaire);

        return beneficiaireRepository
            .findById(beneficiaire.getId())
            .map(existingBeneficiaire -> {
                if (beneficiaire.getExterneId() != null) {
                    existingBeneficiaire.setExterneId(beneficiaire.getExterneId());
                }
                if (beneficiaire.getIsActif() != null) {
                    existingBeneficiaire.setIsActif(beneficiaire.getIsActif());
                }
                if (beneficiaire.getDateDesactivation() != null) {
                    existingBeneficiaire.setDateDesactivation(beneficiaire.getDateDesactivation());
                }
                if (beneficiaire.getIsInscrit() != null) {
                    existingBeneficiaire.setIsInscrit(beneficiaire.getIsInscrit());
                }
                if (beneficiaire.getDateInscription() != null) {
                    existingBeneficiaire.setDateInscription(beneficiaire.getDateInscription());
                }
                if (beneficiaire.getDateResiliation() != null) {
                    existingBeneficiaire.setDateResiliation(beneficiaire.getDateResiliation());
                }

                return existingBeneficiaire;
            })
            .map(beneficiaireRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beneficiaire> findAll() {
        log.debug("Request to get all Beneficiaires");
        return beneficiaireRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Beneficiaire> findOne(Long id) {
        log.debug("Request to get Beneficiaire : {}", id);
        return beneficiaireRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Beneficiaire : {}", id);
        beneficiaireRepository.deleteById(id);
    }
}
