package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.DroitAide;
import fr.tixou.bca.repository.DroitAideRepository;
import fr.tixou.bca.service.DroitAideService;
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
 * REST controller for managing {@link fr.tixou.bca.domain.DroitAide}.
 */
@RestController
@RequestMapping("/api")
public class DroitAideResource {

    private final Logger log = LoggerFactory.getLogger(DroitAideResource.class);

    private static final String ENTITY_NAME = "droitAide";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitAideService droitAideService;

    private final DroitAideRepository droitAideRepository;

    public DroitAideResource(DroitAideService droitAideService, DroitAideRepository droitAideRepository) {
        this.droitAideService = droitAideService;
        this.droitAideRepository = droitAideRepository;
    }

    /**
     * {@code POST  /droit-aides} : Create a new droitAide.
     *
     * @param droitAide the droitAide to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droitAide, or with status {@code 400 (Bad Request)} if the droitAide has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/droit-aides")
    public ResponseEntity<DroitAide> createDroitAide(@Valid @RequestBody DroitAide droitAide) throws URISyntaxException {
        log.debug("REST request to save DroitAide : {}", droitAide);
        if (droitAide.getId() != null) {
            throw new BadRequestAlertException("A new droitAide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitAide result = droitAideService.save(droitAide);
        return ResponseEntity
            .created(new URI("/api/droit-aides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /droit-aides/:id} : Updates an existing droitAide.
     *
     * @param id the id of the droitAide to save.
     * @param droitAide the droitAide to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitAide,
     * or with status {@code 400 (Bad Request)} if the droitAide is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droitAide couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/droit-aides/{id}")
    public ResponseEntity<DroitAide> updateDroitAide(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitAide droitAide
    ) throws URISyntaxException {
        log.debug("REST request to update DroitAide : {}, {}", id, droitAide);
        if (droitAide.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitAide.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitAideRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DroitAide result = droitAideService.save(droitAide);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitAide.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /droit-aides/:id} : Partial updates given fields of an existing droitAide, field will ignore if it is null
     *
     * @param id the id of the droitAide to save.
     * @param droitAide the droitAide to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitAide,
     * or with status {@code 400 (Bad Request)} if the droitAide is not valid,
     * or with status {@code 404 (Not Found)} if the droitAide is not found,
     * or with status {@code 500 (Internal Server Error)} if the droitAide couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/droit-aides/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroitAide> partialUpdateDroitAide(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitAide droitAide
    ) throws URISyntaxException {
        log.debug("REST request to partial update DroitAide partially : {}, {}", id, droitAide);
        if (droitAide.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitAide.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droitAideRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DroitAide> result = droitAideService.partialUpdate(droitAide);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitAide.getId().toString())
        );
    }

    /**
     * {@code GET  /droit-aides} : get all the droitAides.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droitAides in body.
     */
    @GetMapping("/droit-aides")
    public List<DroitAide> getAllDroitAides() {
        log.debug("REST request to get all DroitAides");
        return droitAideService.findAll();
    }

    /**
     * {@code GET  /droit-aides/:id} : get the "id" droitAide.
     *
     * @param id the id of the droitAide to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droitAide, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/droit-aides/{id}")
    public ResponseEntity<DroitAide> getDroitAide(@PathVariable Long id) {
        log.debug("REST request to get DroitAide : {}", id);
        Optional<DroitAide> droitAide = droitAideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitAide);
    }

    /**
     * {@code DELETE  /droit-aides/:id} : delete the "id" droitAide.
     *
     * @param id the id of the droitAide to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/droit-aides/{id}")
    public ResponseEntity<Void> deleteDroitAide(@PathVariable Long id) {
        log.debug("REST request to delete DroitAide : {}", id);
        droitAideService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
