package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.Teaching;
import cs.vsu.is.repository.TeachingRepository;
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
 * Integration tests for the {@link TeachingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeachingResourceIT {

    private static final String ENTITY_API_URL = "/api/teachings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeachingRepository teachingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeachingMockMvc;

    private Teaching teaching;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teaching createEntity(EntityManager em) {
        Teaching teaching = new Teaching();
        return teaching;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teaching createUpdatedEntity(EntityManager em) {
        Teaching teaching = new Teaching();
        return teaching;
    }

    @BeforeEach
    public void initTest() {
        teaching = createEntity(em);
    }

    @Test
    @Transactional
    void createTeaching() throws Exception {
        int databaseSizeBeforeCreate = teachingRepository.findAll().size();
        // Create the Teaching
        restTeachingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teaching)))
            .andExpect(status().isCreated());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeCreate + 1);
        Teaching testTeaching = teachingList.get(teachingList.size() - 1);
    }

    @Test
    @Transactional
    void createTeachingWithExistingId() throws Exception {
        // Create the Teaching with an existing ID
        teaching.setId(1L);

        int databaseSizeBeforeCreate = teachingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teaching)))
            .andExpect(status().isBadRequest());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeachings() throws Exception {
        // Initialize the database
        teachingRepository.saveAndFlush(teaching);

        // Get all the teachingList
        restTeachingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teaching.getId().intValue())));
    }

    @Test
    @Transactional
    void getTeaching() throws Exception {
        // Initialize the database
        teachingRepository.saveAndFlush(teaching);

        // Get the teaching
        restTeachingMockMvc
            .perform(get(ENTITY_API_URL_ID, teaching.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teaching.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTeaching() throws Exception {
        // Get the teaching
        restTeachingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeaching() throws Exception {
        // Initialize the database
        teachingRepository.saveAndFlush(teaching);

        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();

        // Update the teaching
        Teaching updatedTeaching = teachingRepository.findById(teaching.getId()).get();
        // Disconnect from session so that the updates on updatedTeaching are not directly saved in db
        em.detach(updatedTeaching);

        restTeachingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTeaching.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTeaching))
            )
            .andExpect(status().isOk());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
        Teaching testTeaching = teachingList.get(teachingList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingTeaching() throws Exception {
        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();
        teaching.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teaching.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teaching))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeaching() throws Exception {
        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();
        teaching.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teaching))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeaching() throws Exception {
        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();
        teaching.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teaching)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeachingWithPatch() throws Exception {
        // Initialize the database
        teachingRepository.saveAndFlush(teaching);

        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();

        // Update the teaching using partial update
        Teaching partialUpdatedTeaching = new Teaching();
        partialUpdatedTeaching.setId(teaching.getId());

        restTeachingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeaching.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeaching))
            )
            .andExpect(status().isOk());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
        Teaching testTeaching = teachingList.get(teachingList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateTeachingWithPatch() throws Exception {
        // Initialize the database
        teachingRepository.saveAndFlush(teaching);

        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();

        // Update the teaching using partial update
        Teaching partialUpdatedTeaching = new Teaching();
        partialUpdatedTeaching.setId(teaching.getId());

        restTeachingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeaching.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeaching))
            )
            .andExpect(status().isOk());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
        Teaching testTeaching = teachingList.get(teachingList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingTeaching() throws Exception {
        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();
        teaching.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teaching.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teaching))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeaching() throws Exception {
        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();
        teaching.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teaching))
            )
            .andExpect(status().isBadRequest());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeaching() throws Exception {
        int databaseSizeBeforeUpdate = teachingRepository.findAll().size();
        teaching.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teaching)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Teaching in the database
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeaching() throws Exception {
        // Initialize the database
        teachingRepository.saveAndFlush(teaching);

        int databaseSizeBeforeDelete = teachingRepository.findAll().size();

        // Delete the teaching
        restTeachingMockMvc
            .perform(delete(ENTITY_API_URL_ID, teaching.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teaching> teachingList = teachingRepository.findAll();
        assertThat(teachingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
