package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.DroitsStrategieApa;
import fr.tixou.bca.repository.DroitsStrategieApaRepository;
import fr.tixou.bca.service.DroitsStrategieApaService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.DroitsStrategieApa}.
 */
@RestController
@RequestMapping("/api")
public class DroitsStrategieApaResource {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategieApaResource.class);

    private static final String ENTITY_NAME = "droitsStrategieApa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitsStrategieApaService droitsStrategieApaService;

    private final DroitsStrategieApaRepository droitsStrategieApaRepository;

    public DroitsStrategieApaResource(
        DroitsStrategieApaService droitsStrategieApaService,
        DroitsStrategieApaRepository droitsStrategieApaRepository
    ) {
        this.droitsStrategieApaService = droitsStrategieApaService;
        this.droitsStrategieApaRepository = droitsStrategieApaRepository;
    }

    /**
     * {@code POST  /droits-strategie-apas} : Create a new droitsStrategieApa.
     *
     * @param droitsStrategieApa the droitsStrategieApa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droitsStrategieApa, or with status {@code 400 (Bad Request)} if the droitsStrategieApa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/droits-strategie-apas")
    public ResponseEntity<DroitsStrategieApa> createDroitsStrategieApa(@Valid @RequestBody DroitsStrategieApa droitsStrategieApa)
        throws URISyntaxException {
        log.debug("REST request to save DroitsStrategieApa : {}", droitsStrategieApa);
        if (droitsStrategieApa.getId() != null) {
            throw new BadRequestAlertException("A new droitsStrategieApa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitsStrategieApa result = droitsStrategieApaService.save(droitsStrategieApa);
        return ResponseEntity
            .created(new URI("/api/droits-strategie-apas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /droits-strategie-apas/:id} : Updates an existing droitsStrategieApa.
     *
     * @param id the id of the droitsStrategieApa to save.
     * @param droitsStrategieApa the droitsStrategieApa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategieApa,
     * or with status {@code 400 (Bad Request)} if the droitsStrategieApa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategieApa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/droits-strategie-apas/{id}")
    public ResponseEntity<DroitsStrategieApa> updateDroitsStrategieApa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitsStrategieApa droitsStrategieApa
    ) throws URISyntaxException {
        log.debug("REST request to update DroitsStrategieApa : {}, {}", id, droitsStrategieApa);
        if (droitsStrategieApa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategieApa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategieApaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DroitsStrategieApa result = droitsStrategieApaService.save(droitsStrategieApa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategieApa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /droits-strategie-apas/:id} : Partial updates given fields of an existing droitsStrategieApa, field will ignore if it is null
     *
     * @param id the id of the droitsStrategieApa to save.
     * @param droitsStrategieApa the droitsStrategieApa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategieApa,
     * or with status {@code 400 (Bad Request)} if the droitsStrategieApa is not valid,
     * or with status {@code 404 (Not Found)} if the droitsStrategieApa is not found,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategieApa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/droits-strategie-apas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroitsStrategieApa> partialUpdateDroitsStrategieApa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitsStrategieApa droitsStrategieApa
    ) throws URISyntaxException {
        log.debug("REST request to partial update DroitsStrategieApa partially : {}, {}", id, droitsStrategieApa);
        if (droitsStrategieApa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategieApa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategieApaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DroitsStrategieApa> result = droitsStrategieApaService.partialUpdate(droitsStrategieApa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategieApa.getId().toString())
        );
    }

    /**
     * {@code GET  /droits-strategie-apas} : get all the droitsStrategieApas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droitsStrategieApas in body.
     */
    @GetMapping("/droits-strategie-apas")
    public List<DroitsStrategieApa> getAllDroitsStrategieApas() {
        log.debug("REST request to get all DroitsStrategieApas");
        return droitsStrategieApaService.findAll();
    }

    /**
     * {@code GET  /droits-strategie-apas/:id} : get the "id" droitsStrategieApa.
     *
     * @param id the id of the droitsStrategieApa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droitsStrategieApa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/droits-strategie-apas/{id}")
    public ResponseEntity<DroitsStrategieApa> getDroitsStrategieApa(@PathVariable Long id) {
        log.debug("REST request to get DroitsStrategieApa : {}", id);
        Optional<DroitsStrategieApa> droitsStrategieApa = droitsStrategieApaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitsStrategieApa);
    }

    /**
     * {@code DELETE  /droits-strategie-apas/:id} : delete the "id" droitsStrategieApa.
     *
     * @param id the id of the droitsStrategieApa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/droits-strategie-apas/{id}")
    public ResponseEntity<Void> deleteDroitsStrategieApa(@PathVariable Long id) {
        log.debug("REST request to delete DroitsStrategieApa : {}", id);
        droitsStrategieApaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
