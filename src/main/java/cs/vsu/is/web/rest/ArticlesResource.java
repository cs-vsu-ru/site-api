package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Articles;
import cs.vsu.is.repository.ArticlesRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.Articles}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArticlesResource {

    private final Logger log = LoggerFactory.getLogger(ArticlesResource.class);

    private static final String ENTITY_NAME = "articles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlesRepository articlesRepository;

    public ArticlesResource(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    /**
     * {@code POST  /articles} : Create a new articles.
     *
     * @param articles the articles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articles, or with status {@code 400 (Bad Request)} if the articles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articles")
    public ResponseEntity<Articles> createArticles(@RequestBody Articles articles) throws URISyntaxException {
        log.debug("REST request to save Articles : {}", articles);
        if (articles.getId() != null) {
            throw new BadRequestAlertException("A new articles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Articles result = articlesRepository.save(articles);
        return ResponseEntity
            .created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articles/:id} : Updates an existing articles.
     *
     * @param id the id of the articles to save.
     * @param articles the articles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articles,
     * or with status {@code 400 (Bad Request)} if the articles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articles/{id}")
    public ResponseEntity<Articles> updateArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Articles articles
    ) throws URISyntaxException {
        log.debug("REST request to update Articles : {}, {}", id, articles);
        if (articles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Articles result = articlesRepository.save(articles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /articles/:id} : Partial updates given fields of an existing articles, field will ignore if it is null
     *
     * @param id the id of the articles to save.
     * @param articles the articles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articles,
     * or with status {@code 400 (Bad Request)} if the articles is not valid,
     * or with status {@code 404 (Not Found)} if the articles is not found,
     * or with status {@code 500 (Internal Server Error)} if the articles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/articles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Articles> partialUpdateArticles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Articles articles
    ) throws URISyntaxException {
        log.debug("REST request to partial update Articles partially : {}, {}", id, articles);
        if (articles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, articles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!articlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Articles> result = articlesRepository
            .findById(articles.getId())
            .map(existingArticles -> {
                if (articles.getPublicationDate() != null) {
                    existingArticles.setPublicationDate(articles.getPublicationDate());
                }
                if (articles.getContent() != null) {
                    existingArticles.setContent(articles.getContent());
                }

                return existingArticles;
            })
            .map(articlesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, articles.getId().toString())
        );
    }

    /**
     * {@code GET  /articles} : get all the articles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articles in body.
     */
    @GetMapping("/articles")
    public List<Articles> getAllArticles() {
        log.debug("REST request to get all Articles");
        return articlesRepository.findAll();
    }

    /**
     * {@code GET  /articles/:id} : get the "id" articles.
     *
     * @param id the id of the articles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles/{id}")
    public ResponseEntity<Articles> getArticles(@PathVariable Long id) {
        log.debug("REST request to get Articles : {}", id);
        Optional<Articles> articles = articlesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(articles);
    }

    /**
     * {@code DELETE  /articles/:id} : delete the "id" articles.
     *
     * @param id the id of the articles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticles(@PathVariable Long id) {
        log.debug("REST request to delete Articles : {}", id);
        articlesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
