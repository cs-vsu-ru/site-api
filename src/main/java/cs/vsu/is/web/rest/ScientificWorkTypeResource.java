package cs.vsu.is.web.rest;

import cs.vsu.is.repository.ScientificWorkTypeRepository;
import cs.vsu.is.service.ScientificWorkTypeService;
import cs.vsu.is.service.dto.ScientificWorkTypeDTO;
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
 * REST controller for managing {@link cs.vsu.is.domain.ScientificWorkType}.
 */
@RestController
@RequestMapping("/api")
public class ScientificWorkTypeResource {

  private final Logger log = LoggerFactory.getLogger(ScientificWorkTypeResource.class);

  private static final String ENTITY_NAME = "scientificWorkType";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ScientificWorkTypeService scientificWorkTypeService;

  private final ScientificWorkTypeRepository scientificWorkTypeRepository;

  public ScientificWorkTypeResource(
      ScientificWorkTypeService scientificWorkTypeService,
      ScientificWorkTypeRepository scientificWorkTypeRepository) {
    this.scientificWorkTypeService = scientificWorkTypeService;
    this.scientificWorkTypeRepository = scientificWorkTypeRepository;
  }

  /**
   * {@code POST  /scientific-work-types} : Create a new scientificWorkType.
   *
   * @param scientificWorkTypeDTO the scientificWorkTypeDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new scientificWorkTypeDTO, or with status
   *         {@code 400 (Bad Request)} if the scientificWorkType has already an
   *         ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/scientific-work-types")
  public ResponseEntity<ScientificWorkTypeDTO> createScientificWorkType(
      @RequestBody ScientificWorkTypeDTO scientificWorkTypeDTO)
      throws URISyntaxException {
    log.debug("REST request to save ScientificWorkType : {}", scientificWorkTypeDTO);
    if (scientificWorkTypeDTO.getId() != null) {
      throw new BadRequestAlertException("A new scientificWorkType cannot already have an ID", ENTITY_NAME, "idexists");
    }
    ScientificWorkTypeDTO result = scientificWorkTypeService.save(scientificWorkTypeDTO);
    return ResponseEntity
        .created(new URI("/api/scientific-work-types/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /scientific-work-types/:id} : Updates an existing
   * scientificWorkType.
   *
   * @param id                    the id of the scientificWorkTypeDTO to save.
   * @param scientificWorkTypeDTO the scientificWorkTypeDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated scientificWorkTypeDTO,
   *         or with status {@code 400 (Bad Request)} if the scientificWorkTypeDTO
   *         is not valid,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         scientificWorkTypeDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/scientific-work-types/{id}")
  public ResponseEntity<ScientificWorkTypeDTO> updateScientificWorkType(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody ScientificWorkTypeDTO scientificWorkTypeDTO) throws URISyntaxException {
    log.debug("REST request to update ScientificWorkType : {}, {}", id, scientificWorkTypeDTO);
    if (scientificWorkTypeDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, scientificWorkTypeDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!scientificWorkTypeRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    ScientificWorkTypeDTO result = scientificWorkTypeService.update(scientificWorkTypeDTO);
    return ResponseEntity
        .ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
            scientificWorkTypeDTO.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /scientific-work-types/:id} : Partial updates given fields of
   * an existing scientificWorkType, field will ignore if it is null
   *
   * @param id                    the id of the scientificWorkTypeDTO to save.
   * @param scientificWorkTypeDTO the scientificWorkTypeDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated scientificWorkTypeDTO,
   *         or with status {@code 400 (Bad Request)} if the scientificWorkTypeDTO
   *         is not valid,
   *         or with status {@code 404 (Not Found)} if the scientificWorkTypeDTO
   *         is not found,
   *         or with status {@code 500 (Internal Server Error)} if the
   *         scientificWorkTypeDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  // @PatchMapping(value = "/scientific-work-types/{id}", consumes = {
  // "application/json", "application/merge-patch+json" })
  // public ResponseEntity<ScientificWorkTypeDTO> partialUpdateScientificWorkType(
  // @PathVariable(value = "id", required = false) final Long id,
  // @RequestBody ScientificWorkTypeDTO scientificWorkTypeDTO
  // ) throws URISyntaxException {
  // log.debug("REST request to partial update ScientificWorkType partially : {},
  // {}", id, scientificWorkTypeDTO);
  // if (scientificWorkTypeDTO.getId() == null) {
  // throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
  // }
  // if (!Objects.equals(id, scientificWorkTypeDTO.getId())) {
  // throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
  // }

  // if (!scientificWorkTypeRepository.existsById(id)) {
  // throw new BadRequestAlertException("Entity not found", ENTITY_NAME,
  // "idnotfound");
  // }

  // Optional<ScientificWorkTypeDTO> result =
  // scientificWorkTypeService.partialUpdate(scientificWorkTypeDTO);

  // return ResponseUtil.wrapOrNotFound(
  // result,
  // HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
  // scientificWorkTypeDTO.getId().toString())
  // );
  // }

  /**
   * {@code GET  /scientific-work-types} : get all the scientificWorkTypes.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of scientificWorkTypes in body.
   */
  @GetMapping("/scientific-work-types")
  public List<ScientificWorkTypeDTO> getAllScientificWorkTypes() {
    log.debug("REST request to get all ScientificWorkTypes");
    return scientificWorkTypeService.findAll();
  }

  /**
   * {@code GET  /scientific-work-types/:id} : get the "id" scientificWorkType.
   *
   * @param id the id of the scientificWorkTypeDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the scientificWorkTypeDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/scientific-work-types/{id}")
  public ResponseEntity<ScientificWorkTypeDTO> getScientificWorkType(@PathVariable Long id) {
    log.debug("REST request to get ScientificWorkType : {}", id);
    Optional<ScientificWorkTypeDTO> scientificWorkTypeDTO = scientificWorkTypeService.findOne(id);
    return ResponseUtil.wrapOrNotFound(scientificWorkTypeDTO);
  }

  /**
   * {@code DELETE  /scientific-work-types/:id} : delete the "id"
   * scientificWorkType.
   *
   * @param id the id of the scientificWorkTypeDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/scientific-work-types/{id}")
  public ResponseEntity<Void> deleteScientificWorkType(@PathVariable Long id) {
    log.debug("REST request to delete ScientificWorkType : {}", id);
    scientificWorkTypeService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }
}
