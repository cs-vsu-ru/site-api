package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.Articles;
import cs.vsu.is.repository.ArticlesRepository;
import cs.vsu.is.service.dto.ArticlesDTO;
import cs.vsu.is.service.mapper.ArticlesMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArticlesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArticlesResourceIT {

    private static final Instant DEFAULT_PUBLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/articles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlesMockMvc;

    private Articles articles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articles createEntity(EntityManager em) {
        Articles articles = new Articles().publicationDate(DEFAULT_PUBLICATION_DATE).content(DEFAULT_CONTENT);
        return articles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articles createUpdatedEntity(EntityManager em) {
        Articles articles = new Articles().publicationDate(UPDATED_PUBLICATION_DATE).content(UPDATED_CONTENT);
        return articles;
    }

    @BeforeEach
    public void initTest() {
        articles = createEntity(em);
    }

    @Test
    @Transactional
    void createArticles() throws Exception {
        int databaseSizeBeforeCreate = articlesRepository.findAll().size();
        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);
        restArticlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isCreated());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeCreate + 1);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testArticles.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createArticlesWithExistingId() throws Exception {
        // Create the Articles with an existing ID
        articles.setId(1L);
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        int databaseSizeBeforeCreate = articlesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        // Get all the articlesList
        restArticlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articles.getId().intValue())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        // Get the articles
        restArticlesMockMvc
            .perform(get(ENTITY_API_URL_ID, articles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articles.getId().intValue()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingArticles() throws Exception {
        // Get the articles
        restArticlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles
        Articles updatedArticles = articlesRepository.findById(articles.getId()).get();
        // Disconnect from session so that the updates on updatedArticles are not directly saved in db
        em.detach(updatedArticles);
        updatedArticles.publicationDate(UPDATED_PUBLICATION_DATE).content(UPDATED_CONTENT);
        ArticlesDTO articlesDTO = articlesMapper.toDto(updatedArticles);

        restArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testArticles.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(count.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, articlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(count.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(count.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(articlesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArticlesWithPatch() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles using partial update
        Articles partialUpdatedArticles = new Articles();
        partialUpdatedArticles.setId(articles.getId());

        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticles))
            )
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testArticles.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdateArticlesWithPatch() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles using partial update
        Articles partialUpdatedArticles = new Articles();
        partialUpdatedArticles.setId(articles.getId());

        partialUpdatedArticles.publicationDate(UPDATED_PUBLICATION_DATE).content(UPDATED_CONTENT);

        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArticles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArticles))
            )
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testArticles.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(count.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, articlesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(count.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();
        articles.setId(count.incrementAndGet());

        // Create the Articles
        ArticlesDTO articlesDTO = articlesMapper.toDto(articles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArticlesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(articlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeDelete = articlesRepository.findAll().size();

        // Delete the articles
        restArticlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, articles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
