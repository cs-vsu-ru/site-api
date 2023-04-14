package cs.vsu.is.web.rest;

import cs.vsu.is.repository.TeachingRepository;
import cs.vsu.is.service.TeachingService;
import cs.vsu.is.service.dto.TeachingDTO;
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
     * @param teachingDTO the teachingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachingDTO, or with status {@code 400 (Bad Request)} if the teaching has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachings")
    public ResponseEntity<TeachingDTO> createTeaching(@RequestBody TeachingDTO teachingDTO) throws URISyntaxException {
        log.debug("REST request to save Teaching : {}", teachingDTO);
        if (teachingDTO.getId() != null) {
            throw new BadRequestAlertException("A new teaching cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeachingDTO result = teachingService.save(teachingDTO);
        return ResponseEntity
            .created(new URI("/api/teachings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachings/:id} : Updates an existing teaching.
     *
     * @param id the id of the teachingDTO to save.
     * @param teachingDTO the teachingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachingDTO,
     * or with status {@code 400 (Bad Request)} if the teachingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachings/{id}")
    public ResponseEntity<TeachingDTO> updateTeaching(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeachingDTO teachingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Teaching : {}, {}", id, teachingDTO);
        if (teachingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teachingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeachingDTO result = teachingService.update(teachingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teachingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /teachings/:id} : Partial updates given fields of an existing teaching, field will ignore if it is null
     *
     * @param id the id of the teachingDTO to save.
     * @param teachingDTO the teachingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachingDTO,
     * or with status {@code 400 (Bad Request)} if the teachingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teachingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teachingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/teachings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeachingDTO> partialUpdateTeaching(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TeachingDTO teachingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Teaching partially : {}, {}", id, teachingDTO);
        if (teachingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teachingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeachingDTO> result = teachingService.partialUpdate(teachingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teachingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /teachings} : get all the teachings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachings in body.
     */
    @GetMapping("/teachings")
    public List<TeachingDTO> getAllTeachings() {
        log.debug("REST request to get all Teachings");
        return teachingService.findAll();
    }

    /**
     * {@code GET  /teachings/:id} : get the "id" teaching.
     *
     * @param id the id of the teachingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teachingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachings/{id}")
    public ResponseEntity<TeachingDTO> getTeaching(@PathVariable Long id) {
        log.debug("REST request to get Teaching : {}", id);
        Optional<TeachingDTO> teachingDTO = teachingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teachingDTO);
    }

    /**
     * {@code DELETE  /teachings/:id} : delete the "id" teaching.
     *
     * @param id the id of the teachingDTO to delete.
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
