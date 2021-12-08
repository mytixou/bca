package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.DroitsStrategiePch;
import fr.tixou.bca.repository.DroitsStrategiePchRepository;
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
 * Integration tests for the {@link DroitsStrategiePchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DroitsStrategiePchResourceIT {

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

    private static final String ENTITY_API_URL = "/api/droits-strategie-pches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DroitsStrategiePchRepository droitsStrategiePchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroitsStrategiePchMockMvc;

    private DroitsStrategiePch droitsStrategiePch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategiePch createEntity(EntityManager em) {
        DroitsStrategiePch droitsStrategiePch = new DroitsStrategiePch()
            .isActif(DEFAULT_IS_ACTIF)
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .montantPlafond(DEFAULT_MONTANT_PLAFOND)
            .montantPlafondPlus(DEFAULT_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(DEFAULT_NB_HEURE_PLAFOND)
            .tauxCotisations(DEFAULT_TAUX_COTISATIONS)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE);
        return droitsStrategiePch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategiePch createUpdatedEntity(EntityManager em) {
        DroitsStrategiePch droitsStrategiePch = new DroitsStrategiePch()
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);
        return droitsStrategiePch;
    }

    @BeforeEach
    public void initTest() {
        droitsStrategiePch = createEntity(em);
    }

    @Test
    @Transactional
    void createDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeCreate = droitsStrategiePchRepository.findAll().size();
        // Create the DroitsStrategiePch
        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isCreated());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeCreate + 1);
        DroitsStrategiePch testDroitsStrategiePch = droitsStrategiePchList.get(droitsStrategiePchList.size() - 1);
        assertThat(testDroitsStrategiePch.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testDroitsStrategiePch.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testDroitsStrategiePch.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testDroitsStrategiePch.getMontantPlafond()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePch.getMontantPlafondPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePch.getNbHeurePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePch.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePch.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePch.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void createDroitsStrategiePchWithExistingId() throws Exception {
        // Create the DroitsStrategiePch with an existing ID
        droitsStrategiePch.setId(1L);

        int databaseSizeBeforeCreate = droitsStrategiePchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setIsActif(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setAnne(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setMois(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setMontantPlafond(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setMontantPlafondPlus(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeurePlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setNbHeurePlafond(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setTauxCotisations(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOuvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategiePchRepository.findAll().size();
        // set the field null
        droitsStrategiePch.setDateOuverture(null);

        // Create the DroitsStrategiePch, which fails.

        restDroitsStrategiePchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDroitsStrategiePches() throws Exception {
        // Initialize the database
        droitsStrategiePchRepository.saveAndFlush(droitsStrategiePch);

        // Get all the droitsStrategiePchList
        restDroitsStrategiePchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitsStrategiePch.getId().intValue())))
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
    void getDroitsStrategiePch() throws Exception {
        // Initialize the database
        droitsStrategiePchRepository.saveAndFlush(droitsStrategiePch);

        // Get the droitsStrategiePch
        restDroitsStrategiePchMockMvc
            .perform(get(ENTITY_API_URL_ID, droitsStrategiePch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(droitsStrategiePch.getId().intValue()))
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
    void getNonExistingDroitsStrategiePch() throws Exception {
        // Get the droitsStrategiePch
        restDroitsStrategiePchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDroitsStrategiePch() throws Exception {
        // Initialize the database
        droitsStrategiePchRepository.saveAndFlush(droitsStrategiePch);

        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();

        // Update the droitsStrategiePch
        DroitsStrategiePch updatedDroitsStrategiePch = droitsStrategiePchRepository.findById(droitsStrategiePch.getId()).get();
        // Disconnect from session so that the updates on updatedDroitsStrategiePch are not directly saved in db
        em.detach(updatedDroitsStrategiePch);
        updatedDroitsStrategiePch
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDroitsStrategiePch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDroitsStrategiePch))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategiePch testDroitsStrategiePch = droitsStrategiePchList.get(droitsStrategiePchList.size() - 1);
        assertThat(testDroitsStrategiePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategiePch.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategiePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategiePch.getMontantPlafond()).isEqualTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePch.getMontantPlafondPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePch.getNbHeurePlafond()).isEqualTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePch.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePch.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePch.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void putNonExistingDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();
        droitsStrategiePch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droitsStrategiePch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();
        droitsStrategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();
        droitsStrategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDroitsStrategiePchWithPatch() throws Exception {
        // Initialize the database
        droitsStrategiePchRepository.saveAndFlush(droitsStrategiePch);

        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();

        // Update the droitsStrategiePch using partial update
        DroitsStrategiePch partialUpdatedDroitsStrategiePch = new DroitsStrategiePch();
        partialUpdatedDroitsStrategiePch.setId(droitsStrategiePch.getId());

        partialUpdatedDroitsStrategiePch
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND);

        restDroitsStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategiePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategiePch))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategiePch testDroitsStrategiePch = droitsStrategiePchList.get(droitsStrategiePchList.size() - 1);
        assertThat(testDroitsStrategiePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategiePch.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategiePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategiePch.getMontantPlafond()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePch.getMontantPlafondPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePch.getNbHeurePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePch.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePch.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePch.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void fullUpdateDroitsStrategiePchWithPatch() throws Exception {
        // Initialize the database
        droitsStrategiePchRepository.saveAndFlush(droitsStrategiePch);

        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();

        // Update the droitsStrategiePch using partial update
        DroitsStrategiePch partialUpdatedDroitsStrategiePch = new DroitsStrategiePch();
        partialUpdatedDroitsStrategiePch.setId(droitsStrategiePch.getId());

        partialUpdatedDroitsStrategiePch
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafond(UPDATED_MONTANT_PLAFOND)
            .montantPlafondPlus(UPDATED_MONTANT_PLAFOND_PLUS)
            .nbHeurePlafond(UPDATED_NB_HEURE_PLAFOND)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategiePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategiePch))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategiePch testDroitsStrategiePch = droitsStrategiePchList.get(droitsStrategiePchList.size() - 1);
        assertThat(testDroitsStrategiePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategiePch.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategiePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDroitsStrategiePch.getMontantPlafond()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND);
        assertThat(testDroitsStrategiePch.getMontantPlafondPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_PLUS);
        assertThat(testDroitsStrategiePch.getNbHeurePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_PLAFOND);
        assertThat(testDroitsStrategiePch.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testDroitsStrategiePch.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategiePch.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void patchNonExistingDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();
        droitsStrategiePch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, droitsStrategiePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();
        droitsStrategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDroitsStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategiePchRepository.findAll().size();
        droitsStrategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategiePch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategiePch in the database
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDroitsStrategiePch() throws Exception {
        // Initialize the database
        droitsStrategiePchRepository.saveAndFlush(droitsStrategiePch);

        int databaseSizeBeforeDelete = droitsStrategiePchRepository.findAll().size();

        // Delete the droitsStrategiePch
        restDroitsStrategiePchMockMvc
            .perform(delete(ENTITY_API_URL_ID, droitsStrategiePch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DroitsStrategiePch> droitsStrategiePchList = droitsStrategiePchRepository.findAll();
        assertThat(droitsStrategiePchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
