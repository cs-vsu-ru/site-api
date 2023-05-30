package cs.vsu.is.web.rest;

import cs.vsu.is.domain.Emails;
import cs.vsu.is.domain.Newsletter;
import cs.vsu.is.repository.EmailsRepository;
import cs.vsu.is.repository.NewsletterRepository;
import cs.vsu.is.service.dto.NewsletterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<NewsletterDTO> createNewsletter(@RequestBody Newsletter newsletter) throws URISyntaxException {
        newsletter.setNewsletterDate(newsletter.getNewsletterDate().minusHours(3));
        newsletter.setStatus("open");
        newsletter =  newsletterRepository.save(newsletter);
        for(Emails item: newsletter.getEmails()) {
            item.setNewsletter(newsletter);
        }
        List<Emails> emails = emailsRepository.saveAll(newsletter.getEmails());
        newsletter.setEmails(emails);
        return ResponseEntity
            .created(new URI("/api/newsletter/" + newsletter.getId()))
            .body(convert(newsletter));
    }

    @PutMapping("/newsletter/{id}")
    public ResponseEntity<NewsletterDTO> updateNewsletter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Newsletter newsletter) {
        newsletter.setId(id);
        if(newsletter.getNewsletterDate() != null) {
            newsletter.setNewsletterDate(newsletter.getNewsletterDate().minusHours(3));
        }
        Newsletter result = newsletterRepository.save(newsletter);
        return ResponseEntity
            .ok()
            .body(convert(result));
    }

    @GetMapping("/newsletter/{id}")
    public ResponseEntity<NewsletterDTO> getNewsletter(@PathVariable Long id) {
        Optional<Newsletter> newsletter = newsletterRepository.findById(id);
        return newsletter.map(value -> ResponseEntity.ok(convert(value))).orElseGet(() -> ResponseEntity.ok(null));
    }

    @GetMapping("/newsletters")
    public ResponseEntity<List<NewsletterDTO>> getAllNewsletter() {
        List<Newsletter> newsletters = newsletterRepository.findAll();
        List<NewsletterDTO> collect = newsletters.stream().map(this::convert).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @DeleteMapping("/newsletter/{id}")
    public void deleteNewsletter(
        @PathVariable(value = "id", required = false) final Long id) {
        newsletterRepository.deleteById(id);
    }

    private NewsletterDTO convert(Newsletter newsletter) {
        NewsletterDTO newsletterDTO = new NewsletterDTO();
        newsletterDTO.setId(newsletter.getId());
        newsletterDTO.setSubject(newsletter.getSubject());
        newsletterDTO.setStatus(newsletterDTO.getStatus());
        newsletterDTO.setContent(newsletterDTO.getContent());
        newsletterDTO.setNewsletterDate(newsletter.getNewsletterDate().toString());
        newsletterDTO.setEmails(newsletter.getEmails());
        return newsletterDTO;
    }
}
