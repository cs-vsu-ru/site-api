package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Students;
import cs.vsu.is.repository.StudentsRepository;
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
 * REST controller for managing {@link cs.vsu.is.domain.Students}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StudentsResource {

    private final Logger log = LoggerFactory.getLogger(StudentsResource.class);

    private static final String ENTITY_NAME = "students";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentsRepository studentsRepository;

    public StudentsResource(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    /**
     * {@code POST  /students} : Create a new students.
     *
     * @param students the students to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new students, or with status {@code 400 (Bad Request)} if the students has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/students")
    public ResponseEntity<Students> createStudents(@RequestBody Students students) throws URISyntaxException {
        log.debug("REST request to save Students : {}", students);
        if (students.getId() != null) {
            throw new BadRequestAlertException("A new students cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Students result = studentsRepository.save(students);
        return ResponseEntity
            .created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /students/:id} : Updates an existing students.
     *
     * @param id the id of the students to save.
     * @param students the students to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated students,
     * or with status {@code 400 (Bad Request)} if the students is not valid,
     * or with status {@code 500 (Internal Server Error)} if the students couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<Students> updateStudents(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Students students
    ) throws URISyntaxException {
        log.debug("REST request to update Students : {}, {}", id, students);
        if (students.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, students.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Students result = studentsRepository.save(students);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, students.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /students/:id} : Partial updates given fields of an existing students, field will ignore if it is null
     *
     * @param id the id of the students to save.
     * @param students the students to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated students,
     * or with status {@code 400 (Bad Request)} if the students is not valid,
     * or with status {@code 404 (Not Found)} if the students is not found,
     * or with status {@code 500 (Internal Server Error)} if the students couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/students/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Students> partialUpdateStudents(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Students students
    ) throws URISyntaxException {
        log.debug("REST request to partial update Students partially : {}, {}", id, students);
        if (students.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, students.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!studentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Students> result = studentsRepository
            .findById(students.getId())
            .map(existingStudents -> {
                if (students.getName() != null) {
                    existingStudents.setName(students.getName());
                }
                if (students.getSurname() != null) {
                    existingStudents.setSurname(students.getSurname());
                }

                return existingStudents;
            })
            .map(studentsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, students.getId().toString())
        );
    }

    /**
     * {@code GET  /students} : get all the students.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of students in body.
     */
    @GetMapping("/students")
    public List<Students> getAllStudents(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Students");
        if (eagerload) {
            return studentsRepository.findAllWithEagerRelationships();
        } else {
            return studentsRepository.findAll();
        }
    }

    /**
     * {@code GET  /students/:id} : get the "id" students.
     *
     * @param id the id of the students to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the students, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<Students> getStudents(@PathVariable Long id) {
        log.debug("REST request to get Students : {}", id);
        Optional<Students> students = studentsRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(students);
    }

    /**
     * {@code DELETE  /students/:id} : delete the "id" students.
     *
     * @param id the id of the students to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudents(@PathVariable Long id) {
        log.debug("REST request to delete Students : {}", id);
        studentsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
