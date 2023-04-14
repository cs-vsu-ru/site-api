package cs.vsu.is.service;

import cs.vsu.is.domain.Events;
import cs.vsu.is.repository.EventsRepository;
import cs.vsu.is.service.dto.EventsDTO;
import cs.vsu.is.service.mapper.EventsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Events}.
 */
@Service
@Transactional
public class EventsService {

    private final Logger log = LoggerFactory.getLogger(EventsService.class);

    private final EventsRepository eventsRepository;

    private final EventsMapper eventsMapper;

    public EventsService(EventsRepository eventsRepository, EventsMapper eventsMapper) {
        this.eventsRepository = eventsRepository;
        this.eventsMapper = eventsMapper;
    }

    /**
     * Save a events.
     *
     * @param eventsDTO the entity to save.
     * @return the persisted entity.
     */
    public EventsDTO save(EventsDTO eventsDTO) {
        log.debug("Request to save Events : {}", eventsDTO);
        Events events = eventsMapper.toEntity(eventsDTO);
        events = eventsRepository.save(events);
        return eventsMapper.toDto(events);
    }

    /**
     * Update a events.
     *
     * @param eventsDTO the entity to save.
     * @return the persisted entity.
     */
    public EventsDTO update(EventsDTO eventsDTO) {
        log.debug("Request to update Events : {}", eventsDTO);
        Events events = eventsMapper.toEntity(eventsDTO);
        events = eventsRepository.save(events);
        return eventsMapper.toDto(events);
    }

    /**
     * Partially update a events.
     *
     * @param eventsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventsDTO> partialUpdate(EventsDTO eventsDTO) {
        log.debug("Request to partially update Events : {}", eventsDTO);

        return eventsRepository
            .findById(eventsDTO.getId())
            .map(existingEvents -> {
                eventsMapper.partialUpdate(existingEvents, eventsDTO);

                return existingEvents;
            })
            .map(eventsRepository::save)
            .map(eventsMapper::toDto);
    }

    /**
     * Get all the events.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EventsDTO> findAll() {
        log.debug("Request to get all Events");
        return eventsRepository.findAll().stream().map(eventsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one events by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventsDTO> findOne(Long id) {
        log.debug("Request to get Events : {}", id);
        return eventsRepository.findById(id).map(eventsMapper::toDto);
    }

    /**
     * Delete the events by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Events : {}", id);
        eventsRepository.deleteById(id);
    }
}
