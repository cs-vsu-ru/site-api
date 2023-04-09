package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.AccessModes;
import cs.vsu.is.repository.AccessModesRepository;
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
 * Integration tests for the {@link AccessModesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccessModesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/access-modes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccessModesRepository accessModesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccessModesMockMvc;

    private AccessModes accessModes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccessModes createEntity(EntityManager em) {
        AccessModes accessModes = new AccessModes().name(DEFAULT_NAME);
        return accessModes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccessModes createUpdatedEntity(EntityManager em) {
        AccessModes accessModes = new AccessModes().name(UPDATED_NAME);
        return accessModes;
    }

    @BeforeEach
    public void initTest() {
        accessModes = createEntity(em);
    }

    @Test
    @Transactional
    void createAccessModes() throws Exception {
        int databaseSizeBeforeCreate = accessModesRepository.findAll().size();
        // Create the AccessModes
        restAccessModesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accessModes)))
            .andExpect(status().isCreated());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeCreate + 1);
        AccessModes testAccessModes = accessModesList.get(accessModesList.size() - 1);
        assertThat(testAccessModes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createAccessModesWithExistingId() throws Exception {
        // Create the AccessModes with an existing ID
        accessModes.setId(1L);

        int databaseSizeBeforeCreate = accessModesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessModesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accessModes)))
            .andExpect(status().isBadRequest());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccessModes() throws Exception {
        // Initialize the database
        accessModesRepository.saveAndFlush(accessModes);

        // Get all the accessModesList
        restAccessModesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accessModes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getAccessModes() throws Exception {
        // Initialize the database
        accessModesRepository.saveAndFlush(accessModes);

        // Get the accessModes
        restAccessModesMockMvc
            .perform(get(ENTITY_API_URL_ID, accessModes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accessModes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAccessModes() throws Exception {
        // Get the accessModes
        restAccessModesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccessModes() throws Exception {
        // Initialize the database
        accessModesRepository.saveAndFlush(accessModes);

        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();

        // Update the accessModes
        AccessModes updatedAccessModes = accessModesRepository.findById(accessModes.getId()).get();
        // Disconnect from session so that the updates on updatedAccessModes are not directly saved in db
        em.detach(updatedAccessModes);
        updatedAccessModes.name(UPDATED_NAME);

        restAccessModesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccessModes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccessModes))
            )
            .andExpect(status().isOk());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
        AccessModes testAccessModes = accessModesList.get(accessModesList.size() - 1);
        assertThat(testAccessModes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAccessModes() throws Exception {
        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();
        accessModes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessModesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accessModes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accessModes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccessModes() throws Exception {
        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();
        accessModes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessModesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accessModes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccessModes() throws Exception {
        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();
        accessModes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessModesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accessModes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccessModesWithPatch() throws Exception {
        // Initialize the database
        accessModesRepository.saveAndFlush(accessModes);

        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();

        // Update the accessModes using partial update
        AccessModes partialUpdatedAccessModes = new AccessModes();
        partialUpdatedAccessModes.setId(accessModes.getId());

        restAccessModesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccessModes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccessModes))
            )
            .andExpect(status().isOk());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
        AccessModes testAccessModes = accessModesList.get(accessModesList.size() - 1);
        assertThat(testAccessModes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAccessModesWithPatch() throws Exception {
        // Initialize the database
        accessModesRepository.saveAndFlush(accessModes);

        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();

        // Update the accessModes using partial update
        AccessModes partialUpdatedAccessModes = new AccessModes();
        partialUpdatedAccessModes.setId(accessModes.getId());

        partialUpdatedAccessModes.name(UPDATED_NAME);

        restAccessModesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccessModes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccessModes))
            )
            .andExpect(status().isOk());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
        AccessModes testAccessModes = accessModesList.get(accessModesList.size() - 1);
        assertThat(testAccessModes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAccessModes() throws Exception {
        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();
        accessModes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessModesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accessModes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accessModes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccessModes() throws Exception {
        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();
        accessModes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessModesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accessModes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccessModes() throws Exception {
        int databaseSizeBeforeUpdate = accessModesRepository.findAll().size();
        accessModes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessModesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accessModes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccessModes in the database
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccessModes() throws Exception {
        // Initialize the database
        accessModesRepository.saveAndFlush(accessModes);

        int databaseSizeBeforeDelete = accessModesRepository.findAll().size();

        // Delete the accessModes
        restAccessModesMockMvc
            .perform(delete(ENTITY_API_URL_ID, accessModes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccessModes> accessModesList = accessModesRepository.findAll();
        assertThat(accessModesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
