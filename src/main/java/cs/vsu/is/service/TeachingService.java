package cs.vsu.is.service;

import cs.vsu.is.domain.Teaching;
import cs.vsu.is.repository.TeachingRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Teaching}.
 */
@Service
@Transactional
public class TeachingService {

    private final Logger log = LoggerFactory.getLogger(TeachingService.class);

    private final TeachingRepository teachingRepository;

    public TeachingService(TeachingRepository teachingRepository) {
        this.teachingRepository = teachingRepository;
    }

    /**
     * Save a teaching.
     *
     * @param teaching the entity to save.
     * @return the persisted entity.
     */
    public Teaching save(Teaching teaching) {
        log.debug("Request to save Teaching : {}", teaching);
        return teachingRepository.save(teaching);
    }

    /**
     * Update a teaching.
     *
     * @param teaching the entity to save.
     * @return the persisted entity.
     */
    public Teaching update(Teaching teaching) {
        log.debug("Request to update Teaching : {}", teaching);
        return teachingRepository.save(teaching);
    }

    /**
     * Partially update a teaching.
     *
     * @param teaching the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Teaching> partialUpdate(Teaching teaching) {
        log.debug("Request to partially update Teaching : {}", teaching);

        return teachingRepository
            .findById(teaching.getId())
            .map(existingTeaching -> {
                return existingTeaching;
            })
            .map(teachingRepository::save);
    }

    /**
     * Get all the teachings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Teaching> findAll() {
        log.debug("Request to get all Teachings");
        return teachingRepository.findAll();
    }

    /**
     * Get one teaching by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Teaching> findOne(Long id) {
        log.debug("Request to get Teaching : {}", id);
        return teachingRepository.findById(id);
    }

    /**
     * Delete the teaching by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Teaching : {}", id);
        teachingRepository.deleteById(id);
    }
}
