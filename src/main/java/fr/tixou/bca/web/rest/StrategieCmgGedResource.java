package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.StrategieCmgGed;
import fr.tixou.bca.repository.StrategieCmgGedRepository;
import fr.tixou.bca.service.StrategieCmgGedService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.StrategieCmgGed}.
 */
@RestController
@RequestMapping("/api")
public class StrategieCmgGedResource {

    private final Logger log = LoggerFactory.getLogger(StrategieCmgGedResource.class);

    private static final String ENTITY_NAME = "strategieCmgGed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrategieCmgGedService strategieCmgGedService;

    private final StrategieCmgGedRepository strategieCmgGedRepository;

    public StrategieCmgGedResource(StrategieCmgGedService strategieCmgGedService, StrategieCmgGedRepository strategieCmgGedRepository) {
        this.strategieCmgGedService = strategieCmgGedService;
        this.strategieCmgGedRepository = strategieCmgGedRepository;
    }

    /**
     * {@code POST  /strategie-cmg-geds} : Create a new strategieCmgGed.
     *
     * @param strategieCmgGed the strategieCmgGed to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strategieCmgGed, or with status {@code 400 (Bad Request)} if the strategieCmgGed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strategie-cmg-geds")
    public ResponseEntity<StrategieCmgGed> createStrategieCmgGed(@Valid @RequestBody StrategieCmgGed strategieCmgGed)
        throws URISyntaxException {
        log.debug("REST request to save StrategieCmgGed : {}", strategieCmgGed);
        if (strategieCmgGed.getId() != null) {
            throw new BadRequestAlertException("A new strategieCmgGed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrategieCmgGed result = strategieCmgGedService.save(strategieCmgGed);
        return ResponseEntity
            .created(new URI("/api/strategie-cmg-geds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strategie-cmg-geds/:id} : Updates an existing strategieCmgGed.
     *
     * @param id the id of the strategieCmgGed to save.
     * @param strategieCmgGed the strategieCmgGed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strategieCmgGed,
     * or with status {@code 400 (Bad Request)} if the strategieCmgGed is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strategieCmgGed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strategie-cmg-geds/{id}")
    public ResponseEntity<StrategieCmgGed> updateStrategieCmgGed(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StrategieCmgGed strategieCmgGed
    ) throws URISyntaxException {
        log.debug("REST request to update StrategieCmgGed : {}, {}", id, strategieCmgGed);
        if (strategieCmgGed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strategieCmgGed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strategieCmgGedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StrategieCmgGed result = strategieCmgGedService.save(strategieCmgGed);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strategieCmgGed.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strategie-cmg-geds/:id} : Partial updates given fields of an existing strategieCmgGed, field will ignore if it is null
     *
     * @param id the id of the strategieCmgGed to save.
     * @param strategieCmgGed the strategieCmgGed to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strategieCmgGed,
     * or with status {@code 400 (Bad Request)} if the strategieCmgGed is not valid,
     * or with status {@code 404 (Not Found)} if the strategieCmgGed is not found,
     * or with status {@code 500 (Internal Server Error)} if the strategieCmgGed couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strategie-cmg-geds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StrategieCmgGed> partialUpdateStrategieCmgGed(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StrategieCmgGed strategieCmgGed
    ) throws URISyntaxException {
        log.debug("REST request to partial update StrategieCmgGed partially : {}, {}", id, strategieCmgGed);
        if (strategieCmgGed.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strategieCmgGed.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strategieCmgGedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StrategieCmgGed> result = strategieCmgGedService.partialUpdate(strategieCmgGed);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strategieCmgGed.getId().toString())
        );
    }

    /**
     * {@code GET  /strategie-cmg-geds} : get all the strategieCmgGeds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strategieCmgGeds in body.
     */
    @GetMapping("/strategie-cmg-geds")
    public List<StrategieCmgGed> getAllStrategieCmgGeds() {
        log.debug("REST request to get all StrategieCmgGeds");
        return strategieCmgGedService.findAll();
    }

    /**
     * {@code GET  /strategie-cmg-geds/:id} : get the "id" strategieCmgGed.
     *
     * @param id the id of the strategieCmgGed to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strategieCmgGed, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strategie-cmg-geds/{id}")
    public ResponseEntity<StrategieCmgGed> getStrategieCmgGed(@PathVariable Long id) {
        log.debug("REST request to get StrategieCmgGed : {}", id);
        Optional<StrategieCmgGed> strategieCmgGed = strategieCmgGedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strategieCmgGed);
    }

    /**
     * {@code DELETE  /strategie-cmg-geds/:id} : delete the "id" strategieCmgGed.
     *
     * @param id the id of the strategieCmgGed to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strategie-cmg-geds/{id}")
    public ResponseEntity<Void> deleteStrategieCmgGed(@PathVariable Long id) {
        log.debug("REST request to delete StrategieCmgGed : {}", id);
        strategieCmgGedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
