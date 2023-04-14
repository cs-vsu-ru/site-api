package cs.vsu.is.service;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.repository.EduSchedulePlaceRepository;
import cs.vsu.is.service.dto.EduSchedulePlaceDTO;
import cs.vsu.is.service.mapper.EduSchedulePlaceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EduSchedulePlace}.
 */
@Service
@Transactional
public class EduSchedulePlaceService {

    private final Logger log = LoggerFactory.getLogger(EduSchedulePlaceService.class);

    private final EduSchedulePlaceRepository eduSchedulePlaceRepository;

    private final EduSchedulePlaceMapper eduSchedulePlaceMapper;

    public EduSchedulePlaceService(EduSchedulePlaceRepository eduSchedulePlaceRepository, EduSchedulePlaceMapper eduSchedulePlaceMapper) {
        this.eduSchedulePlaceRepository = eduSchedulePlaceRepository;
        this.eduSchedulePlaceMapper = eduSchedulePlaceMapper;
    }

    /**
     * Save a eduSchedulePlace.
     *
     * @param eduSchedulePlaceDTO the entity to save.
     * @return the persisted entity.
     */
    public EduSchedulePlaceDTO save(EduSchedulePlaceDTO eduSchedulePlaceDTO) {
        log.debug("Request to save EduSchedulePlace : {}", eduSchedulePlaceDTO);
        EduSchedulePlace eduSchedulePlace = eduSchedulePlaceMapper.toEntity(eduSchedulePlaceDTO);
        eduSchedulePlace = eduSchedulePlaceRepository.save(eduSchedulePlace);
        return eduSchedulePlaceMapper.toDto(eduSchedulePlace);
    }

    /**
     * Update a eduSchedulePlace.
     *
     * @param eduSchedulePlaceDTO the entity to save.
     * @return the persisted entity.
     */
    public EduSchedulePlaceDTO update(EduSchedulePlaceDTO eduSchedulePlaceDTO) {
        log.debug("Request to update EduSchedulePlace : {}", eduSchedulePlaceDTO);
        EduSchedulePlace eduSchedulePlace = eduSchedulePlaceMapper.toEntity(eduSchedulePlaceDTO);
        eduSchedulePlace = eduSchedulePlaceRepository.save(eduSchedulePlace);
        return eduSchedulePlaceMapper.toDto(eduSchedulePlace);
    }

    /**
     * Partially update a eduSchedulePlace.
     *
     * @param eduSchedulePlaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EduSchedulePlaceDTO> partialUpdate(EduSchedulePlaceDTO eduSchedulePlaceDTO) {
        log.debug("Request to partially update EduSchedulePlace : {}", eduSchedulePlaceDTO);

        return eduSchedulePlaceRepository
            .findById(eduSchedulePlaceDTO.getId())
            .map(existingEduSchedulePlace -> {
                eduSchedulePlaceMapper.partialUpdate(existingEduSchedulePlace, eduSchedulePlaceDTO);

                return existingEduSchedulePlace;
            })
            .map(eduSchedulePlaceRepository::save)
            .map(eduSchedulePlaceMapper::toDto);
    }

    /**
     * Get all the eduSchedulePlaces.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EduSchedulePlaceDTO> findAll() {
        log.debug("Request to get all EduSchedulePlaces");
        return eduSchedulePlaceRepository
            .findAll()
            .stream()
            .map(eduSchedulePlaceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eduSchedulePlace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EduSchedulePlaceDTO> findOne(Long id) {
        log.debug("Request to get EduSchedulePlace : {}", id);
        return eduSchedulePlaceRepository.findById(id).map(eduSchedulePlaceMapper::toDto);
    }

    /**
     * Delete the eduSchedulePlace by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EduSchedulePlace : {}", id);
        eduSchedulePlaceRepository.deleteById(id);
    }
}
