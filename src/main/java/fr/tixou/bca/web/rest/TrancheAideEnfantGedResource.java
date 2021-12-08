package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.TrancheAideEnfantGed;
import fr.tixou.bca.repository.TrancheAideEnfantGedRepository;
import fr.tixou.bca.service.TrancheAideEnfantGedService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.TrancheAideEnfantGed}.
 */
@RestController
@RequestMapping("/api")
public class TrancheAideEnfantGedResource {

    private final Logger log = LoggerFactory.getLogger(TrancheAideEnfantGedResource.class);

    private static final String ENTITY_NAME = "trancheAideEnfantGed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrancheAideEnfantGedService trancheAideEnfantGedService;

    private final TrancheAideEnfantGedRepository trancheAideEnfantGedRepository;

    public TrancheAideEnfantGedResource(
        TrancheAideEnfantGedService trancheAideEnfantGedService,
        TrancheAideEnfantGedRepository trancheAideEnfantGedRepository
    ) {
        this.trancheAideEnfantGedService = trancheAideEnfantGedService;
        this.trancheAideEnfantGedRepository = trancheAideEnfantGedRepository;
    }

    /**
     * {@code POST  /tranche-aide-enfant-geds} : Create a new trancheAideEnfantGed.
     *
     * @param trancheAideEnfantGed the trancheAideEnfantGed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trancheAideEnfantGed, or with status {@code 400 (Bad Request)} if the trancheAideEnfantGed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tranche-aide-enfant-geds")
    public ResponseEntity<TrancheAideEnfantGed> createTrancheAideEnfantGed(@Valid @RequestBody TrancheAideEnfantGed trancheAideEnfantGed)
        throws URISyntaxException {
        log.debug("REST request to save TrancheAideEnfantGed : {}", trancheAideEnfantGed);
        if (trancheAideEnfantGed.getId() != null) {
            throw new BadRequestAlertException("A new trancheAideEnfantGed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrancheAideEnfantGed result = trancheAideEnfantGedService.save(trancheAideEnfantGed);
        return ResponseEntity
            .created(new URI("/api/tranche-aide-enfant-geds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tranche-aide-enfant-geds/:id} : Updates an existing trancheAideEnfantGed.
     *
     * @param id the id of the trancheAideEnfantGed to save.
     * @param trancheAideEnfantGed the trancheAideEnfantGed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trancheAideEnfantGed,
     * or with status {@code 400 (Bad Request)} if the trancheAideEnfantGed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trancheAideEnfantGed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tranche-aide-enfant-geds/{id}")
    public ResponseEntity<TrancheAideEnfantGed> updateTrancheAideEnfantGed(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrancheAideEnfantGed trancheAideEnfantGed
    ) throws URISyntaxException {
        log.debug("REST request to update TrancheAideEnfantGed : {}, {}", id, trancheAideEnfantGed);
        if (trancheAideEnfantGed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trancheAideEnfantGed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trancheAideEnfantGedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrancheAideEnfantGed result = trancheAideEnfantGedService.save(trancheAideEnfantGed);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trancheAideEnfantGed.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tranche-aide-enfant-geds/:id} : Partial updates given fields of an existing trancheAideEnfantGed, field will ignore if it is null
     *
     * @param id the id of the trancheAideEnfantGed to save.
     * @param trancheAideEnfantGed the trancheAideEnfantGed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trancheAideEnfantGed,
     * or with status {@code 400 (Bad Request)} if the trancheAideEnfantGed is not valid,
     * or with status {@code 404 (Not Found)} if the trancheAideEnfantGed is not found,
     * or with status {@code 500 (Internal Server Error)} if the trancheAideEnfantGed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tranche-aide-enfant-geds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrancheAideEnfantGed> partialUpdateTrancheAideEnfantGed(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrancheAideEnfantGed trancheAideEnfantGed
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrancheAideEnfantGed partially : {}, {}", id, trancheAideEnfantGed);
        if (trancheAideEnfantGed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trancheAideEnfantGed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trancheAideEnfantGedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrancheAideEnfantGed> result = trancheAideEnfantGedService.partialUpdate(trancheAideEnfantGed);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trancheAideEnfantGed.getId().toString())
        );
    }

    /**
     * {@code GET  /tranche-aide-enfant-geds} : get all the trancheAideEnfantGeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trancheAideEnfantGeds in body.
     */
    @GetMapping("/tranche-aide-enfant-geds")
    public List<TrancheAideEnfantGed> getAllTrancheAideEnfantGeds() {
        log.debug("REST request to get all TrancheAideEnfantGeds");
        return trancheAideEnfantGedService.findAll();
    }

    /**
     * {@code GET  /tranche-aide-enfant-geds/:id} : get the "id" trancheAideEnfantGed.
     *
     * @param id the id of the trancheAideEnfantGed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trancheAideEnfantGed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tranche-aide-enfant-geds/{id}")
    public ResponseEntity<TrancheAideEnfantGed> getTrancheAideEnfantGed(@PathVariable Long id) {
        log.debug("REST request to get TrancheAideEnfantGed : {}", id);
        Optional<TrancheAideEnfantGed> trancheAideEnfantGed = trancheAideEnfantGedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trancheAideEnfantGed);
    }

    /**
     * {@code DELETE  /tranche-aide-enfant-geds/:id} : delete the "id" trancheAideEnfantGed.
     *
     * @param id the id of the trancheAideEnfantGed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tranche-aide-enfant-geds/{id}")
    public ResponseEntity<Void> deleteTrancheAideEnfantGed(@PathVariable Long id) {
        log.debug("REST request to delete TrancheAideEnfantGed : {}", id);
        trancheAideEnfantGedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
