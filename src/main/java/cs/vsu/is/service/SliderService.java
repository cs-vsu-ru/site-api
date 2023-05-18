package cs.vsu.is.service;

import cs.vsu.is.domain.Slider;
import cs.vsu.is.repository.SliderRepository;
import cs.vsu.is.service.dto.SliderDTO;
import cs.vsu.is.service.convertor.SliderConverter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Slider}.
 */
@Service
@Transactional
public class SliderService {

  private final Logger log = LoggerFactory.getLogger(SliderService.class);

  private final SliderRepository sliderRepository;

  private final SliderConverter sliderMapper;

  public SliderService(SliderRepository sliderRepository, SliderConverter sliderMapper) {
    this.sliderRepository = sliderRepository;
    this.sliderMapper = sliderMapper;
  }

  /**
   * Save a slider.
   *
   * @param sliderDTO the entity to save.
   * @return the persisted entity.
   */
  public SliderDTO save(SliderDTO sliderDTO) {
    log.debug("Request to save Slider : {}", sliderDTO);
    Slider slider = sliderMapper.toEntity(sliderDTO);
    slider = sliderRepository.save(slider);
    return sliderMapper.toDto(slider);
  }

  /**
   * Update a slider.
   *
   * @param sliderDTO the entity to save.
   * @return the persisted entity.
   */
  public SliderDTO update(SliderDTO sliderDTO) {
    log.debug("Request to update Slider : {}", sliderDTO);
    Slider slider = sliderMapper.toEntity(sliderDTO);
    slider = sliderRepository.save(slider);
    return sliderMapper.toDto(slider);
  }

  /**
   * Partially update a slider.
   *
   * @param sliderDTO the entity to update partially.
   * @return the persisted entity.
   */
  // public Optional<SliderDTO> partialUpdate(SliderDTO sliderDTO) {
  // log.debug("Request to partially update Slider : {}", sliderDTO);

  // return sliderRepository
  // .findById(sliderDTO.getId())
  // .map(existingSlider -> {
  // sliderMapper.partialUpdate(existingSlider, sliderDTO);

  // return existingSlider;
  // })
  // .map(sliderRepository::save)
  // .map(sliderMapper::toDto);
  // }

  /**
   * Get all the sliders.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<SliderDTO> findAll() {
    log.debug("Request to get all Sliders");
    return sliderRepository.findAll().stream().map(sliderMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  /**
   * Get one slider by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<SliderDTO> findOne(Long id) {
    log.debug("Request to get Slider : {}", id);
    return sliderRepository.findById(id).map(sliderMapper::toDto);
  }

  /**
   * Delete the slider by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Slider : {}", id);
      Optional<Slider> byId = sliderRepository.findById(id);
      if(byId.isPresent()) {
          String imageURL = byId.get().getImageURL();
          File file = new File(imageURL.substring(imageURL.indexOf("api/")+4));
          file.delete();
      }
      sliderRepository.deleteById(id);
  }
}
