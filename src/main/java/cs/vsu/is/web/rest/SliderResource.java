package cs.vsu.is.web.rest;

import cs.vsu.is.repository.SliderRepository;
import cs.vsu.is.service.SliderService;
import cs.vsu.is.service.dto.SliderDTO;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.Slider}.
 */
@RestController
@RequestMapping("/api")
public class SliderResource {

  private final Logger log = LoggerFactory.getLogger(SliderResource.class);

  private static final String ENTITY_NAME = "slider";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final SliderService sliderService;

  private final SliderRepository sliderRepository;

  public SliderResource(SliderService sliderService, SliderRepository sliderRepository) {
    this.sliderService = sliderService;
    this.sliderRepository = sliderRepository;
  }

  /**
   * {@code POST  /sliders} : Create a new slider.
   *
   * @param sliderDTO the sliderDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
   *         body the new sliderDTO, or with status {@code 400 (Bad Request)} if
   *         the slider has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/sliders")
  public ResponseEntity<SliderDTO> createSlider(@RequestBody SliderDTO sliderDTO) throws URISyntaxException {
    log.debug("REST request to save Slider : {}", sliderDTO);
    if (sliderDTO.getId() != null) {
      throw new BadRequestAlertException("A new slider cannot already have an ID", ENTITY_NAME, "idexists");
    }
    SliderDTO result = sliderService.save(sliderDTO);
    return ResponseEntity
        .created(new URI("/api/sliders/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /sliders/:id} : Updates an existing slider.
   *
   * @param id        the id of the sliderDTO to save.
   * @param sliderDTO the sliderDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated sliderDTO,
   *         or with status {@code 400 (Bad Request)} if the sliderDTO is not
   *         valid,
   *         or with status {@code 500 (Internal Server Error)} if the sliderDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/sliders/{id}")
  public ResponseEntity<SliderDTO> updateSlider(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody SliderDTO sliderDTO) throws URISyntaxException {
    log.debug("REST request to update Slider : {}, {}", id, sliderDTO);
    if (sliderDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, sliderDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!sliderRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    SliderDTO result = sliderService.update(sliderDTO);
    return ResponseEntity
        .ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sliderDTO.getId().toString()))
        .body(result);
  }

  /**
   * {@code PATCH  /sliders/:id} : Partial updates given fields of an existing
   * slider, field will ignore if it is null
   *
   * @param id        the id of the sliderDTO to save.
   * @param sliderDTO the sliderDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the updated sliderDTO,
   *         or with status {@code 400 (Bad Request)} if the sliderDTO is not
   *         valid,
   *         or with status {@code 404 (Not Found)} if the sliderDTO is not found,
   *         or with status {@code 500 (Internal Server Error)} if the sliderDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  // @PatchMapping(value = "/sliders/{id}", consumes = { "application/json",
  // "application/merge-patch+json" })
  // public ResponseEntity<SliderDTO> partialUpdateSlider(
  // @PathVariable(value = "id", required = false) final Long id,
  // @RequestBody SliderDTO sliderDTO
  // ) throws URISyntaxException {
  // log.debug("REST request to partial update Slider partially : {}, {}", id,
  // sliderDTO);
  // if (sliderDTO.getId() == null) {
  // throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
  // }
  // if (!Objects.equals(id, sliderDTO.getId())) {
  // throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
  // }

  // if (!sliderRepository.existsById(id)) {
  // throw new BadRequestAlertException("Entity not found", ENTITY_NAME,
  // "idnotfound");
  // }

  // Optional<SliderDTO> result = sliderService.partialUpdate(sliderDTO);

  // return ResponseUtil.wrapOrNotFound(
  // result,
  // HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
  // sliderDTO.getId().toString())
  // );
  // }

  /**
   * {@code GET  /sliders} : get all the sliders.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
   *         of sliders in body.
   */

  @GetMapping("/sliders")
  public List<SliderDTO> getAllSliders() {
    log.debug("REST request to get all Sliders");
    return sliderService.findAll();
  }

  /**
   * {@code GET  /sliders/:id} : get the "id" slider.
   *
   * @param id the id of the sliderDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
   *         the sliderDTO, or with status {@code 404 (Not Found)}.
   */

  @GetMapping("/sliders/{id}")
  public ResponseEntity<SliderDTO> getSlider(@PathVariable Long id) {
    log.debug("REST request to get Slider : {}", id);
    Optional<SliderDTO> sliderDTO = sliderService.findOne(id);
    return ResponseUtil.wrapOrNotFound(sliderDTO);
  }

  /**
   * {@code DELETE  /sliders/:id} : delete the "id" slider.
   *
   * @param id the id of the sliderDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/sliders/{id}")
  public ResponseEntity<Void> deleteSlider(@PathVariable Long id) {
    log.debug("REST request to delete Slider : {}", id);
    sliderService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }
}
