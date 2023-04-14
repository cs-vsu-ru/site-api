package cs.vsu.is.web.rest;

import cs.vsu.is.repository.ArticlesRepository;
import cs.vsu.is.service.ArticlesService;
import cs.vsu.is.service.dto.ArticlesDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.Articles}.
 */
@RestController
@RequestMapping("/api")
public class ArticlesResource {

    private final Logger log = LoggerFactory.getLogger(ArticlesResource.class);

    private static final String ENTITY_NAME = "articles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlesService articlesService;

    private final ArticlesRepository articlesRepository;

    public ArticlesResource(ArticlesService articlesService, ArticlesRepository articlesRepository) {
        this.articlesService = articlesService;
        this.articlesRepository = articlesRepository;
    }

    /**
     * {@code POST  /articles} : Create a new articles.
     *
     * @param articlesDTO the articlesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articlesDTO, or with status {@code 400 (Bad Request)} if the articles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articles")
    public ResponseEntity<ArticlesDTO> createArticles(@RequestBody ArticlesDTO articlesDTO) throws URISyntaxException {
        log.debug("REST request to save Articles : {}", articlesDTO);
        if (articlesDTO.getId() != null) {
            throw new BadRequestAlertException("A new articles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticlesDTO result = articlesService.save(articlesDTO);
        return ResponseEntity
            .created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articles/:id} : Updates an existing articles.
     *
     * @param id the id of the articlesDTO to save.
     * @param articlesDTO the articlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articlesDTO,
     * or with status {@code 400 (Bad Request)} if the articlesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticlesDTO> updateArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArticlesDTO articlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Articles : {}, {}", id, articlesDTO);
        if (articlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArticlesDTO result = articlesService.update(articlesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articlesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /articles/:id} : Partial updates given fields of an existing articles, field will ignore if it is null
     *
     * @param id the id of the articlesDTO to save.
     * @param articlesDTO the articlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articlesDTO,
     * or with status {@code 400 (Bad Request)} if the articlesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the articlesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the articlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArticlesDTO> partialUpdateArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArticlesDTO articlesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Articles partially : {}, {}", id, articlesDTO);
        if (articlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArticlesDTO> result = articlesService.partialUpdate(articlesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articlesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /articles} : get all the articles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/articles")
    public List<ArticlesDTO> getAllArticles() {
        log.debug("REST request to get all Articles");
        return articlesService.findAll();
    }

    /**
     * {@code GET  /articles/:id} : get the "id" articles.
     *
     * @param id the id of the articlesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articlesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticlesDTO> getArticles(@PathVariable Long id) {
        log.debug("REST request to get Articles : {}", id);
        Optional<ArticlesDTO> articlesDTO = articlesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articlesDTO);
    }

    /**
     * {@code DELETE  /articles/:id} : delete the "id" articles.
     *
     * @param id the id of the articlesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticles(@PathVariable Long id) {
        log.debug("REST request to delete Articles : {}", id);
        articlesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
