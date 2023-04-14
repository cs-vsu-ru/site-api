package cs.vsu.is.service;

import cs.vsu.is.domain.Students;
import cs.vsu.is.repository.StudentsRepository;
import cs.vsu.is.service.dto.StudentsDTO;
import cs.vsu.is.service.mapper.StudentsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Students}.
 */
@Service
@Transactional
public class StudentsService {

    private final Logger log = LoggerFactory.getLogger(StudentsService.class);

    private final StudentsRepository studentsRepository;

    private final StudentsMapper studentsMapper;

    public StudentsService(StudentsRepository studentsRepository, StudentsMapper studentsMapper) {
        this.studentsRepository = studentsRepository;
        this.studentsMapper = studentsMapper;
    }

    /**
     * Save a students.
     *
     * @param studentsDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentsDTO save(StudentsDTO studentsDTO) {
        log.debug("Request to save Students : {}", studentsDTO);
        Students students = studentsMapper.toEntity(studentsDTO);
        students = studentsRepository.save(students);
        return studentsMapper.toDto(students);
    }

    /**
     * Update a students.
     *
     * @param studentsDTO the entity to save.
     * @return the persisted entity.
     */
    public StudentsDTO update(StudentsDTO studentsDTO) {
        log.debug("Request to update Students : {}", studentsDTO);
        Students students = studentsMapper.toEntity(studentsDTO);
        students = studentsRepository.save(students);
        return studentsMapper.toDto(students);
    }

    /**
     * Partially update a students.
     *
     * @param studentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StudentsDTO> partialUpdate(StudentsDTO studentsDTO) {
        log.debug("Request to partially update Students : {}", studentsDTO);

        return studentsRepository
            .findById(studentsDTO.getId())
            .map(existingStudents -> {
                studentsMapper.partialUpdate(existingStudents, studentsDTO);

                return existingStudents;
            })
            .map(studentsRepository::save)
            .map(studentsMapper::toDto);
    }

    /**
     * Get all the students.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudentsDTO> findAll() {
        log.debug("Request to get all Students");
        return studentsRepository.findAll().stream().map(studentsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one students by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentsDTO> findOne(Long id) {
        log.debug("Request to get Students : {}", id);
        return studentsRepository.findById(id).map(studentsMapper::toDto);
    }

    /**
     * Delete the students by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Students : {}", id);
        studentsRepository.deleteById(id);
    }
}
