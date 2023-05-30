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
import cs.vsu.is.service.dto.store.EmployeeDTOStore;
import cs.vsu.is.service.dto.update.EmployeeDTOUpdate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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
	private final UserConverter userMapper;
	private final EmployeeConverterStore employeeMapperStore;

	private final EmployeeConverterUpdate employeeMapperUpdate;

    private RestTemplate restTemplate;

	/**
	 * Save a employee.
	 *
	 * @param employeeDTO the entity to save.
	 * @return the persisted entity.
	 * @throws Exception
	 */
	public EmployeeDTO save(@Valid EmployeeDTOStore employeeDTO) throws Exception {
		log.debug("Request to save Employee : {}", employeeDTO);

		// if (employeeDTO.getImageFile() != null) {
		// 	Path path = Paths.get("images").resolve(employeeDTO.getImageFile().getOriginalFilename());
		// 	System.out.println(path.toUri().getPath());
		// 	Files.copy(employeeDTO.getImageFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		// }
		AdminUserDTO userDTO = employeeMapperStore.toAdminUserDTO(employeeDTO);
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_USER");
        authorities.add("ROLE_EMPLOYEE");
        if(employeeDTO.getMainRole().equals("ROLE_MODERATOR")) {
            authorities.add("ROLE_MODERATOR");
        }
        if(employeeDTO.getMainRole().equals("ROLE_ADMIN")) {
            authorities.add("ROLE_ADMIN");
        }
        userDTO.setAuthorities(authorities);
		User user = userMapper.toEntity(userService.createUser(userDTO));
		Employee employee = employeeMapperStore.toEmployeeEntity(employeeDTO);
		employee.setUser(user);

		employee = employeeRepository.save(employee);

        try {
            createVoidLessons(employee.getId());
        }catch (Exception e) {
            e.printStackTrace();
        }

		return employeeMapper.toDto(employee);
	}


    public class EmployeeRequest {
        private Long employeeId;

        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }
    }

    private void createVoidLessons(Long id) {
        log.debug("отправляем запрос на создание пустых занятий");
        String url = "http://parser_api:8000/api/parser/employees/";

        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "parser_api:8000");
        headers.add("Content-Type", "application/json");

        HttpEntity<EmployeeRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(url, entity, Void.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            // Обработка успешного ответа (201)
            log.debug("Запрос выполнен успешно");
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Обработка ошибки (404)
            log.debug("Не удалось выполнить запрос");
        } else {
            // Обработка других статусов ответа
            log.debug("Получен непредвиденный статус ответа");
        }
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
		return employeeRepository.findAll().stream().map(employeeMapper::toDto)
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
