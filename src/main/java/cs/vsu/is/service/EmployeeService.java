package cs.vsu.is.service;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.User;
import cs.vsu.is.repository.EmployeeRepository;
import cs.vsu.is.repository.UserRepository;
import cs.vsu.is.service.convertor.AdminUserConverter;
import cs.vsu.is.service.convertor.EmployeeConverter;
import cs.vsu.is.service.convertor.UserConverter;
import cs.vsu.is.service.convertor.store.EmployeeConverterStore;
import cs.vsu.is.service.convertor.update.EmployeeConverterUpdate;
import cs.vsu.is.service.dto.AdminEmployeeDTO;
import cs.vsu.is.service.dto.AdminUserDTO;
import cs.vsu.is.service.dto.EmployeeDTO;
import cs.vsu.is.service.dto.UserDTO;
import cs.vsu.is.service.dto.store.EmployeeDTOStore;
import cs.vsu.is.service.dto.update.EmployeeDTOUpdate;
import lombok.AllArgsConstructor;

import java.util.Collections;
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

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link Employee}.
 */
@AllArgsConstructor
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;
    private final UserService userService;
    private final AdminUserConverter adminUserConverter;

    private final EmployeeConverter employeeMapper;
    private final EmployeeConverterStore employeeMapperStore;

    private final EmployeeConverterUpdate employeeMapperUpdate;
    private final UserConverter userConverter;

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     * @throws Exception
     */
    public EmployeeDTO save(@Valid EmployeeDTOStore employeeDTO) throws Exception {
        log.debug("Request to save Employee : {}", employeeDTO);
        AdminUserDTO userDTO = employeeMapperStore.toAdminUserDTO(employeeDTO);
        UserDTO userDTO1 = userService.createUser(userDTO);
        User user = userConverter.toEntity(userDTO1);
        Employee employee = employeeMapperStore.toEmployeeEntity(employeeDTO);
        employee.setUser(user);
        employee = employeeRepository.save(employee);

        return employeeMapper.toDto(employee);
    }

    /**
     * Update a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public AdminEmployeeDTO update(@Valid EmployeeDTOUpdate employeeDTO) {
        log.debug("Request to update Employee : {}", employeeDTO);
        Employee employee = employeeRepository.findById(employeeDTO.getId()).get();
        User user = userRepository.findById(employeeDTO.getId()).get();
        employeeMapperUpdate.toEmployeeEntity(employeeDTO, employee);
        employeeMapperUpdate.toUserEntity(employeeDTO, user);
        employeeRepository.save(employee);
        userRepository.save(user);
        return employeeMapper.toAdminDto(employee);
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
        LinkedList<EmployeeDTO> collect = employeeRepository.findAll()
            .stream()
            .map(employeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
        Collections.shuffle(collect);
        return collect;
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

    @Transactional(readOnly = true)
    public Optional<AdminEmployeeDTO> findAdminOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id).map(employeeMapper::toAdminDto);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        Long idN = employeeRepository.findById(id).get().getUser().getId();
        employeeRepository.deleteById(id);
        userRepository.deleteById(idN);
    }
}
