package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.DroitsStrategiePchE;
import fr.tixou.bca.repository.DroitsStrategiePchERepository;
import fr.tixou.bca.service.DroitsStrategiePchEService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.DroitsStrategiePchE}.
 */
@RestController
@RequestMapping("/api")
public class DroitsStrategiePchEResource {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategiePchEResource.class);

    private static final String ENTITY_NAME = "droitsStrategiePchE";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitsStrategiePchEService droitsStrategiePchEService;

    private final DroitsStrategiePchERepository droitsStrategiePchERepository;

    public DroitsStrategiePchEResource(
        DroitsStrategiePchEService droitsStrategiePchEService,
        DroitsStrategiePchERepository droitsStrategiePchERepository
    ) {
        this.droitsStrategiePchEService = droitsStrategiePchEService;
        this.droitsStrategiePchERepository = droitsStrategiePchERepository;
    }

    /**
     * {@code POST  /droits-strategie-pch-es} : Create a new droitsStrategiePchE.
     *
     * @param droitsStrategiePchE the droitsStrategiePchE to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droitsStrategiePchE, or with status {@code 400 (Bad Request)} if the droitsStrategiePchE has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/droits-strategie-pch-es")
    public ResponseEntity<DroitsStrategiePchE> createDroitsStrategiePchE(@Valid @RequestBody DroitsStrategiePchE droitsStrategiePchE)
        throws URISyntaxException {
        log.debug("REST request to save DroitsStrategiePchE : {}", droitsStrategiePchE);
        if (droitsStrategiePchE.getId() != null) {
            throw new BadRequestAlertException("A new droitsStrategiePchE cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitsStrategiePchE result = droitsStrategiePchEService.save(droitsStrategiePchE);
        return ResponseEntity
            .created(new URI("/api/droits-strategie-pch-es/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /droits-strategie-pch-es/:id} : Updates an existing droitsStrategiePchE.
     *
     * @param id the id of the droitsStrategiePchE to save.
     * @param droitsStrategiePchE the droitsStrategiePchE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategiePchE,
     * or with status {@code 400 (Bad Request)} if the droitsStrategiePchE is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategiePchE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/droits-strategie-pch-es/{id}")
    public ResponseEntity<DroitsStrategiePchE> updateDroitsStrategiePchE(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitsStrategiePchE droitsStrategiePchE
    ) throws URISyntaxException {
        log.debug("REST request to update DroitsStrategiePchE : {}, {}", id, droitsStrategiePchE);
        if (droitsStrategiePchE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategiePchE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategiePchERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DroitsStrategiePchE result = droitsStrategiePchEService.save(droitsStrategiePchE);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategiePchE.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /droits-strategie-pch-es/:id} : Partial updates given fields of an existing droitsStrategiePchE, field will ignore if it is null
     *
     * @param id the id of the droitsStrategiePchE to save.
     * @param droitsStrategiePchE the droitsStrategiePchE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategiePchE,
     * or with status {@code 400 (Bad Request)} if the droitsStrategiePchE is not valid,
     * or with status {@code 404 (Not Found)} if the droitsStrategiePchE is not found,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategiePchE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/droits-strategie-pch-es/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroitsStrategiePchE> partialUpdateDroitsStrategiePchE(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitsStrategiePchE droitsStrategiePchE
    ) throws URISyntaxException {
        log.debug("REST request to partial update DroitsStrategiePchE partially : {}, {}", id, droitsStrategiePchE);
        if (droitsStrategiePchE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategiePchE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategiePchERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DroitsStrategiePchE> result = droitsStrategiePchEService.partialUpdate(droitsStrategiePchE);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategiePchE.getId().toString())
        );
    }

    /**
     * {@code GET  /droits-strategie-pch-es} : get all the droitsStrategiePchES.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droitsStrategiePchES in body.
     */
    @GetMapping("/droits-strategie-pch-es")
    public List<DroitsStrategiePchE> getAllDroitsStrategiePchES() {
        log.debug("REST request to get all DroitsStrategiePchES");
        return droitsStrategiePchEService.findAll();
    }

    /**
     * {@code GET  /droits-strategie-pch-es/:id} : get the "id" droitsStrategiePchE.
     *
     * @param id the id of the droitsStrategiePchE to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droitsStrategiePchE, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/droits-strategie-pch-es/{id}")
    public ResponseEntity<DroitsStrategiePchE> getDroitsStrategiePchE(@PathVariable Long id) {
        log.debug("REST request to get DroitsStrategiePchE : {}", id);
        Optional<DroitsStrategiePchE> droitsStrategiePchE = droitsStrategiePchEService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitsStrategiePchE);
    }

    /**
     * {@code DELETE  /droits-strategie-pch-es/:id} : delete the "id" droitsStrategiePchE.
     *
     * @param id the id of the droitsStrategiePchE to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/droits-strategie-pch-es/{id}")
    public ResponseEntity<Void> deleteDroitsStrategiePchE(@PathVariable Long id) {
        log.debug("REST request to delete DroitsStrategiePchE : {}", id);
        droitsStrategiePchEService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
