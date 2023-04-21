package cs.vsu.is.web.rest;

import cs.vsu.is.repository.EduSchedulePlaceRepository;
import cs.vsu.is.service.EduSchedulePlaceService;
import cs.vsu.is.service.dto.EduSchedulePlaceDTO;
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
 * REST controller for managing {@link cs.vsu.is.domain.EduSchedulePlace}.
 */
@RestController
@RequestMapping("/api")
public class EduSchedulePlaceResource {

  private final Logger log = LoggerFactory.getLogger(EduSchedulePlaceResource.class);

  private static final String ENTITY_NAME = "eduSchedulePlace";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final EduSchedulePlaceService eduSchedulePlaceService;

  private final EduSchedulePlaceRepository eduSchedulePlaceRepository;

  public EduSchedulePlaceResource(
      EduSchedulePlaceService eduSchedulePlaceService,
      EduSchedulePlaceRepository eduSchedulePlaceRepository) {
    this.eduSchedulePlaceService = eduSchedulePlaceService;
    this.eduSchedulePlaceRepository = eduSchedulePlaceRepository;
  }

  /**
   * {@code POST  /edu-schedule-places} : Create a new eduSchedulePlace.
   *
   * @param eduSchedulePlaceDTO the eduSchedulePlaceDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new eduSchedulePlaceDTO, or with status
   *         {@code 400 (Bad Request)} if the eduSchedulePlace has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/edu-schedule-places")
  public ResponseEntity<EduSchedulePlaceDTO> createEduSchedulePlace(
      @RequestBody EduSchedulePlaceDTO eduSchedulePlaceDTO)
      throws URISyntaxException {
    log.debug("REST request to save EduSchedulePlace : {}", eduSchedulePlaceDTO);
    if (eduSchedulePlaceDTO.getId() != null) {
      throw new BadRequestAlertException("A new eduSchedulePlace cannot already have an ID", ENTITY_NAME, "idexists");
    }
    EduSchedulePlaceDTO result = eduSchedulePlaceService.save(eduSchedulePlaceDTO);
    return ResponseEntity
        .created(new URI("/api/edu-schedule-places/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /edu-schedule-places/:id} : Updates an existing eduSchedulePlace.
   *
   * @param id                  the id of the eduSchedulePlaceDTO to save.
   * @param eduSchedulePlaceDTO the eduSchedulePlaceDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated eduSchedulePlaceDTO,
   *         or with status {@code 400 (Bad Request)} if the eduSchedulePlaceDTO
   *         is not valid,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         eduSchedulePlaceDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/edu-schedule-places/{id}")
  public ResponseEntity<EduSchedulePlaceDTO> updateEduSchedulePlace(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody EduSchedulePlaceDTO eduSchedulePlaceDTO) throws URISyntaxException {
    log.debug("REST request to update EduSchedulePlace : {}, {}", id, eduSchedulePlaceDTO);
    if (eduSchedulePlaceDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, eduSchedulePlaceDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!eduSchedulePlaceRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    EduSchedulePlaceDTO result = eduSchedulePlaceService.update(eduSchedulePlaceDTO);
    return ResponseEntity
        .ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
            eduSchedulePlaceDTO.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /edu-schedule-places/:id} : Partial updates given fields of an
   * existing eduSchedulePlace, field will ignore if it is null
   *
   * @param id                  the id of the eduSchedulePlaceDTO to save.
   * @param eduSchedulePlaceDTO the eduSchedulePlaceDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated eduSchedulePlaceDTO,
   *         or with status {@code 400 (Bad Request)} if the eduSchedulePlaceDTO
   *         is not valid,
   *         or with status {@code 404 (Not Found)} if the eduSchedulePlaceDTO is
   *         not found,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         eduSchedulePlaceDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  // @PatchMapping(value = "/edu-schedule-places/{id}", consumes = {
  // "application/json", "application/merge-patch+json" })
  // public ResponseEntity<EduSchedulePlaceDTO> partialUpdateEduSchedulePlace(
  // @PathVariable(value = "id", required = false) final Long id,
  // @RequestBody EduSchedulePlaceDTO eduSchedulePlaceDTO
  // ) throws URISyntaxException {
  // log.debug("REST request to partial update EduSchedulePlace partially : {},
  // {}", id, eduSchedulePlaceDTO);
  // if (eduSchedulePlaceDTO.getId() == null) {
  // throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
  // }
  // if (!Objects.equals(id, eduSchedulePlaceDTO.getId())) {
  // throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
  // }

  // if (!eduSchedulePlaceRepository.existsById(id)) {
  // throw new BadRequestAlertException("Entity not found", ENTITY_NAME,
  // "idnotfound");
  // }

  // Optional<EduSchedulePlaceDTO> result =
  // eduSchedulePlaceService.partialUpdate(eduSchedulePlaceDTO);

  // return ResponseUtil.wrapOrNotFound(
  // result,
  // HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
  // eduSchedulePlaceDTO.getId().toString())
  // );
  // }

  /**
   * {@code GET  /edu-schedule-places} : get all the eduSchedulePlaces.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of eduSchedulePlaces in body.
   */
  @GetMapping("/edu-schedule-places")
  public List<EduSchedulePlaceDTO> getAllEduSchedulePlaces() {
    log.debug("REST request to get all EduSchedulePlaces");
    return eduSchedulePlaceService.findAll();
  }

  /**
   * {@code GET  /edu-schedule-places/:id} : get the "id" eduSchedulePlace.
   *
   * @param id the id of the eduSchedulePlaceDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the eduSchedulePlaceDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/edu-schedule-places/{id}")
  public ResponseEntity<EduSchedulePlaceDTO> getEduSchedulePlace(@PathVariable Long id) {
    log.debug("REST request to get EduSchedulePlace : {}", id);
    Optional<EduSchedulePlaceDTO> eduSchedulePlaceDTO = eduSchedulePlaceService.findOne(id);
    return ResponseUtil.wrapOrNotFound(eduSchedulePlaceDTO);
  }

  /**
   * {@code DELETE  /edu-schedule-places/:id} : delete the "id" eduSchedulePlace.
   *
   * @param id the id of the eduSchedulePlaceDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/edu-schedule-places/{id}")
  public ResponseEntity<Void> deleteEduSchedulePlace(@PathVariable Long id) {
    log.debug("REST request to delete EduSchedulePlace : {}", id);
    eduSchedulePlaceService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }
}
