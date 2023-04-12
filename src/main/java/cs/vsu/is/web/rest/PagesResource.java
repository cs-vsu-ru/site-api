package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Pages;
import cs.vsu.is.repository.PagesRepository;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cs.vsu.is.domain.Pages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PagesResource {

    private final Logger log = LoggerFactory.getLogger(PagesResource.class);

    private static final String ENTITY_NAME = "pages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PagesRepository pagesRepository;

    public PagesResource(PagesRepository pagesRepository) {
        this.pagesRepository = pagesRepository;
    }

    /**
     * {@code POST  /pages} : Create a new pages.
     *
     * @param pages the pages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pages, or with status {@code 400 (Bad Request)} if the pages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pages")
    public ResponseEntity<Pages> createPages(@Valid @RequestBody Pages pages) throws URISyntaxException {
        log.debug("REST request to save Pages : {}", pages);
        if (pages.getId() != null) {
            throw new BadRequestAlertException("A new pages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pages result = pagesRepository.save(pages);
        return ResponseEntity
            .created(new URI("/api/pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pages/:id} : Updates an existing pages.
     *
     * @param id the id of the pages to save.
     * @param pages the pages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pages,
     * or with status {@code 400 (Bad Request)} if the pages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pages/{id}")
    public ResponseEntity<Pages> updatePages(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Pages pages)
        throws URISyntaxException {
        log.debug("REST request to update Pages : {}, {}", id, pages);
        if (pages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pages result = pagesRepository.save(pages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pages/:id} : Partial updates given fields of an existing pages, field will ignore if it is null
     *
     * @param id the id of the pages to save.
     * @param pages the pages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pages,
     * or with status {@code 400 (Bad Request)} if the pages is not valid,
     * or with status {@code 404 (Not Found)} if the pages is not found,
     * or with status {@code 500 (Internal Server Error)} if the pages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pages> partialUpdatePages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pages pages
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pages partially : {}, {}", id, pages);
        if (pages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pages> result = pagesRepository
            .findById(pages.getId())
            .map(existingPages -> {
                if (pages.getContent() != null) {
                    existingPages.setContent(pages.getContent());
                }

                return existingPages;
            })
            .map(pagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pages.getId().toString())
        );
    }

    /**
     * {@code GET  /pages} : get all the pages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pages in body.
     */
    @GetMapping("/pages")
    public List<Pages> getAllPages() {
        log.debug("REST request to get all Pages");
        return pagesRepository.findAll();
    }

    /**
     * {@code GET  /pages/:id} : get the "id" pages.
     *
     * @param id the id of the pages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pages/{id}")
    public ResponseEntity<Pages> getPages(@PathVariable Long id) {
        log.debug("REST request to get Pages : {}", id);
        Optional<Pages> pages = pagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pages);
    }

    /**
     * {@code DELETE  /pages/:id} : delete the "id" pages.
     *
     * @param id the id of the pages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pages/{id}")
    public ResponseEntity<Void> deletePages(@PathVariable Long id) {
        log.debug("REST request to delete Pages : {}", id);
        pagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
