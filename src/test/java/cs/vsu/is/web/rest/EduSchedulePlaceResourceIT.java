package cs.vsu.is.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cs.vsu.is.IntegrationTest;
import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.repository.EduSchedulePlaceRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EduSchedulePlaceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EduSchedulePlaceResourceIT {

    private static final Boolean DEFAULT_IS_DENOMINATOR = false;
    private static final Boolean UPDATED_IS_DENOMINATOR = true;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DAY_OF_WEAK = 1;
    private static final Integer UPDATED_DAY_OF_WEAK = 2;

    private static final String ENTITY_API_URL = "/api/edu-schedule-places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EduSchedulePlaceRepository eduSchedulePlaceRepository;

    @Mock
    private EduSchedulePlaceRepository eduSchedulePlaceRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEduSchedulePlaceMockMvc;

    private EduSchedulePlace eduSchedulePlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EduSchedulePlace createEntity(EntityManager em) {
        EduSchedulePlace eduSchedulePlace = new EduSchedulePlace()
            .isDenominator(DEFAULT_IS_DENOMINATOR)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .dayOfWeak(DEFAULT_DAY_OF_WEAK);
        return eduSchedulePlace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EduSchedulePlace createUpdatedEntity(EntityManager em) {
        EduSchedulePlace eduSchedulePlace = new EduSchedulePlace()
            .isDenominator(UPDATED_IS_DENOMINATOR)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .dayOfWeak(UPDATED_DAY_OF_WEAK);
        return eduSchedulePlace;
    }

    @BeforeEach
    public void initTest() {
        eduSchedulePlace = createEntity(em);
    }

    @Test
    @Transactional
    void createEduSchedulePlace() throws Exception {
        int databaseSizeBeforeCreate = eduSchedulePlaceRepository.findAll().size();
        // Create the EduSchedulePlace
        restEduSchedulePlaceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isCreated());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeCreate + 1);
        EduSchedulePlace testEduSchedulePlace = eduSchedulePlaceList.get(eduSchedulePlaceList.size() - 1);
        assertThat(testEduSchedulePlace.getIsDenominator()).isEqualTo(DEFAULT_IS_DENOMINATOR);
        assertThat(testEduSchedulePlace.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testEduSchedulePlace.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testEduSchedulePlace.getDayOfWeak()).isEqualTo(DEFAULT_DAY_OF_WEAK);
    }

    @Test
    @Transactional
    void createEduSchedulePlaceWithExistingId() throws Exception {
        // Create the EduSchedulePlace with an existing ID
        eduSchedulePlace.setId(1L);

        int databaseSizeBeforeCreate = eduSchedulePlaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEduSchedulePlaceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEduSchedulePlaces() throws Exception {
        // Initialize the database
        eduSchedulePlaceRepository.saveAndFlush(eduSchedulePlace);

        // Get all the eduSchedulePlaceList
        restEduSchedulePlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eduSchedulePlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDenominator").value(hasItem(DEFAULT_IS_DENOMINATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].dayOfWeak").value(hasItem(DEFAULT_DAY_OF_WEAK)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEduSchedulePlacesWithEagerRelationshipsIsEnabled() throws Exception {
        when(eduSchedulePlaceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEduSchedulePlaceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(eduSchedulePlaceRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEduSchedulePlacesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(eduSchedulePlaceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEduSchedulePlaceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(eduSchedulePlaceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEduSchedulePlace() throws Exception {
        // Initialize the database
        eduSchedulePlaceRepository.saveAndFlush(eduSchedulePlace);

        // Get the eduSchedulePlace
        restEduSchedulePlaceMockMvc
            .perform(get(ENTITY_API_URL_ID, eduSchedulePlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eduSchedulePlace.getId().intValue()))
            .andExpect(jsonPath("$.isDenominator").value(DEFAULT_IS_DENOMINATOR.booleanValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.dayOfWeak").value(DEFAULT_DAY_OF_WEAK));
    }

    @Test
    @Transactional
    void getNonExistingEduSchedulePlace() throws Exception {
        // Get the eduSchedulePlace
        restEduSchedulePlaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEduSchedulePlace() throws Exception {
        // Initialize the database
        eduSchedulePlaceRepository.saveAndFlush(eduSchedulePlace);

        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();

        // Update the eduSchedulePlace
        EduSchedulePlace updatedEduSchedulePlace = eduSchedulePlaceRepository.findById(eduSchedulePlace.getId()).get();
        // Disconnect from session so that the updates on updatedEduSchedulePlace are not directly saved in db
        em.detach(updatedEduSchedulePlace);
        updatedEduSchedulePlace
            .isDenominator(UPDATED_IS_DENOMINATOR)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .dayOfWeak(UPDATED_DAY_OF_WEAK);

        restEduSchedulePlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEduSchedulePlace.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEduSchedulePlace))
            )
            .andExpect(status().isOk());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
        EduSchedulePlace testEduSchedulePlace = eduSchedulePlaceList.get(eduSchedulePlaceList.size() - 1);
        assertThat(testEduSchedulePlace.getIsDenominator()).isEqualTo(UPDATED_IS_DENOMINATOR);
        assertThat(testEduSchedulePlace.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testEduSchedulePlace.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testEduSchedulePlace.getDayOfWeak()).isEqualTo(UPDATED_DAY_OF_WEAK);
    }

    @Test
    @Transactional
    void putNonExistingEduSchedulePlace() throws Exception {
        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();
        eduSchedulePlace.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEduSchedulePlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eduSchedulePlace.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEduSchedulePlace() throws Exception {
        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();
        eduSchedulePlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEduSchedulePlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEduSchedulePlace() throws Exception {
        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();
        eduSchedulePlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEduSchedulePlaceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEduSchedulePlaceWithPatch() throws Exception {
        // Initialize the database
        eduSchedulePlaceRepository.saveAndFlush(eduSchedulePlace);

        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();

        // Update the eduSchedulePlace using partial update
        EduSchedulePlace partialUpdatedEduSchedulePlace = new EduSchedulePlace();
        partialUpdatedEduSchedulePlace.setId(eduSchedulePlace.getId());

        partialUpdatedEduSchedulePlace.startTime(UPDATED_START_TIME);

        restEduSchedulePlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEduSchedulePlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEduSchedulePlace))
            )
            .andExpect(status().isOk());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
        EduSchedulePlace testEduSchedulePlace = eduSchedulePlaceList.get(eduSchedulePlaceList.size() - 1);
        assertThat(testEduSchedulePlace.getIsDenominator()).isEqualTo(DEFAULT_IS_DENOMINATOR);
        assertThat(testEduSchedulePlace.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testEduSchedulePlace.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testEduSchedulePlace.getDayOfWeak()).isEqualTo(DEFAULT_DAY_OF_WEAK);
    }

    @Test
    @Transactional
    void fullUpdateEduSchedulePlaceWithPatch() throws Exception {
        // Initialize the database
        eduSchedulePlaceRepository.saveAndFlush(eduSchedulePlace);

        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();

        // Update the eduSchedulePlace using partial update
        EduSchedulePlace partialUpdatedEduSchedulePlace = new EduSchedulePlace();
        partialUpdatedEduSchedulePlace.setId(eduSchedulePlace.getId());

        partialUpdatedEduSchedulePlace
            .isDenominator(UPDATED_IS_DENOMINATOR)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .dayOfWeak(UPDATED_DAY_OF_WEAK);

        restEduSchedulePlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEduSchedulePlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEduSchedulePlace))
            )
            .andExpect(status().isOk());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
        EduSchedulePlace testEduSchedulePlace = eduSchedulePlaceList.get(eduSchedulePlaceList.size() - 1);
        assertThat(testEduSchedulePlace.getIsDenominator()).isEqualTo(UPDATED_IS_DENOMINATOR);
        assertThat(testEduSchedulePlace.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testEduSchedulePlace.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testEduSchedulePlace.getDayOfWeak()).isEqualTo(UPDATED_DAY_OF_WEAK);
    }

    @Test
    @Transactional
    void patchNonExistingEduSchedulePlace() throws Exception {
        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();
        eduSchedulePlace.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEduSchedulePlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eduSchedulePlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEduSchedulePlace() throws Exception {
        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();
        eduSchedulePlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEduSchedulePlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEduSchedulePlace() throws Exception {
        int databaseSizeBeforeUpdate = eduSchedulePlaceRepository.findAll().size();
        eduSchedulePlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEduSchedulePlaceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eduSchedulePlace))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EduSchedulePlace in the database
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEduSchedulePlace() throws Exception {
        // Initialize the database
        eduSchedulePlaceRepository.saveAndFlush(eduSchedulePlace);

        int databaseSizeBeforeDelete = eduSchedulePlaceRepository.findAll().size();

        // Delete the eduSchedulePlace
        restEduSchedulePlaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, eduSchedulePlace.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EduSchedulePlace> eduSchedulePlaceList = eduSchedulePlaceRepository.findAll();
        assertThat(eduSchedulePlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
