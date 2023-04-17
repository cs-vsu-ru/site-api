package cs.vsu.is.web.rest;

import cs.vsu.is.repository.StudentsRepository;
import cs.vsu.is.service.StudentsService;
import cs.vsu.is.service.dto.StudentsDTO;
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
 * REST controller for managing {@link cs.vsu.is.domain.Students}.
 */
@RestController
@RequestMapping("/api")
public class StudentsResource {

  private final Logger log = LoggerFactory.getLogger(StudentsResource.class);

  private static final String ENTITY_NAME = "students";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final StudentsService studentsService;

  private final StudentsRepository studentsRepository;

  public StudentsResource(StudentsService studentsService, StudentsRepository studentsRepository) {
    this.studentsService = studentsService;
    this.studentsRepository = studentsRepository;
  }

  /**
   * {@code POST  /students} : Create a new students.
   *
   * @param studentsDTO the studentsDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new studentsDTO, or with status {@code 400 (Bad Request)} if
   *         the students has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/students")
  public ResponseEntity<StudentsDTO> createStudents(@RequestBody StudentsDTO studentsDTO) throws URISyntaxException {
    log.debug("REST request to save Students : {}", studentsDTO);
    if (studentsDTO.getId() != null) {
      throw new BadRequestAlertException("A new students cannot already have an ID", ENTITY_NAME, "idexists");
    }
    StudentsDTO result = studentsService.save(studentsDTO);
    return ResponseEntity
        .created(new URI("/api/students/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /students/:id} : Updates an existing students.
   *
   * @param id          the id of the studentsDTO to save.
   * @param studentsDTO the studentsDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated studentsDTO,
   *         or with status {@code 400 (Bad Request)} if the studentsDTO is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the studentsDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/students/{id}")
  public ResponseEntity<StudentsDTO> updateStudents(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody StudentsDTO studentsDTO) throws URISyntaxException {
    log.debug("REST request to update Students : {}, {}", id, studentsDTO);
    if (studentsDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, studentsDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!studentsRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    StudentsDTO result = studentsService.update(studentsDTO);
    return ResponseEntity
        .ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentsDTO.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /students/:id} : Partial updates given fields of an existing
   * students, field will ignore if it is null
   *
   * @param id          the id of the studentsDTO to save.
   * @param studentsDTO the studentsDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated studentsDTO,
   *         or with status {@code 400 (Bad Request)} if the studentsDTO is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the studentsDTO is not
   *         found,
   *         or with status {@code 500 (Internal Server Error)} if the studentsDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  // @PatchMapping(value = "/students/{id}", consumes = { "application/json",
  // "application/merge-patch+json" })
  // public ResponseEntity<StudentsDTO> partialUpdateStudents(
  // @PathVariable(value = "id", required = false) final Long id,
  // @RequestBody StudentsDTO studentsDTO
  // ) throws URISyntaxException {
  // log.debug("REST request to partial update Students partially : {}, {}", id,
  // studentsDTO);
  // if (studentsDTO.getId() == null) {
  // throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
  // }
  // if (!Objects.equals(id, studentsDTO.getId())) {
  // throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
  // }

  // if (!studentsRepository.existsById(id)) {
  // throw new BadRequestAlertException("Entity not found", ENTITY_NAME,
  // "idnotfound");
  // }

  // Optional<StudentsDTO> result = studentsService.partialUpdate(studentsDTO);

  // return ResponseUtil.wrapOrNotFound(
  // result,
  // HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
  // studentsDTO.getId().toString())
  // );
  // }

  /**
   * {@code GET  /students} : get all the students.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of students in body.
   */
  @GetMapping("/students")
  public List<StudentsDTO> getAllStudents() {
    log.debug("REST request to get all Students");
    return studentsService.findAll();
  }

  /**
   * {@code GET  /students/:id} : get the "id" students.
   *
   * @param id the id of the studentsDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the studentsDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/students/{id}")
  public ResponseEntity<StudentsDTO> getStudents(@PathVariable Long id) {
    log.debug("REST request to get Students : {}", id);
    Optional<StudentsDTO> studentsDTO = studentsService.findOne(id);
    return ResponseUtil.wrapOrNotFound(studentsDTO);
  }

  /**
   * {@code DELETE  /students/:id} : delete the "id" students.
   *
   * @param id the id of the studentsDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/students/{id}")
  public ResponseEntity<Void> deleteStudents(@PathVariable Long id) {
    log.debug("REST request to delete Students : {}", id);
    studentsService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }
}
