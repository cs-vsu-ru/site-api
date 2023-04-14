package cs.vsu.is.web.rest;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.repository.EduSchedulePlaceRepository;
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
 * REST controller for managing {@link cs.vsu.is.domain.EduSchedulePlace}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EduSchedulePlaceResource {

    private final Logger log = LoggerFactory.getLogger(EduSchedulePlaceResource.class);

    private static final String ENTITY_NAME = "eduSchedulePlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EduSchedulePlaceRepository eduSchedulePlaceRepository;

    public EduSchedulePlaceResource(EduSchedulePlaceRepository eduSchedulePlaceRepository) {
        this.eduSchedulePlaceRepository = eduSchedulePlaceRepository;
    }

    /**
     * {@code POST  /edu-schedule-places} : Create a new eduSchedulePlace.
     *
     * @param eduSchedulePlace the eduSchedulePlace to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eduSchedulePlace, or with status {@code 400 (Bad Request)} if the eduSchedulePlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/edu-schedule-places")
    public ResponseEntity<EduSchedulePlace> createEduSchedulePlace(@RequestBody EduSchedulePlace eduSchedulePlace)
        throws URISyntaxException {
        log.debug("REST request to save EduSchedulePlace : {}", eduSchedulePlace);
        if (eduSchedulePlace.getId() != null) {
            throw new BadRequestAlertException("A new eduSchedulePlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EduSchedulePlace result = eduSchedulePlaceRepository.save(eduSchedulePlace);
        return ResponseEntity
            .created(new URI("/api/edu-schedule-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /edu-schedule-places/:id} : Updates an existing eduSchedulePlace.
     *
     * @param id the id of the eduSchedulePlace to save.
     * @param eduSchedulePlace the eduSchedulePlace to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eduSchedulePlace,
     * or with status {@code 400 (Bad Request)} if the eduSchedulePlace is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eduSchedulePlace couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/edu-schedule-places/{id}")
    public ResponseEntity<EduSchedulePlace> updateEduSchedulePlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EduSchedulePlace eduSchedulePlace
    ) throws URISyntaxException {
        log.debug("REST request to update EduSchedulePlace : {}, {}", id, eduSchedulePlace);
        if (eduSchedulePlace.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eduSchedulePlace.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eduSchedulePlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EduSchedulePlace result = eduSchedulePlaceRepository.save(eduSchedulePlace);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eduSchedulePlace.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /edu-schedule-places/:id} : Partial updates given fields of an existing eduSchedulePlace, field will ignore if it is null
     *
     * @param id the id of the eduSchedulePlace to save.
     * @param eduSchedulePlace the eduSchedulePlace to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eduSchedulePlace,
     * or with status {@code 400 (Bad Request)} if the eduSchedulePlace is not valid,
     * or with status {@code 404 (Not Found)} if the eduSchedulePlace is not found,
     * or with status {@code 500 (Internal Server Error)} if the eduSchedulePlace couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/edu-schedule-places/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EduSchedulePlace> partialUpdateEduSchedulePlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EduSchedulePlace eduSchedulePlace
    ) throws URISyntaxException {
        log.debug("REST request to partial update EduSchedulePlace partially : {}, {}", id, eduSchedulePlace);
        if (eduSchedulePlace.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eduSchedulePlace.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eduSchedulePlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EduSchedulePlace> result = eduSchedulePlaceRepository
            .findById(eduSchedulePlace.getId())
            .map(existingEduSchedulePlace -> {
                if (eduSchedulePlace.getIsDenominator() != null) {
                    existingEduSchedulePlace.setIsDenominator(eduSchedulePlace.getIsDenominator());
                }
                if (eduSchedulePlace.getStartTime() != null) {
                    existingEduSchedulePlace.setStartTime(eduSchedulePlace.getStartTime());
                }
                if (eduSchedulePlace.getEndTime() != null) {
                    existingEduSchedulePlace.setEndTime(eduSchedulePlace.getEndTime());
                }
                if (eduSchedulePlace.getDayOfWeak() != null) {
                    existingEduSchedulePlace.setDayOfWeak(eduSchedulePlace.getDayOfWeak());
                }

                return existingEduSchedulePlace;
            })
            .map(eduSchedulePlaceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eduSchedulePlace.getId().toString())
        );
    }

    /**
     * {@code GET  /edu-schedule-places} : get all the eduSchedulePlaces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eduSchedulePlaces in body.
     */
    @GetMapping("/edu-schedule-places")
    public List<EduSchedulePlace> getAllEduSchedulePlaces() {
        log.debug("REST request to get all EduSchedulePlaces");
        return eduSchedulePlaceRepository.findAll();
    }

    /**
     * {@code GET  /edu-schedule-places/:id} : get the "id" eduSchedulePlace.
     *
     * @param id the id of the eduSchedulePlace to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eduSchedulePlace, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/edu-schedule-places/{id}")
    public ResponseEntity<EduSchedulePlace> getEduSchedulePlace(@PathVariable Long id) {
        log.debug("REST request to get EduSchedulePlace : {}", id);
        Optional<EduSchedulePlace> eduSchedulePlace = eduSchedulePlaceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eduSchedulePlace);
    }

    /**
     * {@code DELETE  /edu-schedule-places/:id} : delete the "id" eduSchedulePlace.
     *
     * @param id the id of the eduSchedulePlace to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/edu-schedule-places/{id}")
    public ResponseEntity<Void> deleteEduSchedulePlace(@PathVariable Long id) {
        log.debug("REST request to delete EduSchedulePlace : {}", id);
        eduSchedulePlaceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
