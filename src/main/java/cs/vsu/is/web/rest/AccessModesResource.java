package cs.vsu.is.web.rest;

import cs.vsu.is.repository.AccessModesRepository;
import cs.vsu.is.service.AccessModesService;
import cs.vsu.is.service.dto.AccessModesDTO;
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
 * REST controller for managing {@link cs.vsu.is.domain.AccessModes}.
 */
@RestController
@RequestMapping("/api")
public class AccessModesResource {

    private final Logger log = LoggerFactory.getLogger(AccessModesResource.class);

    private static final String ENTITY_NAME = "accessModes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccessModesService accessModesService;

    private final AccessModesRepository accessModesRepository;

    public AccessModesResource(AccessModesService accessModesService, AccessModesRepository accessModesRepository) {
        this.accessModesService = accessModesService;
        this.accessModesRepository = accessModesRepository;
    }

    /**
     * {@code POST  /access-modes} : Create a new accessModes.
     *
     * @param accessModesDTO the accessModesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accessModesDTO, or with status {@code 400 (Bad Request)} if the accessModes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/access-modes")
    public ResponseEntity<AccessModesDTO> createAccessModes(@RequestBody AccessModesDTO accessModesDTO) throws URISyntaxException {
        log.debug("REST request to save AccessModes : {}", accessModesDTO);
        if (accessModesDTO.getId() != null) {
            throw new BadRequestAlertException("A new accessModes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccessModesDTO result = accessModesService.save(accessModesDTO);
        return ResponseEntity
            .created(new URI("/api/access-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /access-modes/:id} : Updates an existing accessModes.
     *
     * @param id the id of the accessModesDTO to save.
     * @param accessModesDTO the accessModesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accessModesDTO,
     * or with status {@code 400 (Bad Request)} if the accessModesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accessModesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/access-modes/{id}")
    public ResponseEntity<AccessModesDTO> updateAccessModes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccessModesDTO accessModesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccessModes : {}, {}", id, accessModesDTO);
        if (accessModesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accessModesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accessModesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccessModesDTO result = accessModesService.update(accessModesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accessModesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /access-modes/:id} : Partial updates given fields of an existing accessModes, field will ignore if it is null
     *
     * @param id the id of the accessModesDTO to save.
     * @param accessModesDTO the accessModesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accessModesDTO,
     * or with status {@code 400 (Bad Request)} if the accessModesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accessModesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accessModesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/access-modes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccessModesDTO> partialUpdateAccessModes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccessModesDTO accessModesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccessModes partially : {}, {}", id, accessModesDTO);
        if (accessModesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accessModesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accessModesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccessModesDTO> result = accessModesService.partialUpdate(accessModesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, accessModesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /access-modes} : get all the accessModes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accessModes in body.
     */
    @GetMapping("/access-modes")
    public List<AccessModesDTO> getAllAccessModes() {
        log.debug("REST request to get all AccessModes");
        return accessModesService.findAll();
    }

    /**
     * {@code GET  /access-modes/:id} : get the "id" accessModes.
     *
     * @param id the id of the accessModesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accessModesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/access-modes/{id}")
    public ResponseEntity<AccessModesDTO> getAccessModes(@PathVariable Long id) {
        log.debug("REST request to get AccessModes : {}", id);
        Optional<AccessModesDTO> accessModesDTO = accessModesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accessModesDTO);
    }

    /**
     * {@code DELETE  /access-modes/:id} : delete the "id" accessModes.
     *
     * @param id the id of the accessModesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/access-modes/{id}")
    public ResponseEntity<Void> deleteAccessModes(@PathVariable Long id) {
        log.debug("REST request to delete AccessModes : {}", id);
        accessModesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
