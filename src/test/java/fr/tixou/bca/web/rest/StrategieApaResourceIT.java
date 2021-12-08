package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.StrategieApa;
import fr.tixou.bca.repository.StrategieApaRepository;
import fr.tixou.bca.service.StrategieApaService;
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
 * Integration tests for the {@link StrategieApaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StrategieApaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/strategie-apas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategieApaRepository strategieApaRepository;

    @Mock
    private StrategieApaRepository strategieApaRepositoryMock;

    @Mock
    private StrategieApaService strategieApaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategieApaMockMvc;

    private StrategieApa strategieApa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieApa createEntity(EntityManager em) {
        StrategieApa strategieApa = new StrategieApa()
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
        return strategieApa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieApa createUpdatedEntity(EntityManager em) {
        StrategieApa strategieApa = new StrategieApa()
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
        return strategieApa;
    }

    @BeforeEach
    public void initTest() {
        strategieApa = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategieApa() throws Exception {
        int databaseSizeBeforeCreate = strategieApaRepository.findAll().size();
        // Create the StrategieApa
        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isCreated());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeCreate + 1);
        StrategieApa testStrategieApa = strategieApaList.get(strategieApaList.size() - 1);
        assertThat(testStrategieApa.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategieApa.getDateMensuelleDebutValidite()).isEqualTo(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieApa.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieApa.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategieApa.getMontantPlafondSalaire()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategieApa.getMontantPlafondCotisations()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategieApa.getMontantPlafondSalairePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategieApa.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategieApa.getNbHeureSalairePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategieApa.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
        assertThat(testStrategieApa.getTauxCotisations()).isEqualByComparingTo(DEFAULT_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void createStrategieApaWithExistingId() throws Exception {
        // Create the StrategieApa with an existing ID
        strategieApa.setId(1L);

        int databaseSizeBeforeCreate = strategieApaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setIsActif(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateMensuelleDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setDateMensuelleDebutValidite(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setAnne(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMoisIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setMois(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setMontantPlafondSalaire(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setMontantPlafondCotisations(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondSalairePlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setMontantPlafondSalairePlus(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondCotisationsPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setMontantPlafondCotisationsPlus(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeureSalairePlafondIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setNbHeureSalairePlafond(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setTauxSalaire(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxCotisationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieApaRepository.findAll().size();
        // set the field null
        strategieApa.setTauxCotisations(null);

        // Create the StrategieApa, which fails.

        restStrategieApaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isBadRequest());

        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategieApas() throws Exception {
        // Initialize the database
        strategieApaRepository.saveAndFlush(strategieApa);

        // Get all the strategieApaList
        restStrategieApaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategieApa.getId().intValue())))
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
    void getAllStrategieApasWithEagerRelationshipsIsEnabled() throws Exception {
        when(strategieApaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategieApaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategieApaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStrategieApasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(strategieApaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategieApaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategieApaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStrategieApa() throws Exception {
        // Initialize the database
        strategieApaRepository.saveAndFlush(strategieApa);

        // Get the strategieApa
        restStrategieApaMockMvc
            .perform(get(ENTITY_API_URL_ID, strategieApa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategieApa.getId().intValue()))
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
    void getNonExistingStrategieApa() throws Exception {
        // Get the strategieApa
        restStrategieApaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrategieApa() throws Exception {
        // Initialize the database
        strategieApaRepository.saveAndFlush(strategieApa);

        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();

        // Update the strategieApa
        StrategieApa updatedStrategieApa = strategieApaRepository.findById(strategieApa.getId()).get();
        // Disconnect from session so that the updates on updatedStrategieApa are not directly saved in db
        em.detach(updatedStrategieApa);
        updatedStrategieApa
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

        restStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategieApa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategieApa))
            )
            .andExpect(status().isOk());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
        StrategieApa testStrategieApa = strategieApaList.get(strategieApaList.size() - 1);
        assertThat(testStrategieApa.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieApa.getDateMensuelleDebutValidite()).isEqualTo(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieApa.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieApa.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategieApa.getMontantPlafondSalaire()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategieApa.getMontantPlafondCotisations()).isEqualTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategieApa.getMontantPlafondSalairePlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategieApa.getMontantPlafondCotisationsPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategieApa.getNbHeureSalairePlafond()).isEqualTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategieApa.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieApa.getTauxCotisations()).isEqualTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void putNonExistingStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();
        strategieApa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategieApa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();
        strategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieApaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();
        strategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieApaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieApa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategieApaWithPatch() throws Exception {
        // Initialize the database
        strategieApaRepository.saveAndFlush(strategieApa);

        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();

        // Update the strategieApa using partial update
        StrategieApa partialUpdatedStrategieApa = new StrategieApa();
        partialUpdatedStrategieApa.setId(strategieApa.getId());

        partialUpdatedStrategieApa
            .montantPlafondSalaire(UPDATED_MONTANT_PLAFOND_SALAIRE)
            .montantPlafondCotisations(UPDATED_MONTANT_PLAFOND_COTISATIONS)
            .montantPlafondCotisationsPlus(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE)
            .tauxCotisations(UPDATED_TAUX_COTISATIONS);

        restStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieApa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieApa))
            )
            .andExpect(status().isOk());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
        StrategieApa testStrategieApa = strategieApaList.get(strategieApaList.size() - 1);
        assertThat(testStrategieApa.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategieApa.getDateMensuelleDebutValidite()).isEqualTo(DEFAULT_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieApa.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieApa.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testStrategieApa.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategieApa.getMontantPlafondCotisations()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategieApa.getMontantPlafondSalairePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategieApa.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategieApa.getNbHeureSalairePlafond()).isEqualByComparingTo(DEFAULT_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategieApa.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieApa.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void fullUpdateStrategieApaWithPatch() throws Exception {
        // Initialize the database
        strategieApaRepository.saveAndFlush(strategieApa);

        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();

        // Update the strategieApa using partial update
        StrategieApa partialUpdatedStrategieApa = new StrategieApa();
        partialUpdatedStrategieApa.setId(strategieApa.getId());

        partialUpdatedStrategieApa
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

        restStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieApa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieApa))
            )
            .andExpect(status().isOk());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
        StrategieApa testStrategieApa = strategieApaList.get(strategieApaList.size() - 1);
        assertThat(testStrategieApa.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieApa.getDateMensuelleDebutValidite()).isEqualTo(UPDATED_DATE_MENSUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieApa.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieApa.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testStrategieApa.getMontantPlafondSalaire()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE);
        assertThat(testStrategieApa.getMontantPlafondCotisations()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS);
        assertThat(testStrategieApa.getMontantPlafondSalairePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_SALAIRE_PLUS);
        assertThat(testStrategieApa.getMontantPlafondCotisationsPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_COTISATIONS_PLUS);
        assertThat(testStrategieApa.getNbHeureSalairePlafond()).isEqualByComparingTo(UPDATED_NB_HEURE_SALAIRE_PLAFOND);
        assertThat(testStrategieApa.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
        assertThat(testStrategieApa.getTauxCotisations()).isEqualByComparingTo(UPDATED_TAUX_COTISATIONS);
    }

    @Test
    @Transactional
    void patchNonExistingStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();
        strategieApa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategieApa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();
        strategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieApa))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategieApa() throws Exception {
        int databaseSizeBeforeUpdate = strategieApaRepository.findAll().size();
        strategieApa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieApaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strategieApa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieApa in the database
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategieApa() throws Exception {
        // Initialize the database
        strategieApaRepository.saveAndFlush(strategieApa);

        int databaseSizeBeforeDelete = strategieApaRepository.findAll().size();

        // Delete the strategieApa
        restStrategieApaMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategieApa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategieApa> strategieApaList = strategieApaRepository.findAll();
        assertThat(strategieApaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
