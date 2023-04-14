package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.ScientificLeadeship;
import cs.vsu.is.repository.ScientificLeadeshipRepository;
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
 * Integration tests for the {@link ScientificLeadeshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScientificLeadeshipResourceIT {

    private static final String ENTITY_API_URL = "/api/scientific-leadeships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScientificLeadeshipRepository scientificLeadeshipRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScientificLeadeshipMockMvc;

    private ScientificLeadeship scientificLeadeship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScientificLeadeship createEntity(EntityManager em) {
        ScientificLeadeship scientificLeadeship = new ScientificLeadeship();
        return scientificLeadeship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScientificLeadeship createUpdatedEntity(EntityManager em) {
        ScientificLeadeship scientificLeadeship = new ScientificLeadeship();
        return scientificLeadeship;
    }

    @BeforeEach
    public void initTest() {
        scientificLeadeship = createEntity(em);
    }

    @Test
    @Transactional
    void createScientificLeadeship() throws Exception {
        int databaseSizeBeforeCreate = scientificLeadeshipRepository.findAll().size();
        // Create the ScientificLeadeship
        restScientificLeadeshipMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isCreated());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeCreate + 1);
        ScientificLeadeship testScientificLeadeship = scientificLeadeshipList.get(scientificLeadeshipList.size() - 1);
    }

    @Test
    @Transactional
    void createScientificLeadeshipWithExistingId() throws Exception {
        // Create the ScientificLeadeship with an existing ID
        scientificLeadeship.setId(1L);

        int databaseSizeBeforeCreate = scientificLeadeshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScientificLeadeshipMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScientificLeadeships() throws Exception {
        // Initialize the database
        scientificLeadeshipRepository.saveAndFlush(scientificLeadeship);

        // Get all the scientificLeadeshipList
        restScientificLeadeshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scientificLeadeship.getId().intValue())));
    }

    @Test
    @Transactional
    void getScientificLeadeship() throws Exception {
        // Initialize the database
        scientificLeadeshipRepository.saveAndFlush(scientificLeadeship);

        // Get the scientificLeadeship
        restScientificLeadeshipMockMvc
            .perform(get(ENTITY_API_URL_ID, scientificLeadeship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scientificLeadeship.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingScientificLeadeship() throws Exception {
        // Get the scientificLeadeship
        restScientificLeadeshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScientificLeadeship() throws Exception {
        // Initialize the database
        scientificLeadeshipRepository.saveAndFlush(scientificLeadeship);

        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();

        // Update the scientificLeadeship
        ScientificLeadeship updatedScientificLeadeship = scientificLeadeshipRepository.findById(scientificLeadeship.getId()).get();
        // Disconnect from session so that the updates on updatedScientificLeadeship are not directly saved in db
        em.detach(updatedScientificLeadeship);

        restScientificLeadeshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScientificLeadeship.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScientificLeadeship))
            )
            .andExpect(status().isOk());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
        ScientificLeadeship testScientificLeadeship = scientificLeadeshipList.get(scientificLeadeshipList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingScientificLeadeship() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();
        scientificLeadeship.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScientificLeadeshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scientificLeadeship.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScientificLeadeship() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();
        scientificLeadeship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadeshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScientificLeadeship() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();
        scientificLeadeship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadeshipMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScientificLeadeshipWithPatch() throws Exception {
        // Initialize the database
        scientificLeadeshipRepository.saveAndFlush(scientificLeadeship);

        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();

        // Update the scientificLeadeship using partial update
        ScientificLeadeship partialUpdatedScientificLeadeship = new ScientificLeadeship();
        partialUpdatedScientificLeadeship.setId(scientificLeadeship.getId());

        restScientificLeadeshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScientificLeadeship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScientificLeadeship))
            )
            .andExpect(status().isOk());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
        ScientificLeadeship testScientificLeadeship = scientificLeadeshipList.get(scientificLeadeshipList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateScientificLeadeshipWithPatch() throws Exception {
        // Initialize the database
        scientificLeadeshipRepository.saveAndFlush(scientificLeadeship);

        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();

        // Update the scientificLeadeship using partial update
        ScientificLeadeship partialUpdatedScientificLeadeship = new ScientificLeadeship();
        partialUpdatedScientificLeadeship.setId(scientificLeadeship.getId());

        restScientificLeadeshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScientificLeadeship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScientificLeadeship))
            )
            .andExpect(status().isOk());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
        ScientificLeadeship testScientificLeadeship = scientificLeadeshipList.get(scientificLeadeshipList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingScientificLeadeship() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();
        scientificLeadeship.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScientificLeadeshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scientificLeadeship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScientificLeadeship() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();
        scientificLeadeship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadeshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScientificLeadeship() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadeshipRepository.findAll().size();
        scientificLeadeship.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadeshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeadeship))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScientificLeadeship in the database
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScientificLeadeship() throws Exception {
        // Initialize the database
        scientificLeadeshipRepository.saveAndFlush(scientificLeadeship);

        int databaseSizeBeforeDelete = scientificLeadeshipRepository.findAll().size();

        // Delete the scientificLeadeship
        restScientificLeadeshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, scientificLeadeship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScientificLeadeship> scientificLeadeshipList = scientificLeadeshipRepository.findAll();
        assertThat(scientificLeadeshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
