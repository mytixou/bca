package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.TrancheAideEnfantAssmat;
import fr.tixou.bca.repository.TrancheAideEnfantAssmatRepository;
import fr.tixou.bca.service.TrancheAideEnfantAssmatService;
import fr.tixou.bca.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.tixou.bca.domain.TrancheAideEnfantAssmat}.
 */
@RestController
@RequestMapping("/api")
public class TrancheAideEnfantAssmatResource {

    private final Logger log = LoggerFactory.getLogger(TrancheAideEnfantAssmatResource.class);

    private static final String ENTITY_NAME = "trancheAideEnfantAssmat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrancheAideEnfantAssmatService trancheAideEnfantAssmatService;

    private final TrancheAideEnfantAssmatRepository trancheAideEnfantAssmatRepository;

    public TrancheAideEnfantAssmatResource(
        TrancheAideEnfantAssmatService trancheAideEnfantAssmatService,
        TrancheAideEnfantAssmatRepository trancheAideEnfantAssmatRepository
    ) {
        this.trancheAideEnfantAssmatService = trancheAideEnfantAssmatService;
        this.trancheAideEnfantAssmatRepository = trancheAideEnfantAssmatRepository;
    }

    /**
     * {@code POST  /tranche-aide-enfant-assmats} : Create a new trancheAideEnfantAssmat.
     *
     * @param trancheAideEnfantAssmat the trancheAideEnfantAssmat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trancheAideEnfantAssmat, or with status {@code 400 (Bad Request)} if the trancheAideEnfantAssmat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tranche-aide-enfant-assmats")
    public ResponseEntity<TrancheAideEnfantAssmat> createTrancheAideEnfantAssmat(
        @Valid @RequestBody TrancheAideEnfantAssmat trancheAideEnfantAssmat
    ) throws URISyntaxException {
        log.debug("REST request to save TrancheAideEnfantAssmat : {}", trancheAideEnfantAssmat);
        if (trancheAideEnfantAssmat.getId() != null) {
            throw new BadRequestAlertException("A new trancheAideEnfantAssmat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrancheAideEnfantAssmat result = trancheAideEnfantAssmatService.save(trancheAideEnfantAssmat);
        return ResponseEntity
            .created(new URI("/api/tranche-aide-enfant-assmats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tranche-aide-enfant-assmats/:id} : Updates an existing trancheAideEnfantAssmat.
     *
     * @param id the id of the trancheAideEnfantAssmat to save.
     * @param trancheAideEnfantAssmat the trancheAideEnfantAssmat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trancheAideEnfantAssmat,
     * or with status {@code 400 (Bad Request)} if the trancheAideEnfantAssmat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trancheAideEnfantAssmat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tranche-aide-enfant-assmats/{id}")
    public ResponseEntity<TrancheAideEnfantAssmat> updateTrancheAideEnfantAssmat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrancheAideEnfantAssmat trancheAideEnfantAssmat
    ) throws URISyntaxException {
        log.debug("REST request to update TrancheAideEnfantAssmat : {}, {}", id, trancheAideEnfantAssmat);
        if (trancheAideEnfantAssmat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trancheAideEnfantAssmat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trancheAideEnfantAssmatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrancheAideEnfantAssmat result = trancheAideEnfantAssmatService.save(trancheAideEnfantAssmat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trancheAideEnfantAssmat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tranche-aide-enfant-assmats/:id} : Partial updates given fields of an existing trancheAideEnfantAssmat, field will ignore if it is null
     *
     * @param id the id of the trancheAideEnfantAssmat to save.
     * @param trancheAideEnfantAssmat the trancheAideEnfantAssmat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trancheAideEnfantAssmat,
     * or with status {@code 400 (Bad Request)} if the trancheAideEnfantAssmat is not valid,
     * or with status {@code 404 (Not Found)} if the trancheAideEnfantAssmat is not found,
     * or with status {@code 500 (Internal Server Error)} if the trancheAideEnfantAssmat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tranche-aide-enfant-assmats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrancheAideEnfantAssmat> partialUpdateTrancheAideEnfantAssmat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrancheAideEnfantAssmat trancheAideEnfantAssmat
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrancheAideEnfantAssmat partially : {}, {}", id, trancheAideEnfantAssmat);
        if (trancheAideEnfantAssmat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trancheAideEnfantAssmat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trancheAideEnfantAssmatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrancheAideEnfantAssmat> result = trancheAideEnfantAssmatService.partialUpdate(trancheAideEnfantAssmat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trancheAideEnfantAssmat.getId().toString())
        );
    }

    /**
     * {@code GET  /tranche-aide-enfant-assmats} : get all the trancheAideEnfantAssmats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trancheAideEnfantAssmats in body.
     */
    @GetMapping("/tranche-aide-enfant-assmats")
    public List<TrancheAideEnfantAssmat> getAllTrancheAideEnfantAssmats() {
        log.debug("REST request to get all TrancheAideEnfantAssmats");
        return trancheAideEnfantAssmatService.findAll();
    }

    /**
     * {@code GET  /tranche-aide-enfant-assmats/:id} : get the "id" trancheAideEnfantAssmat.
     *
     * @param id the id of the trancheAideEnfantAssmat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trancheAideEnfantAssmat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tranche-aide-enfant-assmats/{id}")
    public ResponseEntity<TrancheAideEnfantAssmat> getTrancheAideEnfantAssmat(@PathVariable Long id) {
        log.debug("REST request to get TrancheAideEnfantAssmat : {}", id);
        Optional<TrancheAideEnfantAssmat> trancheAideEnfantAssmat = trancheAideEnfantAssmatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trancheAideEnfantAssmat);
    }

    /**
     * {@code DELETE  /tranche-aide-enfant-assmats/:id} : delete the "id" trancheAideEnfantAssmat.
     *
     * @param id the id of the trancheAideEnfantAssmat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tranche-aide-enfant-assmats/{id}")
    public ResponseEntity<Void> deleteTrancheAideEnfantAssmat(@PathVariable Long id) {
        log.debug("REST request to delete TrancheAideEnfantAssmat : {}", id);
        trancheAideEnfantAssmatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
