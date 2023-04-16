package cs.vsu.is.service;

import cs.vsu.is.domain.Subject;
import cs.vsu.is.repository.SubjectRepository;
import cs.vsu.is.service.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import cs.vsu.is.service.convertor.SubjectConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Subject}.
 */
@Service
@Transactional
@AllArgsConstructor
public class SubjectService {

  private final Logger log = LoggerFactory.getLogger(SubjectService.class);

  private final SubjectRepository subjectRepository;

  private final SubjectConverter subjectMapper;

  /**
   * Save a subject.
   *
   * @param subjectDTO the entity to save.
   * @return the persisted entity.
   */
  public SubjectDTO save(SubjectDTO subjectDTO) {
    log.debug("Request to save Subject : {}", subjectDTO);
    Subject subject = subjectMapper.toEntity(subjectDTO);
    subject = subjectRepository.save(subject);
    return subjectMapper.toDto(subject);
  }

  /**
   * Update a subject.
   *
   * @param subjectDTO the entity to save.
   * @return the persisted entity.
   */
  public SubjectDTO update(SubjectDTO subjectDTO) {
    log.debug("Request to update Subject : {}", subjectDTO);
    Subject subject = subjectMapper.toEntity(subjectDTO);
    subject = subjectRepository.save(subject);
    return subjectMapper.toDto(subject);
  }

  /**
   * Partially update a subject.
   *
   * @param subjectDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<SubjectDTO> partialUpdate(SubjectDTO subjectDTO) {
  // log.debug("Request to partially update Subject : {}", subjectDTO);

  // return subjectRepository
  // .findById(subjectDTO.getId())
  // .map(existingSubject -> {
  // subjectMapper.partialUpdate(existingSubject, subjectDTO);

  // return existingSubject;
  // })
  // .map(subjectRepository::save)
  // .map(subjectMapper::toDto);
  // }

  /**
   * Get all the subjects.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<SubjectDTO> findAll() {
    log.debug("Request to get all Subjects");
    return subjectRepository.findAll().stream().map(subjectMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one subject by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<SubjectDTO> findOne(Long id) {
    log.debug("Request to get Subject : {}", id);
    return subjectRepository.findById(id).map(subjectMapper::toDto);
  }

  /**
   * Delete the subject by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Subject : {}", id);
    subjectRepository.deleteById(id);
  }
}
