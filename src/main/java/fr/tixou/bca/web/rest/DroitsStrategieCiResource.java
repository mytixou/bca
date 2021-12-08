package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.DroitsStrategieCi;
import fr.tixou.bca.repository.DroitsStrategieCiRepository;
import fr.tixou.bca.service.DroitsStrategieCiService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.DroitsStrategieCi}.
 */
@RestController
@RequestMapping("/api")
public class DroitsStrategieCiResource {

    private final Logger log = LoggerFactory.getLogger(DroitsStrategieCiResource.class);

    private static final String ENTITY_NAME = "droitsStrategieCi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitsStrategieCiService droitsStrategieCiService;

    private final DroitsStrategieCiRepository droitsStrategieCiRepository;

    public DroitsStrategieCiResource(
        DroitsStrategieCiService droitsStrategieCiService,
        DroitsStrategieCiRepository droitsStrategieCiRepository
    ) {
        this.droitsStrategieCiService = droitsStrategieCiService;
        this.droitsStrategieCiRepository = droitsStrategieCiRepository;
    }

    /**
     * {@code POST  /droits-strategie-cis} : Create a new droitsStrategieCi.
     *
     * @param droitsStrategieCi the droitsStrategieCi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droitsStrategieCi, or with status {@code 400 (Bad Request)} if the droitsStrategieCi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/droits-strategie-cis")
    public ResponseEntity<DroitsStrategieCi> createDroitsStrategieCi(@Valid @RequestBody DroitsStrategieCi droitsStrategieCi)
        throws URISyntaxException {
        log.debug("REST request to save DroitsStrategieCi : {}", droitsStrategieCi);
        if (droitsStrategieCi.getId() != null) {
            throw new BadRequestAlertException("A new droitsStrategieCi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitsStrategieCi result = droitsStrategieCiService.save(droitsStrategieCi);
        return ResponseEntity
            .created(new URI("/api/droits-strategie-cis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /droits-strategie-cis/:id} : Updates an existing droitsStrategieCi.
     *
     * @param id the id of the droitsStrategieCi to save.
     * @param droitsStrategieCi the droitsStrategieCi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategieCi,
     * or with status {@code 400 (Bad Request)} if the droitsStrategieCi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategieCi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/droits-strategie-cis/{id}")
    public ResponseEntity<DroitsStrategieCi> updateDroitsStrategieCi(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitsStrategieCi droitsStrategieCi
    ) throws URISyntaxException {
        log.debug("REST request to update DroitsStrategieCi : {}, {}", id, droitsStrategieCi);
        if (droitsStrategieCi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategieCi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategieCiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DroitsStrategieCi result = droitsStrategieCiService.save(droitsStrategieCi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategieCi.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /droits-strategie-cis/:id} : Partial updates given fields of an existing droitsStrategieCi, field will ignore if it is null
     *
     * @param id the id of the droitsStrategieCi to save.
     * @param droitsStrategieCi the droitsStrategieCi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitsStrategieCi,
     * or with status {@code 400 (Bad Request)} if the droitsStrategieCi is not valid,
     * or with status {@code 404 (Not Found)} if the droitsStrategieCi is not found,
     * or with status {@code 500 (Internal Server Error)} if the droitsStrategieCi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/droits-strategie-cis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroitsStrategieCi> partialUpdateDroitsStrategieCi(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitsStrategieCi droitsStrategieCi
    ) throws URISyntaxException {
        log.debug("REST request to partial update DroitsStrategieCi partially : {}, {}", id, droitsStrategieCi);
        if (droitsStrategieCi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitsStrategieCi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitsStrategieCiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DroitsStrategieCi> result = droitsStrategieCiService.partialUpdate(droitsStrategieCi);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitsStrategieCi.getId().toString())
        );
    }

    /**
     * {@code GET  /droits-strategie-cis} : get all the droitsStrategieCis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droitsStrategieCis in body.
     */
    @GetMapping("/droits-strategie-cis")
    public List<DroitsStrategieCi> getAllDroitsStrategieCis() {
        log.debug("REST request to get all DroitsStrategieCis");
        return droitsStrategieCiService.findAll();
    }

    /**
     * {@code GET  /droits-strategie-cis/:id} : get the "id" droitsStrategieCi.
     *
     * @param id the id of the droitsStrategieCi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droitsStrategieCi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/droits-strategie-cis/{id}")
    public ResponseEntity<DroitsStrategieCi> getDroitsStrategieCi(@PathVariable Long id) {
        log.debug("REST request to get DroitsStrategieCi : {}", id);
        Optional<DroitsStrategieCi> droitsStrategieCi = droitsStrategieCiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitsStrategieCi);
    }

    /**
     * {@code DELETE  /droits-strategie-cis/:id} : delete the "id" droitsStrategieCi.
     *
     * @param id the id of the droitsStrategieCi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/droits-strategie-cis/{id}")
    public ResponseEntity<Void> deleteDroitsStrategieCi(@PathVariable Long id) {
        log.debug("REST request to delete DroitsStrategieCi : {}", id);
        droitsStrategieCiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
