package cs.vsu.is.service;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.repository.AccessModesRepository;
import cs.vsu.is.service.dto.AccessModeDTO;
import cs.vsu.is.service.convertor.AccessModeConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccessModes}.
 */
@Service
@Transactional
public class AccessModesService {

  private final Logger log = LoggerFactory.getLogger(AccessModesService.class);

  private final AccessModesRepository accessModesRepository;

  private final AccessModeConverter accessModesMapper;

  public AccessModesService(AccessModesRepository accessModesRepository, AccessModeConverter accessModesMapper) {
    this.accessModesRepository = accessModesRepository;
    this.accessModesMapper = accessModesMapper;
  }

  /**
   * Save a accessModes.
   *
   * @param AccessModeDTO the entity to save.
   * @return the persisted entity.
   */
  public AccessModeDTO save(AccessModeDTO AccessModeDTO) {
    log.debug("Request to save AccessModes : {}", AccessModeDTO);
    AccessModes accessModes = accessModesMapper.toEntity(AccessModeDTO);
    accessModes = accessModesRepository.save(accessModes);
    return accessModesMapper.toDto(accessModes);
  }

  /**
   * Update a accessModes.
   *
   * @param AccessModeDTO the entity to save.
   * @return the persisted entity.
   */
  public AccessModeDTO update(AccessModeDTO AccessModeDTO) {
    log.debug("Request to update AccessModes : {}", AccessModeDTO);
    AccessModes accessModes = accessModesMapper.toEntity(AccessModeDTO);
    accessModes = accessModesRepository.save(accessModes);
    return accessModesMapper.toDto(accessModes);
  }

  /**
   * Partially update a accessModes.
   *
   * @param AccessModeDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<AccessModeDTO> partialUpdate(AccessModeDTO AccessModeDTO) {
  // log.debug("Request to partially update AccessModes : {}", AccessModeDTO);

  // return accessModesRepository
  // .findById(AccessModeDTO.getId())
  // .map(existingAccessModes -> {
  // accessModesMapper.partialUpdate(existingAccessModes, AccessModeDTO);

  // return existingAccessModes;
  // })
  // .map(accessModesRepository::save)
  // .map(accessModesMapper::toDto);
  // }

  /**
   * Get all the accessModes.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<AccessModeDTO> findAll() {
    log.debug("Request to get all AccessModes");
    return accessModesRepository.findAll().stream().map(accessModesMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one accessModes by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<AccessModeDTO> findOne(Long id) {
    log.debug("Request to get AccessModes : {}", id);
    return accessModesRepository.findById(id).map(accessModesMapper::toDto);
  }

  /**
   * Delete the accessModes by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete AccessModes : {}", id);
    accessModesRepository.deleteById(id);
  }
}
