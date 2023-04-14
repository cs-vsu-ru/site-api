package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Specialities;
import cs.vsu.is.repository.SpecialitiesRepository;
import cs.vsu.is.service.SpecialitiesService;
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
 * REST controller for managing {@link cs.vsu.is.domain.Specialities}.
 */
@RestController
@RequestMapping("/api")
public class SpecialitiesResource {

    private final Logger log = LoggerFactory.getLogger(SpecialitiesResource.class);

    private static final String ENTITY_NAME = "specialities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialitiesService specialitiesService;

    private final SpecialitiesRepository specialitiesRepository;

    public SpecialitiesResource(SpecialitiesService specialitiesService, SpecialitiesRepository specialitiesRepository) {
        this.specialitiesService = specialitiesService;
        this.specialitiesRepository = specialitiesRepository;
    }

    /**
     * {@code POST  /specialities} : Create a new specialities.
     *
     * @param specialities the specialities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialities, or with status {@code 400 (Bad Request)} if the specialities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialities")
    public ResponseEntity<Specialities> createSpecialities(@RequestBody Specialities specialities) throws URISyntaxException {
        log.debug("REST request to save Specialities : {}", specialities);
        if (specialities.getId() != null) {
            throw new BadRequestAlertException("A new specialities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Specialities result = specialitiesService.save(specialities);
        return ResponseEntity
            .created(new URI("/api/specialities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialities/:id} : Updates an existing specialities.
     *
     * @param id the id of the specialities to save.
     * @param specialities the specialities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialities,
     * or with status {@code 400 (Bad Request)} if the specialities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialities/{id}")
    public ResponseEntity<Specialities> updateSpecialities(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Specialities specialities
    ) throws URISyntaxException {
        log.debug("REST request to update Specialities : {}, {}", id, specialities);
        if (specialities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialities.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Specialities result = specialitiesService.update(specialities);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specialities.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specialities/:id} : Partial updates given fields of an existing specialities, field will ignore if it is null
     *
     * @param id the id of the specialities to save.
     * @param specialities the specialities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialities,
     * or with status {@code 400 (Bad Request)} if the specialities is not valid,
     * or with status {@code 404 (Not Found)} if the specialities is not found,
     * or with status {@code 500 (Internal Server Error)} if the specialities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specialities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Specialities> partialUpdateSpecialities(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Specialities specialities
    ) throws URISyntaxException {
        log.debug("REST request to partial update Specialities partially : {}, {}", id, specialities);
        if (specialities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialities.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Specialities> result = specialitiesService.partialUpdate(specialities);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specialities.getId().toString())
        );
    }

    /**
     * {@code GET  /specialities} : get all the specialities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialities in body.
     */
    @GetMapping("/specialities")
    public List<Specialities> getAllSpecialities() {
        log.debug("REST request to get all Specialities");
        return specialitiesService.findAll();
    }

    /**
     * {@code GET  /specialities/:id} : get the "id" specialities.
     *
     * @param id the id of the specialities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialities/{id}")
    public ResponseEntity<Specialities> getSpecialities(@PathVariable Long id) {
        log.debug("REST request to get Specialities : {}", id);
        Optional<Specialities> specialities = specialitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialities);
    }

    /**
     * {@code DELETE  /specialities/:id} : delete the "id" specialities.
     *
     * @param id the id of the specialities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialities/{id}")
    public ResponseEntity<Void> deleteSpecialities(@PathVariable Long id) {
        log.debug("REST request to delete Specialities : {}", id);
        specialitiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
