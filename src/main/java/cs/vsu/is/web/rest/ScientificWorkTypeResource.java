package cs.vsu.is.web.rest;

import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.repository.ScientificWorkTypeRepository;
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
 * REST controller for managing {@link cs.vsu.is.domain.ScientificWorkType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScientificWorkTypeResource {

    private final Logger log = LoggerFactory.getLogger(ScientificWorkTypeResource.class);

    private static final String ENTITY_NAME = "scientificWorkType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScientificWorkTypeRepository scientificWorkTypeRepository;

    public ScientificWorkTypeResource(ScientificWorkTypeRepository scientificWorkTypeRepository) {
        this.scientificWorkTypeRepository = scientificWorkTypeRepository;
    }

    /**
     * {@code POST  /scientific-work-types} : Create a new scientificWorkType.
     *
     * @param scientificWorkType the scientificWorkType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scientificWorkType, or with status {@code 400 (Bad Request)} if the scientificWorkType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scientific-work-types")
    public ResponseEntity<ScientificWorkType> createScientificWorkType(@RequestBody ScientificWorkType scientificWorkType)
        throws URISyntaxException {
        log.debug("REST request to save ScientificWorkType : {}", scientificWorkType);
        if (scientificWorkType.getId() != null) {
            throw new BadRequestAlertException("A new scientificWorkType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScientificWorkType result = scientificWorkTypeRepository.save(scientificWorkType);
        return ResponseEntity
            .created(new URI("/api/scientific-work-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scientific-work-types/:id} : Updates an existing scientificWorkType.
     *
     * @param id the id of the scientificWorkType to save.
     * @param scientificWorkType the scientificWorkType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificWorkType,
     * or with status {@code 400 (Bad Request)} if the scientificWorkType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scientificWorkType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scientific-work-types/{id}")
    public ResponseEntity<ScientificWorkType> updateScientificWorkType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificWorkType scientificWorkType
    ) throws URISyntaxException {
        log.debug("REST request to update ScientificWorkType : {}, {}", id, scientificWorkType);
        if (scientificWorkType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificWorkType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificWorkTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScientificWorkType result = scientificWorkTypeRepository.save(scientificWorkType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificWorkType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scientific-work-types/:id} : Partial updates given fields of an existing scientificWorkType, field will ignore if it is null
     *
     * @param id the id of the scientificWorkType to save.
     * @param scientificWorkType the scientificWorkType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificWorkType,
     * or with status {@code 400 (Bad Request)} if the scientificWorkType is not valid,
     * or with status {@code 404 (Not Found)} if the scientificWorkType is not found,
     * or with status {@code 500 (Internal Server Error)} if the scientificWorkType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scientific-work-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScientificWorkType> partialUpdateScientificWorkType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificWorkType scientificWorkType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScientificWorkType partially : {}, {}", id, scientificWorkType);
        if (scientificWorkType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificWorkType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificWorkTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScientificWorkType> result = scientificWorkTypeRepository
            .findById(scientificWorkType.getId())
            .map(existingScientificWorkType -> {
                if (scientificWorkType.getName() != null) {
                    existingScientificWorkType.setName(scientificWorkType.getName());
                }

                return existingScientificWorkType;
            })
            .map(scientificWorkTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificWorkType.getId().toString())
        );
    }

    /**
     * {@code GET  /scientific-work-types} : get all the scientificWorkTypes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scientificWorkTypes in body.
     */
    @GetMapping("/scientific-work-types")
    public List<ScientificWorkType> getAllScientificWorkTypes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ScientificWorkTypes");
        if (eagerload) {
            return scientificWorkTypeRepository.findAllWithEagerRelationships();
        } else {
            return scientificWorkTypeRepository.findAll();
        }
    }

    /**
     * {@code GET  /scientific-work-types/:id} : get the "id" scientificWorkType.
     *
     * @param id the id of the scientificWorkType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scientificWorkType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scientific-work-types/{id}")
    public ResponseEntity<ScientificWorkType> getScientificWorkType(@PathVariable Long id) {
        log.debug("REST request to get ScientificWorkType : {}", id);
        Optional<ScientificWorkType> scientificWorkType = scientificWorkTypeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(scientificWorkType);
    }

    /**
     * {@code DELETE  /scientific-work-types/:id} : delete the "id" scientificWorkType.
     *
     * @param id the id of the scientificWorkType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scientific-work-types/{id}")
    public ResponseEntity<Void> deleteScientificWorkType(@PathVariable Long id) {
        log.debug("REST request to delete ScientificWorkType : {}", id);
        scientificWorkTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
