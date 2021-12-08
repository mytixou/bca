package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.StrategiePchE;
import fr.tixou.bca.repository.StrategiePchERepository;
import fr.tixou.bca.service.StrategiePchEService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StrategiePchEResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StrategiePchEResourceIT {

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_SALAIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_COTISATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_COTISATIONS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NB_HEURE_SALAIRE_PLAFOND = new BigDecimal(1);
    private static final BigDecimal UPDATED_NB_HEURE_SALAIRE_PLAFOND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_SALAIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_COTISATIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_COTISATIONS = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/strategie-pch-es";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategiePchERepository strategiePchERepository;

    @Mock
    private StrategiePchERepository strategiePchERepositoryMock;

    @Mock
    private StrategiePchEService strategiePchEServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategiePchEMockMvc;

    private StrategiePchE strategiePchE;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategiePchE createEntity(EntityManager em) {
        StrategiePchE strategiePchE = new StrategiePchE()
            .isActif(DEFAULT_IS_ACTIF)
            .dateMensuelleDebutValidite(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE)
            .anne(DEFAULT_ANNE)
            .mois(DEFAULT_MOIS)
            .montantPlafondSalaire(DEFAULT_MONTANT_PLAFOND_SALAIRE)
            .montantPlafondCotisations(DEFAULT_MONTANT_PLAFOND_COTISATIONS)
            .montantPlafondSalairePlus(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS)
            .montantPlafondCotisationsPlus(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS)
            .nbHeureSalairePlafond(DEFAULT_NB_HEURE_SALAIRE_PLAFOND)
            .tauxSalaire(DEFAULT_TAUX_SALAIRE)
            .tauxCotisations(DEFAULT_TAUX_COTISATIONS);
        return strategiePchE;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategiePchE createUpdatedEntity(EntityManager em) {
        StrategiePchE strategiePchE = new StrategiePchE()
            .isActif(UPDATED_IS_ACTIF)
            .dateMensuelleDebutValidite(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .montantPlafondCotisations(UPDATED_MONTANT_PLAFOND_COTISATIONS)
            .montantPlafondSalairePlus(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS)
            .montantPlafondCotisationsPlus(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS)
            .nbHeureSalairePlafond(UPDATED_NB_HEURE_SALAIRE_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS);
        return strategiePchE;
    }

    @BeforeEach
    public void initTest() {
        strategiePchE = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategiePchE() throws Exception {
        int databaseSizeBeforeCreate = strategiePchERepository.findAll().size();
        // Create the StrategiePchE
        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isCreated());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeCreate + 1);
        StrategiePchE testStrategiePchE = strategiePchEList.get(strategiePchEList.size() - 1);
        assertThat(testStrategiePchE.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategiePchE.getDateMensuelleDebutValidite()).isEqualTo(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePchE.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategiePchE.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategiePchE.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePchE.getMontantPlafondCotisations()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePchE.getMontantPlafondSalairePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePchE.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePchE.getNbHeureSalairePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePchE.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testStrategiePchE.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void createStrategiePchEWithExistingId() throws Exception {
        // Create the StrategiePchE with an existing ID
        strategiePchE.setId(1L);

        int databaseSizeBeforeCreate = strategiePchERepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setIsActif(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateMensuelleDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setDateMensuelleDebutValidite(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setAnne(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setMois(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setMontantPlafondSalaire(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setMontantPlafondCotisations(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalairePlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setMontantPlafondSalairePlus(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondCotisationsPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setMontantPlafondCotisationsPlus(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeureSalairePlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setNbHeureSalairePlafond(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setTauxSalaire(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchERepository.findAll().size();
        // set the field null
        strategiePchE.setTauxCotisations(null);

        // Create the StrategiePchE, which fails.

        restStrategiePchEMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isBadRequest());

        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategiePchES() throws Exception {
        // Initialize the database
        strategiePchERepository.saveAndFlush(strategiePchE);

        // Get all the strategiePchEList
        restStrategiePchEMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategiePchE.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateMensuelleDebutValidite").value(hasItem(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].montantPlafondSalaire").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_SALAIRE))))
            .andExpect(jsonPath("$.[*].montantPlafondCotisations").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_COTISATIONS))))
            .andExpect(jsonPath("$.[*].montantPlafondSalairePlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS))))
            .andExpect(jsonPath("$.[*].montantPlafondCotisationsPlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS))))
            .andExpect(jsonPath("$.[*].nbHeureSalairePlafond").value(hasItem(sameNumber(DEFAULT_NB_HEURE_SALAIRE_PLAFOND))))
            .andExpect(jsonPath("$.[*].tauxSalaire").value(hasItem(sameNumber(DEFAULT_TAUX_SALAIRE))))
            .andExpect(jsonPath("$.[*].tauxCotisations").value(hasItem(sameNumber(DEFAULT_TAUX_COTISATIONS))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStrategiePchESWithEagerRelationshipsIsEnabled() throws Exception {
        when(strategiePchEServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategiePchEMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategiePchEServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStrategiePchESWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(strategiePchEServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategiePchEMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategiePchEServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStrategiePchE() throws Exception {
        // Initialize the database
        strategiePchERepository.saveAndFlush(strategiePchE);

        // Get the strategiePchE
        restStrategiePchEMockMvc
            .perform(get(ENTITY_API_URL_ID, strategiePchE.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategiePchE.getId().intValue()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateMensuelleDebutValidite").value(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE.toString()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.montantPlafondSalaire").value(sameNumber(DEFAULT_MONTANT_PLAFOND_SALAIRE)))
            .andExpect(jsonPath("$.montantPlafondCotisations").value(sameNumber(DEFAULT_MONTANT_PLAFOND_COTISATIONS)))
            .andExpect(jsonPath("$.montantPlafondSalairePlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS)))
            .andExpect(jsonPath("$.montantPlafondCotisationsPlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS)))
            .andExpect(jsonPath("$.nbHeureSalairePlafond").value(sameNumber(DEFAULT_NB_HEURE_SALAIRE_PLAFOND)))
            .andExpect(jsonPath("$.tauxSalaire").value(sameNumber(DEFAULT_TAUX_SALAIRE)))
            .andExpect(jsonPath("$.tauxCotisations").value(sameNumber(DEFAULT_TAUX_COTISATIONS)));
    }

    @Test
    @Transactional
    void getNonExistingStrategiePchE() throws Exception {
        // Get the strategiePchE
        restStrategiePchEMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrategiePchE() throws Exception {
        // Initialize the database
        strategiePchERepository.saveAndFlush(strategiePchE);

        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();

        // Update the strategiePchE
        StrategiePchE updatedStrategiePchE = strategiePchERepository.findById(strategiePchE.getId()).get();
        // Disconnect from session so that the updates on updatedStrategiePchE are not directly saved in db
        em.detach(updatedStrategiePchE);
        updatedStrategiePchE
            .isActif(UPDATED_IS_ACTIF)
            .dateMensuelleDebutValidite(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .montantPlafondCotisations(UPDATED_MONTANT_PLAFOND_COTISATIONS)
            .montantPlafondSalairePlus(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS)
            .montantPlafondCotisationsPlus(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS)
            .nbHeureSalairePlafond(UPDATED_NB_HEURE_SALAIRE_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS);

        restStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategiePchE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategiePchE))
            )
            .andExpect(status().isOk());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
        StrategiePchE testStrategiePchE = strategiePchEList.get(strategiePchEList.size() - 1);
        assertThat(testStrategiePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategiePchE.getDateMensuelleDebutValidite()).isEqualTo(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePchE.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategiePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategiePchE.getMontantPlafondSalaire()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePchE.getMontantPlafondCotisations()).isEqualTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePchE.getMontantPlafondSalairePlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePchE.getMontantPlafondCotisationsPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePchE.getNbHeureSalairePlafond()).isEqualTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePchE.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategiePchE.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void putNonExistingStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();
        strategiePchE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategiePchE.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();
        strategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchEMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();
        strategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchEMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePchE)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategiePchEWithPatch() throws Exception {
        // Initialize the database
        strategiePchERepository.saveAndFlush(strategiePchE);

        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();

        // Update the strategiePchE using partial update
        StrategiePchE partialUpdatedStrategiePchE = new StrategiePchE();
        partialUpdatedStrategiePchE.setId(strategiePchE.getId());

        partialUpdatedStrategiePchE
            .isActif(UPDATED_IS_ACTIF)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .montantPlafondCotisations(UPDATED_MONTANT_PLAFOND_COTISATIONS)
            .nbHeureSalairePlafond(UPDATED_NB_HEURE_SALAIRE_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE);

        restStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategiePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategiePchE))
            )
            .andExpect(status().isOk());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
        StrategiePchE testStrategiePchE = strategiePchEList.get(strategiePchEList.size() - 1);
        assertThat(testStrategiePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategiePchE.getDateMensuelleDebutValidite()).isEqualTo(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePchE.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategiePchE.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategiePchE.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePchE.getMontantPlafondCotisations()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePchE.getMontantPlafondSalairePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePchE.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePchE.getNbHeureSalairePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePchE.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategiePchE.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void fullUpdateStrategiePchEWithPatch() throws Exception {
        // Initialize the database
        strategiePchERepository.saveAndFlush(strategiePchE);

        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();

        // Update the strategiePchE using partial update
        StrategiePchE partialUpdatedStrategiePchE = new StrategiePchE();
        partialUpdatedStrategiePchE.setId(strategiePchE.getId());

        partialUpdatedStrategiePchE
            .isActif(UPDATED_IS_ACTIF)
            .dateMensuelleDebutValidite(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .montantPlafondCotisations(UPDATED_MONTANT_PLAFOND_COTISATIONS)
            .montantPlafondSalairePlus(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS)
            .montantPlafondCotisationsPlus(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS)
            .nbHeureSalairePlafond(UPDATED_NB_HEURE_SALAIRE_PLAFOND)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS);

        restStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategiePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategiePchE))
            )
            .andExpect(status().isOk());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
        StrategiePchE testStrategiePchE = strategiePchEList.get(strategiePchEList.size() - 1);
        assertThat(testStrategiePchE.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategiePchE.getDateMensuelleDebutValidite()).isEqualTo(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePchE.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategiePchE.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategiePchE.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePchE.getMontantPlafondCotisations()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePchE.getMontantPlafondSalairePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePchE.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePchE.getNbHeureSalairePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePchE.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategiePchE.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void patchNonExistingStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();
        strategiePchE.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategiePchE.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();
        strategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategiePchE))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategiePchE() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchERepository.findAll().size();
        strategiePchE.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchEMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strategiePchE))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategiePchE in the database
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategiePchE() throws Exception {
        // Initialize the database
        strategiePchERepository.saveAndFlush(strategiePchE);

        int databaseSizeBeforeDelete = strategiePchERepository.findAll().size();

        // Delete the strategiePchE
        restStrategiePchEMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategiePchE.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategiePchE> strategiePchEList = strategiePchERepository.findAll();
        assertThat(strategiePchEList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
