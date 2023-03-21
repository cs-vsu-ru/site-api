package cs.vsu.is.web.rest;

import cs.vsu.is.domain.ScientificLeadeship;
import cs.vsu.is.repository.ScientificLeadeshipRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.ScientificLeadeship}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScientificLeadeshipResource {

    private final Logger log = LoggerFactory.getLogger(ScientificLeadeshipResource.class);

    private static final String ENTITY_NAME = "scientificLeadeship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScientificLeadeshipRepository scientificLeadeshipRepository;

    public ScientificLeadeshipResource(ScientificLeadeshipRepository scientificLeadeshipRepository) {
        this.scientificLeadeshipRepository = scientificLeadeshipRepository;
    }

    /**
     * {@code POST  /scientific-leadeships} : Create a new scientificLeadeship.
     *
     * @param scientificLeadeship the scientificLeadeship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scientificLeadeship, or with status {@code 400 (Bad Request)} if the scientificLeadeship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scientific-leadeships")
    public ResponseEntity<ScientificLeadeship> createScientificLeadeship(@RequestBody ScientificLeadeship scientificLeadeship)
        throws URISyntaxException {
        log.debug("REST request to save ScientificLeadeship : {}", scientificLeadeship);
        if (scientificLeadeship.getId() != null) {
            throw new BadRequestAlertException("A new scientificLeadeship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScientificLeadeship result = scientificLeadeshipRepository.save(scientificLeadeship);
        return ResponseEntity
            .created(new URI("/api/scientific-leadeships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scientific-leadeships/:id} : Updates an existing scientificLeadeship.
     *
     * @param id the id of the scientificLeadeship to save.
     * @param scientificLeadeship the scientificLeadeship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificLeadeship,
     * or with status {@code 400 (Bad Request)} if the scientificLeadeship is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scientificLeadeship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scientific-leadeships/{id}")
    public ResponseEntity<ScientificLeadeship> updateScientificLeadeship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificLeadeship scientificLeadeship
    ) throws URISyntaxException {
        log.debug("REST request to update ScientificLeadeship : {}, {}", id, scientificLeadeship);
        if (scientificLeadeship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificLeadeship.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificLeadeshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        // no save call needed as we have no fields that can be updated
        ScientificLeadeship result = scientificLeadeship;
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificLeadeship.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scientific-leadeships/:id} : Partial updates given fields of an existing scientificLeadeship, field will ignore if it is null
     *
     * @param id the id of the scientificLeadeship to save.
     * @param scientificLeadeship the scientificLeadeship to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificLeadeship,
     * or with status {@code 400 (Bad Request)} if the scientificLeadeship is not valid,
     * or with status {@code 404 (Not Found)} if the scientificLeadeship is not found,
     * or with status {@code 500 (Internal Server Error)} if the scientificLeadeship couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scientific-leadeships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScientificLeadeship> partialUpdateScientificLeadeship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificLeadeship scientificLeadeship
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScientificLeadeship partially : {}, {}", id, scientificLeadeship);
        if (scientificLeadeship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificLeadeship.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificLeadeshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScientificLeadeship> result = scientificLeadeshipRepository
            .findById(scientificLeadeship.getId())
            .map(existingScientificLeadeship -> {
                return existingScientificLeadeship;
            })// .map(scientificLeadeshipRepository::save)
        ;

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificLeadeship.getId().toString())
        );
    }

    /**
     * {@code GET  /scientific-leadeships} : get all the scientificLeadeships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scientificLeadeships in body.
     */
    @GetMapping("/scientific-leadeships")
    public List<ScientificLeadeship> getAllScientificLeadeships() {
        log.debug("REST request to get all ScientificLeadeships");
        return scientificLeadeshipRepository.findAll();
    }

    /**
     * {@code GET  /scientific-leadeships/:id} : get the "id" scientificLeadeship.
     *
     * @param id the id of the scientificLeadeship to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scientificLeadeship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scientific-leadeships/{id}")
    public ResponseEntity<ScientificLeadeship> getScientificLeadeship(@PathVariable Long id) {
        log.debug("REST request to get ScientificLeadeship : {}", id);
        Optional<ScientificLeadeship> scientificLeadeship = scientificLeadeshipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scientificLeadeship);
    }

    /**
     * {@code DELETE  /scientific-leadeships/:id} : delete the "id" scientificLeadeship.
     *
     * @param id the id of the scientificLeadeship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scientific-leadeships/{id}")
    public ResponseEntity<Void> deleteScientificLeadeship(@PathVariable Long id) {
        log.debug("REST request to delete ScientificLeadeship : {}", id);
        scientificLeadeshipRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
