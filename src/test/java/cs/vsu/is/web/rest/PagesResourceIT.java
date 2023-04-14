package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Pages;
import cs.vsu.is.repository.PagesRepository;
import cs.vsu.is.service.dto.PagesDTO;
import cs.vsu.is.service.mapper.PagesMapper;
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
 * Integration tests for the {@link PagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PagesResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PagesRepository pagesRepository;

    @Autowired
    private PagesMapper pagesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagesMockMvc;

    private Pages pages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pages createEntity(EntityManager em) {
        Pages pages = new Pages().content(DEFAULT_CONTENT);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        pages.setEmployee(employee);
        return pages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pages createUpdatedEntity(EntityManager em) {
        Pages pages = new Pages().content(UPDATED_CONTENT);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        pages.setEmployee(employee);
        return pages;
    }

    @BeforeEach
    public void initTest() {
        pages = createEntity(em);
    }

    @Test
    @Transactional
    void createPages() throws Exception {
        int databaseSizeBeforeCreate = pagesRepository.findAll().size();
        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);
        restPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeCreate + 1);
        Pages testPages = pagesList.get(pagesList.size() - 1);
        assertThat(testPages.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    void createPagesWithExistingId() throws Exception {
        // Create the Pages with an existing ID
        pages.setId(1L);
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        int databaseSizeBeforeCreate = pagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        // Get all the pagesList
        restPagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pages.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    void getPages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        // Get the pages
        restPagesMockMvc
            .perform(get(ENTITY_API_URL_ID, pages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pages.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }

    @Test
    @Transactional
    void getNonExistingPages() throws Exception {
        // Get the pages
        restPagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();

        // Update the pages
        Pages updatedPages = pagesRepository.findById(pages.getId()).get();
        // Disconnect from session so that the updates on updatedPages are not directly saved in db
        em.detach(updatedPages);
        updatedPages.content(UPDATED_CONTENT);
        PagesDTO pagesDTO = pagesMapper.toDto(updatedPages);

        restPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
        Pages testPages = pagesList.get(pagesList.size() - 1);
        assertThat(testPages.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void putNonExistingPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();
        pages.setId(count.incrementAndGet());

        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();
        pages.setId(count.incrementAndGet());

        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();
        pages.setId(count.incrementAndGet());

        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pagesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePagesWithPatch() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();

        // Update the pages using partial update
        Pages partialUpdatedPages = new Pages();
        partialUpdatedPages.setId(pages.getId());

        partialUpdatedPages.content(UPDATED_CONTENT);

        restPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPages))
            )
            .andExpect(status().isOk());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
        Pages testPages = pagesList.get(pagesList.size() - 1);
        assertThat(testPages.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void fullUpdatePagesWithPatch() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();

        // Update the pages using partial update
        Pages partialUpdatedPages = new Pages();
        partialUpdatedPages.setId(pages.getId());

        partialUpdatedPages.content(UPDATED_CONTENT);

        restPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPages))
            )
            .andExpect(status().isOk());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
        Pages testPages = pagesList.get(pagesList.size() - 1);
        assertThat(testPages.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void patchNonExistingPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();
        pages.setId(count.incrementAndGet());

        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pagesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();
        pages.setId(count.incrementAndGet());

        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pagesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPages() throws Exception {
        int databaseSizeBeforeUpdate = pagesRepository.findAll().size();
        pages.setId(count.incrementAndGet());

        // Create the Pages
        PagesDTO pagesDTO = pagesMapper.toDto(pages);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pagesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pages in the database
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        int databaseSizeBeforeDelete = pagesRepository.findAll().size();

        // Delete the pages
        restPagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, pages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pages> pagesList = pagesRepository.findAll();
        assertThat(pagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
