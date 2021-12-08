package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.StrategieCmgGed;
import fr.tixou.bca.repository.StrategieCmgGedRepository;
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
 * Integration tests for the {@link StrategieCmgGedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrategieCmgGedResourceIT {

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final BigDecimal DEFAULT_NB_HEURE_SEUIL_PLAFOND = new BigDecimal(1);
    private static final BigDecimal UPDATED_NB_HEURE_SEUIL_PLAFOND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_SALAIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_COTISATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_COTISATIONS = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_UPDATED = false;
    private static final Boolean UPDATED_IS_UPDATED = true;

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/strategie-cmg-geds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategieCmgGedRepository strategieCmgGedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategieCmgGedMockMvc;

    private StrategieCmgGed strategieCmgGed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieCmgGed createEntity(EntityManager em) {
        StrategieCmgGed strategieCmgGed = new StrategieCmgGed()
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .nbHeureSeuilPlafond(DEFAULT_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(DEFAULT_TAUX_SALAIRE)
            .tauxCotisations(DEFAULT_TAUX_COTISATIONS)
            .isActif(DEFAULT_IS_ACTIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .isUpdated(DEFAULT_IS_UPDATED)
            .dateModified(DEFAULT_DATE_MODIFIED);
        return strategieCmgGed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieCmgGed createUpdatedEntity(EntityManager em) {
        StrategieCmgGed strategieCmgGed = new StrategieCmgGed()
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .nbHeureSeuilPlafond(UPDATED_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);
        return strategieCmgGed;
    }

    @BeforeEach
    public void initTest() {
        strategieCmgGed = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategieCmgGed() throws Exception {
        int databaseSizeBeforeCreate = strategieCmgGedRepository.findAll().size();
        // Create the StrategieCmgGed
        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isCreated());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeCreate + 1);
        StrategieCmgGed testStrategieCmgGed = strategieCmgGedList.get(strategieCmgGedList.size() - 1);
        assertThat(testStrategieCmgGed.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieCmgGed.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategieCmgGed.getNbHeureSeuilPlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgGed.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testStrategieCmgGed.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testStrategieCmgGed.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategieCmgGed.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testStrategieCmgGed.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testStrategieCmgGed.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void createStrategieCmgGedWithExistingId() throws Exception {
        // Create the StrategieCmgGed with an existing ID
        strategieCmgGed.setId(1L);

        int databaseSizeBeforeCreate = strategieCmgGedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setAnne(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setMois(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeureSeuilPlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setNbHeureSeuilPlafond(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setTauxSalaire(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setTauxCotisations(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setIsActif(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setDateCreated(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgGedRepository.findAll().size();
        // set the field null
        strategieCmgGed.setIsUpdated(null);

        // Create the StrategieCmgGed, which fails.

        restStrategieCmgGedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategieCmgGeds() throws Exception {
        // Initialize the database
        strategieCmgGedRepository.saveAndFlush(strategieCmgGed);

        // Get all the strategieCmgGedList
        restStrategieCmgGedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategieCmgGed.getId().intValue())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].nbHeureSeuilPlafond").value(hasItem(sameNumber(DEFAULT_NB_HEURE_SEUIL_PLAFOND))))
            .andExpect(jsonPath("$.[*].tauxSalaire").value(hasItem(sameNumber(DEFAULT_TAUX_SALAIRE))))
            .andExpect(jsonPath("$.[*].tauxCotisations").value(hasItem(sameNumber(DEFAULT_TAUX_COTISATIONS))))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].isUpdated").value(hasItem(DEFAULT_IS_UPDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())));
    }

    @Test
    @Transactional
    void getStrategieCmgGed() throws Exception {
        // Initialize the database
        strategieCmgGedRepository.saveAndFlush(strategieCmgGed);

        // Get the strategieCmgGed
        restStrategieCmgGedMockMvc
            .perform(get(ENTITY_API_URL_ID, strategieCmgGed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategieCmgGed.getId().intValue()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.nbHeureSeuilPlafond").value(sameNumber(DEFAULT_NB_HEURE_SEUIL_PLAFOND)))
            .andExpect(jsonPath("$.tauxSalaire").value(sameNumber(DEFAULT_TAUX_SALAIRE)))
            .andExpect(jsonPath("$.tauxCotisations").value(sameNumber(DEFAULT_TAUX_COTISATIONS)))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.isUpdated").value(DEFAULT_IS_UPDATED.booleanValue()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStrategieCmgGed() throws Exception {
        // Get the strategieCmgGed
        restStrategieCmgGedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrategieCmgGed() throws Exception {
        // Initialize the database
        strategieCmgGedRepository.saveAndFlush(strategieCmgGed);

        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();

        // Update the strategieCmgGed
        StrategieCmgGed updatedStrategieCmgGed = strategieCmgGedRepository.findById(strategieCmgGed.getId()).get();
        // Disconnect from session so that the updates on updatedStrategieCmgGed are not directly saved in db
        em.detach(updatedStrategieCmgGed);
        updatedStrategieCmgGed
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .nbHeureSeuilPlafond(UPDATED_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restStrategieCmgGedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategieCmgGed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategieCmgGed))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
        StrategieCmgGed testStrategieCmgGed = strategieCmgGedList.get(strategieCmgGedList.size() - 1);
        assertThat(testStrategieCmgGed.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCmgGed.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategieCmgGed.getNbHeureSeuilPlafond()).isEqualTo(UPDATED_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgGed.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieCmgGed.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testStrategieCmgGed.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCmgGed.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testStrategieCmgGed.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testStrategieCmgGed.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingStrategieCmgGed() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();
        strategieCmgGed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieCmgGedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategieCmgGed.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategieCmgGed() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();
        strategieCmgGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgGedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategieCmgGed() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();
        strategieCmgGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgGedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategieCmgGedWithPatch() throws Exception {
        // Initialize the database
        strategieCmgGedRepository.saveAndFlush(strategieCmgGed);

        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();

        // Update the strategieCmgGed using partial update
        StrategieCmgGed partialUpdatedStrategieCmgGed = new StrategieCmgGed();
        partialUpdatedStrategieCmgGed.setId(strategieCmgGed.getId());

        partialUpdatedStrategieCmgGed.tauxCotisations(UPDATED_TAUX_COTISATIONS).isActif(UPDATED_IS_ACTIF);

        restStrategieCmgGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieCmgGed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieCmgGed))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
        StrategieCmgGed testStrategieCmgGed = strategieCmgGedList.get(strategieCmgGedList.size() - 1);
        assertThat(testStrategieCmgGed.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieCmgGed.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategieCmgGed.getNbHeureSeuilPlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgGed.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testStrategieCmgGed.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testStrategieCmgGed.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCmgGed.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testStrategieCmgGed.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testStrategieCmgGed.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateStrategieCmgGedWithPatch() throws Exception {
        // Initialize the database
        strategieCmgGedRepository.saveAndFlush(strategieCmgGed);

        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();

        // Update the strategieCmgGed using partial update
        StrategieCmgGed partialUpdatedStrategieCmgGed = new StrategieCmgGed();
        partialUpdatedStrategieCmgGed.setId(strategieCmgGed.getId());

        partialUpdatedStrategieCmgGed
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .nbHeureSeuilPlafond(UPDATED_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restStrategieCmgGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieCmgGed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieCmgGed))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
        StrategieCmgGed testStrategieCmgGed = strategieCmgGedList.get(strategieCmgGedList.size() - 1);
        assertThat(testStrategieCmgGed.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCmgGed.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategieCmgGed.getNbHeureSeuilPlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgGed.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieCmgGed.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testStrategieCmgGed.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCmgGed.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testStrategieCmgGed.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testStrategieCmgGed.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingStrategieCmgGed() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();
        strategieCmgGed.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieCmgGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategieCmgGed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategieCmgGed() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();
        strategieCmgGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgGedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategieCmgGed() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgGedRepository.findAll().size();
        strategieCmgGed.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgGedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgGed))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieCmgGed in the database
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategieCmgGed() throws Exception {
        // Initialize the database
        strategieCmgGedRepository.saveAndFlush(strategieCmgGed);

        int databaseSizeBeforeDelete = strategieCmgGedRepository.findAll().size();

        // Delete the strategieCmgGed
        restStrategieCmgGedMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategieCmgGed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategieCmgGed> strategieCmgGedList = strategieCmgGedRepository.findAll();
        assertThat(strategieCmgGedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
