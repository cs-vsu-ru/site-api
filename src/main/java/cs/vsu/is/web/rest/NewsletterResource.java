package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Newsletter;
import cs.vsu.is.repository.NewsletterRepository;
import cs.vsu.is.service.dto.LessonDTO;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NewsletterResource {
    private final NewsletterRepository newsletterRepository;

    public NewsletterResource(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    @PostMapping("/newsletter")
    public ResponseEntity<Newsletter> createNewsletter(@RequestBody Newsletter newsletter) throws URISyntaxException {
        newsletter = newsletterRepository.save(newsletter);
        return ResponseEntity
            .created(new URI("/api/newsletter/" + newsletter.getId()))
            .body(newsletter);
    }

    @GetMapping("/newsletter/{id}")
    public ResponseEntity<Newsletter> getNewsletter(@PathVariable Long id) {
        Optional<Newsletter> newsletter = newsletterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(newsletter);
    }

    @GetMapping("/newsletters")
    public ResponseEntity<List<Newsletter>> getAllNewsletter() {
        List<Newsletter> newsletters = newsletterRepository.findAll();
        return ResponseEntity.ok(newsletters);
    }
}
