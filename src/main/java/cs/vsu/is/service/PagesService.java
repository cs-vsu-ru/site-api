package cs.vsu.is.service;

import cs.vsu.is.domain.Pages;
import cs.vsu.is.repository.PagesRepository;
import cs.vsu.is.service.dto.PageDTO;
import cs.vsu.is.service.convertor.PageConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pages}.
 */
@Service
@Transactional
public class PagesService {

  private final Logger log = LoggerFactory.getLogger(PagesService.class);

  private final PagesRepository pagesRepository;

  private final PageConverter pagesMapper;

  public PagesService(PagesRepository pagesRepository, PageConverter pagesMapper) {
    this.pagesRepository = pagesRepository;
    this.pagesMapper = pagesMapper;
  }

  /**
   * Save a pages.
   *
   * @param pagesDTO the entity to save.
   * @return the persisted entity.
   */
  public PageDTO save(PageDTO pagesDTO) {
    log.debug("Request to save Pages : {}", pagesDTO);
    Pages pages = pagesMapper.toEntity(pagesDTO);
    pages = pagesRepository.save(pages);
    return pagesMapper.toDto(pages);
  }

  /**
   * Update a pages.
   *
   * @param pagesDTO the entity to save.
   * @return the persisted entity.
   */
  public PageDTO update(PageDTO pagesDTO) {
    log.debug("Request to update Pages : {}", pagesDTO);
    Pages pages = pagesMapper.toEntity(pagesDTO);
    pages = pagesRepository.save(pages);
    return pagesMapper.toDto(pages);
  }

  /**
   * Partially update a pages.
   *
   * @param pagesDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<PageDTO> partialUpdate(PageDTO pagesDTO) {
  // log.debug("Request to partially update Pages : {}", pagesDTO);

  // return pagesRepository
  // .findById(pagesDTO.getId())
  // .map(existingPages -> {
  // pagesMapper.partialUpdate(existingPages, pagesDTO);

  // return existingPages;
  // })
  // .map(pagesRepository::save)
  // .map(pagesMapper::toDto);
  // }

  /**
   * Get all the pages.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<PageDTO> findAll() {
    log.debug("Request to get all Pages");
    return pagesRepository.findAll().stream().map(pagesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one pages by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<PageDTO> findOne(Long id) {
    log.debug("Request to get Pages : {}", id);
    return pagesRepository.findById(id).map(pagesMapper::toDto);
  }

  /**
   * Delete the pages by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Pages : {}", id);
    pagesRepository.deleteById(id);
  }
}
