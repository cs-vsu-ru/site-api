package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.ScientificWorkType;
import cs.vsu.is.repository.ScientificWorkTypeRepository;
import cs.vsu.is.service.dto.ScientificWorkTypeDTO;
import cs.vsu.is.service.mapper.ScientificWorkTypeMapper;
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
 * Integration tests for the {@link ScientificWorkTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScientificWorkTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/scientific-work-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScientificWorkTypeRepository scientificWorkTypeRepository;

    @Autowired
    private ScientificWorkTypeMapper scientificWorkTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScientificWorkTypeMockMvc;

    private ScientificWorkType scientificWorkType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScientificWorkType createEntity(EntityManager em) {
        ScientificWorkType scientificWorkType = new ScientificWorkType().name(DEFAULT_NAME);
        return scientificWorkType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScientificWorkType createUpdatedEntity(EntityManager em) {
        ScientificWorkType scientificWorkType = new ScientificWorkType().name(UPDATED_NAME);
        return scientificWorkType;
    }

    @BeforeEach
    public void initTest() {
        scientificWorkType = createEntity(em);
    }

    @Test
    @Transactional
    void createScientificWorkType() throws Exception {
        int databaseSizeBeforeCreate = scientificWorkTypeRepository.findAll().size();
        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);
        restScientificWorkTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ScientificWorkType testScientificWorkType = scientificWorkTypeList.get(scientificWorkTypeList.size() - 1);
        assertThat(testScientificWorkType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createScientificWorkTypeWithExistingId() throws Exception {
        // Create the ScientificWorkType with an existing ID
        scientificWorkType.setId(1L);
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        int databaseSizeBeforeCreate = scientificWorkTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScientificWorkTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScientificWorkTypes() throws Exception {
        // Initialize the database
        scientificWorkTypeRepository.saveAndFlush(scientificWorkType);

        // Get all the scientificWorkTypeList
        restScientificWorkTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scientificWorkType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getScientificWorkType() throws Exception {
        // Initialize the database
        scientificWorkTypeRepository.saveAndFlush(scientificWorkType);

        // Get the scientificWorkType
        restScientificWorkTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, scientificWorkType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scientificWorkType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingScientificWorkType() throws Exception {
        // Get the scientificWorkType
        restScientificWorkTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScientificWorkType() throws Exception {
        // Initialize the database
        scientificWorkTypeRepository.saveAndFlush(scientificWorkType);

        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();

        // Update the scientificWorkType
        ScientificWorkType updatedScientificWorkType = scientificWorkTypeRepository.findById(scientificWorkType.getId()).get();
        // Disconnect from session so that the updates on updatedScientificWorkType are not directly saved in db
        em.detach(updatedScientificWorkType);
        updatedScientificWorkType.name(UPDATED_NAME);
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(updatedScientificWorkType);

        restScientificWorkTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scientificWorkTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
        ScientificWorkType testScientificWorkType = scientificWorkTypeList.get(scientificWorkTypeList.size() - 1);
        assertThat(testScientificWorkType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingScientificWorkType() throws Exception {
        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();
        scientificWorkType.setId(count.incrementAndGet());

        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScientificWorkTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scientificWorkTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScientificWorkType() throws Exception {
        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();
        scientificWorkType.setId(count.incrementAndGet());

        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificWorkTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScientificWorkType() throws Exception {
        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();
        scientificWorkType.setId(count.incrementAndGet());

        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificWorkTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScientificWorkTypeWithPatch() throws Exception {
        // Initialize the database
        scientificWorkTypeRepository.saveAndFlush(scientificWorkType);

        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();

        // Update the scientificWorkType using partial update
        ScientificWorkType partialUpdatedScientificWorkType = new ScientificWorkType();
        partialUpdatedScientificWorkType.setId(scientificWorkType.getId());

        partialUpdatedScientificWorkType.name(UPDATED_NAME);

        restScientificWorkTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScientificWorkType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScientificWorkType))
            )
            .andExpect(status().isOk());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
        ScientificWorkType testScientificWorkType = scientificWorkTypeList.get(scientificWorkTypeList.size() - 1);
        assertThat(testScientificWorkType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateScientificWorkTypeWithPatch() throws Exception {
        // Initialize the database
        scientificWorkTypeRepository.saveAndFlush(scientificWorkType);

        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();

        // Update the scientificWorkType using partial update
        ScientificWorkType partialUpdatedScientificWorkType = new ScientificWorkType();
        partialUpdatedScientificWorkType.setId(scientificWorkType.getId());

        partialUpdatedScientificWorkType.name(UPDATED_NAME);

        restScientificWorkTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScientificWorkType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScientificWorkType))
            )
            .andExpect(status().isOk());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
        ScientificWorkType testScientificWorkType = scientificWorkTypeList.get(scientificWorkTypeList.size() - 1);
        assertThat(testScientificWorkType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingScientificWorkType() throws Exception {
        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();
        scientificWorkType.setId(count.incrementAndGet());

        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScientificWorkTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scientificWorkTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScientificWorkType() throws Exception {
        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();
        scientificWorkType.setId(count.incrementAndGet());

        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificWorkTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScientificWorkType() throws Exception {
        int databaseSizeBeforeUpdate = scientificWorkTypeRepository.findAll().size();
        scientificWorkType.setId(count.incrementAndGet());

        // Create the ScientificWorkType
        ScientificWorkTypeDTO scientificWorkTypeDTO = scientificWorkTypeMapper.toDto(scientificWorkType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScientificWorkTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scientificWorkTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScientificWorkType in the database
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScientificWorkType() throws Exception {
        // Initialize the database
        scientificWorkTypeRepository.saveAndFlush(scientificWorkType);

        int databaseSizeBeforeDelete = scientificWorkTypeRepository.findAll().size();

        // Delete the scientificWorkType
        restScientificWorkTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, scientificWorkType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScientificWorkType> scientificWorkTypeList = scientificWorkTypeRepository.findAll();
        assertThat(scientificWorkTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
