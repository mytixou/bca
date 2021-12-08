package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.SoldePchE;
import fr.tixou.bca.repository.SoldePchERepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link SoldePchEResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoldePchEResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Boolean DEFAULT_IS_DERNIER = false;
    private static final Boolean UPDATED_IS_DERNIER = true;

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final BigDecimal DEFAULT_CONSO_MONTANT_PCH_E_COTISATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSO_MONTANT_PCH_E_COTISATIONS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONSO_MONTANT_PCH_E_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSO_MONTANT_PCH_E_SALAIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLDE_MONTANT_PCH_E = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLDE_MONTANT_PCH_E = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONSO_HEURE_PCH_E = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSO_HEURE_PCH_E = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLDE_HEURE_PCH_E = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLDE_HEURE_PCH_E = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/solde-pch-es";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoldePchERepository soldePchERepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoldePchEMockMvc;

    private SoldePchE soldePchE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldePchE createEntity(EntityManager em) {
        SoldePchE soldePchE = new SoldePchE()
            .date(DEFAULT_DATE)
            .isActif(DEFAULT_IS_ACTIF)
            .isDernier(DEFAULT_IS_DERNIER)
            .annee(DEFAULT_ANNEE)
            .mois(DEFAULT_MOIS)
            .consoMontantPchECotisations(DEFAULT_CONSO_MONTANT_PCH_E_COTISATIONS)
            .consoMontantPchESalaire(DEFAULT_CONSO_MONTANT_PCH_E_SALAIRE)
            .soldeMontantPchE(DEFAULT_SOLDE_MONTANT_PCH_E)
            .consoHeurePchE(DEFAULT_CONSO_HEURE_PCH_E)
            .soldeHeurePchE(DEFAULT_SOLDE_HEURE_PCH_E);
        return soldePchE;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldePchE createUpdatedEntity(EntityManager em) {
        SoldePchE soldePchE = new SoldePchE()
            .date(UPDATED_DATE)
            .isActif(UPDATED_IS_ACTIF)
            .isDernier(UPDATED_IS_DERNIER)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchECotisations(UPDATED_CONSO_MONTANT_PCH_E_COTISATIONS)
            .consoMontantPchESalaire(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE)
            .soldeMontantPchE(UPDATED_SOLDE_MONTANT_PCH_E)
            .consoHeurePchE(UPDATED_CONSO_HEURE_PCH_E)
            .soldeHeurePchE(UPDATED_SOLDE_HEURE_PCH_E);
        return soldePchE;
    }

    @BeforeEach
    public void initTest() {
        soldePchE = createEntity(em);
    }

    @Test
    @Transactional
    void createSoldePchE() throws Exception {
        int databaseSizeBeforeCreate = soldePchERepository.findAll().size();
        // Create the SoldePchE
        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isCreated());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeCreate + 1);
        SoldePchE testSoldePchE = soldePchEList.get(soldePchEList.size() - 1);
        assertThat(testSoldePchE.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSoldePchE.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testSoldePchE.getIsDernier()).isEqualTo(DEFAULT_IS_DERNIER);
        assertThat(testSoldePchE.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldePchE.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testSoldePchE.getConsoMontantPchECotisations()).isEqualByComparingTo(DEFAULT_CONSO_MONTANT_PCH_E_COTISATIONS);
        assertThat(testSoldePchE.getConsoMontantPchESalaire()).isEqualByComparingTo(DEFAULT_CONSO_MONTANT_PCH_E_SALAIRE);
        assertThat(testSoldePchE.getSoldeMontantPchE()).isEqualByComparingTo(DEFAULT_SOLDE_MONTANT_PCH_E);
        assertThat(testSoldePchE.getConsoHeurePchE()).isEqualByComparingTo(DEFAULT_CONSO_HEURE_PCH_E);
        assertThat(testSoldePchE.getSoldeHeurePchE()).isEqualByComparingTo(DEFAULT_SOLDE_HEURE_PCH_E);
    }

    @Test
    @Transactional
    void createSoldePchEWithExistingId() throws Exception {
        // Create the SoldePchE with an existing ID
        soldePchE.setId(1L);

        int databaseSizeBeforeCreate = soldePchERepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setDate(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setIsActif(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDernierIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setIsDernier(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setAnnee(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setMois(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsoMontantPchECotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setConsoMontantPchECotisations(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsoMontantPchESalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setConsoMontantPchESalaire(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoldeMontantPchEIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setSoldeMontantPchE(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsoHeurePchEIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setConsoHeurePchE(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoldeHeurePchEIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchERepository.findAll().size();
        // set the field null
        soldePchE.setSoldeHeurePchE(null);

        // Create the SoldePchE, which fails.

        restSoldePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isBadRequest());

        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSoldePchES() throws Exception {
        // Initialize the database
        soldePchERepository.saveAndFlush(soldePchE);

        // Get all the soldePchEList
        restSoldePchEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soldePchE.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].isDernier").value(hasItem(DEFAULT_IS_DERNIER.booleanValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].consoMontantPchECotisations").value(hasItem(sameNumber(DEFAULT_CONSO_MONTANT_PCH_E_COTISATIONS))))
            .andExpect(jsonPath("$.[*].consoMontantPchESalaire").value(hasItem(sameNumber(DEFAULT_CONSO_MONTANT_PCH_E_SALAIRE))))
            .andExpect(jsonPath("$.[*].soldeMontantPchE").value(hasItem(sameNumber(DEFAULT_SOLDE_MONTANT_PCH_E))))
            .andExpect(jsonPath("$.[*].consoHeurePchE").value(hasItem(sameNumber(DEFAULT_CONSO_HEURE_PCH_E))))
            .andExpect(jsonPath("$.[*].soldeHeurePchE").value(hasItem(sameNumber(DEFAULT_SOLDE_HEURE_PCH_E))));
    }

    @Test
    @Transactional
    void getSoldePchE() throws Exception {
        // Initialize the database
        soldePchERepository.saveAndFlush(soldePchE);

        // Get the soldePchE
        restSoldePchEMockMvc
            .perform(get(ENTITY_API_URL_ID, soldePchE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soldePchE.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.isDernier").value(DEFAULT_IS_DERNIER.booleanValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.consoMontantPchECotisations").value(sameNumber(DEFAULT_CONSO_MONTANT_PCH_E_COTISATIONS)))
            .andExpect(jsonPath("$.consoMontantPchESalaire").value(sameNumber(DEFAULT_CONSO_MONTANT_PCH_E_SALAIRE)))
            .andExpect(jsonPath("$.soldeMontantPchE").value(sameNumber(DEFAULT_SOLDE_MONTANT_PCH_E)))
            .andExpect(jsonPath("$.consoHeurePchE").value(sameNumber(DEFAULT_CONSO_HEURE_PCH_E)))
            .andExpect(jsonPath("$.soldeHeurePchE").value(sameNumber(DEFAULT_SOLDE_HEURE_PCH_E)));
    }

    @Test
    @Transactional
    void getNonExistingSoldePchE() throws Exception {
        // Get the soldePchE
        restSoldePchEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSoldePchE() throws Exception {
        // Initialize the database
        soldePchERepository.saveAndFlush(soldePchE);

        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();

        // Update the soldePchE
        SoldePchE updatedSoldePchE = soldePchERepository.findById(soldePchE.getId()).get();
        // Disconnect from session so that the updates on updatedSoldePchE are not directly saved in db
        em.detach(updatedSoldePchE);
        updatedSoldePchE
            .date(UPDATED_DATE)
            .isActif(UPDATED_IS_ACTIF)
            .isDernier(UPDATED_IS_DERNIER)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchECotisations(UPDATED_CONSO_MONTANT_PCH_E_COTISATIONS)
            .consoMontantPchESalaire(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE)
            .soldeMontantPchE(UPDATED_SOLDE_MONTANT_PCH_E)
            .consoHeurePchE(UPDATED_CONSO_HEURE_PCH_E)
            .soldeHeurePchE(UPDATED_SOLDE_HEURE_PCH_E);

        restSoldePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoldePchE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSoldePchE))
            )
            .andExpect(status().isOk());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
        SoldePchE testSoldePchE = soldePchEList.get(soldePchEList.size() - 1);
        assertThat(testSoldePchE.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSoldePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testSoldePchE.getIsDernier()).isEqualTo(UPDATED_IS_DERNIER);
        assertThat(testSoldePchE.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldePchE.getConsoMontantPchECotisations()).isEqualTo(UPDATED_CONSO_MONTANT_PCH_E_COTISATIONS);
        assertThat(testSoldePchE.getConsoMontantPchESalaire()).isEqualTo(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE);
        assertThat(testSoldePchE.getSoldeMontantPchE()).isEqualTo(UPDATED_SOLDE_MONTANT_PCH_E);
        assertThat(testSoldePchE.getConsoHeurePchE()).isEqualTo(UPDATED_CONSO_HEURE_PCH_E);
        assertThat(testSoldePchE.getSoldeHeurePchE()).isEqualTo(UPDATED_SOLDE_HEURE_PCH_E);
    }

    @Test
    @Transactional
    void putNonExistingSoldePchE() throws Exception {
        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();
        soldePchE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soldePchE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoldePchE() throws Exception {
        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();
        soldePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoldePchE() throws Exception {
        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();
        soldePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchEMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePchE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoldePchEWithPatch() throws Exception {
        // Initialize the database
        soldePchERepository.saveAndFlush(soldePchE);

        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();

        // Update the soldePchE using partial update
        SoldePchE partialUpdatedSoldePchE = new SoldePchE();
        partialUpdatedSoldePchE.setId(soldePchE.getId());

        partialUpdatedSoldePchE
            .date(UPDATED_DATE)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchESalaire(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE);

        restSoldePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldePchE))
            )
            .andExpect(status().isOk());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
        SoldePchE testSoldePchE = soldePchEList.get(soldePchEList.size() - 1);
        assertThat(testSoldePchE.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSoldePchE.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testSoldePchE.getIsDernier()).isEqualTo(DEFAULT_IS_DERNIER);
        assertThat(testSoldePchE.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldePchE.getConsoMontantPchECotisations()).isEqualByComparingTo(DEFAULT_CONSO_MONTANT_PCH_E_COTISATIONS);
        assertThat(testSoldePchE.getConsoMontantPchESalaire()).isEqualByComparingTo(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE);
        assertThat(testSoldePchE.getSoldeMontantPchE()).isEqualByComparingTo(DEFAULT_SOLDE_MONTANT_PCH_E);
        assertThat(testSoldePchE.getConsoHeurePchE()).isEqualByComparingTo(DEFAULT_CONSO_HEURE_PCH_E);
        assertThat(testSoldePchE.getSoldeHeurePchE()).isEqualByComparingTo(DEFAULT_SOLDE_HEURE_PCH_E);
    }

    @Test
    @Transactional
    void fullUpdateSoldePchEWithPatch() throws Exception {
        // Initialize the database
        soldePchERepository.saveAndFlush(soldePchE);

        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();

        // Update the soldePchE using partial update
        SoldePchE partialUpdatedSoldePchE = new SoldePchE();
        partialUpdatedSoldePchE.setId(soldePchE.getId());

        partialUpdatedSoldePchE
            .date(UPDATED_DATE)
            .isActif(UPDATED_IS_ACTIF)
            .isDernier(UPDATED_IS_DERNIER)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchECotisations(UPDATED_CONSO_MONTANT_PCH_E_COTISATIONS)
            .consoMontantPchESalaire(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE)
            .soldeMontantPchE(UPDATED_SOLDE_MONTANT_PCH_E)
            .consoHeurePchE(UPDATED_CONSO_HEURE_PCH_E)
            .soldeHeurePchE(UPDATED_SOLDE_HEURE_PCH_E);

        restSoldePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldePchE))
            )
            .andExpect(status().isOk());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
        SoldePchE testSoldePchE = soldePchEList.get(soldePchEList.size() - 1);
        assertThat(testSoldePchE.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSoldePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testSoldePchE.getIsDernier()).isEqualTo(UPDATED_IS_DERNIER);
        assertThat(testSoldePchE.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldePchE.getConsoMontantPchECotisations()).isEqualByComparingTo(UPDATED_CONSO_MONTANT_PCH_E_COTISATIONS);
        assertThat(testSoldePchE.getConsoMontantPchESalaire()).isEqualByComparingTo(UPDATED_CONSO_MONTANT_PCH_E_SALAIRE);
        assertThat(testSoldePchE.getSoldeMontantPchE()).isEqualByComparingTo(UPDATED_SOLDE_MONTANT_PCH_E);
        assertThat(testSoldePchE.getConsoHeurePchE()).isEqualByComparingTo(UPDATED_CONSO_HEURE_PCH_E);
        assertThat(testSoldePchE.getSoldeHeurePchE()).isEqualByComparingTo(UPDATED_SOLDE_HEURE_PCH_E);
    }

    @Test
    @Transactional
    void patchNonExistingSoldePchE() throws Exception {
        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();
        soldePchE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soldePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoldePchE() throws Exception {
        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();
        soldePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoldePchE() throws Exception {
        int databaseSizeBeforeUpdate = soldePchERepository.findAll().size();
        soldePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchEMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(soldePchE))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldePchE in the database
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoldePchE() throws Exception {
        // Initialize the database
        soldePchERepository.saveAndFlush(soldePchE);

        int databaseSizeBeforeDelete = soldePchERepository.findAll().size();

        // Delete the soldePchE
        restSoldePchEMockMvc
            .perform(delete(ENTITY_API_URL_ID, soldePchE.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoldePchE> soldePchEList = soldePchERepository.findAll();
        assertThat(soldePchEList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
