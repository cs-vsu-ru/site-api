package cs.vsu.is.service;

import cs.vsu.is.domain.Specialities;
import cs.vsu.is.repository.SpecialitiesRepository;
import java.util.List;
import java.util.Optional;
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

    public SpecialitiesService(SpecialitiesRepository specialitiesRepository) {
        this.specialitiesRepository = specialitiesRepository;
    }

    /**
     * Save a specialities.
     *
     * @param specialities the entity to save.
     * @return the persisted entity.
     */
    public Specialities save(Specialities specialities) {
        log.debug("Request to save Specialities : {}", specialities);
        return specialitiesRepository.save(specialities);
    }

    /**
     * Update a specialities.
     *
     * @param specialities the entity to save.
     * @return the persisted entity.
     */
    public Specialities update(Specialities specialities) {
        log.debug("Request to update Specialities : {}", specialities);
        // no save call needed as we have no fields that can be updated
        return specialities;
    }

    /**
     * Partially update a specialities.
     *
     * @param specialities the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Specialities> partialUpdate(Specialities specialities) {
        log.debug("Request to partially update Specialities : {}", specialities);

        return specialitiesRepository
            .findById(specialities.getId())
            .map(existingSpecialities -> {
                return existingSpecialities;
            })// .map(specialitiesRepository::save)
        ;
    }

    /**
     * Get all the specialities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Specialities> findAll() {
        log.debug("Request to get all Specialities");
        return specialitiesRepository.findAll();
    }

    /**
     * Get one specialities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Specialities> findOne(Long id) {
        log.debug("Request to get Specialities : {}", id);
        return specialitiesRepository.findById(id);
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
