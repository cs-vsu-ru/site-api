package cs.vsu.is.web.rest;

import cs.vsu.is.repository.ScientificLeadershipsRepository;
import cs.vsu.is.service.ScientificLeadershipsService;
import cs.vsu.is.service.dto.ScientificLeadershipsDTO;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSInput;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.ScientificLeaderships}.
 */
@RestController
@RequestMapping("/api")
public class ScientificLeadershipsResource {

    private final Logger log = LoggerFactory.getLogger(ScientificLeadershipsResource.class);

    private static final String ENTITY_NAME = "scientificLeaderships";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScientificLeadershipsService scientificLeadershipsService;

    private final ScientificLeadershipsRepository scientificLeadershipsRepository;

    public ScientificLeadershipsResource(
        ScientificLeadershipsService scientificLeadershipsService,
        ScientificLeadershipsRepository scientificLeadershipsRepository
    ) {
        this.scientificLeadershipsService = scientificLeadershipsService;
        this.scientificLeadershipsRepository = scientificLeadershipsRepository;
    }

    /**
     * {@code POST  /scientific-leaderships} : Create a new scientificLeaderships.
     *
     * @param scientificLeadershipsDTO the scientificLeadershipsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scientificLeadershipsDTO, or with status {@code 400 (Bad Request)} if the scientificLeaderships has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scientific-leaderships")
    public ResponseEntity<ScientificLeadershipsDTO> createScientificLeaderships(
        @RequestBody ScientificLeadershipsDTO scientificLeadershipsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ScientificLeaderships : {}", scientificLeadershipsDTO);
        if (scientificLeadershipsDTO.getId() != null) {
            throw new BadRequestAlertException("A new scientificLeaderships cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScientificLeadershipsDTO result = scientificLeadershipsService.save(scientificLeadershipsDTO);

        return ResponseEntity
            .created(new URI("/api/scientific-leaderships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scientific-leaderships/:id} : Updates an existing scientificLeaderships.
     *
     * @param id                       the id of the scientificLeadershipsDTO to save.
     * @param scientificLeadershipsDTO the scientificLeadershipsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificLeadershipsDTO,
     * or with status {@code 400 (Bad Request)} if the scientificLeadershipsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scientificLeadershipsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scientific-leaderships/{id}")
    public ResponseEntity<ScientificLeadershipsDTO> updateScientificLeaderships(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScientificLeadershipsDTO scientificLeadershipsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ScientificLeaderships : {}, {}", id, scientificLeadershipsDTO);
        if (scientificLeadershipsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scientificLeadershipsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scientificLeadershipsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScientificLeadershipsDTO result = scientificLeadershipsService.update(scientificLeadershipsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificLeadershipsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scientific-leaderships/:id} : Partial updates given fields of an existing scientificLeaderships, field will ignore if it is null
     *
     * @param id the id of the scientificLeadershipsDTO to save.
     * @param scientificLeadershipsDTO the scientificLeadershipsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scientificLeadershipsDTO,
     * or with status {@code 400 (Bad Request)} if the scientificLeadershipsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the scientificLeadershipsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the scientificLeadershipsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    // @PatchMapping(value = "/scientific-leaderships/{id}", consumes = { "application/json", "application/merge-patch+json" })
    // public ResponseEntity<ScientificLeadershipsDTO> partialUpdateScientificLeaderships(
    //     @PathVariable(value = "id", required = false) final Long id,
    //     @RequestBody ScientificLeadershipsDTO scientificLeadershipsDTO
    // ) throws URISyntaxException {
    //     log.debug("REST request to partial update ScientificLeaderships partially : {}, {}", id, scientificLeadershipsDTO);
    //     if (scientificLeadershipsDTO.getId() == null) {
    //         throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //     }
    //     if (!Objects.equals(id, scientificLeadershipsDTO.getId())) {
    //         throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //     }

    //     if (!scientificLeadershipsRepository.existsById(id)) {
    //         throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //     }

    //     Optional<ScientificLeadershipsDTO> result = scientificLeadershipsService.partialUpdate(scientificLeadershipsDTO);

    //     return ResponseUtil.wrapOrNotFound(
    //         result,
    //         HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scientificLeadershipsDTO.getId().toString())
    //     );
    // }

    /**
     * {@code GET  /scientific-leaderships} : get all the scientificLeaderships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scientificLeaderships in body.
     */
    @GetMapping("/scientific-leaderships")
    public List<ScientificLeadershipsDTO> getAllScientificLeaderships() {
        log.debug("REST request to get all ScientificLeaderships");
        return scientificLeadershipsService.findAll();
    }

    /**
     * {@code GET  /scientific-leaderships/:id} : get the "id" scientificLeaderships.
     *
     * @param id the id of the scientificLeadershipsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scientificLeadershipsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scientific-leaderships/{id}")
    public ResponseEntity<ScientificLeadershipsDTO> getScientificLeaderships(@PathVariable Long id) {
        log.debug("REST request to get ScientificLeaderships : {}", id);
        Optional<ScientificLeadershipsDTO> scientificLeadershipsDTO = scientificLeadershipsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scientificLeadershipsDTO);
    }

    /**
     * {@code DELETE  /scientific-leaderships/:id} : delete the "id" scientificLeaderships.
     *
     * @param id the id of the scientificLeadershipsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scientific-leaderships/{id}")
    public ResponseEntity<Void> deleteScientificLeaderships(@PathVariable Long id) {
        log.debug("REST request to delete ScientificLeaderships : {}", id);
        scientificLeadershipsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/scientific-leaderships/table")
    public ResponseEntity<List<ScientificLeadershipsDTO>> createSciLeadsByTable(
        @RequestParam("file") MultipartFile file
    ) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            List<ScientificLeadershipsDTO> responseArgs = scientificLeadershipsService.createSciLeadsFromTable(workbook.getSheetAt(0));
            return ResponseEntity.ok().body(responseArgs);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
