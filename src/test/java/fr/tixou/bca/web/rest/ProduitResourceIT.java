package fr.tixou.bca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.Produit;
import fr.tixou.bca.domain.enumeration.TypeProduit;
import fr.tixou.bca.repository.ProduitRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProduitResourceIT {

    private static final TypeProduit DEFAULT_NOM = TypeProduit.CESU;
    private static final TypeProduit UPDATED_NOM = TypeProduit.CESUP;

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_LANCEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LANCEMENT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANNE_LANCEMENT = 1;
    private static final Integer UPDATED_ANNE_LANCEMENT = 2;

    private static final Integer DEFAULT_MOIS_LANCEMENT = 1;
    private static final Integer UPDATED_MOIS_LANCEMENT = 2;

    private static final LocalDate DEFAULT_DATE_RESILIATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RESILIATION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DERNIERE_ANNEE = 1;
    private static final Integer UPDATED_DERNIERE_ANNEE = 2;

    private static final Integer DEFAULT_DERNIER_MOIS = 1;
    private static final Integer UPDATED_DERNIER_MOIS = 2;

    private static final String ENTITY_API_URL = "/api/produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitMockMvc;

    private Produit produit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .nom(DEFAULT_NOM)
            .isActif(DEFAULT_IS_ACTIF)
            .dateLancement(DEFAULT_DATE_LANCEMENT)
            .anneLancement(DEFAULT_ANNE_LANCEMENT)
            .moisLancement(DEFAULT_MOIS_LANCEMENT)
            .dateResiliation(DEFAULT_DATE_RESILIATION)
            .derniereAnnee(DEFAULT_DERNIERE_ANNEE)
            .dernierMois(DEFAULT_DERNIER_MOIS);
        return produit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .nom(UPDATED_NOM)
            .isActif(UPDATED_IS_ACTIF)
            .dateLancement(UPDATED_DATE_LANCEMENT)
            .anneLancement(UPDATED_ANNE_LANCEMENT)
            .moisLancement(UPDATED_MOIS_LANCEMENT)
            .dateResiliation(UPDATED_DATE_RESILIATION)
            .derniereAnnee(UPDATED_DERNIERE_ANNEE)
            .dernierMois(UPDATED_DERNIER_MOIS);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();
        // Create the Produit
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProduit.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testProduit.getDateLancement()).isEqualTo(DEFAULT_DATE_LANCEMENT);
        assertThat(testProduit.getAnneLancement()).isEqualTo(DEFAULT_ANNE_LANCEMENT);
        assertThat(testProduit.getMoisLancement()).isEqualTo(DEFAULT_MOIS_LANCEMENT);
        assertThat(testProduit.getDateResiliation()).isEqualTo(DEFAULT_DATE_RESILIATION);
        assertThat(testProduit.getDerniereAnnee()).isEqualTo(DEFAULT_DERNIERE_ANNEE);
        assertThat(testProduit.getDernierMois()).isEqualTo(DEFAULT_DERNIER_MOIS);
    }

    @Test
    @Transactional
    void createProduitWithExistingId() throws Exception {
        // Create the Produit with an existing ID
        produit.setId(1L);

        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setNom(null);

        // Create the Produit, which fails.

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setIsActif(null);

        // Create the Produit, which fails.

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateLancementIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setDateLancement(null);

        // Create the Produit, which fails.

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneLancementIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setAnneLancement(null);

        // Create the Produit, which fails.

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisLancementIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setMoisLancement(null);

        // Create the Produit, which fails.

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateLancement").value(hasItem(DEFAULT_DATE_LANCEMENT.toString())))
            .andExpect(jsonPath("$.[*].anneLancement").value(hasItem(DEFAULT_ANNE_LANCEMENT)))
            .andExpect(jsonPath("$.[*].moisLancement").value(hasItem(DEFAULT_MOIS_LANCEMENT)))
            .andExpect(jsonPath("$.[*].dateResiliation").value(hasItem(DEFAULT_DATE_RESILIATION.toString())))
            .andExpect(jsonPath("$.[*].derniereAnnee").value(hasItem(DEFAULT_DERNIERE_ANNEE)))
            .andExpect(jsonPath("$.[*].dernierMois").value(hasItem(DEFAULT_DERNIER_MOIS)));
    }

    @Test
    @Transactional
    void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc
            .perform(get(ENTITY_API_URL_ID, produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateLancement").value(DEFAULT_DATE_LANCEMENT.toString()))
            .andExpect(jsonPath("$.anneLancement").value(DEFAULT_ANNE_LANCEMENT))
            .andExpect(jsonPath("$.moisLancement").value(DEFAULT_MOIS_LANCEMENT))
            .andExpect(jsonPath("$.dateResiliation").value(DEFAULT_DATE_RESILIATION.toString()))
            .andExpect(jsonPath("$.derniereAnnee").value(DEFAULT_DERNIERE_ANNEE))
            .andExpect(jsonPath("$.dernierMois").value(DEFAULT_DERNIER_MOIS));
    }

    @Test
    @Transactional
    void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .nom(UPDATED_NOM)
            .isActif(UPDATED_IS_ACTIF)
            .dateLancement(UPDATED_DATE_LANCEMENT)
            .anneLancement(UPDATED_ANNE_LANCEMENT)
            .moisLancement(UPDATED_MOIS_LANCEMENT)
            .dateResiliation(UPDATED_DATE_RESILIATION)
            .derniereAnnee(UPDATED_DERNIERE_ANNEE)
            .dernierMois(UPDATED_DERNIER_MOIS);

        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProduit.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testProduit.getDateLancement()).isEqualTo(UPDATED_DATE_LANCEMENT);
        assertThat(testProduit.getAnneLancement()).isEqualTo(UPDATED_ANNE_LANCEMENT);
        assertThat(testProduit.getMoisLancement()).isEqualTo(UPDATED_MOIS_LANCEMENT);
        assertThat(testProduit.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
        assertThat(testProduit.getDerniereAnnee()).isEqualTo(UPDATED_DERNIERE_ANNEE);
        assertThat(testProduit.getDernierMois()).isEqualTo(UPDATED_DERNIER_MOIS);
    }

    @Test
    @Transactional
    void putNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setId(produit.getId());

        partialUpdatedProduit
            .dateLancement(UPDATED_DATE_LANCEMENT)
            .anneLancement(UPDATED_ANNE_LANCEMENT)
            .moisLancement(UPDATED_MOIS_LANCEMENT);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProduit.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testProduit.getDateLancement()).isEqualTo(UPDATED_DATE_LANCEMENT);
        assertThat(testProduit.getAnneLancement()).isEqualTo(UPDATED_ANNE_LANCEMENT);
        assertThat(testProduit.getMoisLancement()).isEqualTo(UPDATED_MOIS_LANCEMENT);
        assertThat(testProduit.getDateResiliation()).isEqualTo(DEFAULT_DATE_RESILIATION);
        assertThat(testProduit.getDerniereAnnee()).isEqualTo(DEFAULT_DERNIERE_ANNEE);
        assertThat(testProduit.getDernierMois()).isEqualTo(DEFAULT_DERNIER_MOIS);
    }

    @Test
    @Transactional
    void fullUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setId(produit.getId());

        partialUpdatedProduit
            .nom(UPDATED_NOM)
            .isActif(UPDATED_IS_ACTIF)
            .dateLancement(UPDATED_DATE_LANCEMENT)
            .anneLancement(UPDATED_ANNE_LANCEMENT)
            .moisLancement(UPDATED_MOIS_LANCEMENT)
            .dateResiliation(UPDATED_DATE_RESILIATION)
            .derniereAnnee(UPDATED_DERNIERE_ANNEE)
            .dernierMois(UPDATED_DERNIER_MOIS);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProduit.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testProduit.getDateLancement()).isEqualTo(UPDATED_DATE_LANCEMENT);
        assertThat(testProduit.getAnneLancement()).isEqualTo(UPDATED_ANNE_LANCEMENT);
        assertThat(testProduit.getMoisLancement()).isEqualTo(UPDATED_MOIS_LANCEMENT);
        assertThat(testProduit.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
        assertThat(testProduit.getDerniereAnnee()).isEqualTo(UPDATED_DERNIERE_ANNEE);
        assertThat(testProduit.getDernierMois()).isEqualTo(UPDATED_DERNIER_MOIS);
    }

    @Test
    @Transactional
    void patchNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc
            .perform(delete(ENTITY_API_URL_ID, produit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
