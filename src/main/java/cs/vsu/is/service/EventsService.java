package cs.vsu.is.service;

import cs.vsu.is.domain.Events;
import cs.vsu.is.repository.EventsRepository;
import cs.vsu.is.service.dto.EventDTO;
import cs.vsu.is.service.convertor.EventConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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

  private final EventConverter eventsMapper;

  public EventsService(EventsRepository eventsRepository, EventConverter eventsMapper) {
    this.eventsRepository = eventsRepository;
    this.eventsMapper = eventsMapper;
  }

  /**
   * Save a events.
   *
   * @param eventsDTO the entity to save.
   * @return the persisted entity.
   */
  public EventDTO save(EventDTO eventsDTO) {
    log.debug("Request to save Events : {}", eventsDTO);
    Events events = eventsMapper.toEntity(eventsDTO);
    events.setPublicationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d")));
    events.setPublicationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("H:m")));
    events = eventsRepository.save(events);
    return eventsMapper.toDto(events);
  }

  /**
   * Update a events.
   *
   * @param eventsDTO the entity to save.
   * @return the persisted entity.
   */
  public EventDTO update(EventDTO eventsDTO) {
    log.debug("Request to update Events : {}", eventsDTO);
    Events events = eventsMapper.toEntity(eventsDTO);
    events.setLastModifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("y-M-d")));
    events.setLastModifiedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("H:m")));
    events = eventsRepository.save(events);
    return eventsMapper.toDto(events);
  }

  /**
   * Partially update a events.
   *
   * @param eventsDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<EventDTO> partialUpdate(EventDTO eventsDTO) {
  // log.debug("Request to partially update Events : {}", eventsDTO);
  //
  // return eventsRepository
  // .findById(eventsDTO.getId())
  // .map(existingEvents -> {
  // eventsMapper.
  // eventsMapper.partialUpdate(existingEvents, eventsDTO);
  //
  // return existingEvents;
  // })
  // .map(eventsRepository::save)
  // .map(eventsMapper::toDto);
  // }

  /**
   * Get all the events.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<EventDTO> findAll() {
      log.debug("Request to get all Events");
      DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      return eventsRepository.findAll().stream()
          .sorted((o1, o2) -> {
              LocalDateTime now = LocalDateTime.now();
              LocalDateTime date1 = now.minusDays(1000);
              LocalDateTime date2 = now.minusDays(1000);
              boolean isDate1Valid = true;
              boolean isDate2Valid = true;

              try {
                  date1 = LocalDateTime.parse(o1.getStartDate() + " " + o1.getStartTime(), FORMATTER);
              } catch (Exception ignored) {
                  isDate1Valid = false;
              }
              try {
                  date2 = LocalDateTime.parse(o2.getStartDate() + " " + o2.getStartTime(), FORMATTER);
              } catch (Exception ignored) {
                  isDate2Valid = false;
              }

              if (isDate1Valid && isDate2Valid) {
                  if (date1.isBefore(now) && date2.isBefore(now)) {
                      return date1.compareTo(date2);
                  } else if (date1.isBefore(now)) {
                      return 1; // date1 is expired, place it after date2
                  } else if (date2.isBefore(now)) {
                      return -1; // date2 is expired, place it after date1
                  } else {
                      // Calculate the difference between dates and sort in ascending order
                      long date1Difference = ChronoUnit.SECONDS.between(now, date1);
                      long date2Difference = ChronoUnit.SECONDS.between(now, date2);
                      return Long.compare(date1Difference, date2Difference);
                  }
              } else if (isDate1Valid) {
                  return -1; // date1 is a valid date, place it before date2
              } else if (isDate2Valid) {
                  return 1; // date2 is a valid date, place it before date1
              } else {
                  return 0; // both are non-date strings, maintain the current order
              }
          })
          .map(eventsMapper::toDto)
          .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one events by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<EventDTO> findOne(Long id) {
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
