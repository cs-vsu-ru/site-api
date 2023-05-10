package cs.vsu.is.web.rest;

import cs.vsu.is.domain.StaticPages;
import cs.vsu.is.repository.StaticPagesRepository;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StaticPagesController {

    private final Logger log = LoggerFactory.getLogger(StaticPagesController.class);

    private StaticPagesRepository staticPagesRepository;

    public StaticPagesController(StaticPagesRepository staticPagesRepository) {
        this.staticPagesRepository = staticPagesRepository;
    }

    @PostMapping("/static-page")
    public ResponseEntity<StaticPages> createStaticPage(@RequestBody StaticPages staticPages) throws URISyntaxException {
        log.debug("REST request to save Static page : {}", staticPages);
        if (staticPages.getId() != null) {
            throw new BadRequestAlertException("A new staticpage cannot already have an ID", "staticPage", "idexists");
        }
        staticPages = staticPagesRepository.save(staticPages);
        return ResponseEntity
            .created(new URI("/api/students/" + staticPages.getId()))
            .body(staticPages);
    }

    @GetMapping("/static-pages")
    public List<StaticPages> getAllStaticPages() {
        log.debug("REST request to get all Static pages");
        return staticPagesRepository.findAll();
    }

    @GetMapping("/static-page/{id}")
    public ResponseEntity<StaticPages> getStaticPage(@PathVariable Long id) {
        log.debug("REST request to get Static page : {}", id);
        Optional<StaticPages> staticPages = staticPagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(staticPages);
    }

    @PutMapping("/static-page")
    public ResponseEntity<StaticPages> editStaticPage(@RequestBody StaticPages staticPages) throws URISyntaxException {
        log.debug("REST request to save Static page : {}", staticPages);
        staticPages = staticPagesRepository.save(staticPages);
        return ResponseEntity
            .created(new URI("/api/students/" + staticPages.getId()))
            .body(staticPages);
    }
}