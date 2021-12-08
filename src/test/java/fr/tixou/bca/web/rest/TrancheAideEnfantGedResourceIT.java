package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.TrancheAideEnfantGed;
import fr.tixou.bca.repository.TrancheAideEnfantGedRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TrancheAideEnfantGedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrancheAideEnfantGedResourceIT {

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final Integer DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE = 1;
    private static final Integer UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE = 2;

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_SALAIRE = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_UPDATED = false;
    private static final Boolean UPDATED_IS_UPDATED = true;

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tranche-aide-enfant-geds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrancheAideEnfantGedRepository trancheAideEnfantGedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrancheAideEnfantGedMockMvc;

    private TrancheAideEnfantGed trancheAideEnfantGed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrancheAideEnfantGed createEntity(EntityManager em) {
        TrancheAideEnfantGed trancheAideEnfantGed = new TrancheAideEnfantGed()
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .ageEnfantRevoluSurPeriode(DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(DEFAULT_MONTANT_PLAFOND_SALAIRE)
            .isActif(DEFAULT_IS_ACTIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .isUpdated(DEFAULT_IS_UPDATED)
            .dateModified(DEFAULT_DATE_MODIFIED);
        return trancheAideEnfantGed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrancheAideEnfantGed createUpdatedEntity(EntityManager em) {
        TrancheAideEnfantGed trancheAideEnfantGed = new TrancheAideEnfantGed()
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);
        return trancheAideEnfantGed;
    }

    @BeforeEach
    public void initTest() {
        trancheAideEnfantGed = createEntity(em);
    }

    @Test
    @Transactional
    void createTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeCreate = trancheAideEnfantGedRepository.findAll().size();
        // Create the TrancheAideEnfantGed
        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isCreated());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeCreate + 1);
        TrancheAideEnfantGed testTrancheAideEnfantGed = trancheAideEnfantGedList.get(trancheAideEnfantGedList.size() - 1);
        assertThat(testTrancheAideEnfantGed.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testTrancheAideEnfantGed.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testTrancheAideEnfantGed.getAgeEnfantRevoluSurPeriode()).isEqualTo(DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantGed.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantGed.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testTrancheAideEnfantGed.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTrancheAideEnfantGed.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testTrancheAideEnfantGed.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void createTrancheAideEnfantGedWithExistingId() throws Exception {
        // Create the TrancheAideEnfantGed with an existing ID
        trancheAideEnfantGed.setId(1L);

        int databaseSizeBeforeCreate = trancheAideEnfantGedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setAnne(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setMois(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeEnfantRevoluSurPeriodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setAgeEnfantRevoluSurPeriode(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setMontantPlafondSalaire(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setIsActif(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setDateCreated(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantGedRepository.findAll().size();
        // set the field null
        trancheAideEnfantGed.setIsUpdated(null);

        // Create the TrancheAideEnfantGed, which fails.

        restTrancheAideEnfantGedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrancheAideEnfantGeds() throws Exception {
        // Initialize the database
        trancheAideEnfantGedRepository.saveAndFlush(trancheAideEnfantGed);

        // Get all the trancheAideEnfantGedList
        restTrancheAideEnfantGedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trancheAideEnfantGed.getId().intValue())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].ageEnfantRevoluSurPeriode").value(hasItem(DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE)))
            .andExpect(jsonPath("$.[*].montantPlafondSalaire").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_SALAIRE))))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].isUpdated").value(hasItem(DEFAULT_IS_UPDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())));
    }

    @Test
    @Transactional
    void getTrancheAideEnfantGed() throws Exception {
        // Initialize the database
        trancheAideEnfantGedRepository.saveAndFlush(trancheAideEnfantGed);

        // Get the trancheAideEnfantGed
        restTrancheAideEnfantGedMockMvc
            .perform(get(ENTITY_API_URL_ID, trancheAideEnfantGed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trancheAideEnfantGed.getId().intValue()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.ageEnfantRevoluSurPeriode").value(DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE))
            .andExpect(jsonPath("$.montantPlafondSalaire").value(sameNumber(DEFAULT_MONTANT_PLAFOND_SALAIRE)))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.isUpdated").value(DEFAULT_IS_UPDATED.booleanValue()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrancheAideEnfantGed() throws Exception {
        // Get the trancheAideEnfantGed
        restTrancheAideEnfantGedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrancheAideEnfantGed() throws Exception {
        // Initialize the database
        trancheAideEnfantGedRepository.saveAndFlush(trancheAideEnfantGed);

        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();

        // Update the trancheAideEnfantGed
        TrancheAideEnfantGed updatedTrancheAideEnfantGed = trancheAideEnfantGedRepository.findById(trancheAideEnfantGed.getId()).get();
        // Disconnect from session so that the updates on updatedTrancheAideEnfantGed are not directly saved in db
        em.detach(updatedTrancheAideEnfantGed);
        updatedTrancheAideEnfantGed
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTrancheAideEnfantGedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrancheAideEnfantGed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrancheAideEnfantGed))
            )
            .andExpect(status().isOk());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
        TrancheAideEnfantGed testTrancheAideEnfantGed = trancheAideEnfantGedList.get(trancheAideEnfantGedList.size() - 1);
        assertThat(testTrancheAideEnfantGed.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testTrancheAideEnfantGed.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testTrancheAideEnfantGed.getAgeEnfantRevoluSurPeriode()).isEqualTo(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantGed.getMontantPlafondSalaire()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantGed.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testTrancheAideEnfantGed.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTrancheAideEnfantGed.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testTrancheAideEnfantGed.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();
        trancheAideEnfantGed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrancheAideEnfantGedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trancheAideEnfantGed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();
        trancheAideEnfantGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantGedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();
        trancheAideEnfantGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantGedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrancheAideEnfantGedWithPatch() throws Exception {
        // Initialize the database
        trancheAideEnfantGedRepository.saveAndFlush(trancheAideEnfantGed);

        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();

        // Update the trancheAideEnfantGed using partial update
        TrancheAideEnfantGed partialUpdatedTrancheAideEnfantGed = new TrancheAideEnfantGed();
        partialUpdatedTrancheAideEnfantGed.setId(trancheAideEnfantGed.getId());

        partialUpdatedTrancheAideEnfantGed
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED);

        restTrancheAideEnfantGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrancheAideEnfantGed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrancheAideEnfantGed))
            )
            .andExpect(status().isOk());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
        TrancheAideEnfantGed testTrancheAideEnfantGed = trancheAideEnfantGedList.get(trancheAideEnfantGedList.size() - 1);
        assertThat(testTrancheAideEnfantGed.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testTrancheAideEnfantGed.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testTrancheAideEnfantGed.getAgeEnfantRevoluSurPeriode()).isEqualTo(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantGed.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantGed.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testTrancheAideEnfantGed.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTrancheAideEnfantGed.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testTrancheAideEnfantGed.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateTrancheAideEnfantGedWithPatch() throws Exception {
        // Initialize the database
        trancheAideEnfantGedRepository.saveAndFlush(trancheAideEnfantGed);

        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();

        // Update the trancheAideEnfantGed using partial update
        TrancheAideEnfantGed partialUpdatedTrancheAideEnfantGed = new TrancheAideEnfantGed();
        partialUpdatedTrancheAideEnfantGed.setId(trancheAideEnfantGed.getId());

        partialUpdatedTrancheAideEnfantGed
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTrancheAideEnfantGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrancheAideEnfantGed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrancheAideEnfantGed))
            )
            .andExpect(status().isOk());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
        TrancheAideEnfantGed testTrancheAideEnfantGed = trancheAideEnfantGedList.get(trancheAideEnfantGedList.size() - 1);
        assertThat(testTrancheAideEnfantGed.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testTrancheAideEnfantGed.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testTrancheAideEnfantGed.getAgeEnfantRevoluSurPeriode()).isEqualTo(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantGed.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantGed.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testTrancheAideEnfantGed.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTrancheAideEnfantGed.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testTrancheAideEnfantGed.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();
        trancheAideEnfantGed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrancheAideEnfantGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trancheAideEnfantGed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();
        trancheAideEnfantGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrancheAideEnfantGed() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantGedRepository.findAll().size();
        trancheAideEnfantGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantGedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantGed))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrancheAideEnfantGed in the database
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrancheAideEnfantGed() throws Exception {
        // Initialize the database
        trancheAideEnfantGedRepository.saveAndFlush(trancheAideEnfantGed);

        int databaseSizeBeforeDelete = trancheAideEnfantGedRepository.findAll().size();

        // Delete the trancheAideEnfantGed
        restTrancheAideEnfantGedMockMvc
            .perform(delete(ENTITY_API_URL_ID, trancheAideEnfantGed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrancheAideEnfantGed> trancheAideEnfantGedList = trancheAideEnfantGedRepository.findAll();
        assertThat(trancheAideEnfantGedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
