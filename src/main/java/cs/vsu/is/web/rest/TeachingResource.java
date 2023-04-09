package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Teaching;
import cs.vsu.is.repository.TeachingRepository;
import cs.vsu.is.service.TeachingService;
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
 * REST controller for managing {@link cs.vsu.is.domain.Teaching}.
 */
@RestController
@RequestMapping("/api")
public class TeachingResource {

    private final Logger log = LoggerFactory.getLogger(TeachingResource.class);

    private static final String ENTITY_NAME = "teaching";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeachingService teachingService;

    private final TeachingRepository teachingRepository;

    public TeachingResource(TeachingService teachingService, TeachingRepository teachingRepository) {
        this.teachingService = teachingService;
        this.teachingRepository = teachingRepository;
    }

    /**
     * {@code POST  /teachings} : Create a new teaching.
     *
     * @param teaching the teaching to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teaching, or with status {@code 400 (Bad Request)} if the teaching has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachings")
    public ResponseEntity<Teaching> createTeaching(@RequestBody Teaching teaching) throws URISyntaxException {
        log.debug("REST request to save Teaching : {}", teaching);
        if (teaching.getId() != null) {
            throw new BadRequestAlertException("A new teaching cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Teaching result = teachingService.save(teaching);
        return ResponseEntity
            .created(new URI("/api/teachings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachings/:id} : Updates an existing teaching.
     *
     * @param id the id of the teaching to save.
     * @param teaching the teaching to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teaching,
     * or with status {@code 400 (Bad Request)} if the teaching is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teaching couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachings/{id}")
    public ResponseEntity<Teaching> updateTeaching(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Teaching teaching
    ) throws URISyntaxException {
        log.debug("REST request to update Teaching : {}, {}", id, teaching);
        if (teaching.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teaching.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Teaching result = teachingService.update(teaching);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teaching.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /teachings/:id} : Partial updates given fields of an existing teaching, field will ignore if it is null
     *
     * @param id the id of the teaching to save.
     * @param teaching the teaching to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teaching,
     * or with status {@code 400 (Bad Request)} if the teaching is not valid,
     * or with status {@code 404 (Not Found)} if the teaching is not found,
     * or with status {@code 500 (Internal Server Error)} if the teaching couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/teachings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Teaching> partialUpdateTeaching(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Teaching teaching
    ) throws URISyntaxException {
        log.debug("REST request to partial update Teaching partially : {}, {}", id, teaching);
        if (teaching.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teaching.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Teaching> result = teachingService.partialUpdate(teaching);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teaching.getId().toString())
        );
    }

    /**
     * {@code GET  /teachings} : get all the teachings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachings in body.
     */
    @GetMapping("/teachings")
    public List<Teaching> getAllTeachings() {
        log.debug("REST request to get all Teachings");
        return teachingService.findAll();
    }

    /**
     * {@code GET  /teachings/:id} : get the "id" teaching.
     *
     * @param id the id of the teaching to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teaching, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachings/{id}")
    public ResponseEntity<Teaching> getTeaching(@PathVariable Long id) {
        log.debug("REST request to get Teaching : {}", id);
        Optional<Teaching> teaching = teachingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teaching);
    }

    /**
     * {@code DELETE  /teachings/:id} : delete the "id" teaching.
     *
     * @param id the id of the teaching to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teachings/{id}")
    public ResponseEntity<Void> deleteTeaching(@PathVariable Long id) {
        log.debug("REST request to delete Teaching : {}", id);
        teachingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
