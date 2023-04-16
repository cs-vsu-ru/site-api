package cs.vsu.is.service;

import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.repository.ScientificWorkTypeRepository;
import cs.vsu.is.service.dto.ScientificWorkTypeDTO;
import lombok.AllArgsConstructor;
import cs.vsu.is.service.convertor.ScientificWorkTypeConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScientificWorkType}.
 */
@Service
@Transactional
@AllArgsConstructor
public class ScientificWorkTypeService {

  private final Logger log = LoggerFactory.getLogger(ScientificWorkTypeService.class);

  private final ScientificWorkTypeRepository scientificWorkTypeRepository;

  private final ScientificWorkTypeConverter scientificWorkTypeMapper;

  /**
   * Save a scientificWorkType.
   *
   * @param scientificWorkTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public ScientificWorkTypeDTO save(ScientificWorkTypeDTO scientificWorkTypeDTO) {
    log.debug("Request to save ScientificWorkType : {}", scientificWorkTypeDTO);
    ScientificWorkType scientificWorkType = scientificWorkTypeMapper.toEntity(scientificWorkTypeDTO);
    scientificWorkType = scientificWorkTypeRepository.save(scientificWorkType);
    return scientificWorkTypeMapper.toDto(scientificWorkType);
  }

  /**
   * Update a scientificWorkType.
   *
   * @param scientificWorkTypeDTO the entity to save.
   * @return the persisted entity.
   */
  public ScientificWorkTypeDTO update(ScientificWorkTypeDTO scientificWorkTypeDTO) {
    log.debug("Request to update ScientificWorkType : {}", scientificWorkTypeDTO);
    ScientificWorkType scientificWorkType = scientificWorkTypeMapper.toEntity(scientificWorkTypeDTO);
    scientificWorkType = scientificWorkTypeRepository.save(scientificWorkType);
    return scientificWorkTypeMapper.toDto(scientificWorkType);
  }

  /**
   * Partially update a scientificWorkType.
   *
   * @param scientificWorkTypeDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<ScientificWorkTypeDTO> partialUpdate(ScientificWorkTypeDTO
  // scientificWorkTypeDTO) {
  // log.debug("Request to partially update ScientificWorkType : {}",
  // scientificWorkTypeDTO);

  // return scientificWorkTypeRepository
  // .findById(scientificWorkTypeDTO.getId())
  // .map(existingScientificWorkType -> {
  // scientificWorkTypeMapper.partialUpdate(existingScientificWorkType,
  // scientificWorkTypeDTO);

  // return existingScientificWorkType;
  // })
  // .map(scientificWorkTypeRepository::save)
  // .map(scientificWorkTypeMapper::toDto);
  // }

  /**
   * Get all the scientificWorkTypes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<ScientificWorkTypeDTO> findAll() {
    log.debug("Request to get all ScientificWorkTypes");
    return scientificWorkTypeRepository
        .findAll()
        .stream()
        .map(scientificWorkTypeMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one scientificWorkType by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<ScientificWorkTypeDTO> findOne(Long id) {
    log.debug("Request to get ScientificWorkType : {}", id);
    return scientificWorkTypeRepository.findById(id).map(scientificWorkTypeMapper::toDto);
  }

  /**
   * Delete the scientificWorkType by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete ScientificWorkType : {}", id);
    scientificWorkTypeRepository.deleteById(id);
  }
}
