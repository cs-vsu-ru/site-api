package cs.vsu.is.service;

import cs.vsu.is.domain.Teaching;
import cs.vsu.is.repository.TeachingRepository;
import cs.vsu.is.service.dto.TeachingDTO;
import cs.vsu.is.service.mapper.TeachingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final TeachingMapper teachingMapper;

    public TeachingService(TeachingRepository teachingRepository, TeachingMapper teachingMapper) {
        this.teachingRepository = teachingRepository;
        this.teachingMapper = teachingMapper;
    }

    /**
     * Save a teaching.
     *
     * @param teachingDTO the entity to save.
     * @return the persisted entity.
     */
    public TeachingDTO save(TeachingDTO teachingDTO) {
        log.debug("Request to save Teaching : {}", teachingDTO);
        Teaching teaching = teachingMapper.toEntity(teachingDTO);
        teaching = teachingRepository.save(teaching);
        return teachingMapper.toDto(teaching);
    }

    /**
     * Update a teaching.
     *
     * @param teachingDTO the entity to save.
     * @return the persisted entity.
     */
    public TeachingDTO update(TeachingDTO teachingDTO) {
        log.debug("Request to update Teaching : {}", teachingDTO);
        Teaching teaching = teachingMapper.toEntity(teachingDTO);
        teaching = teachingRepository.save(teaching);
        return teachingMapper.toDto(teaching);
    }

    /**
     * Partially update a teaching.
     *
     * @param teachingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeachingDTO> partialUpdate(TeachingDTO teachingDTO) {
        log.debug("Request to partially update Teaching : {}", teachingDTO);

        return teachingRepository
            .findById(teachingDTO.getId())
            .map(existingTeaching -> {
                teachingMapper.partialUpdate(existingTeaching, teachingDTO);

                return existingTeaching;
            })
            .map(teachingRepository::save)
            .map(teachingMapper::toDto);
    }

    /**
     * Get all the teachings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TeachingDTO> findAll() {
        log.debug("Request to get all Teachings");
        return teachingRepository.findAll().stream().map(teachingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one teaching by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeachingDTO> findOne(Long id) {
        log.debug("Request to get Teaching : {}", id);
        return teachingRepository.findById(id).map(teachingMapper::toDto);
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
