package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.DroitsStrategiePchE;
import fr.tixou.bca.repository.DroitsStrategiePchERepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DroitsStrategiePchEResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DroitsStrategiePchEResourceIT {

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NB_HEURE_PLAFOND = new BigDecimal(1);
    private static final BigDecimal UPDATED_NB_HEURE_PLAFOND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_COTISATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_COTISATIONS = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FERMETURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FERMETURE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/droits-strategie-pch-es";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DroitsStrategiePchERepository droitsStrategiePchERepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroitsStrategiePchEMockMvc;

    private DroitsStrategiePchE droitsStrategiePchE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategiePchE createEntity(EntityManager em) {
        DroitsStrategiePchE droitsStrategiePchE = new DroitsStrategiePchE()
            .isActif(DEFAULT_IS_ACTIF)
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .montantPlafond(DEFAULT_MONTANT_PLAFOND)
            .montantPlafondPlus(DEFAULT_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(DEFAULT_NB_HEURE_PLAFOND)
            .tauxCotisations(DEFAULT_TAUX_COTISATIONS)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE);
        return droitsStrategiePchE;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategiePchE createUpdatedEntity(EntityManager em) {
        DroitsStrategiePchE droitsStrategiePchE = new DroitsStrategiePchE()
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);
        return droitsStrategiePchE;
    }

    @BeforeEach
    public void initTest() {
        droitsStrategiePchE = createEntity(em);
    }

    @Test
    @Transactional
    void createDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeCreate = droitsStrategiePchERepository.findAll().size();
        // Create the DroitsStrategiePchE
        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isCreated());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeCreate + 1);
        DroitsStrategiePchE testDroitsStrategiePchE = droitsStrategiePchEList.get(droitsStrategiePchEList.size() - 1);
        assertThat(testDroitsStrategiePchE.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testDroitsStrategiePchE.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testDroitsStrategiePchE.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testDroitsStrategiePchE.getMontantPlafond()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePchE.getMontantPlafondPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePchE.getNbHeurePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePchE.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePchE.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePchE.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void createDroitsStrategiePchEWithExistingId() throws Exception {
        // Create the DroitsStrategiePchE with an existing ID
        droitsStrategiePchE.setId(1L);

        int databaseSizeBeforeCreate = droitsStrategiePchERepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setIsActif(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setAnne(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setMois(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setMontantPlafond(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setMontantPlafondPlus(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeurePlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setNbHeurePlafond(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setTauxCotisations(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOuvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchERepository.findAll().size();
        // set the field null
        droitsStrategiePchE.setDateOuverture(null);

        // Create the DroitsStrategiePchE, which fails.

        restDroitsStrategiePchEMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDroitsStrategiePchES() throws Exception {
        // Initialize the database
        droitsStrategiePchERepository.saveAndFlush(droitsStrategiePchE);

        // Get all the droitsStrategiePchEList
        restDroitsStrategiePchEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitsStrategiePchE.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].montantPlafond").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND))))
            .andExpect(jsonPath("$.[*].montantPlafondPlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_PLUS))))
            .andExpect(jsonPath("$.[*].nbHeurePlafond").value(hasItem(sameNumber(DEFAULT_NB_HEURE_PLAFOND))))
            .andExpect(jsonPath("$.[*].tauxCotisations").value(hasItem(sameNumber(DEFAULT_TAUX_COTISATIONS))))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())));
    }

    @Test
    @Transactional
    void getDroitsStrategiePchE() throws Exception {
        // Initialize the database
        droitsStrategiePchERepository.saveAndFlush(droitsStrategiePchE);

        // Get the droitsStrategiePchE
        restDroitsStrategiePchEMockMvc
            .perform(get(ENTITY_API_URL_ID, droitsStrategiePchE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(droitsStrategiePchE.getId().intValue()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.montantPlafond").value(sameNumber(DEFAULT_MONTANT_PLAFOND)))
            .andExpect(jsonPath("$.montantPlafondPlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_PLUS)))
            .andExpect(jsonPath("$.nbHeurePlafond").value(sameNumber(DEFAULT_NB_HEURE_PLAFOND)))
            .andExpect(jsonPath("$.tauxCotisations").value(sameNumber(DEFAULT_TAUX_COTISATIONS)))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateFermeture").value(DEFAULT_DATE_FERMETURE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDroitsStrategiePchE() throws Exception {
        // Get the droitsStrategiePchE
        restDroitsStrategiePchEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDroitsStrategiePchE() throws Exception {
        // Initialize the database
        droitsStrategiePchERepository.saveAndFlush(droitsStrategiePchE);

        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();

        // Update the droitsStrategiePchE
        DroitsStrategiePchE updatedDroitsStrategiePchE = droitsStrategiePchERepository.findById(droitsStrategiePchE.getId()).get();
        // Disconnect from session so that the updates on updatedDroitsStrategiePchE are not directly saved in db
        em.detach(updatedDroitsStrategiePchE);
        updatedDroitsStrategiePchE
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDroitsStrategiePchE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDroitsStrategiePchE))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategiePchE testDroitsStrategiePchE = droitsStrategiePchEList.get(droitsStrategiePchEList.size() - 1);
        assertThat(testDroitsStrategiePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategiePchE.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategiePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategiePchE.getMontantPlafond()).isEqualTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePchE.getMontantPlafondPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePchE.getNbHeurePlafond()).isEqualTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePchE.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePchE.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePchE.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void putNonExistingDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();
        droitsStrategiePchE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droitsStrategiePchE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();
        droitsStrategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();
        droitsStrategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDroitsStrategiePchEWithPatch() throws Exception {
        // Initialize the database
        droitsStrategiePchERepository.saveAndFlush(droitsStrategiePchE);

        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();

        // Update the droitsStrategiePchE using partial update
        DroitsStrategiePchE partialUpdatedDroitsStrategiePchE = new DroitsStrategiePchE();
        partialUpdatedDroitsStrategiePchE.setId(droitsStrategiePchE.getId());

        partialUpdatedDroitsStrategiePchE
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE);

        restDroitsStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategiePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategiePchE))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategiePchE testDroitsStrategiePchE = droitsStrategiePchEList.get(droitsStrategiePchEList.size() - 1);
        assertThat(testDroitsStrategiePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategiePchE.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategiePchE.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testDroitsStrategiePchE.getMontantPlafond()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePchE.getMontantPlafondPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePchE.getNbHeurePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePchE.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePchE.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePchE.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void fullUpdateDroitsStrategiePchEWithPatch() throws Exception {
        // Initialize the database
        droitsStrategiePchERepository.saveAndFlush(droitsStrategiePchE);

        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();

        // Update the droitsStrategiePchE using partial update
        DroitsStrategiePchE partialUpdatedDroitsStrategiePchE = new DroitsStrategiePchE();
        partialUpdatedDroitsStrategiePchE.setId(droitsStrategiePchE.getId());

        partialUpdatedDroitsStrategiePchE
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategiePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategiePchE))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategiePchE testDroitsStrategiePchE = droitsStrategiePchEList.get(droitsStrategiePchEList.size() - 1);
        assertThat(testDroitsStrategiePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategiePchE.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategiePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategiePchE.getMontantPlafond()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePchE.getMontantPlafondPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePchE.getNbHeurePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePchE.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePchE.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePchE.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void patchNonExistingDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();
        droitsStrategiePchE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, droitsStrategiePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();
        droitsStrategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDroitsStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchERepository.findAll().size();
        droitsStrategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePchE))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategiePchE in the database
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDroitsStrategiePchE() throws Exception {
        // Initialize the database
        droitsStrategiePchERepository.saveAndFlush(droitsStrategiePchE);

        int databaseSizeBeforeDelete = droitsStrategiePchERepository.findAll().size();

        // Delete the droitsStrategiePchE
        restDroitsStrategiePchEMockMvc
            .perform(delete(ENTITY_API_URL_ID, droitsStrategiePchE.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DroitsStrategiePchE> droitsStrategiePchEList = droitsStrategiePchERepository.findAll();
        assertThat(droitsStrategiePchEList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
