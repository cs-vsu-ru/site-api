package cs.vsu.is.web.rest;

import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.repository.ScientificLeadershipsRepository;
import cs.vsu.is.service.ScientificLeadershipsService;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.ScientificLeaderships}.
 */
@RestController
@RequestMapping("/api")
public class ScientificLeadershipsResource {

    private final Logger log = LoggerFactory.getLogger(ScientificLeadershipsResource.class);

    private static final String ENTITY_NAME = "scientificLeaderships";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScientificLeadershipsService scientificLeadershipsService;

    private final ScientificLeadershipsRepository scientificLeadershipsRepository;

    public ScientificLeadershipsResource(
        ScientificLeadershipsService scientificLeadershipsService,
        ScientificLeadershipsRepository scientificLeadershipsRepository
    ) {
        this.scientificLeadershipsService = scientificLeadershipsService;
        this.scientificLeadershipsRepository = scientificLeadershipsRepository;
    }

    /**
     * {@code POST  /scientific-leaderships} : Create a new scientificLeaderships.
     *
     * @param scientificLeaderships the scientificLeaderships to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scientificLeaderships, or with status {@code 400 (Bad Request)} if the scientificLeaderships has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scientific-leaderships")
    public ResponseEntity<ScientificLeaderships> createScientificLeaderships(@RequestBody ScientificLeaderships scientificLeaderships)
        throws URISyntaxException {
        log.debug("REST request to save ScientificLeaderships : {}", scientificLeaderships);
        if (scientificLeaderships.getId() != null) {
            throw new BadRequestAlertException("A new scientificLeaderships cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScientificLeaderships result = scientificLeadershipsService.save(scientificLeaderships);
        return ResponseEntity
            .created(new URI("/api/scientific-leaderships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scientific-leaderships/:id} : Updates an existing scientificLeaderships.
     *
     * @param id the id of the scientificLeaderships to save.
     * @param scientificLeaderships the scientificLeaderships to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificLeaderships,
     * or with status {@code 400 (Bad Request)} if the scientificLeaderships is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scientificLeaderships couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scientific-leaderships/{id}")
    public ResponseEntity<ScientificLeaderships> updateScientificLeaderships(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificLeaderships scientificLeaderships
    ) throws URISyntaxException {
        log.debug("REST request to update ScientificLeaderships : {}, {}", id, scientificLeaderships);
        if (scientificLeaderships.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificLeaderships.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificLeadershipsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScientificLeaderships result = scientificLeadershipsService.update(scientificLeaderships);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificLeaderships.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scientific-leaderships/:id} : Partial updates given fields of an existing scientificLeaderships, field will ignore if it is null
     *
     * @param id the id of the scientificLeaderships to save.
     * @param scientificLeaderships the scientificLeaderships to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificLeaderships,
     * or with status {@code 400 (Bad Request)} if the scientificLeaderships is not valid,
     * or with status {@code 404 (Not Found)} if the scientificLeaderships is not found,
     * or with status {@code 500 (Internal Server Error)} if the scientificLeaderships couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scientific-leaderships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScientificLeaderships> partialUpdateScientificLeaderships(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificLeaderships scientificLeaderships
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScientificLeaderships partially : {}, {}", id, scientificLeaderships);
        if (scientificLeaderships.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificLeaderships.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificLeadershipsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScientificLeaderships> result = scientificLeadershipsService.partialUpdate(scientificLeaderships);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificLeaderships.getId().toString())
        );
    }

    /**
     * {@code GET  /scientific-leaderships} : get all the scientificLeaderships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scientificLeaderships in body.
     */
    @GetMapping("/scientific-leaderships")
    public List<ScientificLeaderships> getAllScientificLeaderships() {
        log.debug("REST request to get all ScientificLeaderships");
        return scientificLeadershipsService.findAll();
    }

    /**
     * {@code GET  /scientific-leaderships/:id} : get the "id" scientificLeaderships.
     *
     * @param id the id of the scientificLeaderships to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scientificLeaderships, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scientific-leaderships/{id}")
    public ResponseEntity<ScientificLeaderships> getScientificLeaderships(@PathVariable Long id) {
        log.debug("REST request to get ScientificLeaderships : {}", id);
        Optional<ScientificLeaderships> scientificLeaderships = scientificLeadershipsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scientificLeaderships);
    }

    /**
     * {@code DELETE  /scientific-leaderships/:id} : delete the "id" scientificLeaderships.
     *
     * @param id the id of the scientificLeaderships to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scientific-leaderships/{id}")
    public ResponseEntity<Void> deleteScientificLeaderships(@PathVariable Long id) {
        log.debug("REST request to delete ScientificLeaderships : {}", id);
        scientificLeadershipsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
