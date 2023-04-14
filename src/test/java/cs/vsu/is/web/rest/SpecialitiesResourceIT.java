package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.Specialities;
import cs.vsu.is.repository.SpecialitiesRepository;
import cs.vsu.is.service.dto.SpecialitiesDTO;
import cs.vsu.is.service.mapper.SpecialitiesMapper;
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
 * Integration tests for the {@link SpecialitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecialitiesResourceIT {

    private static final String ENTITY_API_URL = "/api/specialities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecialitiesRepository specialitiesRepository;

    @Autowired
    private SpecialitiesMapper specialitiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialitiesMockMvc;

    private Specialities specialities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialities createEntity(EntityManager em) {
        Specialities specialities = new Specialities();
        return specialities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialities createUpdatedEntity(EntityManager em) {
        Specialities specialities = new Specialities();
        return specialities;
    }

    @BeforeEach
    public void initTest() {
        specialities = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecialities() throws Exception {
        int databaseSizeBeforeCreate = specialitiesRepository.findAll().size();
        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);
        restSpecialitiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Specialities testSpecialities = specialitiesList.get(specialitiesList.size() - 1);
    }

    @Test
    @Transactional
    void createSpecialitiesWithExistingId() throws Exception {
        // Create the Specialities with an existing ID
        specialities.setId(1L);
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        int databaseSizeBeforeCreate = specialitiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialitiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpecialities() throws Exception {
        // Initialize the database
        specialitiesRepository.saveAndFlush(specialities);

        // Get all the specialitiesList
        restSpecialitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialities.getId().intValue())));
    }

    @Test
    @Transactional
    void getSpecialities() throws Exception {
        // Initialize the database
        specialitiesRepository.saveAndFlush(specialities);

        // Get the specialities
        restSpecialitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, specialities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialities.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSpecialities() throws Exception {
        // Get the specialities
        restSpecialitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpecialities() throws Exception {
        // Initialize the database
        specialitiesRepository.saveAndFlush(specialities);

        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();

        // Update the specialities
        Specialities updatedSpecialities = specialitiesRepository.findById(specialities.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialities are not directly saved in db
        em.detach(updatedSpecialities);
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(updatedSpecialities);

        restSpecialitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialitiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
        Specialities testSpecialities = specialitiesList.get(specialitiesList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();
        specialities.setId(count.incrementAndGet());

        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialitiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();
        specialities.setId(count.incrementAndGet());

        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();
        specialities.setId(count.incrementAndGet());

        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialitiesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecialitiesWithPatch() throws Exception {
        // Initialize the database
        specialitiesRepository.saveAndFlush(specialities);

        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();

        // Update the specialities using partial update
        Specialities partialUpdatedSpecialities = new Specialities();
        partialUpdatedSpecialities.setId(specialities.getId());

        restSpecialitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialities))
            )
            .andExpect(status().isOk());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
        Specialities testSpecialities = specialitiesList.get(specialitiesList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateSpecialitiesWithPatch() throws Exception {
        // Initialize the database
        specialitiesRepository.saveAndFlush(specialities);

        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();

        // Update the specialities using partial update
        Specialities partialUpdatedSpecialities = new Specialities();
        partialUpdatedSpecialities.setId(specialities.getId());

        restSpecialitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialities))
            )
            .andExpect(status().isOk());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
        Specialities testSpecialities = specialitiesList.get(specialitiesList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();
        specialities.setId(count.incrementAndGet());

        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specialitiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();
        specialities.setId(count.incrementAndGet());

        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecialities() throws Exception {
        int databaseSizeBeforeUpdate = specialitiesRepository.findAll().size();
        specialities.setId(count.incrementAndGet());

        // Create the Specialities
        SpecialitiesDTO specialitiesDTO = specialitiesMapper.toDto(specialities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialitiesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specialities in the database
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecialities() throws Exception {
        // Initialize the database
        specialitiesRepository.saveAndFlush(specialities);

        int databaseSizeBeforeDelete = specialitiesRepository.findAll().size();

        // Delete the specialities
        restSpecialitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, specialities.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialities> specialitiesList = specialitiesRepository.findAll();
        assertThat(specialitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
