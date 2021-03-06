package fr.tixou.bca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.Beneficiaire;
import fr.tixou.bca.repository.BeneficiaireRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link BeneficiaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BeneficiaireResourceIT {

    private static final UUID DEFAULT_EXTERNE_ID = UUID.randomUUID();
    private static final UUID UPDATED_EXTERNE_ID = UUID.randomUUID();

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_DESACTIVATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DESACTIVATION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_INSCRIT = false;
    private static final Boolean UPDATED_IS_INSCRIT = true;

    private static final LocalDate DEFAULT_DATE_INSCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INSCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RESILIATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RESILIATION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/beneficiaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BeneficiaireRepository beneficiaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeneficiaireMockMvc;

    private Beneficiaire beneficiaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiaire createEntity(EntityManager em) {
        Beneficiaire beneficiaire = new Beneficiaire()
            .externeId(DEFAULT_EXTERNE_ID)
            .isActif(DEFAULT_IS_ACTIF)
            .dateDesactivation(DEFAULT_DATE_DESACTIVATION)
            .isInscrit(DEFAULT_IS_INSCRIT)
            .dateInscription(DEFAULT_DATE_INSCRIPTION)
            .dateResiliation(DEFAULT_DATE_RESILIATION);
        return beneficiaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiaire createUpdatedEntity(EntityManager em) {
        Beneficiaire beneficiaire = new Beneficiaire()
            .externeId(UPDATED_EXTERNE_ID)
            .isActif(UPDATED_IS_ACTIF)
            .dateDesactivation(UPDATED_DATE_DESACTIVATION)
            .isInscrit(UPDATED_IS_INSCRIT)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .dateResiliation(UPDATED_DATE_RESILIATION);
        return beneficiaire;
    }

    @BeforeEach
    public void initTest() {
        beneficiaire = createEntity(em);
    }

    @Test
    @Transactional
    void createBeneficiaire() throws Exception {
        int databaseSizeBeforeCreate = beneficiaireRepository.findAll().size();
        // Create the Beneficiaire
        restBeneficiaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isCreated());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiaire testBeneficiaire = beneficiaireList.get(beneficiaireList.size() - 1);
        assertThat(testBeneficiaire.getExterneId()).isEqualTo(DEFAULT_EXTERNE_ID);
        assertThat(testBeneficiaire.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testBeneficiaire.getDateDesactivation()).isEqualTo(DEFAULT_DATE_DESACTIVATION);
        assertThat(testBeneficiaire.getIsInscrit()).isEqualTo(DEFAULT_IS_INSCRIT);
        assertThat(testBeneficiaire.getDateInscription()).isEqualTo(DEFAULT_DATE_INSCRIPTION);
        assertThat(testBeneficiaire.getDateResiliation()).isEqualTo(DEFAULT_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void createBeneficiaireWithExistingId() throws Exception {
        // Create the Beneficiaire with an existing ID
        beneficiaire.setId(1L);

        int databaseSizeBeforeCreate = beneficiaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkExterneIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaireRepository.findAll().size();
        // set the field null
        beneficiaire.setExterneId(null);

        // Create the Beneficiaire, which fails.

        restBeneficiaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaireRepository.findAll().size();
        // set the field null
        beneficiaire.setIsActif(null);

        // Create the Beneficiaire, which fails.

        restBeneficiaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsInscritIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaireRepository.findAll().size();
        // set the field null
        beneficiaire.setIsInscrit(null);

        // Create the Beneficiaire, which fails.

        restBeneficiaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateInscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaireRepository.findAll().size();
        // set the field null
        beneficiaire.setDateInscription(null);

        // Create the Beneficiaire, which fails.

        restBeneficiaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBeneficiaires() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        // Get all the beneficiaireList
        restBeneficiaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].externeId").value(hasItem(DEFAULT_EXTERNE_ID.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateDesactivation").value(hasItem(DEFAULT_DATE_DESACTIVATION.toString())))
            .andExpect(jsonPath("$.[*].isInscrit").value(hasItem(DEFAULT_IS_INSCRIT.booleanValue())))
            .andExpect(jsonPath("$.[*].dateInscription").value(hasItem(DEFAULT_DATE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateResiliation").value(hasItem(DEFAULT_DATE_RESILIATION.toString())));
    }

    @Test
    @Transactional
    void getBeneficiaire() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        // Get the beneficiaire
        restBeneficiaireMockMvc
            .perform(get(ENTITY_API_URL_ID, beneficiaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiaire.getId().intValue()))
            .andExpect(jsonPath("$.externeId").value(DEFAULT_EXTERNE_ID.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateDesactivation").value(DEFAULT_DATE_DESACTIVATION.toString()))
            .andExpect(jsonPath("$.isInscrit").value(DEFAULT_IS_INSCRIT.booleanValue()))
            .andExpect(jsonPath("$.dateInscription").value(DEFAULT_DATE_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.dateResiliation").value(DEFAULT_DATE_RESILIATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBeneficiaire() throws Exception {
        // Get the beneficiaire
        restBeneficiaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBeneficiaire() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();

        // Update the beneficiaire
        Beneficiaire updatedBeneficiaire = beneficiaireRepository.findById(beneficiaire.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiaire are not directly saved in db
        em.detach(updatedBeneficiaire);
        updatedBeneficiaire
            .externeId(UPDATED_EXTERNE_ID)
            .isActif(UPDATED_IS_ACTIF)
            .dateDesactivation(UPDATED_DATE_DESACTIVATION)
            .isInscrit(UPDATED_IS_INSCRIT)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .dateResiliation(UPDATED_DATE_RESILIATION);

        restBeneficiaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBeneficiaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBeneficiaire))
            )
            .andExpect(status().isOk());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
        Beneficiaire testBeneficiaire = beneficiaireList.get(beneficiaireList.size() - 1);
        assertThat(testBeneficiaire.getExterneId()).isEqualTo(UPDATED_EXTERNE_ID);
        assertThat(testBeneficiaire.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testBeneficiaire.getDateDesactivation()).isEqualTo(UPDATED_DATE_DESACTIVATION);
        assertThat(testBeneficiaire.getIsInscrit()).isEqualTo(UPDATED_IS_INSCRIT);
        assertThat(testBeneficiaire.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testBeneficiaire.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void putNonExistingBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();
        beneficiaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, beneficiaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beneficiaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();
        beneficiaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(beneficiaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();
        beneficiaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBeneficiaireWithPatch() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();

        // Update the beneficiaire using partial update
        Beneficiaire partialUpdatedBeneficiaire = new Beneficiaire();
        partialUpdatedBeneficiaire.setId(beneficiaire.getId());

        partialUpdatedBeneficiaire.externeId(UPDATED_EXTERNE_ID).dateInscription(UPDATED_DATE_INSCRIPTION);

        restBeneficiaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeneficiaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBeneficiaire))
            )
            .andExpect(status().isOk());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
        Beneficiaire testBeneficiaire = beneficiaireList.get(beneficiaireList.size() - 1);
        assertThat(testBeneficiaire.getExterneId()).isEqualTo(UPDATED_EXTERNE_ID);
        assertThat(testBeneficiaire.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testBeneficiaire.getDateDesactivation()).isEqualTo(DEFAULT_DATE_DESACTIVATION);
        assertThat(testBeneficiaire.getIsInscrit()).isEqualTo(DEFAULT_IS_INSCRIT);
        assertThat(testBeneficiaire.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testBeneficiaire.getDateResiliation()).isEqualTo(DEFAULT_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void fullUpdateBeneficiaireWithPatch() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();

        // Update the beneficiaire using partial update
        Beneficiaire partialUpdatedBeneficiaire = new Beneficiaire();
        partialUpdatedBeneficiaire.setId(beneficiaire.getId());

        partialUpdatedBeneficiaire
            .externeId(UPDATED_EXTERNE_ID)
            .isActif(UPDATED_IS_ACTIF)
            .dateDesactivation(UPDATED_DATE_DESACTIVATION)
            .isInscrit(UPDATED_IS_INSCRIT)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .dateResiliation(UPDATED_DATE_RESILIATION);

        restBeneficiaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBeneficiaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBeneficiaire))
            )
            .andExpect(status().isOk());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
        Beneficiaire testBeneficiaire = beneficiaireList.get(beneficiaireList.size() - 1);
        assertThat(testBeneficiaire.getExterneId()).isEqualTo(UPDATED_EXTERNE_ID);
        assertThat(testBeneficiaire.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testBeneficiaire.getDateDesactivation()).isEqualTo(UPDATED_DATE_DESACTIVATION);
        assertThat(testBeneficiaire.getIsInscrit()).isEqualTo(UPDATED_IS_INSCRIT);
        assertThat(testBeneficiaire.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testBeneficiaire.getDateResiliation()).isEqualTo(UPDATED_DATE_RESILIATION);
    }

    @Test
    @Transactional
    void patchNonExistingBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();
        beneficiaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, beneficiaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beneficiaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();
        beneficiaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(beneficiaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();
        beneficiaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBeneficiaireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(beneficiaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBeneficiaire() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        int databaseSizeBeforeDelete = beneficiaireRepository.findAll().size();

        // Delete the beneficiaire
        restBeneficiaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, beneficiaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
