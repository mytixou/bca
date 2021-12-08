package fr.tixou.bca.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.Pec;
import fr.tixou.bca.domain.enumeration.TypeProduit;
import fr.tixou.bca.repository.PecRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PecResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PecResourceIT {

    private static final String DEFAULT_ID_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_ID_PRODUIT = "BBBBBBBBBB";

    private static final TypeProduit DEFAULT_PRODUIT = TypeProduit.CESU;
    private static final TypeProduit UPDATED_PRODUIT = TypeProduit.CESUP;

    private static final Boolean DEFAULT_IS_PLUS = false;
    private static final Boolean UPDATED_IS_PLUS = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_UPDATED = false;
    private static final Boolean UPDATED_IS_UPDATED = true;

    private static final Instant DEFAULT_DATE_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final String DEFAULT_PEC_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PEC_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pecs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PecRepository pecRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPecMockMvc;

    private Pec pec;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pec createEntity(EntityManager em) {
        Pec pec = new Pec()
            .idProduit(DEFAULT_ID_PRODUIT)
            .produit(DEFAULT_PRODUIT)
            .isPlus(DEFAULT_IS_PLUS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .isUpdated(DEFAULT_IS_UPDATED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .isActif(DEFAULT_IS_ACTIF)
            .pecDetails(DEFAULT_PEC_DETAILS);
        return pec;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pec createUpdatedEntity(EntityManager em) {
        Pec pec = new Pec()
            .idProduit(UPDATED_ID_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .isPlus(UPDATED_IS_PLUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .isActif(UPDATED_IS_ACTIF)
            .pecDetails(UPDATED_PEC_DETAILS);
        return pec;
    }

    @BeforeEach
    public void initTest() {
        pec = createEntity(em);
    }

    @Test
    @Transactional
    void createPec() throws Exception {
        int databaseSizeBeforeCreate = pecRepository.findAll().size();
        // Create the Pec
        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isCreated());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeCreate + 1);
        Pec testPec = pecList.get(pecList.size() - 1);
        assertThat(testPec.getIdProduit()).isEqualTo(DEFAULT_ID_PRODUIT);
        assertThat(testPec.getProduit()).isEqualTo(DEFAULT_PRODUIT);
        assertThat(testPec.getIsPlus()).isEqualTo(DEFAULT_IS_PLUS);
        assertThat(testPec.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPec.getIsUpdated()).isEqualTo(DEFAULT_IS_UPDATED);
        assertThat(testPec.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testPec.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testPec.getPecDetails()).isEqualTo(DEFAULT_PEC_DETAILS);
    }

    @Test
    @Transactional
    void createPecWithExistingId() throws Exception {
        // Create the Pec with an existing ID
        pecRepository.saveAndFlush(pec);

        int databaseSizeBeforeCreate = pecRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = pecRepository.findAll().size();
        // set the field null
        pec.setIdProduit(null);

        // Create the Pec, which fails.

        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = pecRepository.findAll().size();
        // set the field null
        pec.setProduit(null);

        // Create the Pec, which fails.

        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pecRepository.findAll().size();
        // set the field null
        pec.setIsPlus(null);

        // Create the Pec, which fails.

        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = pecRepository.findAll().size();
        // set the field null
        pec.setDateCreated(null);

        // Create the Pec, which fails.

        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsUpdatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = pecRepository.findAll().size();
        // set the field null
        pec.setIsUpdated(null);

        // Create the Pec, which fails.

        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = pecRepository.findAll().size();
        // set the field null
        pec.setIsActif(null);

        // Create the Pec, which fails.

        restPecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isBadRequest());

        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPecs() throws Exception {
        // Initialize the database
        pecRepository.saveAndFlush(pec);

        // Get all the pecList
        restPecMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pec.getId().toString())))
            .andExpect(jsonPath("$.[*].idProduit").value(hasItem(DEFAULT_ID_PRODUIT)))
            .andExpect(jsonPath("$.[*].produit").value(hasItem(DEFAULT_PRODUIT.toString())))
            .andExpect(jsonPath("$.[*].isPlus").value(hasItem(DEFAULT_IS_PLUS.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].isUpdated").value(hasItem(DEFAULT_IS_UPDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].pecDetails").value(hasItem(DEFAULT_PEC_DETAILS.toString())));
    }

    @Test
    @Transactional
    void getPec() throws Exception {
        // Initialize the database
        pecRepository.saveAndFlush(pec);

        // Get the pec
        restPecMockMvc
            .perform(get(ENTITY_API_URL_ID, pec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pec.getId().toString()))
            .andExpect(jsonPath("$.idProduit").value(DEFAULT_ID_PRODUIT))
            .andExpect(jsonPath("$.produit").value(DEFAULT_PRODUIT.toString()))
            .andExpect(jsonPath("$.isPlus").value(DEFAULT_IS_PLUS.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.isUpdated").value(DEFAULT_IS_UPDATED.booleanValue()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.pecDetails").value(DEFAULT_PEC_DETAILS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPec() throws Exception {
        // Get the pec
        restPecMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPec() throws Exception {
        // Initialize the database
        pecRepository.saveAndFlush(pec);

        int databaseSizeBeforeUpdate = pecRepository.findAll().size();

        // Update the pec
        Pec updatedPec = pecRepository.findById(pec.getId()).get();
        // Disconnect from session so that the updates on updatedPec are not directly saved in db
        em.detach(updatedPec);
        updatedPec
            .idProduit(UPDATED_ID_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .isPlus(UPDATED_IS_PLUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .isActif(UPDATED_IS_ACTIF)
            .pecDetails(UPDATED_PEC_DETAILS);

        restPecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPec.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPec))
            )
            .andExpect(status().isOk());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
        Pec testPec = pecList.get(pecList.size() - 1);
        assertThat(testPec.getIdProduit()).isEqualTo(UPDATED_ID_PRODUIT);
        assertThat(testPec.getProduit()).isEqualTo(UPDATED_PRODUIT);
        assertThat(testPec.getIsPlus()).isEqualTo(UPDATED_IS_PLUS);
        assertThat(testPec.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPec.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testPec.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testPec.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testPec.getPecDetails()).isEqualTo(UPDATED_PEC_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingPec() throws Exception {
        int databaseSizeBeforeUpdate = pecRepository.findAll().size();
        pec.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pec.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPec() throws Exception {
        int databaseSizeBeforeUpdate = pecRepository.findAll().size();
        pec.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pec))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPec() throws Exception {
        int databaseSizeBeforeUpdate = pecRepository.findAll().size();
        pec.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPecMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePecWithPatch() throws Exception {
        // Initialize the database
        pecRepository.saveAndFlush(pec);

        int databaseSizeBeforeUpdate = pecRepository.findAll().size();

        // Update the pec using partial update
        Pec partialUpdatedPec = new Pec();
        partialUpdatedPec.setId(pec.getId());

        partialUpdatedPec
            .idProduit(UPDATED_ID_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .isPlus(UPDATED_IS_PLUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .pecDetails(UPDATED_PEC_DETAILS);

        restPecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPec.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPec))
            )
            .andExpect(status().isOk());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
        Pec testPec = pecList.get(pecList.size() - 1);
        assertThat(testPec.getIdProduit()).isEqualTo(UPDATED_ID_PRODUIT);
        assertThat(testPec.getProduit()).isEqualTo(UPDATED_PRODUIT);
        assertThat(testPec.getIsPlus()).isEqualTo(UPDATED_IS_PLUS);
        assertThat(testPec.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPec.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testPec.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testPec.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testPec.getPecDetails()).isEqualTo(UPDATED_PEC_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdatePecWithPatch() throws Exception {
        // Initialize the database
        pecRepository.saveAndFlush(pec);

        int databaseSizeBeforeUpdate = pecRepository.findAll().size();

        // Update the pec using partial update
        Pec partialUpdatedPec = new Pec();
        partialUpdatedPec.setId(pec.getId());

        partialUpdatedPec
            .idProduit(UPDATED_ID_PRODUIT)
            .produit(UPDATED_PRODUIT)
            .isPlus(UPDATED_IS_PLUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .isUpdated(UPDATED_IS_UPDATED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .isActif(UPDATED_IS_ACTIF)
            .pecDetails(UPDATED_PEC_DETAILS);

        restPecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPec.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPec))
            )
            .andExpect(status().isOk());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
        Pec testPec = pecList.get(pecList.size() - 1);
        assertThat(testPec.getIdProduit()).isEqualTo(UPDATED_ID_PRODUIT);
        assertThat(testPec.getProduit()).isEqualTo(UPDATED_PRODUIT);
        assertThat(testPec.getIsPlus()).isEqualTo(UPDATED_IS_PLUS);
        assertThat(testPec.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPec.getIsUpdated()).isEqualTo(UPDATED_IS_UPDATED);
        assertThat(testPec.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testPec.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testPec.getPecDetails()).isEqualTo(UPDATED_PEC_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingPec() throws Exception {
        int databaseSizeBeforeUpdate = pecRepository.findAll().size();
        pec.setId(UUID.randomUUID());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pec.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pec))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPec() throws Exception {
        int databaseSizeBeforeUpdate = pecRepository.findAll().size();
        pec.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pec))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPec() throws Exception {
        int databaseSizeBeforeUpdate = pecRepository.findAll().size();
        pec.setId(UUID.randomUUID());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPecMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pec)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pec in the database
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePec() throws Exception {
        // Initialize the database
        pecRepository.saveAndFlush(pec);

        int databaseSizeBeforeDelete = pecRepository.findAll().size();

        // Delete the pec
        restPecMockMvc
            .perform(delete(ENTITY_API_URL_ID, pec.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pec> pecList = pecRepository.findAll();
        assertThat(pecList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
