package fr.tixou.bca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.DroitAide;
import fr.tixou.bca.repository.DroitAideRepository;
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
 * Integration tests for the {@link DroitAideResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DroitAideResourceIT {

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FERMETURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FERMETURE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/droit-aides";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DroitAideRepository droitAideRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroitAideMockMvc;

    private DroitAide droitAide;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitAide createEntity(EntityManager em) {
        DroitAide droitAide = new DroitAide()
            .isActif(DEFAULT_IS_ACTIF)
            .anne(DEFAULT_ANNE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE);
        return droitAide;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DroitAide createUpdatedEntity(EntityManager em) {
        DroitAide droitAide = new DroitAide()
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);
        return droitAide;
    }

    @BeforeEach
    public void initTest() {
        droitAide = createEntity(em);
    }

    @Test
    @Transactional
    void createDroitAide() throws Exception {
        int databaseSizeBeforeCreate = droitAideRepository.findAll().size();
        // Create the DroitAide
        restDroitAideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitAide)))
            .andExpect(status().isCreated());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeCreate + 1);
        DroitAide testDroitAide = droitAideList.get(droitAideList.size() - 1);
        assertThat(testDroitAide.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testDroitAide.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testDroitAide.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testDroitAide.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void createDroitAideWithExistingId() throws Exception {
        // Create the DroitAide with an existing ID
        droitAide.setId(1L);

        int databaseSizeBeforeCreate = droitAideRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroitAideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitAide)))
            .andExpect(status().isBadRequest());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitAideRepository.findAll().size();
        // set the field null
        droitAide.setIsActif(null);

        // Create the DroitAide, which fails.

        restDroitAideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitAide)))
            .andExpect(status().isBadRequest());

        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitAideRepository.findAll().size();
        // set the field null
        droitAide.setAnne(null);

        // Create the DroitAide, which fails.

        restDroitAideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitAide)))
            .andExpect(status().isBadRequest());

        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOuvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = droitAideRepository.findAll().size();
        // set the field null
        droitAide.setDateOuverture(null);

        // Create the DroitAide, which fails.

        restDroitAideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitAide)))
            .andExpect(status().isBadRequest());

        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDroitAides() throws Exception {
        // Initialize the database
        droitAideRepository.saveAndFlush(droitAide);

        // Get all the droitAideList
        restDroitAideMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(droitAide.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())));
    }

    @Test
    @Transactional
    void getDroitAide() throws Exception {
        // Initialize the database
        droitAideRepository.saveAndFlush(droitAide);

        // Get the droitAide
        restDroitAideMockMvc
            .perform(get(ENTITY_API_URL_ID, droitAide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(droitAide.getId().intValue()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateFermeture").value(DEFAULT_DATE_FERMETURE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDroitAide() throws Exception {
        // Get the droitAide
        restDroitAideMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDroitAide() throws Exception {
        // Initialize the database
        droitAideRepository.saveAndFlush(droitAide);

        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();

        // Update the droitAide
        DroitAide updatedDroitAide = droitAideRepository.findById(droitAide.getId()).get();
        // Disconnect from session so that the updates on updatedDroitAide are not directly saved in db
        em.detach(updatedDroitAide);
        updatedDroitAide
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitAideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDroitAide.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDroitAide))
            )
            .andExpect(status().isOk());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
        DroitAide testDroitAide = droitAideList.get(droitAideList.size() - 1);
        assertThat(testDroitAide.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitAide.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitAide.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitAide.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void putNonExistingDroitAide() throws Exception {
        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();
        droitAide.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitAideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droitAide.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitAide))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDroitAide() throws Exception {
        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();
        droitAide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitAideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droitAide))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDroitAide() throws Exception {
        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();
        droitAide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitAideMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droitAide)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDroitAideWithPatch() throws Exception {
        // Initialize the database
        droitAideRepository.saveAndFlush(droitAide);

        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();

        // Update the droitAide using partial update
        DroitAide partialUpdatedDroitAide = new DroitAide();
        partialUpdatedDroitAide.setId(droitAide.getId());

        partialUpdatedDroitAide.isActif(UPDATED_IS_ACTIF).dateOuverture(UPDATED_DATE_OUVERTURE);

        restDroitAideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitAide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitAide))
            )
            .andExpect(status().isOk());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
        DroitAide testDroitAide = droitAideList.get(droitAideList.size() - 1);
        assertThat(testDroitAide.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitAide.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testDroitAide.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitAide.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void fullUpdateDroitAideWithPatch() throws Exception {
        // Initialize the database
        droitAideRepository.saveAndFlush(droitAide);

        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();

        // Update the droitAide using partial update
        DroitAide partialUpdatedDroitAide = new DroitAide();
        partialUpdatedDroitAide.setId(droitAide.getId());

        partialUpdatedDroitAide
            .isActif(UPDATED_IS_ACTIF)
            .anne(UPDATED_ANNE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE);

        restDroitAideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDroitAide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDroitAide))
            )
            .andExpect(status().isOk());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
        DroitAide testDroitAide = droitAideList.get(droitAideList.size() - 1);
        assertThat(testDroitAide.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testDroitAide.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testDroitAide.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testDroitAide.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
    }

    @Test
    @Transactional
    void patchNonExistingDroitAide() throws Exception {
        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();
        droitAide.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroitAideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, droitAide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitAide))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDroitAide() throws Exception {
        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();
        droitAide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitAideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droitAide))
            )
            .andExpect(status().isBadRequest());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDroitAide() throws Exception {
        int databaseSizeBeforeUpdate = droitAideRepository.findAll().size();
        droitAide.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroitAideMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(droitAide))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DroitAide in the database
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDroitAide() throws Exception {
        // Initialize the database
        droitAideRepository.saveAndFlush(droitAide);

        int databaseSizeBeforeDelete = droitAideRepository.findAll().size();

        // Delete the droitAide
        restDroitAideMockMvc
            .perform(delete(ENTITY_API_URL_ID, droitAide.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DroitAide> droitAideList = droitAideRepository.findAll();
        assertThat(droitAideList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
