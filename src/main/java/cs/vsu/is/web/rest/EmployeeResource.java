package cs.vsu.is.web.rest;

import cs.vsu.is.repository.EmployeeRepository;
import cs.vsu.is.service.EmployeeService;
import cs.vsu.is.service.dto.AdminEmployeeDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.store.EmployeeDTOStore;
import cs.vsu.is.service.dto.update.EmployeeDTOUpdate;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {
  private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

  private static final String ENTITY_NAME = "employee";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final EmployeeService employeeService;

  private final EmployeeRepository employeeRepository;

  public EmployeeResource(EmployeeService employeeService, EmployeeRepository employeeRepository) {
    this.employeeService = employeeService;
    this.employeeRepository = employeeRepository;
  }

  /**
   * {@code POST  /employees} : Create a new employee.
   *
   * @param employeeDTO the employeeDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new employeeDTO, or with status {@code 400 (Bad Request)} if
   *         the employee has already an ID.
   * @throws Exception
   */
  @PostMapping("/employees")
  public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTOStore employeeDTO)
      throws Exception {
    log.debug("REST request to save Employee : {}", employeeDTO);
    EmployeeDTO result = employeeService.save(employeeDTO);
    return ResponseEntity
        .created(new URI("/api/employees/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /employees/:id} : Updates an existing employee.
   *
   * @param id          the id of the employeeDTO to save.
   * @param employeeDTO the employeeDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated employeeDTO,
   *         or with status {@code 400 (Bad Request)} if the employeeDTO is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the employeeDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping("/employees/{id}")
  public ResponseEntity<AdminEmployeeDTO> updateEmployee(
      @PathVariable(value = "id", required = false) final Long id,
      @Valid @RequestBody EmployeeDTOUpdate employeeDTO) throws URISyntaxException {
    log.debug("REST request to update Employee : {}, {}", id, employeeDTO);
    if (employeeDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, employeeDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!employeeRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }
    AdminEmployeeDTO result = employeeService.update(employeeDTO);
    return ResponseEntity
        .ok()
        .headers(
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDTO.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /employees/:id} : Partial updates given fields of an existing
   * employee, field will ignore if it is null
   *
   * @param id          the id of the employeeDTO to save.
   * @param employeeDTO the employeeDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated employeeDTO,
   *         or with status {@code 400 (Bad Request)} if the employeeDTO is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the employeeDTO is not
   *         found,
   *         or with status {@code 500 (Internal Server Error)} if the employeeDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  // @PatchMapping(value = "/employees/{id}", consumes = { "application/json",
  // "application/merge-patch+json" })
  // public ResponseEntity<EmployeeDTO> partialUpdateEmployee(
  // @PathVariable(value = "id", required = false) final Long id,
  // @NotNull @RequestBody EmployeeDTO employeeDTO
  // ) throws URISyntaxException {
  // log.debug("REST request to partial update Employee partially : {}, {}", id,
  // employeeDTO);
  // if (employeeDTO.getId() == null) {
  // throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
  // }
  // if (!Objects.equals(id, employeeDTO.getId())) {
  // throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
  // }

  // if (!employeeRepository.existsById(id)) {
  // throw new BadRequestAlertException("Entity not found", ENTITY_NAME,
  // "idnotfound");
  // }

  // Optional<EmployeeDTO> result = employeeService.partialUpdate(employeeDTO);

  // return ResponseUtil.wrapOrNotFound(
  // result,
  // HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
  // employeeDTO.getId().toString())
  // );
  // }

  /**
   * {@code GET  /employees} : get all the employees.
   *
   * @param eagerload flag to eager load entities from relationships (This is
   *                  applicable for many-to-many).
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of employees in body.
   */
  @GetMapping("/employees")
  public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
    log.debug("REST request to get all Employees");
    return employeeService.findAll();
  }

  /**
   * {@code GET  /employees/:id} : get the "id" employee.
   *
   * @param id the id of the employeeDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the employeeDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/employees/{id}")
  public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
    log.debug("REST request to get Employee : {}", id);
    Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
    return ResponseEntity.ok(employeeDTO.get());
  }

  @GetMapping("/admin-employees/{id}")
  public ResponseEntity<AdminEmployeeDTO> getAdminEmployee(@PathVariable Long id) {
    log.debug("REST request to get Employee : {}", id);
    Optional<AdminEmployeeDTO> employeeDTO = employeeService.findAdminOne(id);
    return ResponseEntity.ok(employeeDTO.get());
  }

  /**
   * {@code DELETE  /employees/:id} : delete the "id" employee.
   *
   * @param id the id of the employeeDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    log.debug("REST request to delete Employee : {}", id);
    employeeService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }
}
