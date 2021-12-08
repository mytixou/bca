package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.SoldePch;
import fr.tixou.bca.repository.SoldePchRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link SoldePchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SoldePchResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final Boolean DEFAULT_IS_DERNIER = false;
    private static final Boolean UPDATED_IS_DERNIER = true;

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final BigDecimal DEFAULT_CONSO_MONTANT_PCH_COTISATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSO_MONTANT_PCH_COTISATIONS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONSO_MONTANT_PCH_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSO_MONTANT_PCH_SALAIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLDE_MONTANT_PCH = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLDE_MONTANT_PCH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONSO_HEURE_PCH = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONSO_HEURE_PCH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SOLDE_HEURE_PCH = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLDE_HEURE_PCH = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/solde-pches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoldePchRepository soldePchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoldePchMockMvc;

    private SoldePch soldePch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldePch createEntity(EntityManager em) {
        SoldePch soldePch = new SoldePch()
            .date(DEFAULT_DATE)
            .isActif(DEFAULT_IS_ACTIF)
            .isDernier(DEFAULT_IS_DERNIER)
            .annee(DEFAULT_ANNEE)
            .mois(DEFAULT_MOIS)
            .consoMontantPchCotisations(DEFAULT_CONSO_MONTANT_PCH_COTISATIONS)
            .consoMontantPchSalaire(DEFAULT_CONSO_MONTANT_PCH_SALAIRE)
            .soldeMontantPch(DEFAULT_SOLDE_MONTANT_PCH)
            .consoHeurePch(DEFAULT_CONSO_HEURE_PCH)
            .soldeHeurePch(DEFAULT_SOLDE_HEURE_PCH);
        return soldePch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldePch createUpdatedEntity(EntityManager em) {
        SoldePch soldePch = new SoldePch()
            .date(UPDATED_DATE)
            .isActif(UPDATED_IS_ACTIF)
            .isDernier(UPDATED_IS_DERNIER)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchCotisations(UPDATED_CONSO_MONTANT_PCH_COTISATIONS)
            .consoMontantPchSalaire(UPDATED_CONSO_MONTANT_PCH_SALAIRE)
            .soldeMontantPch(UPDATED_SOLDE_MONTANT_PCH)
            .consoHeurePch(UPDATED_CONSO_HEURE_PCH)
            .soldeHeurePch(UPDATED_SOLDE_HEURE_PCH);
        return soldePch;
    }

    @BeforeEach
    public void initTest() {
        soldePch = createEntity(em);
    }

    @Test
    @Transactional
    void createSoldePch() throws Exception {
        int databaseSizeBeforeCreate = soldePchRepository.findAll().size();
        // Create the SoldePch
        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isCreated());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeCreate + 1);
        SoldePch testSoldePch = soldePchList.get(soldePchList.size() - 1);
        assertThat(testSoldePch.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSoldePch.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testSoldePch.getIsDernier()).isEqualTo(DEFAULT_IS_DERNIER);
        assertThat(testSoldePch.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldePch.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testSoldePch.getConsoMontantPchCotisations()).isEqualByComparingTo(DEFAULT_CONSO_MONTANT_PCH_COTISATIONS);
        assertThat(testSoldePch.getConsoMontantPchSalaire()).isEqualByComparingTo(DEFAULT_CONSO_MONTANT_PCH_SALAIRE);
        assertThat(testSoldePch.getSoldeMontantPch()).isEqualByComparingTo(DEFAULT_SOLDE_MONTANT_PCH);
        assertThat(testSoldePch.getConsoHeurePch()).isEqualByComparingTo(DEFAULT_CONSO_HEURE_PCH);
        assertThat(testSoldePch.getSoldeHeurePch()).isEqualByComparingTo(DEFAULT_SOLDE_HEURE_PCH);
    }

    @Test
    @Transactional
    void createSoldePchWithExistingId() throws Exception {
        // Create the SoldePch with an existing ID
        soldePch.setId(1L);

        int databaseSizeBeforeCreate = soldePchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setDate(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setIsActif(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsDernierIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setIsDernier(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneeIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setAnnee(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setMois(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsoMontantPchCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setConsoMontantPchCotisations(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsoMontantPchSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setConsoMontantPchSalaire(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoldeMontantPchIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setSoldeMontantPch(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsoHeurePchIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setConsoHeurePch(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSoldeHeurePchIsRequired() throws Exception {
        int databaseSizeBeforeTest = soldePchRepository.findAll().size();
        // set the field null
        soldePch.setSoldeHeurePch(null);

        // Create the SoldePch, which fails.

        restSoldePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isBadRequest());

        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSoldePches() throws Exception {
        // Initialize the database
        soldePchRepository.saveAndFlush(soldePch);

        // Get all the soldePchList
        restSoldePchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soldePch.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].isDernier").value(hasItem(DEFAULT_IS_DERNIER.booleanValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].consoMontantPchCotisations").value(hasItem(sameNumber(DEFAULT_CONSO_MONTANT_PCH_COTISATIONS))))
            .andExpect(jsonPath("$.[*].consoMontantPchSalaire").value(hasItem(sameNumber(DEFAULT_CONSO_MONTANT_PCH_SALAIRE))))
            .andExpect(jsonPath("$.[*].soldeMontantPch").value(hasItem(sameNumber(DEFAULT_SOLDE_MONTANT_PCH))))
            .andExpect(jsonPath("$.[*].consoHeurePch").value(hasItem(sameNumber(DEFAULT_CONSO_HEURE_PCH))))
            .andExpect(jsonPath("$.[*].soldeHeurePch").value(hasItem(sameNumber(DEFAULT_SOLDE_HEURE_PCH))));
    }

    @Test
    @Transactional
    void getSoldePch() throws Exception {
        // Initialize the database
        soldePchRepository.saveAndFlush(soldePch);

        // Get the soldePch
        restSoldePchMockMvc
            .perform(get(ENTITY_API_URL_ID, soldePch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soldePch.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.isDernier").value(DEFAULT_IS_DERNIER.booleanValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.consoMontantPchCotisations").value(sameNumber(DEFAULT_CONSO_MONTANT_PCH_COTISATIONS)))
            .andExpect(jsonPath("$.consoMontantPchSalaire").value(sameNumber(DEFAULT_CONSO_MONTANT_PCH_SALAIRE)))
            .andExpect(jsonPath("$.soldeMontantPch").value(sameNumber(DEFAULT_SOLDE_MONTANT_PCH)))
            .andExpect(jsonPath("$.consoHeurePch").value(sameNumber(DEFAULT_CONSO_HEURE_PCH)))
            .andExpect(jsonPath("$.soldeHeurePch").value(sameNumber(DEFAULT_SOLDE_HEURE_PCH)));
    }

    @Test
    @Transactional
    void getNonExistingSoldePch() throws Exception {
        // Get the soldePch
        restSoldePchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSoldePch() throws Exception {
        // Initialize the database
        soldePchRepository.saveAndFlush(soldePch);

        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();

        // Update the soldePch
        SoldePch updatedSoldePch = soldePchRepository.findById(soldePch.getId()).get();
        // Disconnect from session so that the updates on updatedSoldePch are not directly saved in db
        em.detach(updatedSoldePch);
        updatedSoldePch
            .date(UPDATED_DATE)
            .isActif(UPDATED_IS_ACTIF)
            .isDernier(UPDATED_IS_DERNIER)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchCotisations(UPDATED_CONSO_MONTANT_PCH_COTISATIONS)
            .consoMontantPchSalaire(UPDATED_CONSO_MONTANT_PCH_SALAIRE)
            .soldeMontantPch(UPDATED_SOLDE_MONTANT_PCH)
            .consoHeurePch(UPDATED_CONSO_HEURE_PCH)
            .soldeHeurePch(UPDATED_SOLDE_HEURE_PCH);

        restSoldePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoldePch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSoldePch))
            )
            .andExpect(status().isOk());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
        SoldePch testSoldePch = soldePchList.get(soldePchList.size() - 1);
        assertThat(testSoldePch.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSoldePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testSoldePch.getIsDernier()).isEqualTo(UPDATED_IS_DERNIER);
        assertThat(testSoldePch.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldePch.getConsoMontantPchCotisations()).isEqualTo(UPDATED_CONSO_MONTANT_PCH_COTISATIONS);
        assertThat(testSoldePch.getConsoMontantPchSalaire()).isEqualTo(UPDATED_CONSO_MONTANT_PCH_SALAIRE);
        assertThat(testSoldePch.getSoldeMontantPch()).isEqualTo(UPDATED_SOLDE_MONTANT_PCH);
        assertThat(testSoldePch.getConsoHeurePch()).isEqualTo(UPDATED_CONSO_HEURE_PCH);
        assertThat(testSoldePch.getSoldeHeurePch()).isEqualTo(UPDATED_SOLDE_HEURE_PCH);
    }

    @Test
    @Transactional
    void putNonExistingSoldePch() throws Exception {
        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();
        soldePch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soldePch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoldePch() throws Exception {
        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();
        soldePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoldePch() throws Exception {
        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();
        soldePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoldePchWithPatch() throws Exception {
        // Initialize the database
        soldePchRepository.saveAndFlush(soldePch);

        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();

        // Update the soldePch using partial update
        SoldePch partialUpdatedSoldePch = new SoldePch();
        partialUpdatedSoldePch.setId(soldePch.getId());

        partialUpdatedSoldePch
            .isActif(UPDATED_IS_ACTIF)
            .consoMontantPchSalaire(UPDATED_CONSO_MONTANT_PCH_SALAIRE)
            .soldeMontantPch(UPDATED_SOLDE_MONTANT_PCH)
            .consoHeurePch(UPDATED_CONSO_HEURE_PCH);

        restSoldePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldePch))
            )
            .andExpect(status().isOk());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
        SoldePch testSoldePch = soldePchList.get(soldePchList.size() - 1);
        assertThat(testSoldePch.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSoldePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testSoldePch.getIsDernier()).isEqualTo(DEFAULT_IS_DERNIER);
        assertThat(testSoldePch.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldePch.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testSoldePch.getConsoMontantPchCotisations()).isEqualByComparingTo(DEFAULT_CONSO_MONTANT_PCH_COTISATIONS);
        assertThat(testSoldePch.getConsoMontantPchSalaire()).isEqualByComparingTo(UPDATED_CONSO_MONTANT_PCH_SALAIRE);
        assertThat(testSoldePch.getSoldeMontantPch()).isEqualByComparingTo(UPDATED_SOLDE_MONTANT_PCH);
        assertThat(testSoldePch.getConsoHeurePch()).isEqualByComparingTo(UPDATED_CONSO_HEURE_PCH);
        assertThat(testSoldePch.getSoldeHeurePch()).isEqualByComparingTo(DEFAULT_SOLDE_HEURE_PCH);
    }

    @Test
    @Transactional
    void fullUpdateSoldePchWithPatch() throws Exception {
        // Initialize the database
        soldePchRepository.saveAndFlush(soldePch);

        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();

        // Update the soldePch using partial update
        SoldePch partialUpdatedSoldePch = new SoldePch();
        partialUpdatedSoldePch.setId(soldePch.getId());

        partialUpdatedSoldePch
            .date(UPDATED_DATE)
            .isActif(UPDATED_IS_ACTIF)
            .isDernier(UPDATED_IS_DERNIER)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .consoMontantPchCotisations(UPDATED_CONSO_MONTANT_PCH_COTISATIONS)
            .consoMontantPchSalaire(UPDATED_CONSO_MONTANT_PCH_SALAIRE)
            .soldeMontantPch(UPDATED_SOLDE_MONTANT_PCH)
            .consoHeurePch(UPDATED_CONSO_HEURE_PCH)
            .soldeHeurePch(UPDATED_SOLDE_HEURE_PCH);

        restSoldePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldePch))
            )
            .andExpect(status().isOk());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
        SoldePch testSoldePch = soldePchList.get(soldePchList.size() - 1);
        assertThat(testSoldePch.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSoldePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testSoldePch.getIsDernier()).isEqualTo(UPDATED_IS_DERNIER);
        assertThat(testSoldePch.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldePch.getConsoMontantPchCotisations()).isEqualByComparingTo(UPDATED_CONSO_MONTANT_PCH_COTISATIONS);
        assertThat(testSoldePch.getConsoMontantPchSalaire()).isEqualByComparingTo(UPDATED_CONSO_MONTANT_PCH_SALAIRE);
        assertThat(testSoldePch.getSoldeMontantPch()).isEqualByComparingTo(UPDATED_SOLDE_MONTANT_PCH);
        assertThat(testSoldePch.getConsoHeurePch()).isEqualByComparingTo(UPDATED_CONSO_HEURE_PCH);
        assertThat(testSoldePch.getSoldeHeurePch()).isEqualByComparingTo(UPDATED_SOLDE_HEURE_PCH);
    }

    @Test
    @Transactional
    void patchNonExistingSoldePch() throws Exception {
        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();
        soldePch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soldePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoldePch() throws Exception {
        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();
        soldePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoldePch() throws Exception {
        int databaseSizeBeforeUpdate = soldePchRepository.findAll().size();
        soldePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldePchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(soldePch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldePch in the database
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoldePch() throws Exception {
        // Initialize the database
        soldePchRepository.saveAndFlush(soldePch);

        int databaseSizeBeforeDelete = soldePchRepository.findAll().size();

        // Delete the soldePch
        restSoldePchMockMvc
            .perform(delete(ENTITY_API_URL_ID, soldePch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoldePch> soldePchList = soldePchRepository.findAll();
        assertThat(soldePchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
