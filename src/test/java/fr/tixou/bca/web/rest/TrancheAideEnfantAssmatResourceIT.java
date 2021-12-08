package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.TrancheAideEnfantAssmat;
import fr.tixou.bca.repository.TrancheAideEnfantAssmatRepository;
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
 * Integration tests for the {@link TrancheAideEnfantAssmatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrancheAideEnfantAssmatResourceIT {

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

    private static final String ENTITY_API_URL = "/api/tranche-aide-enfant-assmats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrancheAideEnfantAssmatRepository trancheAideEnfantAssmatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrancheAideEnfantAssmatMockMvc;

    private TrancheAideEnfantAssmat trancheAideEnfantAssmat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrancheAideEnfantAssmat createEntity(EntityManager em) {
        TrancheAideEnfantAssmat trancheAideEnfantAssmat = new TrancheAideEnfantAssmat()
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .ageEnfantRevoluSurPeriode(DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(DEFAULT_MONTANT_PLAFOND_SALAIRE)
            .isActif(DEFAULT_IS_ACTIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .isUpdated(DEFAULT_IS_UPDATED)
            .dateModified(DEFAULT_DATE_MODIFIED);
        return trancheAideEnfantAssmat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrancheAideEnfantAssmat createUpdatedEntity(EntityManager em) {
        TrancheAideEnfantAssmat trancheAideEnfantAssmat = new TrancheAideEnfantAssmat()
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);
        return trancheAideEnfantAssmat;
    }

    @BeforeEach
    public void initTest() {
        trancheAideEnfantAssmat = createEntity(em);
    }

    @Test
    @Transactional
    void createTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeCreate = trancheAideEnfantAssmatRepository.findAll().size();
        // Create the TrancheAideEnfantAssmat
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isCreated());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeCreate + 1);
        TrancheAideEnfantAssmat testTrancheAideEnfantAssmat = trancheAideEnfantAssmatList.get(trancheAideEnfantAssmatList.size() - 1);
        assertThat(testTrancheAideEnfantAssmat.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testTrancheAideEnfantAssmat.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testTrancheAideEnfantAssmat.getAgeEnfantRevoluSurPeriode()).isEqualTo(DEFAULT_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantAssmat.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantAssmat.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testTrancheAideEnfantAssmat.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTrancheAideEnfantAssmat.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testTrancheAideEnfantAssmat.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void createTrancheAideEnfantAssmatWithExistingId() throws Exception {
        // Create the TrancheAideEnfantAssmat with an existing ID
        trancheAideEnfantAssmat.setId(1L);

        int databaseSizeBeforeCreate = trancheAideEnfantAssmatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setAnne(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setMois(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAgeEnfantRevoluSurPeriodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setAgeEnfantRevoluSurPeriode(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setMontantPlafondSalaire(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setIsActif(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setDateCreated(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = trancheAideEnfantAssmatRepository.findAll().size();
        // set the field null
        trancheAideEnfantAssmat.setIsUpdated(null);

        // Create the TrancheAideEnfantAssmat, which fails.

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrancheAideEnfantAssmats() throws Exception {
        // Initialize the database
        trancheAideEnfantAssmatRepository.saveAndFlush(trancheAideEnfantAssmat);

        // Get all the trancheAideEnfantAssmatList
        restTrancheAideEnfantAssmatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trancheAideEnfantAssmat.getId().intValue())))
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
    void getTrancheAideEnfantAssmat() throws Exception {
        // Initialize the database
        trancheAideEnfantAssmatRepository.saveAndFlush(trancheAideEnfantAssmat);

        // Get the trancheAideEnfantAssmat
        restTrancheAideEnfantAssmatMockMvc
            .perform(get(ENTITY_API_URL_ID, trancheAideEnfantAssmat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trancheAideEnfantAssmat.getId().intValue()))
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
    void getNonExistingTrancheAideEnfantAssmat() throws Exception {
        // Get the trancheAideEnfantAssmat
        restTrancheAideEnfantAssmatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrancheAideEnfantAssmat() throws Exception {
        // Initialize the database
        trancheAideEnfantAssmatRepository.saveAndFlush(trancheAideEnfantAssmat);

        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();

        // Update the trancheAideEnfantAssmat
        TrancheAideEnfantAssmat updatedTrancheAideEnfantAssmat = trancheAideEnfantAssmatRepository
            .findById(trancheAideEnfantAssmat.getId())
            .get();
        // Disconnect from session so that the updates on updatedTrancheAideEnfantAssmat are not directly saved in db
        em.detach(updatedTrancheAideEnfantAssmat);
        updatedTrancheAideEnfantAssmat
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrancheAideEnfantAssmat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrancheAideEnfantAssmat))
            )
            .andExpect(status().isOk());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
        TrancheAideEnfantAssmat testTrancheAideEnfantAssmat = trancheAideEnfantAssmatList.get(trancheAideEnfantAssmatList.size() - 1);
        assertThat(testTrancheAideEnfantAssmat.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testTrancheAideEnfantAssmat.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testTrancheAideEnfantAssmat.getAgeEnfantRevoluSurPeriode()).isEqualTo(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantAssmat.getMontantPlafondSalaire()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantAssmat.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testTrancheAideEnfantAssmat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTrancheAideEnfantAssmat.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testTrancheAideEnfantAssmat.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();
        trancheAideEnfantAssmat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trancheAideEnfantAssmat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();
        trancheAideEnfantAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();
        trancheAideEnfantAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrancheAideEnfantAssmatWithPatch() throws Exception {
        // Initialize the database
        trancheAideEnfantAssmatRepository.saveAndFlush(trancheAideEnfantAssmat);

        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();

        // Update the trancheAideEnfantAssmat using partial update
        TrancheAideEnfantAssmat partialUpdatedTrancheAideEnfantAssmat = new TrancheAideEnfantAssmat();
        partialUpdatedTrancheAideEnfantAssmat.setId(trancheAideEnfantAssmat.getId());

        partialUpdatedTrancheAideEnfantAssmat
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrancheAideEnfantAssmat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrancheAideEnfantAssmat))
            )
            .andExpect(status().isOk());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
        TrancheAideEnfantAssmat testTrancheAideEnfantAssmat = trancheAideEnfantAssmatList.get(trancheAideEnfantAssmatList.size() - 1);
        assertThat(testTrancheAideEnfantAssmat.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testTrancheAideEnfantAssmat.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testTrancheAideEnfantAssmat.getAgeEnfantRevoluSurPeriode()).isEqualTo(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantAssmat.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantAssmat.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testTrancheAideEnfantAssmat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTrancheAideEnfantAssmat.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testTrancheAideEnfantAssmat.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateTrancheAideEnfantAssmatWithPatch() throws Exception {
        // Initialize the database
        trancheAideEnfantAssmatRepository.saveAndFlush(trancheAideEnfantAssmat);

        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();

        // Update the trancheAideEnfantAssmat using partial update
        TrancheAideEnfantAssmat partialUpdatedTrancheAideEnfantAssmat = new TrancheAideEnfantAssmat();
        partialUpdatedTrancheAideEnfantAssmat.setId(trancheAideEnfantAssmat.getId());

        partialUpdatedTrancheAideEnfantAssmat
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .ageEnfantRevoluSurPeriode(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restTrancheAideEnfantAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrancheAideEnfantAssmat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrancheAideEnfantAssmat))
            )
            .andExpect(status().isOk());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
        TrancheAideEnfantAssmat testTrancheAideEnfantAssmat = trancheAideEnfantAssmatList.get(trancheAideEnfantAssmatList.size() - 1);
        assertThat(testTrancheAideEnfantAssmat.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testTrancheAideEnfantAssmat.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testTrancheAideEnfantAssmat.getAgeEnfantRevoluSurPeriode()).isEqualTo(UPDATED_AGE_ENFANT_REVOLU_SUR_PERIODE);
        assertThat(testTrancheAideEnfantAssmat.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testTrancheAideEnfantAssmat.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testTrancheAideEnfantAssmat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTrancheAideEnfantAssmat.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testTrancheAideEnfantAssmat.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();
        trancheAideEnfantAssmat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trancheAideEnfantAssmat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();
        trancheAideEnfantAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrancheAideEnfantAssmat() throws Exception {
        int databaseSizeBeforeUpdate = trancheAideEnfantAssmatRepository.findAll().size();
        trancheAideEnfantAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrancheAideEnfantAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trancheAideEnfantAssmat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrancheAideEnfantAssmat in the database
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrancheAideEnfantAssmat() throws Exception {
        // Initialize the database
        trancheAideEnfantAssmatRepository.saveAndFlush(trancheAideEnfantAssmat);

        int databaseSizeBeforeDelete = trancheAideEnfantAssmatRepository.findAll().size();

        // Delete the trancheAideEnfantAssmat
        restTrancheAideEnfantAssmatMockMvc
            .perform(delete(ENTITY_API_URL_ID, trancheAideEnfantAssmat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrancheAideEnfantAssmat> trancheAideEnfantAssmatList = trancheAideEnfantAssmatRepository.findAll();
        assertThat(trancheAideEnfantAssmatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
