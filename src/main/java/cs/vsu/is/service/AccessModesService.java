package cs.vsu.is.service;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.repository.AccessModesRepository;
import cs.vsu.is.service.dto.AccessModesDTO;
import cs.vsu.is.service.mapper.AccessModesMapper;
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

    private final AccessModesMapper accessModesMapper;

    public AccessModesService(AccessModesRepository accessModesRepository, AccessModesMapper accessModesMapper) {
        this.accessModesRepository = accessModesRepository;
        this.accessModesMapper = accessModesMapper;
    }

    /**
     * Save a accessModes.
     *
     * @param accessModesDTO the entity to save.
     * @return the persisted entity.
     */
    public AccessModesDTO save(AccessModesDTO accessModesDTO) {
        log.debug("Request to save AccessModes : {}", accessModesDTO);
        AccessModes accessModes = accessModesMapper.toEntity(accessModesDTO);
        accessModes = accessModesRepository.save(accessModes);
        return accessModesMapper.toDto(accessModes);
    }

    /**
     * Update a accessModes.
     *
     * @param accessModesDTO the entity to save.
     * @return the persisted entity.
     */
    public AccessModesDTO update(AccessModesDTO accessModesDTO) {
        log.debug("Request to update AccessModes : {}", accessModesDTO);
        AccessModes accessModes = accessModesMapper.toEntity(accessModesDTO);
        accessModes = accessModesRepository.save(accessModes);
        return accessModesMapper.toDto(accessModes);
    }

    /**
     * Partially update a accessModes.
     *
     * @param accessModesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccessModesDTO> partialUpdate(AccessModesDTO accessModesDTO) {
        log.debug("Request to partially update AccessModes : {}", accessModesDTO);

        return accessModesRepository
            .findById(accessModesDTO.getId())
            .map(existingAccessModes -> {
                accessModesMapper.partialUpdate(existingAccessModes, accessModesDTO);

                return existingAccessModes;
            })
            .map(accessModesRepository::save)
            .map(accessModesMapper::toDto);
    }

    /**
     * Get all the accessModes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AccessModesDTO> findAll() {
        log.debug("Request to get all AccessModes");
        return accessModesRepository.findAll().stream().map(accessModesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one accessModes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccessModesDTO> findOne(Long id) {
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
