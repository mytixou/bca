package fr.tixou.bca.web.rest;

import fr.tixou.bca.domain.Pec;
import fr.tixou.bca.repository.PecRepository;
import fr.tixou.bca.service.PecService;
import fr.tixou.bca.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
 * REST controller for managing {@link fr.tixou.bca.domain.Pec}.
 */
@RestController
@RequestMapping("/api")
public class PecResource {

    private final Logger log = LoggerFactory.getLogger(PecResource.class);

    private static final String ENTITY_NAME = "pec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PecService pecService;

    private final PecRepository pecRepository;

    public PecResource(PecService pecService, PecRepository pecRepository) {
        this.pecService = pecService;
        this.pecRepository = pecRepository;
    }

    /**
     * {@code POST  /pecs} : Create a new pec.
     *
     * @param pec the pec to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pec, or with status {@code 400 (Bad Request)} if the pec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pecs")
    public ResponseEntity<Pec> createPec(@Valid @RequestBody Pec pec) throws URISyntaxException {
        log.debug("REST request to save Pec : {}", pec);
        if (pec.getId() != null) {
            throw new BadRequestAlertException("A new pec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pec result = pecService.save(pec);
        return ResponseEntity
            .created(new URI("/api/pecs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pecs/:id} : Updates an existing pec.
     *
     * @param id the id of the pec to save.
     * @param pec the pec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pec,
     * or with status {@code 400 (Bad Request)} if the pec is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pecs/{id}")
    public ResponseEntity<Pec> updatePec(@PathVariable(value = "id", required = false) final UUID id, @Valid @RequestBody Pec pec)
        throws URISyntaxException {
        log.debug("REST request to update Pec : {}, {}", id, pec);
        if (pec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pec.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pec result = pecService.save(pec);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pec.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pecs/:id} : Partial updates given fields of an existing pec, field will ignore if it is null
     *
     * @param id the id of the pec to save.
     * @param pec the pec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pec,
     * or with status {@code 400 (Bad Request)} if the pec is not valid,
     * or with status {@code 404 (Not Found)} if the pec is not found,
     * or with status {@code 500 (Internal Server Error)} if the pec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pecs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pec> partialUpdatePec(@PathVariable(value = "id", required = false) final UUID id, @NotNull @RequestBody Pec pec)
        throws URISyntaxException {
        log.debug("REST request to partial update Pec partially : {}, {}", id, pec);
        if (pec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pec.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pec> result = pecService.partialUpdate(pec);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pec.getId().toString())
        );
    }

    /**
     * {@code GET  /pecs} : get all the pecs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pecs in body.
     */
    @GetMapping("/pecs")
    public List<Pec> getAllPecs() {
        log.debug("REST request to get all Pecs");
        return pecService.findAll();
    }

    /**
     * {@code GET  /pecs/:id} : get the "id" pec.
     *
     * @param id the id of the pec to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pec, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pecs/{id}")
    public ResponseEntity<Pec> getPec(@PathVariable UUID id) {
        log.debug("REST request to get Pec : {}", id);
        Optional<Pec> pec = pecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pec);
    }

    /**
     * {@code DELETE  /pecs/:id} : delete the "id" pec.
     *
     * @param id the id of the pec to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pecs/{id}")
    public ResponseEntity<Void> deletePec(@PathVariable UUID id) {
        log.debug("REST request to delete Pec : {}", id);
        pecService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
