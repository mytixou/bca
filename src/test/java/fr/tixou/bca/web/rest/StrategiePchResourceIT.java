package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.StrategiePch;
import fr.tixou.bca.repository.StrategiePchRepository;
import fr.tixou.bca.service.StrategiePchService;
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
 * Integration tests for the {@link StrategiePchResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StrategiePchResourceIT {

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

    private static final String ENTITY_API_URL = "/api/strategie-pches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategiePchRepository strategiePchRepository;

    @Mock
    private StrategiePchRepository strategiePchRepositoryMock;

    @Mock
    private StrategiePchService strategiePchServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategiePchMockMvc;

    private StrategiePch strategiePch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategiePch createEntity(EntityManager em) {
        StrategiePch strategiePch = new StrategiePch()
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
        return strategiePch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategiePch createUpdatedEntity(EntityManager em) {
        StrategiePch strategiePch = new StrategiePch()
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
        return strategiePch;
    }

    @BeforeEach
    public void initTest() {
        strategiePch = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategiePch() throws Exception {
        int databaseSizeBeforeCreate = strategiePchRepository.findAll().size();
        // Create the StrategiePch
        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isCreated());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeCreate + 1);
        StrategiePch testStrategiePch = strategiePchList.get(strategiePchList.size() - 1);
        assertThat(testStrategiePch.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategiePch.getDateMensuelleDebutValidite()).isEqualTo(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePch.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategiePch.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategiePch.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePch.getMontantPlafondCotisations()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePch.getMontantPlafondSalairePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePch.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePch.getNbHeureSalairePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePch.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testStrategiePch.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void createStrategiePchWithExistingId() throws Exception {
        // Create the StrategiePch with an existing ID
        strategiePch.setId(1L);

        int databaseSizeBeforeCreate = strategiePchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setIsActif(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateMensuelleDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setDateMensuelleDebutValidite(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setAnne(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setMois(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setMontantPlafondSalaire(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setMontantPlafondCotisations(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalairePlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setMontantPlafondSalairePlus(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondCotisationsPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setMontantPlafondCotisationsPlus(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeureSalairePlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setNbHeureSalairePlafond(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setTauxSalaire(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategiePchRepository.findAll().size();
        // set the field null
        strategiePch.setTauxCotisations(null);

        // Create the StrategiePch, which fails.

        restStrategiePchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isBadRequest());

        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategiePches() throws Exception {
        // Initialize the database
        strategiePchRepository.saveAndFlush(strategiePch);

        // Get all the strategiePchList
        restStrategiePchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategiePch.getId().intValue())))
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
    void getAllStrategiePchesWithEagerRelationshipsIsEnabled() throws Exception {
        when(strategiePchServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategiePchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategiePchServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStrategiePchesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(strategiePchServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategiePchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategiePchServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStrategiePch() throws Exception {
        // Initialize the database
        strategiePchRepository.saveAndFlush(strategiePch);

        // Get the strategiePch
        restStrategiePchMockMvc
            .perform(get(ENTITY_API_URL_ID, strategiePch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategiePch.getId().intValue()))
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
    void getNonExistingStrategiePch() throws Exception {
        // Get the strategiePch
        restStrategiePchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrategiePch() throws Exception {
        // Initialize the database
        strategiePchRepository.saveAndFlush(strategiePch);

        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();

        // Update the strategiePch
        StrategiePch updatedStrategiePch = strategiePchRepository.findById(strategiePch.getId()).get();
        // Disconnect from session so that the updates on updatedStrategiePch are not directly saved in db
        em.detach(updatedStrategiePch);
        updatedStrategiePch
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

        restStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategiePch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategiePch))
            )
            .andExpect(status().isOk());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
        StrategiePch testStrategiePch = strategiePchList.get(strategiePchList.size() - 1);
        assertThat(testStrategiePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategiePch.getDateMensuelleDebutValidite()).isEqualTo(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePch.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategiePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategiePch.getMontantPlafondSalaire()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePch.getMontantPlafondCotisations()).isEqualTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePch.getMontantPlafondSalairePlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePch.getMontantPlafondCotisationsPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePch.getNbHeureSalairePlafond()).isEqualTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePch.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategiePch.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void putNonExistingStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();
        strategiePch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategiePch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();
        strategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();
        strategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategiePch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategiePchWithPatch() throws Exception {
        // Initialize the database
        strategiePchRepository.saveAndFlush(strategiePch);

        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();

        // Update the strategiePch using partial update
        StrategiePch partialUpdatedStrategiePch = new StrategiePch();
        partialUpdatedStrategiePch.setId(strategiePch.getId());

        partialUpdatedStrategiePch
            .anne(UPDATED_ANNE)
            .mois(UPDATED_MOIS)
            .montantPlafondSalairePlus(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS);

        restStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategiePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategiePch))
            )
            .andExpect(status().isOk());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
        StrategiePch testStrategiePch = strategiePchList.get(strategiePchList.size() - 1);
        assertThat(testStrategiePch.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategiePch.getDateMensuelleDebutValidite()).isEqualTo(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePch.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategiePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategiePch.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePch.getMontantPlafondCotisations()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePch.getMontantPlafondSalairePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePch.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePch.getNbHeureSalairePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePch.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategiePch.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void fullUpdateStrategiePchWithPatch() throws Exception {
        // Initialize the database
        strategiePchRepository.saveAndFlush(strategiePch);

        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();

        // Update the strategiePch using partial update
        StrategiePch partialUpdatedStrategiePch = new StrategiePch();
        partialUpdatedStrategiePch.setId(strategiePch.getId());

        partialUpdatedStrategiePch
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

        restStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategiePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategiePch))
            )
            .andExpect(status().isOk());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
        StrategiePch testStrategiePch = strategiePchList.get(strategiePchList.size() - 1);
        assertThat(testStrategiePch.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategiePch.getDateMensuelleDebutValidite()).isEqualTo(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategiePch.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategiePch.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategiePch.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategiePch.getMontantPlafondCotisations()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategiePch.getMontantPlafondSalairePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategiePch.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategiePch.getNbHeureSalairePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategiePch.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategiePch.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void patchNonExistingStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();
        strategiePch.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategiePch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();
        strategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategiePch))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategiePch() throws Exception {
        int databaseSizeBeforeUpdate = strategiePchRepository.findAll().size();
        strategiePch.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategiePchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strategiePch))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategiePch in the database
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategiePch() throws Exception {
        // Initialize the database
        strategiePchRepository.saveAndFlush(strategiePch);

        int databaseSizeBeforeDelete = strategiePchRepository.findAll().size();

        // Delete the strategiePch
        restStrategiePchMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategiePch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategiePch> strategiePchList = strategiePchRepository.findAll();
        assertThat(strategiePchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
