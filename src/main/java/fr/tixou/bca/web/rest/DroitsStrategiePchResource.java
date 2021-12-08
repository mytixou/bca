package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.DroitsStrategiePch;
import fr.tixou.bca.repository.DroitsStrategiePchRepository;
import fr.tixou.bca.service.DroitsStrategiePchService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.DroitsStrategiePch}.
 */
@RestController
@RequestMapping("/api")
public class DroitsStrategiePchResource {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategiePchResource.class);

    private static final String ENTITY_NAME = "droitsStrategiePch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitsStrategiePchService droitsStrategiePchService;

    private final DroitsStrategiePchRepository droitsStrategiePchRepository;

    public DroitsStrategiePchResource(
        DroitsStrategiePchService droitsStrategiePchService,
        DroitsStrategiePchRepository droitsStrategiePchRepository
    ) {
        this.droitsStrategiePchService = droitsStrategiePchService;
        this.droitsStrategiePchRepository = droitsStrategiePchRepository;
    }

    /**
     * {@code POST  /droits-strategie-pches} : Create a new droitsStrategiePch.
     *
     * @param droitsStrategiePch the droitsStrategiePch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droitsStrategiePch, or with status {@code 400 (Bad Request)} if the droitsStrategiePch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/droits-strategie-pches")
    public ResponseEntity<DroitsStrategiePch> createDroitsStrategiePch(@Valid @RequestBody DroitsStrategiePch droitsStrategiePch)
        throws URISyntaxException {
        log.debug("REST request to save DroitsStrategiePch : {}", droitsStrategiePch);
        if (droitsStrategiePch.getId() != null) {
            throw new BadRequestAlertException("A new droitsStrategiePch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitsStrategiePch result = droitsStrategiePchService.save(droitsStrategiePch);
        return ResponseEntity
            .created(new URI("/api/droits-strategie-pches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /droits-strategie-pches/:id} : Updates an existing droitsStrategiePch.
     *
     * @param id the id of the droitsStrategiePch to save.
     * @param droitsStrategiePch the droitsStrategiePch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategiePch,
     * or with status {@code 400 (Bad Request)} if the droitsStrategiePch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategiePch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/droits-strategie-pches/{id}")
    public ResponseEntity<DroitsStrategiePch> updateDroitsStrategiePch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitsStrategiePch droitsStrategiePch
    ) throws URISyntaxException {
        log.debug("REST request to update DroitsStrategiePch : {}, {}", id, droitsStrategiePch);
        if (droitsStrategiePch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategiePch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategiePchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DroitsStrategiePch result = droitsStrategiePchService.save(droitsStrategiePch);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategiePch.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /droits-strategie-pches/:id} : Partial updates given fields of an existing droitsStrategiePch, field will ignore if it is null
     *
     * @param id the id of the droitsStrategiePch to save.
     * @param droitsStrategiePch the droitsStrategiePch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategiePch,
     * or with status {@code 400 (Bad Request)} if the droitsStrategiePch is not valid,
     * or with status {@code 404 (Not Found)} if the droitsStrategiePch is not found,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategiePch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/droits-strategie-pches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroitsStrategiePch> partialUpdateDroitsStrategiePch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitsStrategiePch droitsStrategiePch
    ) throws URISyntaxException {
        log.debug("REST request to partial update DroitsStrategiePch partially : {}, {}", id, droitsStrategiePch);
        if (droitsStrategiePch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategiePch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategiePchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DroitsStrategiePch> result = droitsStrategiePchService.partialUpdate(droitsStrategiePch);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategiePch.getId().toString())
        );
    }

    /**
     * {@code GET  /droits-strategie-pches} : get all the droitsStrategiePches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droitsStrategiePches in body.
     */
    @GetMapping("/droits-strategie-pches")
    public List<DroitsStrategiePch> getAllDroitsStrategiePches() {
        log.debug("REST request to get all DroitsStrategiePches");
        return droitsStrategiePchService.findAll();
    }

    /**
     * {@code GET  /droits-strategie-pches/:id} : get the "id" droitsStrategiePch.
     *
     * @param id the id of the droitsStrategiePch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droitsStrategiePch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/droits-strategie-pches/{id}")
    public ResponseEntity<DroitsStrategiePch> getDroitsStrategiePch(@PathVariable Long id) {
        log.debug("REST request to get DroitsStrategiePch : {}", id);
        Optional<DroitsStrategiePch> droitsStrategiePch = droitsStrategiePchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitsStrategiePch);
    }

    /**
     * {@code DELETE  /droits-strategie-pches/:id} : delete the "id" droitsStrategiePch.
     *
     * @param id the id of the droitsStrategiePch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/droits-strategie-pches/{id}")
    public ResponseEntity<Void> deleteDroitsStrategiePch(@PathVariable Long id) {
        log.debug("REST request to delete DroitsStrategiePch : {}", id);
        droitsStrategiePchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
