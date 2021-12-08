package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.DroitsStrategieCi;
import fr.tixou.bca.repository.DroitsStrategieCiRepository;
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
 * Integration tests for the {@link DroitsStrategieCiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DroitsStrategieCiResourceIT {

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_DEFAUT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_DEFAUT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_HANDICAPE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_HANDICAPE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_SALAIRE = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FERMETURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FERMETURE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/droits-strategie-cis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DroitsStrategieCiRepository droitsStrategieCiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroitsStrategieCiMockMvc;

    private DroitsStrategieCi droitsStrategieCi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategieCi createEntity(EntityManager em) {
        DroitsStrategieCi droitsStrategieCi = new DroitsStrategieCi()
            .isActif(DEFAULT_IS_ACTIF)
            .anne(DEFAULT_ANNE)
            .montantPlafondDefaut(DEFAULT_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(DEFAULT_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(DEFAULT_TAUX_SALAIRE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE);
        return droitsStrategieCi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitsStrategieCi createUpdatedEntity(EntityManager em) {
        DroitsStrategieCi droitsStrategieCi = new DroitsStrategieCi()
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);
        return droitsStrategieCi;
    }

    @BeforeEach
    public void initTest() {
        droitsStrategieCi = createEntity(em);
    }

    @Test
    @Transactional
    void createDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeCreate = droitsStrategieCiRepository.findAll().size();
        // Create the DroitsStrategieCi
        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isCreated());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeCreate + 1);
        DroitsStrategieCi testDroitsStrategieCi = droitsStrategieCiList.get(droitsStrategieCiList.size() - 1);
        assertThat(testDroitsStrategieCi.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testDroitsStrategieCi.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefaut()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicape()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefautPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicapePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testDroitsStrategieCi.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testDroitsStrategieCi.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitsStrategieCi.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void createDroitsStrategieCiWithExistingId() throws Exception {
        // Create the DroitsStrategieCi with an existing ID
        droitsStrategieCi.setId(1L);

        int databaseSizeBeforeCreate = droitsStrategieCiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setIsActif(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setAnne(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondDefautIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setMontantPlafondDefaut(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondHandicapeIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setMontantPlafondHandicape(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondDefautPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setMontantPlafondDefautPlus(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondHandicapePlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setMontantPlafondHandicapePlus(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setTauxSalaire(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOuvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitsStrategieCiRepository.findAll().size();
        // set the field null
        droitsStrategieCi.setDateOuverture(null);

        // Create the DroitsStrategieCi, which fails.

        restDroitsStrategieCiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDroitsStrategieCis() throws Exception {
        // Initialize the database
        droitsStrategieCiRepository.saveAndFlush(droitsStrategieCi);

        // Get all the droitsStrategieCiList
        restDroitsStrategieCiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitsStrategieCi.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].montantPlafondDefaut").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT))))
            .andExpect(jsonPath("$.[*].montantPlafondHandicape").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE))))
            .andExpect(jsonPath("$.[*].montantPlafondDefautPlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS))))
            .andExpect(jsonPath("$.[*].montantPlafondHandicapePlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS))))
            .andExpect(jsonPath("$.[*].tauxSalaire").value(hasItem(sameNumber(DEFAULT_TAUX_SALAIRE))))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())));
    }

    @Test
    @Transactional
    void getDroitsStrategieCi() throws Exception {
        // Initialize the database
        droitsStrategieCiRepository.saveAndFlush(droitsStrategieCi);

        // Get the droitsStrategieCi
        restDroitsStrategieCiMockMvc
            .perform(get(ENTITY_API_URL_ID, droitsStrategieCi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(droitsStrategieCi.getId().intValue()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.montantPlafondDefaut").value(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT)))
            .andExpect(jsonPath("$.montantPlafondHandicape").value(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE)))
            .andExpect(jsonPath("$.montantPlafondDefautPlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS)))
            .andExpect(jsonPath("$.montantPlafondHandicapePlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS)))
            .andExpect(jsonPath("$.tauxSalaire").value(sameNumber(DEFAULT_TAUX_SALAIRE)))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateFermeture").value(DEFAULT_DATE_FERMETURE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDroitsStrategieCi() throws Exception {
        // Get the droitsStrategieCi
        restDroitsStrategieCiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDroitsStrategieCi() throws Exception {
        // Initialize the database
        droitsStrategieCiRepository.saveAndFlush(droitsStrategieCi);

        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();

        // Update the droitsStrategieCi
        DroitsStrategieCi updatedDroitsStrategieCi = droitsStrategieCiRepository.findById(droitsStrategieCi.getId()).get();
        // Disconnect from session so that the updates on updatedDroitsStrategieCi are not directly saved in db
        em.detach(updatedDroitsStrategieCi);
        updatedDroitsStrategieCi
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDroitsStrategieCi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDroitsStrategieCi))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategieCi testDroitsStrategieCi = droitsStrategieCiList.get(droitsStrategieCiList.size() - 1);
        assertThat(testDroitsStrategieCi.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategieCi.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefaut()).isEqualTo(UPDATED_MONTANT_PLAFOND_DEFAUT);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicape()).isEqualTo(UPDATED_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefautPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicapePlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testDroitsStrategieCi.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
        assertThat(testDroitsStrategieCi.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategieCi.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void putNonExistingDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();
        droitsStrategieCi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droitsStrategieCi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();
        droitsStrategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();
        droitsStrategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDroitsStrategieCiWithPatch() throws Exception {
        // Initialize the database
        droitsStrategieCiRepository.saveAndFlush(droitsStrategieCi);

        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();

        // Update the droitsStrategieCi using partial update
        DroitsStrategieCi partialUpdatedDroitsStrategieCi = new DroitsStrategieCi();
        partialUpdatedDroitsStrategieCi.setId(droitsStrategieCi.getId());

        partialUpdatedDroitsStrategieCi
            .anne(UPDATED_ANNE)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategieCi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategieCi))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategieCi testDroitsStrategieCi = droitsStrategieCiList.get(droitsStrategieCiList.size() - 1);
        assertThat(testDroitsStrategieCi.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testDroitsStrategieCi.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefaut()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicape()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefautPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicapePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testDroitsStrategieCi.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testDroitsStrategieCi.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategieCi.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void fullUpdateDroitsStrategieCiWithPatch() throws Exception {
        // Initialize the database
        droitsStrategieCiRepository.saveAndFlush(droitsStrategieCi);

        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();

        // Update the droitsStrategieCi using partial update
        DroitsStrategieCi partialUpdatedDroitsStrategieCi = new DroitsStrategieCi();
        partialUpdatedDroitsStrategieCi.setId(droitsStrategieCi.getId());

        partialUpdatedDroitsStrategieCi
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitsStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitsStrategieCi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitsStrategieCi))
            )
            .andExpect(status().isOk());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
        DroitsStrategieCi testDroitsStrategieCi = droitsStrategieCiList.get(droitsStrategieCiList.size() - 1);
        assertThat(testDroitsStrategieCi.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitsStrategieCi.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefaut()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_DEFAUT);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicape()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testDroitsStrategieCi.getMontantPlafondDefautPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testDroitsStrategieCi.getMontantPlafondHandicapePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testDroitsStrategieCi.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testDroitsStrategieCi.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitsStrategieCi.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void patchNonExistingDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();
        droitsStrategieCi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitsStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, droitsStrategieCi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();
        droitsStrategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDroitsStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = droitsStrategieCiRepository.findAll().size();
        droitsStrategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitsStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitsStrategieCi))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitsStrategieCi in the database
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDroitsStrategieCi() throws Exception {
        // Initialize the database
        droitsStrategieCiRepository.saveAndFlush(droitsStrategieCi);

        int databaseSizeBeforeDelete = droitsStrategieCiRepository.findAll().size();

        // Delete the droitsStrategieCi
        restDroitsStrategieCiMockMvc
            .perform(delete(ENTITY_API_URL_ID, droitsStrategieCi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DroitsStrategieCi> droitsStrategieCiList = droitsStrategieCiRepository.findAll();
        assertThat(droitsStrategieCiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
