package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Emails;
import cs.vsu.is.domain.Newsletter;
import cs.vsu.is.repository.EmailsRepository;
import cs.vsu.is.repository.NewsletterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NewsletterResource {
    private final NewsletterRepository newsletterRepository;
    private final EmailsRepository emailsRepository;

    public NewsletterResource(NewsletterRepository newsletterRepository, EmailsRepository emailsRepository) {
        this.newsletterRepository = newsletterRepository;
        this.emailsRepository = emailsRepository;
    }

    @PostMapping("/newsletter")
    public ResponseEntity<Newsletter> createNewsletter(@RequestBody Newsletter newsletter) throws URISyntaxException {
        newsletter.setNewsletterDate(newsletter.getNewsletterDate().minusHours(3));
        newsletterRepository.save(newsletter);
        for(Emails item: newsletter.getEmails()) {
            item.setNewsletter(newsletter);
        }
        List<Emails> emails = emailsRepository.saveAll(newsletter.getEmails());
        newsletter.setEmails(emails);
        return ResponseEntity
            .created(new URI("/api/newsletter/" + newsletter.getId()))
            .body(newsletter);
    }

    @PutMapping("/newsletter/{id}")
    public ResponseEntity<Newsletter> updateNewsletter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Newsletter newsletter) {
        newsletter.setId(id);
        if(newsletter.getNewsletterDate() != null) {
            newsletter.setNewsletterDate(newsletter.getNewsletterDate().minusHours(3));
        }
        Newsletter result = newsletterRepository.save(newsletter);
        return ResponseEntity
            .ok()
            .body(result);
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
