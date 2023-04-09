package cs.vsu.is.service;

import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.repository.AccessModesRepository;
import java.util.List;
import java.util.Optional;
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

    public AccessModesService(AccessModesRepository accessModesRepository) {
        this.accessModesRepository = accessModesRepository;
    }

    /**
     * Save a accessModes.
     *
     * @param accessModes the entity to save.
     * @return the persisted entity.
     */
    public AccessModes save(AccessModes accessModes) {
        log.debug("Request to save AccessModes : {}", accessModes);
        return accessModesRepository.save(accessModes);
    }

    /**
     * Update a accessModes.
     *
     * @param accessModes the entity to save.
     * @return the persisted entity.
     */
    public AccessModes update(AccessModes accessModes) {
        log.debug("Request to update AccessModes : {}", accessModes);
        return accessModesRepository.save(accessModes);
    }

    /**
     * Partially update a accessModes.
     *
     * @param accessModes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccessModes> partialUpdate(AccessModes accessModes) {
        log.debug("Request to partially update AccessModes : {}", accessModes);

        return accessModesRepository
            .findById(accessModes.getId())
            .map(existingAccessModes -> {
                if (accessModes.getName() != null) {
                    existingAccessModes.setName(accessModes.getName());
                }

                return existingAccessModes;
            })
            .map(accessModesRepository::save);
    }

    /**
     * Get all the accessModes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AccessModes> findAll() {
        log.debug("Request to get all AccessModes");
        return accessModesRepository.findAll();
    }

    /**
     * Get one accessModes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccessModes> findOne(Long id) {
        log.debug("Request to get AccessModes : {}", id);
        return accessModesRepository.findById(id);
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
