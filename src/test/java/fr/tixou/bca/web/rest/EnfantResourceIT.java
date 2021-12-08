package fr.tixou.bca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.Enfant;
import fr.tixou.bca.repository.EnfantRepository;
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
 * Integration tests for the {@link EnfantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnfantResourceIT {

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_INSCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INSCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RESILIATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RESILIATION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/enfants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnfantMockMvc;

    private Enfant enfant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createEntity(EntityManager em) {
        Enfant enfant = new Enfant()
            .isActif(DEFAULT_IS_ACTIF)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .dateInscription(DEFAULT_DATE_INSCRIPTION)
            .dateResiliation(DEFAULT_DATE_RESILIATION);
        return enfant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enfant createUpdatedEntity(EntityManager em) {
        Enfant enfant = new Enfant()
            .isActif(UPDATED_IS_ACTIF)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .dateResiliation(UPDATED_DATE_RESILIATION);
        return enfant;
    }

    @BeforeEach
    public void initTest() {
        enfant = createEntity(em);
    }

    @Test
    @Transactional
    void createEnfant() throws Exception {
        int databaseSizeBeforeCreate = enfantRepository.findAll().size();
        // Create the Enfant
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isCreated());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeCreate + 1);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEnfant.getDateInscription()).isEqualTo(DEFAULT_DATE_INSCRIPTION);
        assertThat(testEnfant.getDateResiliation()).isEqualTo(DEFAULT_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void createEnfantWithExistingId() throws Exception {
        // Create the Enfant with an existing ID
        enfant.setId(1L);

        int databaseSizeBeforeCreate = enfantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfantRepository.findAll().size();
        // set the field null
        enfant.setIsActif(null);

        // Create the Enfant, which fails.

        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfantRepository.findAll().size();
        // set the field null
        enfant.setDateNaissance(null);

        // Create the Enfant, which fails.

        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateInscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfantRepository.findAll().size();
        // set the field null
        enfant.setDateInscription(null);

        // Create the Enfant, which fails.

        restEnfantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isBadRequest());

        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEnfants() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        // Get all the enfantList
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enfant.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].dateInscription").value(hasItem(DEFAULT_DATE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateResiliation").value(hasItem(DEFAULT_DATE_RESILIATION.toString())));
    }

    @Test
    @Transactional
    void getEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        // Get the enfant
        restEnfantMockMvc
            .perform(get(ENTITY_API_URL_ID, enfant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enfant.getId().intValue()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.dateInscription").value(DEFAULT_DATE_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.dateResiliation").value(DEFAULT_DATE_RESILIATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEnfant() throws Exception {
        // Get the enfant
        restEnfantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant
        Enfant updatedEnfant = enfantRepository.findById(enfant.getId()).get();
        // Disconnect from session so that the updates on updatedEnfant are not directly saved in db
        em.detach(updatedEnfant);
        updatedEnfant
            .isActif(UPDATED_IS_ACTIF)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .dateResiliation(UPDATED_DATE_RESILIATION);

        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnfant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEnfant.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testEnfant.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void putNonExistingEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enfant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant.isActif(UPDATED_IS_ACTIF).dateInscription(UPDATED_DATE_INSCRIPTION).dateResiliation(UPDATED_DATE_RESILIATION);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEnfant.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testEnfant.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void fullUpdateEnfantWithPatch() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();

        // Update the enfant using partial update
        Enfant partialUpdatedEnfant = new Enfant();
        partialUpdatedEnfant.setId(enfant.getId());

        partialUpdatedEnfant
            .isActif(UPDATED_IS_ACTIF)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .dateResiliation(UPDATED_DATE_RESILIATION);

        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnfant))
            )
            .andExpect(status().isOk());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
        Enfant testEnfant = enfantList.get(enfantList.size() - 1);
        assertThat(testEnfant.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testEnfant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEnfant.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testEnfant.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void patchNonExistingEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enfant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enfant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnfant() throws Exception {
        int databaseSizeBeforeUpdate = enfantRepository.findAll().size();
        enfant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnfantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enfant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enfant in the database
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnfant() throws Exception {
        // Initialize the database
        enfantRepository.saveAndFlush(enfant);

        int databaseSizeBeforeDelete = enfantRepository.findAll().size();

        // Delete the enfant
        restEnfantMockMvc
            .perform(delete(ENTITY_API_URL_ID, enfant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enfant> enfantList = enfantRepository.findAll();
        assertThat(enfantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
