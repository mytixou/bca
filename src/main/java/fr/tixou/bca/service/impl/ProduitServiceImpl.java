package fr.tixou.bca.service.impl;

import fr.tixou.bca.domain.Produit;
import fr.tixou.bca.repository.ProduitRepository;
import fr.tixou.bca.service.ProduitService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Produit}.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit save(Produit produit) {
        log.debug("Request to save Produit : {}", produit);
        return produitRepository.save(produit);
    }

    @Override
    public Optional<Produit> partialUpdate(Produit produit) {
        log.debug("Request to partially update Produit : {}", produit);

        return produitRepository
            .findById(produit.getId())
            .map(existingProduit -> {
                if (produit.getNom() != null) {
                    existingProduit.setNom(produit.getNom());
                }
                if (produit.getIsActif() != null) {
                    existingProduit.setIsActif(produit.getIsActif());
                }
                if (produit.getDateLancement() != null) {
                    existingProduit.setDateLancement(produit.getDateLancement());
                }
                if (produit.getAnneLancement() != null) {
                    existingProduit.setAnneLancement(produit.getAnneLancement());
                }
                if (produit.getMoisLancement() != null) {
                    existingProduit.setMoisLancement(produit.getMoisLancement());
                }
                if (produit.getDateResiliation() != null) {
                    existingProduit.setDateResiliation(produit.getDateResiliation());
                }
                if (produit.getDerniereAnnee() != null) {
                    existingProduit.setDerniereAnnee(produit.getDerniereAnnee());
                }
                if (produit.getDernierMois() != null) {
                    existingProduit.setDernierMois(produit.getDernierMois());
                }

                return existingProduit;
            })
            .map(produitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produit> findAll() {
        log.debug("Request to get all Produits");
        return produitRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Produit> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }
}
