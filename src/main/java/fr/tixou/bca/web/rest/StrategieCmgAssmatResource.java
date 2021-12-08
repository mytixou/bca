package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.StrategieCmgAssmat;
import fr.tixou.bca.repository.StrategieCmgAssmatRepository;
import fr.tixou.bca.service.StrategieCmgAssmatService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.StrategieCmgAssmat}.
 */
@RestController
@RequestMapping("/api")
public class StrategieCmgAssmatResource {

    private final Logger log = LoggerFactory.getLogger(StrategieCmgAssmatResource.class);

    private static final String ENTITY_NAME = "strategieCmgAssmat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrategieCmgAssmatService strategieCmgAssmatService;

    private final StrategieCmgAssmatRepository strategieCmgAssmatRepository;

    public StrategieCmgAssmatResource(
        StrategieCmgAssmatService strategieCmgAssmatService,
        StrategieCmgAssmatRepository strategieCmgAssmatRepository
    ) {
        this.strategieCmgAssmatService = strategieCmgAssmatService;
        this.strategieCmgAssmatRepository = strategieCmgAssmatRepository;
    }

    /**
     * {@code POST  /strategie-cmg-assmats} : Create a new strategieCmgAssmat.
     *
     * @param strategieCmgAssmat the strategieCmgAssmat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strategieCmgAssmat, or with status {@code 400 (Bad Request)} if the strategieCmgAssmat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strategie-cmg-assmats")
    public ResponseEntity<StrategieCmgAssmat> createStrategieCmgAssmat(@Valid @RequestBody StrategieCmgAssmat strategieCmgAssmat)
        throws URISyntaxException {
        log.debug("REST request to save StrategieCmgAssmat : {}", strategieCmgAssmat);
        if (strategieCmgAssmat.getId() != null) {
            throw new BadRequestAlertException("A new strategieCmgAssmat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrategieCmgAssmat result = strategieCmgAssmatService.save(strategieCmgAssmat);
        return ResponseEntity
            .created(new URI("/api/strategie-cmg-assmats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strategie-cmg-assmats/:id} : Updates an existing strategieCmgAssmat.
     *
     * @param id the id of the strategieCmgAssmat to save.
     * @param strategieCmgAssmat the strategieCmgAssmat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strategieCmgAssmat,
     * or with status {@code 400 (Bad Request)} if the strategieCmgAssmat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strategieCmgAssmat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strategie-cmg-assmats/{id}")
    public ResponseEntity<StrategieCmgAssmat> updateStrategieCmgAssmat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StrategieCmgAssmat strategieCmgAssmat
    ) throws URISyntaxException {
        log.debug("REST request to update StrategieCmgAssmat : {}, {}", id, strategieCmgAssmat);
        if (strategieCmgAssmat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strategieCmgAssmat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strategieCmgAssmatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StrategieCmgAssmat result = strategieCmgAssmatService.save(strategieCmgAssmat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strategieCmgAssmat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strategie-cmg-assmats/:id} : Partial updates given fields of an existing strategieCmgAssmat, field will ignore if it is null
     *
     * @param id the id of the strategieCmgAssmat to save.
     * @param strategieCmgAssmat the strategieCmgAssmat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strategieCmgAssmat,
     * or with status {@code 400 (Bad Request)} if the strategieCmgAssmat is not valid,
     * or with status {@code 404 (Not Found)} if the strategieCmgAssmat is not found,
     * or with status {@code 500 (Internal Server Error)} if the strategieCmgAssmat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strategie-cmg-assmats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StrategieCmgAssmat> partialUpdateStrategieCmgAssmat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StrategieCmgAssmat strategieCmgAssmat
    ) throws URISyntaxException {
        log.debug("REST request to partial update StrategieCmgAssmat partially : {}, {}", id, strategieCmgAssmat);
        if (strategieCmgAssmat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strategieCmgAssmat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strategieCmgAssmatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StrategieCmgAssmat> result = strategieCmgAssmatService.partialUpdate(strategieCmgAssmat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strategieCmgAssmat.getId().toString())
        );
    }

    /**
     * {@code GET  /strategie-cmg-assmats} : get all the strategieCmgAssmats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strategieCmgAssmats in body.
     */
    @GetMapping("/strategie-cmg-assmats")
    public List<StrategieCmgAssmat> getAllStrategieCmgAssmats() {
        log.debug("REST request to get all StrategieCmgAssmats");
        return strategieCmgAssmatService.findAll();
    }

    /**
     * {@code GET  /strategie-cmg-assmats/:id} : get the "id" strategieCmgAssmat.
     *
     * @param id the id of the strategieCmgAssmat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strategieCmgAssmat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strategie-cmg-assmats/{id}")
    public ResponseEntity<StrategieCmgAssmat> getStrategieCmgAssmat(@PathVariable Long id) {
        log.debug("REST request to get StrategieCmgAssmat : {}", id);
        Optional<StrategieCmgAssmat> strategieCmgAssmat = strategieCmgAssmatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strategieCmgAssmat);
    }

    /**
     * {@code DELETE  /strategie-cmg-assmats/:id} : delete the "id" strategieCmgAssmat.
     *
     * @param id the id of the strategieCmgAssmat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strategie-cmg-assmats/{id}")
    public ResponseEntity<Void> deleteStrategieCmgAssmat(@PathVariable Long id) {
        log.debug("REST request to delete StrategieCmgAssmat : {}", id);
        strategieCmgAssmatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
