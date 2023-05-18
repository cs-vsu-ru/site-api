package cs.vsu.is.web.rest;

import cs.vsu.is.domain.StaticPages;
import cs.vsu.is.repository.StaticPagesRepository;
import cs.vsu.is.service.convertor.update.StaticPagesConverterUpdate;
import cs.vsu.is.service.dto.StaticPagesDTO;
import cs.vsu.is.web.rest.errors.BadRequestAlertException;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public class StaticPagesController {

  private final Logger log = LoggerFactory.getLogger(StaticPagesController.class);

  private StaticPagesRepository staticPagesRepository;
  private final StaticPagesConverterUpdate converterUpdate;

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

  @PutMapping("/static-page/{id}")
  public ResponseEntity<StaticPages> editStaticPage(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody StaticPagesDTO staticPages) throws Exception {
    log.debug("REST request to save Static page : {}", staticPages);
    Optional<StaticPages> founded = staticPagesRepository.findById(id);
    if (!founded.isPresent()) {
      throw new Exception("Не найдено записи с id=1");
    }
    StaticPages entity = founded.get();
    converterUpdate.substitute(staticPages, entity);
    entity = staticPagesRepository.save(entity);
    return ResponseEntity
        .created(new URI("/api/students/" + staticPages.getId()))
        .body(entity);
  }

  @DeleteMapping("/static-page/{id}")
  public ResponseEntity<Void> deleteStaticPage(@PathVariable Long id) {
    log.debug("REST request to delete Static page : {}", id);
    staticPagesRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
