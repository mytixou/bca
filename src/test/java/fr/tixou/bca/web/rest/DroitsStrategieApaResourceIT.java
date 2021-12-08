package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.DroitsStrategieApa;
import fr.tixou.bca.repository.DroitsStrategieApaRepository;
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
 * Integration tests for the {@link DroitsStrategieApaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DroitsStrategieApaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/droits-strategie-apas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DroitsStrategieApaRepository droitsStrategieApaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroitsStrategieApaMockMvc;

    private DroitsStrategieApa droitsStrategieApa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategieApa createEntity(EntityManager em) {
        DroitsStrategieApa droitsStrategieApa = new DroitsStrategieApa()
            .isActif(DEFAULT_IS_ACTIF)
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .montantPlafond(DEFAULT_MONTANT_PLAFOND)
            .montantPlafondPlus(DEFAULT_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(DEFAULT_NB_HEURE_PLAFOND)
            .tauxCotisations(DEFAULT_TAUX_COTISATIONS)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE);
        return droitsStrategieApa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategieApa createUpdatedEntity(EntityManager em) {
        DroitsStrategieApa droitsStrategieApa = new DroitsStrategieApa()
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);
        return droitsStrategieApa;
    }

    @BeforeEach
    public void initTest() {
        droitsStrategieApa = createEntity(em);
    }

    @Test
    @Transactional
    void createDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeCreate = droitsStrategieApaRepository.findAll().size();
        // Create the DroitsStrategieApa
        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isCreated());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeCreate + 1);
        DroitsStrategieApa testDroitsStrategieApa = droitsStrategieApaList.get(droitsStrategieApaList.size() - 1);
        assertThat(testDroitsStrategieApa.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testDroitsStrategieApa.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testDroitsStrategieApa.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testDroitsStrategieApa.getMontantPlafond()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND);
        assertThat(testDroitsStrategieApa.getMontantPlafondPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategieApa.getNbHeurePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategieApa.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testDroitsStrategieApa.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitsStrategieApa.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void createDroitsStrategieApaWithExistingId() throws Exception {
        // Create the DroitsStrategieApa with an existing ID
        droitsStrategieApa.setId(1L);

        int databaseSizeBeforeCreate = droitsStrategieApaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setIsActif(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setAnne(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setMois(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setMontantPlafond(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setMontantPlafondPlus(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeurePlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setNbHeurePlafond(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setTauxCotisations(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOuvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieApaRepository.findAll().size();
        // set the field null
        droitsStrategieApa.setDateOuverture(null);

        // Create the DroitsStrategieApa, which fails.

        restDroitsStrategieApaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDroitsStrategieApas() throws Exception {
        // Initialize the database
        droitsStrategieApaRepository.saveAndFlush(droitsStrategieApa);

        // Get all the droitsStrategieApaList
        restDroitsStrategieApaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitsStrategieApa.getId().intValue())))
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
    void getDroitsStrategieApa() throws Exception {
        // Initialize the database
        droitsStrategieApaRepository.saveAndFlush(droitsStrategieApa);

        // Get the droitsStrategieApa
        restDroitsStrategieApaMockMvc
            .perform(get(ENTITY_API_URL_ID, droitsStrategieApa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(droitsStrategieApa.getId().intValue()))
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
    void getNonExistingDroitsStrategieApa() throws Exception {
        // Get the droitsStrategieApa
        restDroitsStrategieApaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDroitsStrategieApa() throws Exception {
        // Initialize the database
        droitsStrategieApaRepository.saveAndFlush(droitsStrategieApa);

        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();

        // Update the droitsStrategieApa
        DroitsStrategieApa updatedDroitsStrategieApa = droitsStrategieApaRepository.findById(droitsStrategieApa.getId()).get();
        // Disconnect from session so that the updates on updatedDroitsStrategieApa are not directly saved in db
        em.detach(updatedDroitsStrategieApa);
        updatedDroitsStrategieApa
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDroitsStrategieApa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDroitsStrategieApa))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategieApa testDroitsStrategieApa = droitsStrategieApaList.get(droitsStrategieApaList.size() - 1);
        assertThat(testDroitsStrategieApa.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategieApa.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategieApa.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategieApa.getMontantPlafond()).isEqualTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategieApa.getMontantPlafondPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategieApa.getNbHeurePlafond()).isEqualTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategieApa.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategieApa.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategieApa.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void putNonExistingDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();
        droitsStrategieApa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droitsStrategieApa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();
        droitsStrategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();
        droitsStrategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDroitsStrategieApaWithPatch() throws Exception {
        // Initialize the database
        droitsStrategieApaRepository.saveAndFlush(droitsStrategieApa);

        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();

        // Update the droitsStrategieApa using partial update
        DroitsStrategieApa partialUpdatedDroitsStrategieApa = new DroitsStrategieApa();
        partialUpdatedDroitsStrategieApa.setId(droitsStrategieApa.getId());

        partialUpdatedDroitsStrategieApa.isActif(UPDATED_IS_ACTIF).anne(UPDATED_ANNE).montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS);

        restDroitsStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategieApa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategieApa))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategieApa testDroitsStrategieApa = droitsStrategieApaList.get(droitsStrategieApaList.size() - 1);
        assertThat(testDroitsStrategieApa.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategieApa.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategieApa.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testDroitsStrategieApa.getMontantPlafond()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND);
        assertThat(testDroitsStrategieApa.getMontantPlafondPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategieApa.getNbHeurePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategieApa.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testDroitsStrategieApa.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitsStrategieApa.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void fullUpdateDroitsStrategieApaWithPatch() throws Exception {
        // Initialize the database
        droitsStrategieApaRepository.saveAndFlush(droitsStrategieApa);

        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();

        // Update the droitsStrategieApa using partial update
        DroitsStrategieApa partialUpdatedDroitsStrategieApa = new DroitsStrategieApa();
        partialUpdatedDroitsStrategieApa.setId(droitsStrategieApa.getId());

        partialUpdatedDroitsStrategieApa
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategieApa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategieApa))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategieApa testDroitsStrategieApa = droitsStrategieApaList.get(droitsStrategieApaList.size() - 1);
        assertThat(testDroitsStrategieApa.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategieApa.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategieApa.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategieApa.getMontantPlafond()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategieApa.getMontantPlafondPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategieApa.getNbHeurePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategieApa.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategieApa.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategieApa.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void patchNonExistingDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();
        droitsStrategieApa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, droitsStrategieApa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();
        droitsStrategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDroitsStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieApaRepository.findAll().size();
        droitsStrategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieApa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategieApa in the database
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDroitsStrategieApa() throws Exception {
        // Initialize the database
        droitsStrategieApaRepository.saveAndFlush(droitsStrategieApa);

        int databaseSizeBeforeDelete = droitsStrategieApaRepository.findAll().size();

        // Delete the droitsStrategieApa
        restDroitsStrategieApaMockMvc
            .perform(delete(ENTITY_API_URL_ID, droitsStrategieApa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DroitsStrategieApa> droitsStrategieApaList = droitsStrategieApaRepository.findAll();
        assertThat(droitsStrategieApaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
