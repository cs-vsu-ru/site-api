package cs.vsu.is.service;

import cs.vsu.is.domain.Specialities;
import cs.vsu.is.domain.Subject;
import cs.vsu.is.repository.SpecialitiesRepository;
import cs.vsu.is.repository.SubjectRepository;
import cs.vsu.is.service.convertor.SubjectConverter;
import cs.vsu.is.service.dto.SpecialitiesDTO;
import cs.vsu.is.service.convertor.SpecialitiesConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import cs.vsu.is.service.dto.SubjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Specialities}.
 */
@Service
@Transactional
public class SpecialitiesService {

    private final Logger log = LoggerFactory.getLogger(SpecialitiesService.class);

    private final SpecialitiesRepository specialitiesRepository;
    private final SubjectRepository subjectRepository;

    private final SpecialitiesConverter specialitiesMapper;
    private final SubjectConverter subjectConverter;

    public SpecialitiesService(SpecialitiesRepository specialitiesRepository, SubjectRepository subjectRepository, SpecialitiesConverter specialitiesMapper, SubjectConverter subjectConverter) {
        this.specialitiesRepository = specialitiesRepository;
        this.subjectRepository = subjectRepository;
        this.specialitiesMapper = specialitiesMapper;
        this.subjectConverter = subjectConverter;
    }

    /**
     * Save a specialities.
     *
     * @param specialitiesDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecialitiesDTO save(SpecialitiesDTO specialitiesDTO) {
        log.debug("Request to save Specialities : {}", specialitiesDTO);
        Set<SubjectDTO> subjectsDTO = specialitiesDTO.getSubjects();
        List<Subject> subjects = subjectsDTO.stream()
            .map(subjectConverter::toEntity)
            .collect(Collectors.toList());
        List<Subject> subjectsAfterSave = subjectRepository.saveAll(subjects);
        Specialities specialities = specialitiesMapper.toEntity(specialitiesDTO);
        specialities.setSubjects(Set.copyOf(subjectsAfterSave));
        specialities = specialitiesRepository.save(specialities);
        return specialitiesMapper.toDto(specialities);
    }

    /**
     * Update a specialities.
     *
     * @param specialitiesDTO the entity to save.
     * @return the persisted entity.
     */
    public SpecialitiesDTO update(SpecialitiesDTO specialitiesDTO) {
        log.debug("Request to update Specialities : {}", specialitiesDTO);
        Specialities specialities = specialitiesMapper.toEntity(specialitiesDTO);
        specialities = specialitiesRepository.save(specialities);
        return specialitiesMapper.toDto(specialities);
    }

    /**
     * Partially update a specialities.
     *
     * @param specialitiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    // public Optional<SpecialitiesDTO> partialUpdate(SpecialitiesDTO
    // specialitiesDTO) {
    // log.debug("Request to partially update Specialities : {}", specialitiesDTO);

    // return specialitiesRepository
    // .findById(specialitiesDTO.getId())
    // .map(existingSpecialities -> {
    // specialitiesMapper.partialUpdate(existingSpecialities, specialitiesDTO);

    // return existingSpecialities;
    // })
    // .map(specialitiesRepository::save)
    // .map(specialitiesMapper::toDto);
    // }

    /**
     * Get all the specialities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SpecialitiesDTO> findAll() {
        log.debug("Request to get all Specialities");
        return specialitiesRepository.findAll().stream().map(specialitiesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one specialities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpecialitiesDTO> findOne(Long id) {
        log.debug("Request to get Specialities : {}", id);
        return specialitiesRepository.findById(id).map(specialitiesMapper::toDto);
    }

    /**
     * Delete the specialities by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Specialities : {}", id);
        specialitiesRepository.deleteById(id);
    }
}
