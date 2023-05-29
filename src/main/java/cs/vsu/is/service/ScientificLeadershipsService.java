package cs.vsu.is.service;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.domain.Students;
import cs.vsu.is.repository.EmployeeRepository;
import cs.vsu.is.repository.ScientificLeadershipsRepository;
import cs.vsu.is.repository.ScientificWorkTypeRepository;
import cs.vsu.is.repository.StudentsRepository;
import cs.vsu.is.service.dto.ScientificLeadershipsDTO;
import lombok.AllArgsConstructor;
import cs.vsu.is.service.convertor.ScientificLeadershipsConverter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScientificLeaderships}.
 */
@Service
@Transactional
@AllArgsConstructor
public class ScientificLeadershipsService {

	private final Logger log = LoggerFactory.getLogger(ScientificLeadershipsService.class);

	private final ScientificLeadershipsRepository scientificLeadershipsRepository;

	private final ScientificLeadershipsConverter scientificLeadershipsMapper;
	// private final ScientificWorkTypeRepository scientificWorkTypeRepository;
	// private final StudentsRepository studentsRepository;
	// private final EmployeeRepository employeeRepository;

	/**
	 * Save a scientificLeaderships.
	 *
	 * @param scientificLeadershipsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ScientificLeadershipsDTO save(ScientificLeadershipsDTO scientificLeadershipsDTO) {
		log.debug("Request to save ScientificLeaderships : {}", scientificLeadershipsDTO.toString());
		ScientificLeaderships scientificLeaderships = scientificLeadershipsMapper.toEntity(scientificLeadershipsDTO);
		scientificLeaderships = scientificLeadershipsRepository.save(scientificLeaderships);
		return scientificLeadershipsMapper.toDto(scientificLeaderships);
	}

	/**
	 * Update a scientificLeaderships.
	 *
	 * @param scientificLeadershipsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ScientificLeadershipsDTO update(ScientificLeadershipsDTO scientificLeadershipsDTO) {
		log.debug("Request to update ScientificLeaderships : {}", scientificLeadershipsDTO);
		ScientificLeaderships old = scientificLeadershipsRepository.findById(scientificLeadershipsDTO.getId()).get();
		scientificLeadershipsMapper.substitute(scientificLeadershipsDTO, old);
		old = scientificLeadershipsRepository.save(old);
		return scientificLeadershipsMapper.toDto(old);
	}

	/**
	 * Partially update a scientificLeaderships.
	 *
	 * @param scientificLeadershipsDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	// public Optional<ScientificLeadershipsDTO>
	// partialUpdate(ScientificLeadershipsDTO scientificLeadershipsDTO) {
	// log.debug("Request to partially update ScientificLeaderships : {}",
	// scientificLeadershipsDTO);

	// return scientificLeadershipsRepository
	// .findById(scientificLeadershipsDTO.getId())
	// .map(existingScientificLeaderships -> {
	// scientificLeadershipsMapper.partialUpdate(existingScientificLeaderships,
	// scientificLeadershipsDTO);

	// return existingScientificLeaderships;
	// })
	// .map(scientificLeadershipsRepository::save)
	// .map(scientificLeadershipsMapper::toDto);
	// }

	/**
	 * Get all the scientificLeaderships.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<ScientificLeadershipsDTO> findAll() {
		log.debug("Request to get all ScientificLeaderships");
		return scientificLeadershipsRepository
				.findAll()
				.stream()
				.map(scientificLeadershipsMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one scientificLeaderships by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<ScientificLeadershipsDTO> findOne(Long id) {
		log.debug("Request to get ScientificLeaderships : {}", id);
		return scientificLeadershipsRepository.findById(id).map(scientificLeadershipsMapper::toDto);
	}

	/**
	 * Delete the scientificLeaderships by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete ScientificLeaderships : {}", id);
		scientificLeadershipsRepository.deleteById(id);
	}

	// public List<ScientificLeadershipsDTO> createSciLeadsFromTable(Sheet sheet) {
	// List<ScientificLeadershipsDTO> result = new ArrayList<>();

	// for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	// String studentPersNum =
	// String.valueOf(sheet.getRow(i).getCell(0).getNumericCellValue());
	// Students student =
	// studentsRepository.findFirstByStudentPersonalNumber(studentPersNum);
	// char studentNameInitial =
	// sheet.getRow(i).getCell(1).getStringCellValue().split(" ")[1].charAt(0);

	// if
	// (student.getSurname().equals(sheet.getRow(i).getCell(1).getStringCellValue())
	// &&
	// student.getName().charAt(0) == studentNameInitial) {

	// String employeeInitials =
	// sheet.getRow(i).getCell(2).getStringCellValue().split(" ")[1];
	// char[] employeeNameSurnameInitial = {employeeInitials.charAt(0),
	// employeeInitials.charAt(2)};
	// Employee employee =
	// employeeRepository.findByUserLastName(sheet.getRow(i).getCell(2).getStringCellValue().split("
	// ")[0]);
	// if (employee.getPatronymic().charAt(0) == employeeNameSurnameInitial[1] &&
	// employee.getUser().getFirstName().charAt(0) == employeeNameSurnameInitial[0])
	// {
	// int year = (int) sheet.getRow(i).getCell(5).getNumericCellValue();

	// ScientificWorkType workType =
	// scientificWorkTypeRepository.findFirstByName(sheet.getRow(i).getCell(3).getStringCellValue());
	// if (!workType.getName().contains("статья")) {
	// List<ScientificLeaderships> previousLeadsByEmployee =
	// scientificLeadershipsRepository.findAllByEmployeeAndScientificWorkTypeAndStudentAndYear(employee,
	// workType,
	// student,
	// year);
	// if (previousLeadsByEmployee.size() == 0) {
	// saveSciLeadByRepo(sheet, result, i, student, employee, year, workType);
	// }
	// } else {
	// saveSciLeadByRepo(sheet, result, i, student, employee, year, workType);
	// }
	// }
	// }
	// }
	// return result;
	// }

	// private void saveSciLeadByRepo(Sheet sheet, List<ScientificLeadershipsDTO>
	// result, int i, Students student, Employee employee, int year,
	// ScientificWorkType workType) {
	// ScientificLeaderships newSciLead = new ScientificLeaderships();
	// newSciLead.setYear(year);
	// newSciLead.setStudent(student);
	// newSciLead.setScientificWorkType(workType);
	// newSciLead.setEmployee(employee);
	// newSciLead.setSciWorkName(sheet.getRow(i).getCell(4).getStringCellValue());
	// result.add(scientificLeadershipsMapper.toDto(scientificLeadershipsRepository.save(newSciLead)));
	// }
}
