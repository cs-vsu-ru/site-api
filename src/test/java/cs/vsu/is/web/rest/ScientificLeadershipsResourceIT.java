package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.ScientificLeaderships;
import cs.vsu.is.repository.ScientificLeadershipsRepository;
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
 * Integration tests for the {@link ScientificLeadershipsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScientificLeadershipsResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String ENTITY_API_URL = "/api/scientific-leaderships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScientificLeadershipsRepository scientificLeadershipsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScientificLeadershipsMockMvc;

    private ScientificLeaderships scientificLeaderships;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScientificLeaderships createEntity(EntityManager em) {
        ScientificLeaderships scientificLeaderships = new ScientificLeaderships().year(DEFAULT_YEAR);
        return scientificLeaderships;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScientificLeaderships createUpdatedEntity(EntityManager em) {
        ScientificLeaderships scientificLeaderships = new ScientificLeaderships().year(UPDATED_YEAR);
        return scientificLeaderships;
    }

    @BeforeEach
    public void initTest() {
        scientificLeaderships = createEntity(em);
    }

    @Test
    @Transactional
    void createScientificLeaderships() throws Exception {
        int databaseSizeBeforeCreate = scientificLeadershipsRepository.findAll().size();
        // Create the ScientificLeaderships
        restScientificLeadershipsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isCreated());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeCreate + 1);
        ScientificLeaderships testScientificLeaderships = scientificLeadershipsList.get(scientificLeadershipsList.size() - 1);
        assertThat(testScientificLeaderships.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    void createScientificLeadershipsWithExistingId() throws Exception {
        // Create the ScientificLeaderships with an existing ID
        scientificLeaderships.setId(1L);

        int databaseSizeBeforeCreate = scientificLeadershipsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScientificLeadershipsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScientificLeaderships() throws Exception {
        // Initialize the database
        scientificLeadershipsRepository.saveAndFlush(scientificLeaderships);

        // Get all the scientificLeadershipsList
        restScientificLeadershipsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scientificLeaderships.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }

    @Test
    @Transactional
    void getScientificLeaderships() throws Exception {
        // Initialize the database
        scientificLeadershipsRepository.saveAndFlush(scientificLeaderships);

        // Get the scientificLeaderships
        restScientificLeadershipsMockMvc
            .perform(get(ENTITY_API_URL_ID, scientificLeaderships.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scientificLeaderships.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }

    @Test
    @Transactional
    void getNonExistingScientificLeaderships() throws Exception {
        // Get the scientificLeaderships
        restScientificLeadershipsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScientificLeaderships() throws Exception {
        // Initialize the database
        scientificLeadershipsRepository.saveAndFlush(scientificLeaderships);

        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();

        // Update the scientificLeaderships
        ScientificLeaderships updatedScientificLeaderships = scientificLeadershipsRepository.findById(scientificLeaderships.getId()).get();
        // Disconnect from session so that the updates on updatedScientificLeaderships are not directly saved in db
        em.detach(updatedScientificLeaderships);
        updatedScientificLeaderships.year(UPDATED_YEAR);

        restScientificLeadershipsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScientificLeaderships.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScientificLeaderships))
            )
            .andExpect(status().isOk());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
        ScientificLeaderships testScientificLeaderships = scientificLeadershipsList.get(scientificLeadershipsList.size() - 1);
        assertThat(testScientificLeaderships.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void putNonExistingScientificLeaderships() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();
        scientificLeaderships.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScientificLeadershipsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scientificLeaderships.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScientificLeaderships() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();
        scientificLeaderships.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadershipsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScientificLeaderships() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();
        scientificLeaderships.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadershipsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScientificLeadershipsWithPatch() throws Exception {
        // Initialize the database
        scientificLeadershipsRepository.saveAndFlush(scientificLeaderships);

        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();

        // Update the scientificLeaderships using partial update
        ScientificLeaderships partialUpdatedScientificLeaderships = new ScientificLeaderships();
        partialUpdatedScientificLeaderships.setId(scientificLeaderships.getId());

        partialUpdatedScientificLeaderships.year(UPDATED_YEAR);

        restScientificLeadershipsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScientificLeaderships.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScientificLeaderships))
            )
            .andExpect(status().isOk());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
        ScientificLeaderships testScientificLeaderships = scientificLeadershipsList.get(scientificLeadershipsList.size() - 1);
        assertThat(testScientificLeaderships.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void fullUpdateScientificLeadershipsWithPatch() throws Exception {
        // Initialize the database
        scientificLeadershipsRepository.saveAndFlush(scientificLeaderships);

        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();

        // Update the scientificLeaderships using partial update
        ScientificLeaderships partialUpdatedScientificLeaderships = new ScientificLeaderships();
        partialUpdatedScientificLeaderships.setId(scientificLeaderships.getId());

        partialUpdatedScientificLeaderships.year(UPDATED_YEAR);

        restScientificLeadershipsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScientificLeaderships.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScientificLeaderships))
            )
            .andExpect(status().isOk());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
        ScientificLeaderships testScientificLeaderships = scientificLeadershipsList.get(scientificLeadershipsList.size() - 1);
        assertThat(testScientificLeaderships.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void patchNonExistingScientificLeaderships() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();
        scientificLeaderships.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScientificLeadershipsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scientificLeaderships.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScientificLeaderships() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();
        scientificLeaderships.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadershipsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScientificLeaderships() throws Exception {
        int databaseSizeBeforeUpdate = scientificLeadershipsRepository.findAll().size();
        scientificLeaderships.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificLeadershipsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificLeaderships))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScientificLeaderships in the database
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScientificLeaderships() throws Exception {
        // Initialize the database
        scientificLeadershipsRepository.saveAndFlush(scientificLeaderships);

        int databaseSizeBeforeDelete = scientificLeadershipsRepository.findAll().size();

        // Delete the scientificLeaderships
        restScientificLeadershipsMockMvc
            .perform(delete(ENTITY_API_URL_ID, scientificLeaderships.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScientificLeaderships> scientificLeadershipsList = scientificLeadershipsRepository.findAll();
        assertThat(scientificLeadershipsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
