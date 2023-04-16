package cs.vsu.is.service;

import cs.vsu.is.domain.Role;
import cs.vsu.is.repository.RoleRepository;
import cs.vsu.is.service.dto.RoleDTO;
import cs.vsu.is.service.convertor.RoleConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Role}.
 */
@Service
@Transactional
public class RoleService {

  private final Logger log = LoggerFactory.getLogger(RoleService.class);

  private final RoleRepository roleRepository;

  private final RoleConverter roleMapper;

  public RoleService(RoleRepository roleRepository, RoleConverter roleMapper) {
    this.roleRepository = roleRepository;
    this.roleMapper = roleMapper;
  }

  /**
   * Save a role.
   *
   * @param roleDTO the entity to save.
   * @return the persisted entity.
   */
  public RoleDTO save(RoleDTO roleDTO) {
    log.debug("Request to save Role : {}", roleDTO);
    Role role = roleMapper.toEntity(roleDTO);
    role = roleRepository.save(role);
    return roleMapper.toDto(role);
  }

  /**
   * Update a role.
   *
   * @param roleDTO the entity to save.
   * @return the persisted entity.
   */
  public RoleDTO update(RoleDTO roleDTO) {
    log.debug("Request to update Role : {}", roleDTO);
    Role role = roleMapper.toEntity(roleDTO);
    role = roleRepository.save(role);
    return roleMapper.toDto(role);
  }

  /**
   * Partially update a role.
   *
   * @param roleDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<RoleDTO> partialUpdate(RoleDTO roleDTO) {
  // log.debug("Request to partially update Role : {}", roleDTO);

  // return roleRepository
  // .findById(roleDTO.getId())
  // .map(existingRole -> {
  // roleMapper.partialUpdate(existingRole, roleDTO);

  // return existingRole;
  // })
  // .map(roleRepository::save)
  // .map(roleMapper::toDto);
  // }

  /**
   * Get all the roles.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<RoleDTO> findAll() {
    log.debug("Request to get all Roles");
    return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one role by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<RoleDTO> findOne(Long id) {
    log.debug("Request to get Role : {}", id);
    return roleRepository.findById(id).map(roleMapper::toDto);
  }

  /**
   * Delete the role by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Role : {}", id);
    roleRepository.deleteById(id);
  }
}
