package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.StrategieCmgAssmat;
import fr.tixou.bca.repository.StrategieCmgAssmatRepository;
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
 * Integration tests for the {@link StrategieCmgAssmatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StrategieCmgAssmatResourceIT {

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

    private static final String ENTITY_API_URL = "/api/strategie-cmg-assmats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategieCmgAssmatRepository strategieCmgAssmatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategieCmgAssmatMockMvc;

    private StrategieCmgAssmat strategieCmgAssmat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieCmgAssmat createEntity(EntityManager em) {
        StrategieCmgAssmat strategieCmgAssmat = new StrategieCmgAssmat()
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .nbHeureSeuilPlafond(DEFAULT_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(DEFAULT_TAUX_SALAIRE)
            .tauxCotisations(DEFAULT_TAUX_COTISATIONS)
            .isActif(DEFAULT_IS_ACTIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .isUpdated(DEFAULT_IS_UPDATED)
            .dateModified(DEFAULT_DATE_MODIFIED);
        return strategieCmgAssmat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieCmgAssmat createUpdatedEntity(EntityManager em) {
        StrategieCmgAssmat strategieCmgAssmat = new StrategieCmgAssmat()
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .nbHeureSeuilPlafond(UPDATED_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);
        return strategieCmgAssmat;
    }

    @BeforeEach
    public void initTest() {
        strategieCmgAssmat = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeCreate = strategieCmgAssmatRepository.findAll().size();
        // Create the StrategieCmgAssmat
        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isCreated());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeCreate + 1);
        StrategieCmgAssmat testStrategieCmgAssmat = strategieCmgAssmatList.get(strategieCmgAssmatList.size() - 1);
        assertThat(testStrategieCmgAssmat.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieCmgAssmat.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategieCmgAssmat.getNbHeureSeuilPlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgAssmat.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testStrategieCmgAssmat.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testStrategieCmgAssmat.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategieCmgAssmat.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testStrategieCmgAssmat.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testStrategieCmgAssmat.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void createStrategieCmgAssmatWithExistingId() throws Exception {
        // Create the StrategieCmgAssmat with an existing ID
        strategieCmgAssmat.setId(1L);

        int databaseSizeBeforeCreate = strategieCmgAssmatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setAnne(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setMois(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeureSeuilPlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setNbHeureSeuilPlafond(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setTauxSalaire(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setTauxCotisations(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setIsActif(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setDateCreated(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCmgAssmatRepository.findAll().size();
        // set the field null
        strategieCmgAssmat.setIsUpdated(null);

        // Create the StrategieCmgAssmat, which fails.

        restStrategieCmgAssmatMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategieCmgAssmats() throws Exception {
        // Initialize the database
        strategieCmgAssmatRepository.saveAndFlush(strategieCmgAssmat);

        // Get all the strategieCmgAssmatList
        restStrategieCmgAssmatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategieCmgAssmat.getId().intValue())))
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
    void getStrategieCmgAssmat() throws Exception {
        // Initialize the database
        strategieCmgAssmatRepository.saveAndFlush(strategieCmgAssmat);

        // Get the strategieCmgAssmat
        restStrategieCmgAssmatMockMvc
            .perform(get(ENTITY_API_URL_ID, strategieCmgAssmat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategieCmgAssmat.getId().intValue()))
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
    void getNonExistingStrategieCmgAssmat() throws Exception {
        // Get the strategieCmgAssmat
        restStrategieCmgAssmatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrategieCmgAssmat() throws Exception {
        // Initialize the database
        strategieCmgAssmatRepository.saveAndFlush(strategieCmgAssmat);

        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();

        // Update the strategieCmgAssmat
        StrategieCmgAssmat updatedStrategieCmgAssmat = strategieCmgAssmatRepository.findById(strategieCmgAssmat.getId()).get();
        // Disconnect from session so that the updates on updatedStrategieCmgAssmat are not directly saved in db
        em.detach(updatedStrategieCmgAssmat);
        updatedStrategieCmgAssmat
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .nbHeureSeuilPlafond(UPDATED_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restStrategieCmgAssmatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategieCmgAssmat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategieCmgAssmat))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
        StrategieCmgAssmat testStrategieCmgAssmat = strategieCmgAssmatList.get(strategieCmgAssmatList.size() - 1);
        assertThat(testStrategieCmgAssmat.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCmgAssmat.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategieCmgAssmat.getNbHeureSeuilPlafond()).isEqualTo(UPDATED_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgAssmat.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieCmgAssmat.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testStrategieCmgAssmat.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCmgAssmat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testStrategieCmgAssmat.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testStrategieCmgAssmat.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void putNonExistingStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();
        strategieCmgAssmat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieCmgAssmatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategieCmgAssmat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();
        strategieCmgAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgAssmatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();
        strategieCmgAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgAssmatMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategieCmgAssmatWithPatch() throws Exception {
        // Initialize the database
        strategieCmgAssmatRepository.saveAndFlush(strategieCmgAssmat);

        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();

        // Update the strategieCmgAssmat using partial update
        StrategieCmgAssmat partialUpdatedStrategieCmgAssmat = new StrategieCmgAssmat();
        partialUpdatedStrategieCmgAssmat.setId(strategieCmgAssmat.getId());

        partialUpdatedStrategieCmgAssmat.tauxSalaire(UPDATED_TAUX_SALAIRE).isActif(UPDATED_IS_ACTIF).dateCreated(UPDATED_DATE_CREATED);

        restStrategieCmgAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieCmgAssmat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieCmgAssmat))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
        StrategieCmgAssmat testStrategieCmgAssmat = strategieCmgAssmatList.get(strategieCmgAssmatList.size() - 1);
        assertThat(testStrategieCmgAssmat.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieCmgAssmat.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategieCmgAssmat.getNbHeureSeuilPlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgAssmat.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieCmgAssmat.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
        assertThat(testStrategieCmgAssmat.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCmgAssmat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testStrategieCmgAssmat.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testStrategieCmgAssmat.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void fullUpdateStrategieCmgAssmatWithPatch() throws Exception {
        // Initialize the database
        strategieCmgAssmatRepository.saveAndFlush(strategieCmgAssmat);

        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();

        // Update the strategieCmgAssmat using partial update
        StrategieCmgAssmat partialUpdatedStrategieCmgAssmat = new StrategieCmgAssmat();
        partialUpdatedStrategieCmgAssmat.setId(strategieCmgAssmat.getId());

        partialUpdatedStrategieCmgAssmat
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .nbHeureSeuilPlafond(UPDATED_NB_HEURE_SEUIL_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS)
            .isActif(UPDATED_IS_ACTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED);

        restStrategieCmgAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieCmgAssmat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieCmgAssmat))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
        StrategieCmgAssmat testStrategieCmgAssmat = strategieCmgAssmatList.get(strategieCmgAssmatList.size() - 1);
        assertThat(testStrategieCmgAssmat.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCmgAssmat.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategieCmgAssmat.getNbHeureSeuilPlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_SEUIL_PLAFOND);
        assertThat(testStrategieCmgAssmat.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieCmgAssmat.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
        assertThat(testStrategieCmgAssmat.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCmgAssmat.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testStrategieCmgAssmat.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testStrategieCmgAssmat.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
    }

    @Test
    @Transactional
    void patchNonExistingStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();
        strategieCmgAssmat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieCmgAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategieCmgAssmat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();
        strategieCmgAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategieCmgAssmat() throws Exception {
        int databaseSizeBeforeUpdate = strategieCmgAssmatRepository.findAll().size();
        strategieCmgAssmat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCmgAssmatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCmgAssmat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieCmgAssmat in the database
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategieCmgAssmat() throws Exception {
        // Initialize the database
        strategieCmgAssmatRepository.saveAndFlush(strategieCmgAssmat);

        int databaseSizeBeforeDelete = strategieCmgAssmatRepository.findAll().size();

        // Delete the strategieCmgAssmat
        restStrategieCmgAssmatMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategieCmgAssmat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategieCmgAssmat> strategieCmgAssmatList = strategieCmgAssmatRepository.findAll();
        assertThat(strategieCmgAssmatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
