package cs.vsu.is.service;

import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.repository.ScientificLeadershipsRepository;
import cs.vsu.is.service.dto.ScientificLeadershipsDTO;
import lombok.AllArgsConstructor;
import cs.vsu.is.service.convertor.ScientificLeadershipsConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

  /**
   * Save a scientificLeaderships.
   *
   * @param scientificLeadershipsDTO the entity to save.
   * @return the persisted entity.
   */
  public ScientificLeadershipsDTO save(ScientificLeadershipsDTO scientificLeadershipsDTO) {
    log.debug("Request to save ScientificLeaderships : {}", scientificLeadershipsDTO);
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
    ScientificLeaderships scientificLeaderships = scientificLeadershipsMapper.toEntity(scientificLeadershipsDTO);
    scientificLeaderships = scientificLeadershipsRepository.save(scientificLeaderships);
    return scientificLeadershipsMapper.toDto(scientificLeaderships);
  }

  /**
   * Partially update a scientificLeaderships.
   *
   * @param scientificLeadershipsDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<ScientificLeadershipsDTO> partialUpdate(ScientificLeadershipsDTO scientificLeadershipsDTO) {
  //   log.debug("Request to partially update ScientificLeaderships : {}", scientificLeadershipsDTO);

  //   return scientificLeadershipsRepository
  //       .findById(scientificLeadershipsDTO.getId())
  //       .map(existingScientificLeaderships -> {
  //         scientificLeadershipsMapper.partialUpdate(existingScientificLeaderships, scientificLeadershipsDTO);

  //         return existingScientificLeaderships;
  //       })
  //       .map(scientificLeadershipsRepository::save)
  //       .map(scientificLeadershipsMapper::toDto);
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
}
