package cs.vsu.is.service;

import cs.vsu.is.domain.Articles;
import cs.vsu.is.repository.ArticlesRepository;
import cs.vsu.is.service.dto.ArticlesDTO;
import cs.vsu.is.service.mapper.ArticlesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Articles}.
 */
@Service
@Transactional
public class ArticlesService {

    private final Logger log = LoggerFactory.getLogger(ArticlesService.class);

    private final ArticlesRepository articlesRepository;

    private final ArticlesMapper articlesMapper;

    public ArticlesService(ArticlesRepository articlesRepository, ArticlesMapper articlesMapper) {
        this.articlesRepository = articlesRepository;
        this.articlesMapper = articlesMapper;
    }

    /**
     * Save a articles.
     *
     * @param articlesDTO the entity to save.
     * @return the persisted entity.
     */
    public ArticlesDTO save(ArticlesDTO articlesDTO) {
        log.debug("Request to save Articles : {}", articlesDTO);
        Articles articles = articlesMapper.toEntity(articlesDTO);
        articles = articlesRepository.save(articles);
        return articlesMapper.toDto(articles);
    }

    /**
     * Update a articles.
     *
     * @param articlesDTO the entity to save.
     * @return the persisted entity.
     */
    public ArticlesDTO update(ArticlesDTO articlesDTO) {
        log.debug("Request to update Articles : {}", articlesDTO);
        Articles articles = articlesMapper.toEntity(articlesDTO);
        articles = articlesRepository.save(articles);
        return articlesMapper.toDto(articles);
    }

    /**
     * Partially update a articles.
     *
     * @param articlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArticlesDTO> partialUpdate(ArticlesDTO articlesDTO) {
        log.debug("Request to partially update Articles : {}", articlesDTO);

        return articlesRepository
            .findById(articlesDTO.getId())
            .map(existingArticles -> {
                articlesMapper.partialUpdate(existingArticles, articlesDTO);

                return existingArticles;
            })
            .map(articlesRepository::save)
            .map(articlesMapper::toDto);
    }

    /**
     * Get all the articles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ArticlesDTO> findAll() {
        log.debug("Request to get all Articles");
        return articlesRepository.findAll().stream().map(articlesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one articles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticlesDTO> findOne(Long id) {
        log.debug("Request to get Articles : {}", id);
        return articlesRepository.findById(id).map(articlesMapper::toDto);
    }

    /**
     * Delete the articles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Articles : {}", id);
        articlesRepository.deleteById(id);
    }
}
