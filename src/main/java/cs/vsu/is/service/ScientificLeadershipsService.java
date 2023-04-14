package cs.vsu.is.service;

import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.repository.ScientificLeadershipsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScientificLeaderships}.
 */
@Service
@Transactional
public class ScientificLeadershipsService {

    private final Logger log = LoggerFactory.getLogger(ScientificLeadershipsService.class);

    private final ScientificLeadershipsRepository scientificLeadershipsRepository;

    public ScientificLeadershipsService(ScientificLeadershipsRepository scientificLeadershipsRepository) {
        this.scientificLeadershipsRepository = scientificLeadershipsRepository;
    }

    /**
     * Save a scientificLeaderships.
     *
     * @param scientificLeaderships the entity to save.
     * @return the persisted entity.
     */
    public ScientificLeaderships save(ScientificLeaderships scientificLeaderships) {
        log.debug("Request to save ScientificLeaderships : {}", scientificLeaderships);
        return scientificLeadershipsRepository.save(scientificLeaderships);
    }

    /**
     * Update a scientificLeaderships.
     *
     * @param scientificLeaderships the entity to save.
     * @return the persisted entity.
     */
    public ScientificLeaderships update(ScientificLeaderships scientificLeaderships) {
        log.debug("Request to update ScientificLeaderships : {}", scientificLeaderships);
        return scientificLeadershipsRepository.save(scientificLeaderships);
    }

    /**
     * Partially update a scientificLeaderships.
     *
     * @param scientificLeaderships the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ScientificLeaderships> partialUpdate(ScientificLeaderships scientificLeaderships) {
        log.debug("Request to partially update ScientificLeaderships : {}", scientificLeaderships);

        return scientificLeadershipsRepository
            .findById(scientificLeaderships.getId())
            .map(existingScientificLeaderships -> {
                if (scientificLeaderships.getYear() != null) {
                    existingScientificLeaderships.setYear(scientificLeaderships.getYear());
                }

                return existingScientificLeaderships;
            })
            .map(scientificLeadershipsRepository::save);
    }

    /**
     * Get all the scientificLeaderships.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ScientificLeaderships> findAll() {
        log.debug("Request to get all ScientificLeaderships");
        return scientificLeadershipsRepository.findAll();
    }

    /**
     * Get one scientificLeaderships by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScientificLeaderships> findOne(Long id) {
        log.debug("Request to get ScientificLeaderships : {}", id);
        return scientificLeadershipsRepository.findById(id);
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
