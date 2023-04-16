package cs.vsu.is.service;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.repository.EmployeeRepository;
import cs.vsu.is.service.convertor.EmployeeConverter;
import cs.vsu.is.service.dto.EmployeeDTO;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@AllArgsConstructor
@Service
@Transactional
public class EmployeeService {

  private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

  private final EmployeeRepository employeeRepository;

  private final EmployeeConverter employeeMapper;
  private final EmployeeConverter employeeConvertor;

  /**
   * Save a employee.
   *
   * @param employeeDTO the entity to save.
   * @return the persisted entity.
   */
  public EmployeeDTO save(EmployeeDTO employeeDTO) {
    log.debug("Request to save Employee : {}", employeeDTO);
    Employee employee = employeeMapper.toEntity(employeeDTO);
    employee = employeeRepository.save(employee);
    return employeeMapper.toDto(employee);
  }

  /**
   * Update a employee.
   *
   * @param employeeDTO the entity to save.
   * @return the persisted entity.
   */
  public EmployeeDTO update(EmployeeDTO employeeDTO) {
    log.debug("Request to update Employee : {}", employeeDTO);
    Employee employee = employeeMapper.toEntity(employeeDTO);
    employee = employeeRepository.save(employee);
    return employeeMapper.toDto(employee);
  }

  /**
   * Partially update a employee.
   *
   * @param employeeDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<EmployeeDTO> partialUpdate(EmployeeDTO employeeDTO) {
  // log.debug("Request to partially update Employee : {}", employeeDTO);

  // return employeeRepository
  // .findById(employeeDTO.getId())
  // .map(existingEmployee -> {
  // employeeMapper.partialUpdate(existingEmployee, employeeDTO);

  // return existingEmployee;
  // })
  // .map(employeeRepository::save)
  // .map(employeeMapper::toDto);
  // }

  /**
   * Get all the employees.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<EmployeeDTO> findAll() {
    log.debug("Request to get all Employees");
    return employeeRepository.findAll().stream().map(employeeConvertor::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get all the employees with eager load of many-to-many relationships.
   *
   * @return the list of entities.
   */
  public Page<EmployeeDTO> findAllWithEagerRelationships(Pageable pageable) {
    return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
  }

  /**
   * Get one employee by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<EmployeeDTO> findOne(Long id) {
    log.debug("Request to get Employee : {}", id);
    return employeeRepository.findById(id).map(employeeMapper::toDto);
  }

  /**
   * Delete the employee by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Employee : {}", id);
    employeeRepository.deleteById(id);
  }
}
