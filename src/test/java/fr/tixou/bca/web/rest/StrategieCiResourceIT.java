package fr.tixou.bca.web.rest;

import static fr.tixou.bca.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.tixou.bca.IntegrationTest;
import fr.tixou.bca.domain.StrategieCi;
import fr.tixou.bca.repository.StrategieCiRepository;
import fr.tixou.bca.service.StrategieCiService;
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
 * Integration tests for the {@link StrategieCiResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StrategieCiResourceIT {

    private static final Boolean DEFAULT_IS_ACTIF = false;
    private static final Boolean UPDATED_IS_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_ANNUELLE_DEBUT_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANNE = 1;
    private static final Integer UPDATED_ANNE = 2;

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_DEFAUT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_DEFAUT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_HANDICAPE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_HANDICAPE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAUX_SALAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX_SALAIRE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/strategie-cis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StrategieCiRepository strategieCiRepository;

    @Mock
    private StrategieCiRepository strategieCiRepositoryMock;

    @Mock
    private StrategieCiService strategieCiServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStrategieCiMockMvc;

    private StrategieCi strategieCi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieCi createEntity(EntityManager em) {
        StrategieCi strategieCi = new StrategieCi()
            .isActif(DEFAULT_IS_ACTIF)
            .dateAnnuelleDebutValidite(DEFAULT_DATE_ANNUELLE_DEBUT_VALIDITE)
            .anne(DEFAULT_ANNE)
            .montantPlafondDefaut(DEFAULT_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(DEFAULT_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(DEFAULT_TAUX_SALAIRE);
        return strategieCi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrategieCi createUpdatedEntity(EntityManager em) {
        StrategieCi strategieCi = new StrategieCi()
            .isActif(UPDATED_IS_ACTIF)
            .dateAnnuelleDebutValidite(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE);
        return strategieCi;
    }

    @BeforeEach
    public void initTest() {
        strategieCi = createEntity(em);
    }

    @Test
    @Transactional
    void createStrategieCi() throws Exception {
        int databaseSizeBeforeCreate = strategieCiRepository.findAll().size();
        // Create the StrategieCi
        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isCreated());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeCreate + 1);
        StrategieCi testStrategieCi = strategieCiList.get(strategieCiList.size() - 1);
        assertThat(testStrategieCi.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategieCi.getDateAnnuelleDebutValidite()).isEqualTo(DEFAULT_DATE_ANNUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieCi.getAnne()).isEqualTo(DEFAULT_ANNE);
        assertThat(testStrategieCi.getMontantPlafondDefaut()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT);
        assertThat(testStrategieCi.getMontantPlafondHandicape()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testStrategieCi.getMontantPlafondDefautPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testStrategieCi.getMontantPlafondHandicapePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testStrategieCi.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
    }

    @Test
    @Transactional
    void createStrategieCiWithExistingId() throws Exception {
        // Create the StrategieCi with an existing ID
        strategieCi.setId(1L);

        int databaseSizeBeforeCreate = strategieCiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setIsActif(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateAnnuelleDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setDateAnnuelleDebutValidite(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnneIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setAnne(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondDefautIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setMontantPlafondDefaut(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondHandicapeIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setMontantPlafondHandicape(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondDefautPlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setMontantPlafondDefautPlus(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantPlafondHandicapePlusIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setMontantPlafondHandicapePlus(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTauxSalaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = strategieCiRepository.findAll().size();
        // set the field null
        strategieCi.setTauxSalaire(null);

        // Create the StrategieCi, which fails.

        restStrategieCiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isBadRequest());

        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStrategieCis() throws Exception {
        // Initialize the database
        strategieCiRepository.saveAndFlush(strategieCi);

        // Get all the strategieCiList
        restStrategieCiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strategieCi.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActif").value(hasItem(DEFAULT_IS_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAnnuelleDebutValidite").value(hasItem(DEFAULT_DATE_ANNUELLE_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].anne").value(hasItem(DEFAULT_ANNE)))
            .andExpect(jsonPath("$.[*].montantPlafondDefaut").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT))))
            .andExpect(jsonPath("$.[*].montantPlafondHandicape").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE))))
            .andExpect(jsonPath("$.[*].montantPlafondDefautPlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS))))
            .andExpect(jsonPath("$.[*].montantPlafondHandicapePlus").value(hasItem(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS))))
            .andExpect(jsonPath("$.[*].tauxSalaire").value(hasItem(sameNumber(DEFAULT_TAUX_SALAIRE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStrategieCisWithEagerRelationshipsIsEnabled() throws Exception {
        when(strategieCiServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategieCiMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategieCiServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStrategieCisWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(strategieCiServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStrategieCiMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(strategieCiServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStrategieCi() throws Exception {
        // Initialize the database
        strategieCiRepository.saveAndFlush(strategieCi);

        // Get the strategieCi
        restStrategieCiMockMvc
            .perform(get(ENTITY_API_URL_ID, strategieCi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(strategieCi.getId().intValue()))
            .andExpect(jsonPath("$.isActif").value(DEFAULT_IS_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateAnnuelleDebutValidite").value(DEFAULT_DATE_ANNUELLE_DEBUT_VALIDITE.toString()))
            .andExpect(jsonPath("$.anne").value(DEFAULT_ANNE))
            .andExpect(jsonPath("$.montantPlafondDefaut").value(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT)))
            .andExpect(jsonPath("$.montantPlafondHandicape").value(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE)))
            .andExpect(jsonPath("$.montantPlafondDefautPlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS)))
            .andExpect(jsonPath("$.montantPlafondHandicapePlus").value(sameNumber(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS)))
            .andExpect(jsonPath("$.tauxSalaire").value(sameNumber(DEFAULT_TAUX_SALAIRE)));
    }

    @Test
    @Transactional
    void getNonExistingStrategieCi() throws Exception {
        // Get the strategieCi
        restStrategieCiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStrategieCi() throws Exception {
        // Initialize the database
        strategieCiRepository.saveAndFlush(strategieCi);

        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();

        // Update the strategieCi
        StrategieCi updatedStrategieCi = strategieCiRepository.findById(strategieCi.getId()).get();
        // Disconnect from session so that the updates on updatedStrategieCi are not directly saved in db
        em.detach(updatedStrategieCi);
        updatedStrategieCi
            .isActif(UPDATED_IS_ACTIF)
            .dateAnnuelleDebutValidite(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE);

        restStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStrategieCi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStrategieCi))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
        StrategieCi testStrategieCi = strategieCiList.get(strategieCiList.size() - 1);
        assertThat(testStrategieCi.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCi.getDateAnnuelleDebutValidite()).isEqualTo(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieCi.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCi.getMontantPlafondDefaut()).isEqualTo(UPDATED_MONTANT_PLAFOND_DEFAUT);
        assertThat(testStrategieCi.getMontantPlafondHandicape()).isEqualTo(UPDATED_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testStrategieCi.getMontantPlafondDefautPlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testStrategieCi.getMontantPlafondHandicapePlus()).isEqualTo(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testStrategieCi.getTauxSalaire()).isEqualTo(UPDATED_TAUX_SALAIRE);
    }

    @Test
    @Transactional
    void putNonExistingStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();
        strategieCi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, strategieCi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();
        strategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(strategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();
        strategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(strategieCi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStrategieCiWithPatch() throws Exception {
        // Initialize the database
        strategieCiRepository.saveAndFlush(strategieCi);

        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();

        // Update the strategieCi using partial update
        StrategieCi partialUpdatedStrategieCi = new StrategieCi();
        partialUpdatedStrategieCi.setId(strategieCi.getId());

        partialUpdatedStrategieCi
            .dateAnnuelleDebutValidite(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE);

        restStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieCi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieCi))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
        StrategieCi testStrategieCi = strategieCiList.get(strategieCiList.size() - 1);
        assertThat(testStrategieCi.getIsActif()).isEqualTo(DEFAULT_IS_ACTIF);
        assertThat(testStrategieCi.getDateAnnuelleDebutValidite()).isEqualTo(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieCi.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCi.getMontantPlafondDefaut()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_DEFAUT);
        assertThat(testStrategieCi.getMontantPlafondHandicape()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testStrategieCi.getMontantPlafondDefautPlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testStrategieCi.getMontantPlafondHandicapePlus()).isEqualByComparingTo(DEFAULT_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testStrategieCi.getTauxSalaire()).isEqualByComparingTo(DEFAULT_TAUX_SALAIRE);
    }

    @Test
    @Transactional
    void fullUpdateStrategieCiWithPatch() throws Exception {
        // Initialize the database
        strategieCiRepository.saveAndFlush(strategieCi);

        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();

        // Update the strategieCi using partial update
        StrategieCi partialUpdatedStrategieCi = new StrategieCi();
        partialUpdatedStrategieCi.setId(strategieCi.getId());

        partialUpdatedStrategieCi
            .isActif(UPDATED_IS_ACTIF)
            .dateAnnuelleDebutValidite(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE)
            .anne(UPDATED_ANNE)
            .montantPlafondDefaut(UPDATED_MONTANT_PLAFOND_DEFAUT)
            .montantPlafondHandicape(UPDATED_MONTANT_PLAFOND_HANDICAPE)
            .montantPlafondDefautPlus(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS)
            .montantPlafondHandicapePlus(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS)
            .tauxSalaire(UPDATED_TAUX_SALAIRE);

        restStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStrategieCi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStrategieCi))
            )
            .andExpect(status().isOk());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
        StrategieCi testStrategieCi = strategieCiList.get(strategieCiList.size() - 1);
        assertThat(testStrategieCi.getIsActif()).isEqualTo(UPDATED_IS_ACTIF);
        assertThat(testStrategieCi.getDateAnnuelleDebutValidite()).isEqualTo(UPDATED_DATE_ANNUELLE_DEBUT_VALIDITE);
        assertThat(testStrategieCi.getAnne()).isEqualTo(UPDATED_ANNE);
        assertThat(testStrategieCi.getMontantPlafondDefaut()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_DEFAUT);
        assertThat(testStrategieCi.getMontantPlafondHandicape()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE);
        assertThat(testStrategieCi.getMontantPlafondDefautPlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_DEFAUT_PLUS);
        assertThat(testStrategieCi.getMontantPlafondHandicapePlus()).isEqualByComparingTo(UPDATED_MONTANT_PLAFOND_HANDICAPE_PLUS);
        assertThat(testStrategieCi.getTauxSalaire()).isEqualByComparingTo(UPDATED_TAUX_SALAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();
        strategieCi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, strategieCi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();
        strategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(strategieCi))
            )
            .andExpect(status().isBadRequest());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStrategieCi() throws Exception {
        int databaseSizeBeforeUpdate = strategieCiRepository.findAll().size();
        strategieCi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStrategieCiMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(strategieCi))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StrategieCi in the database
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStrategieCi() throws Exception {
        // Initialize the database
        strategieCiRepository.saveAndFlush(strategieCi);

        int databaseSizeBeforeDelete = strategieCiRepository.findAll().size();

        // Delete the strategieCi
        restStrategieCiMockMvc
            .perform(delete(ENTITY_API_URL_ID, strategieCi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrategieCi> strategieCiList = strategieCiRepository.findAll();
        assertThat(strategieCiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
