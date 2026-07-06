package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.MaterielSurLeTerrainAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaterielSurLeTerrain;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.domain.enumeration.Models;
import com.mycompany.myapp.repository.MaterielSurLeTerrainRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MaterielSurLeTerrainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterielSurLeTerrainResourceIT {

    private static final LocalDate DEFAULT_DATE_AFFECTATTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AFFECTATTION = LocalDate.now(ZoneId.systemDefault());

    private static final Models DEFAULT_MATERIEL_1 = Models.AI9;
    private static final Models UPDATED_MATERIEL_1 = Models.HT5MM;

    private static final String DEFAULT_SERIE_1 = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_1 = "BBBBBBBBBB";

    private static final Etat DEFAULT_ETAT_1 = Etat.BON;
    private static final Etat UPDATED_ETAT_1 = Etat.DEFFECTUEUX;

    private static final Integer DEFAULT_NOMBRE_1 = 1;
    private static final Integer UPDATED_NOMBRE_1 = 2;

    private static final Models DEFAULT_MATERIEL_2 = Models.AI9;
    private static final Models UPDATED_MATERIEL_2 = Models.HT5MM;

    private static final String DEFAULT_SERIE_2 = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_2 = "BBBBBBBBBB";

    private static final Etat DEFAULT_ETAT_2 = Etat.BON;
    private static final Etat UPDATED_ETAT_2 = Etat.DEFFECTUEUX;

    private static final Integer DEFAULT_NOMBRE_2 = 1;
    private static final Integer UPDATED_NOMBRE_2 = 2;

    private static final Models DEFAULT_MATERIEL_3 = Models.AI9;
    private static final Models UPDATED_MATERIEL_3 = Models.HT5MM;

    private static final String DEFAULT_SERIE_3 = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_3 = "BBBBBBBBBB";

    private static final Etat DEFAULT_ETAT_3 = Etat.BON;
    private static final Etat UPDATED_ETAT_3 = Etat.DEFFECTUEUX;

    private static final Integer DEFAULT_NOMBRE_3 = 1;
    private static final Integer UPDATED_NOMBRE_3 = 2;

    private static final Models DEFAULT_MATERIEL_4 = Models.AI9;
    private static final Models UPDATED_MATERIEL_4 = Models.HT5MM;

    private static final String DEFAULT_SERIE_4 = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_4 = "BBBBBBBBBB";

    private static final Etat DEFAULT_ETAT_4 = Etat.BON;
    private static final Etat UPDATED_ETAT_4 = Etat.DEFFECTUEUX;

    private static final Integer DEFAULT_NOMBRE_4 = 1;
    private static final Integer UPDATED_NOMBRE_4 = 2;

    private static final String ENTITY_API_URL = "/api/materiel-sur-le-terrains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaterielSurLeTerrainRepository materielSurLeTerrainRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterielSurLeTerrainMockMvc;

    private MaterielSurLeTerrain materielSurLeTerrain;

    private MaterielSurLeTerrain insertedMaterielSurLeTerrain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterielSurLeTerrain createEntity() {
        return new MaterielSurLeTerrain()
            .dateAffectattion(DEFAULT_DATE_AFFECTATTION)
            .materiel1(DEFAULT_MATERIEL_1)
            .serie1(DEFAULT_SERIE_1)
            .etat1(DEFAULT_ETAT_1)
            .nombre1(DEFAULT_NOMBRE_1)
            .materiel2(DEFAULT_MATERIEL_2)
            .serie2(DEFAULT_SERIE_2)
            .etat2(DEFAULT_ETAT_2)
            .nombre2(DEFAULT_NOMBRE_2)
            .materiel3(DEFAULT_MATERIEL_3)
            .serie3(DEFAULT_SERIE_3)
            .etat3(DEFAULT_ETAT_3)
            .nombre3(DEFAULT_NOMBRE_3)
            .materiel4(DEFAULT_MATERIEL_4)
            .serie4(DEFAULT_SERIE_4)
            .etat4(DEFAULT_ETAT_4)
            .nombre4(DEFAULT_NOMBRE_4);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterielSurLeTerrain createUpdatedEntity() {
        return new MaterielSurLeTerrain()
            .dateAffectattion(UPDATED_DATE_AFFECTATTION)
            .materiel1(UPDATED_MATERIEL_1)
            .serie1(UPDATED_SERIE_1)
            .etat1(UPDATED_ETAT_1)
            .nombre1(UPDATED_NOMBRE_1)
            .materiel2(UPDATED_MATERIEL_2)
            .serie2(UPDATED_SERIE_2)
            .etat2(UPDATED_ETAT_2)
            .nombre2(UPDATED_NOMBRE_2)
            .materiel3(UPDATED_MATERIEL_3)
            .serie3(UPDATED_SERIE_3)
            .etat3(UPDATED_ETAT_3)
            .nombre3(UPDATED_NOMBRE_3)
            .materiel4(UPDATED_MATERIEL_4)
            .serie4(UPDATED_SERIE_4)
            .etat4(UPDATED_ETAT_4)
            .nombre4(UPDATED_NOMBRE_4);
    }

    @BeforeEach
    void initTest() {
        materielSurLeTerrain = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMaterielSurLeTerrain != null) {
            materielSurLeTerrainRepository.delete(insertedMaterielSurLeTerrain);
            insertedMaterielSurLeTerrain = null;
        }
    }

    @Test
    @Transactional
    void createMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MaterielSurLeTerrain
        var returnedMaterielSurLeTerrain = om.readValue(
            restMaterielSurLeTerrainMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielSurLeTerrain)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MaterielSurLeTerrain.class
        );

        // Validate the MaterielSurLeTerrain in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMaterielSurLeTerrainUpdatableFieldsEquals(
            returnedMaterielSurLeTerrain,
            getPersistedMaterielSurLeTerrain(returnedMaterielSurLeTerrain)
        );

        insertedMaterielSurLeTerrain = returnedMaterielSurLeTerrain;
    }

    @Test
    @Transactional
    void createMaterielSurLeTerrainWithExistingId() throws Exception {
        // Create the MaterielSurLeTerrain with an existing ID
        materielSurLeTerrain.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterielSurLeTerrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielSurLeTerrain)))
            .andExpect(status().isBadRequest());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterielSurLeTerrains() throws Exception {
        // Initialize the database
        insertedMaterielSurLeTerrain = materielSurLeTerrainRepository.saveAndFlush(materielSurLeTerrain);

        // Get all the materielSurLeTerrainList
        restMaterielSurLeTerrainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materielSurLeTerrain.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAffectattion").value(hasItem(DEFAULT_DATE_AFFECTATTION.toString())))
            .andExpect(jsonPath("$.[*].materiel1").value(hasItem(DEFAULT_MATERIEL_1.toString())))
            .andExpect(jsonPath("$.[*].serie1").value(hasItem(DEFAULT_SERIE_1)))
            .andExpect(jsonPath("$.[*].etat1").value(hasItem(DEFAULT_ETAT_1.toString())))
            .andExpect(jsonPath("$.[*].nombre1").value(hasItem(DEFAULT_NOMBRE_1)))
            .andExpect(jsonPath("$.[*].materiel2").value(hasItem(DEFAULT_MATERIEL_2.toString())))
            .andExpect(jsonPath("$.[*].serie2").value(hasItem(DEFAULT_SERIE_2)))
            .andExpect(jsonPath("$.[*].etat2").value(hasItem(DEFAULT_ETAT_2.toString())))
            .andExpect(jsonPath("$.[*].nombre2").value(hasItem(DEFAULT_NOMBRE_2)))
            .andExpect(jsonPath("$.[*].materiel3").value(hasItem(DEFAULT_MATERIEL_3.toString())))
            .andExpect(jsonPath("$.[*].serie3").value(hasItem(DEFAULT_SERIE_3)))
            .andExpect(jsonPath("$.[*].etat3").value(hasItem(DEFAULT_ETAT_3.toString())))
            .andExpect(jsonPath("$.[*].nombre3").value(hasItem(DEFAULT_NOMBRE_3)))
            .andExpect(jsonPath("$.[*].materiel4").value(hasItem(DEFAULT_MATERIEL_4.toString())))
            .andExpect(jsonPath("$.[*].serie4").value(hasItem(DEFAULT_SERIE_4)))
            .andExpect(jsonPath("$.[*].etat4").value(hasItem(DEFAULT_ETAT_4.toString())))
            .andExpect(jsonPath("$.[*].nombre4").value(hasItem(DEFAULT_NOMBRE_4)));
    }

    @Test
    @Transactional
    void getMaterielSurLeTerrain() throws Exception {
        // Initialize the database
        insertedMaterielSurLeTerrain = materielSurLeTerrainRepository.saveAndFlush(materielSurLeTerrain);

        // Get the materielSurLeTerrain
        restMaterielSurLeTerrainMockMvc
            .perform(get(ENTITY_API_URL_ID, materielSurLeTerrain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materielSurLeTerrain.getId().intValue()))
            .andExpect(jsonPath("$.dateAffectattion").value(DEFAULT_DATE_AFFECTATTION.toString()))
            .andExpect(jsonPath("$.materiel1").value(DEFAULT_MATERIEL_1.toString()))
            .andExpect(jsonPath("$.serie1").value(DEFAULT_SERIE_1))
            .andExpect(jsonPath("$.etat1").value(DEFAULT_ETAT_1.toString()))
            .andExpect(jsonPath("$.nombre1").value(DEFAULT_NOMBRE_1))
            .andExpect(jsonPath("$.materiel2").value(DEFAULT_MATERIEL_2.toString()))
            .andExpect(jsonPath("$.serie2").value(DEFAULT_SERIE_2))
            .andExpect(jsonPath("$.etat2").value(DEFAULT_ETAT_2.toString()))
            .andExpect(jsonPath("$.nombre2").value(DEFAULT_NOMBRE_2))
            .andExpect(jsonPath("$.materiel3").value(DEFAULT_MATERIEL_3.toString()))
            .andExpect(jsonPath("$.serie3").value(DEFAULT_SERIE_3))
            .andExpect(jsonPath("$.etat3").value(DEFAULT_ETAT_3.toString()))
            .andExpect(jsonPath("$.nombre3").value(DEFAULT_NOMBRE_3))
            .andExpect(jsonPath("$.materiel4").value(DEFAULT_MATERIEL_4.toString()))
            .andExpect(jsonPath("$.serie4").value(DEFAULT_SERIE_4))
            .andExpect(jsonPath("$.etat4").value(DEFAULT_ETAT_4.toString()))
            .andExpect(jsonPath("$.nombre4").value(DEFAULT_NOMBRE_4));
    }

    @Test
    @Transactional
    void getNonExistingMaterielSurLeTerrain() throws Exception {
        // Get the materielSurLeTerrain
        restMaterielSurLeTerrainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterielSurLeTerrain() throws Exception {
        // Initialize the database
        insertedMaterielSurLeTerrain = materielSurLeTerrainRepository.saveAndFlush(materielSurLeTerrain);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielSurLeTerrain
        MaterielSurLeTerrain updatedMaterielSurLeTerrain = materielSurLeTerrainRepository
            .findById(materielSurLeTerrain.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMaterielSurLeTerrain are not directly saved in db
        em.detach(updatedMaterielSurLeTerrain);
        updatedMaterielSurLeTerrain
            .dateAffectattion(UPDATED_DATE_AFFECTATTION)
            .materiel1(UPDATED_MATERIEL_1)
            .serie1(UPDATED_SERIE_1)
            .etat1(UPDATED_ETAT_1)
            .nombre1(UPDATED_NOMBRE_1)
            .materiel2(UPDATED_MATERIEL_2)
            .serie2(UPDATED_SERIE_2)
            .etat2(UPDATED_ETAT_2)
            .nombre2(UPDATED_NOMBRE_2)
            .materiel3(UPDATED_MATERIEL_3)
            .serie3(UPDATED_SERIE_3)
            .etat3(UPDATED_ETAT_3)
            .nombre3(UPDATED_NOMBRE_3)
            .materiel4(UPDATED_MATERIEL_4)
            .serie4(UPDATED_SERIE_4)
            .etat4(UPDATED_ETAT_4)
            .nombre4(UPDATED_NOMBRE_4);

        restMaterielSurLeTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterielSurLeTerrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMaterielSurLeTerrain))
            )
            .andExpect(status().isOk());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMaterielSurLeTerrainToMatchAllProperties(updatedMaterielSurLeTerrain);
    }

    @Test
    @Transactional
    void putNonExistingMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielSurLeTerrain.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielSurLeTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materielSurLeTerrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materielSurLeTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielSurLeTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielSurLeTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materielSurLeTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielSurLeTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielSurLeTerrainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielSurLeTerrain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterielSurLeTerrainWithPatch() throws Exception {
        // Initialize the database
        insertedMaterielSurLeTerrain = materielSurLeTerrainRepository.saveAndFlush(materielSurLeTerrain);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielSurLeTerrain using partial update
        MaterielSurLeTerrain partialUpdatedMaterielSurLeTerrain = new MaterielSurLeTerrain();
        partialUpdatedMaterielSurLeTerrain.setId(materielSurLeTerrain.getId());

        partialUpdatedMaterielSurLeTerrain
            .dateAffectattion(UPDATED_DATE_AFFECTATTION)
            .materiel1(UPDATED_MATERIEL_1)
            .serie1(UPDATED_SERIE_1)
            .etat1(UPDATED_ETAT_1)
            .serie2(UPDATED_SERIE_2)
            .materiel3(UPDATED_MATERIEL_3)
            .materiel4(UPDATED_MATERIEL_4)
            .etat4(UPDATED_ETAT_4);

        restMaterielSurLeTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterielSurLeTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaterielSurLeTerrain))
            )
            .andExpect(status().isOk());

        // Validate the MaterielSurLeTerrain in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaterielSurLeTerrainUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMaterielSurLeTerrain, materielSurLeTerrain),
            getPersistedMaterielSurLeTerrain(materielSurLeTerrain)
        );
    }

    @Test
    @Transactional
    void fullUpdateMaterielSurLeTerrainWithPatch() throws Exception {
        // Initialize the database
        insertedMaterielSurLeTerrain = materielSurLeTerrainRepository.saveAndFlush(materielSurLeTerrain);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielSurLeTerrain using partial update
        MaterielSurLeTerrain partialUpdatedMaterielSurLeTerrain = new MaterielSurLeTerrain();
        partialUpdatedMaterielSurLeTerrain.setId(materielSurLeTerrain.getId());

        partialUpdatedMaterielSurLeTerrain
            .dateAffectattion(UPDATED_DATE_AFFECTATTION)
            .materiel1(UPDATED_MATERIEL_1)
            .serie1(UPDATED_SERIE_1)
            .etat1(UPDATED_ETAT_1)
            .nombre1(UPDATED_NOMBRE_1)
            .materiel2(UPDATED_MATERIEL_2)
            .serie2(UPDATED_SERIE_2)
            .etat2(UPDATED_ETAT_2)
            .nombre2(UPDATED_NOMBRE_2)
            .materiel3(UPDATED_MATERIEL_3)
            .serie3(UPDATED_SERIE_3)
            .etat3(UPDATED_ETAT_3)
            .nombre3(UPDATED_NOMBRE_3)
            .materiel4(UPDATED_MATERIEL_4)
            .serie4(UPDATED_SERIE_4)
            .etat4(UPDATED_ETAT_4)
            .nombre4(UPDATED_NOMBRE_4);

        restMaterielSurLeTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterielSurLeTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaterielSurLeTerrain))
            )
            .andExpect(status().isOk());

        // Validate the MaterielSurLeTerrain in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaterielSurLeTerrainUpdatableFieldsEquals(
            partialUpdatedMaterielSurLeTerrain,
            getPersistedMaterielSurLeTerrain(partialUpdatedMaterielSurLeTerrain)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielSurLeTerrain.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielSurLeTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materielSurLeTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materielSurLeTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielSurLeTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielSurLeTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materielSurLeTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterielSurLeTerrain() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielSurLeTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielSurLeTerrainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(materielSurLeTerrain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterielSurLeTerrain in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterielSurLeTerrain() throws Exception {
        // Initialize the database
        insertedMaterielSurLeTerrain = materielSurLeTerrainRepository.saveAndFlush(materielSurLeTerrain);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the materielSurLeTerrain
        restMaterielSurLeTerrainMockMvc
            .perform(delete(ENTITY_API_URL_ID, materielSurLeTerrain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return materielSurLeTerrainRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected MaterielSurLeTerrain getPersistedMaterielSurLeTerrain(MaterielSurLeTerrain materielSurLeTerrain) {
        return materielSurLeTerrainRepository.findById(materielSurLeTerrain.getId()).orElseThrow();
    }

    protected void assertPersistedMaterielSurLeTerrainToMatchAllProperties(MaterielSurLeTerrain expectedMaterielSurLeTerrain) {
        assertMaterielSurLeTerrainAllPropertiesEquals(
            expectedMaterielSurLeTerrain,
            getPersistedMaterielSurLeTerrain(expectedMaterielSurLeTerrain)
        );
    }

    protected void assertPersistedMaterielSurLeTerrainToMatchUpdatableProperties(MaterielSurLeTerrain expectedMaterielSurLeTerrain) {
        assertMaterielSurLeTerrainAllUpdatablePropertiesEquals(
            expectedMaterielSurLeTerrain,
            getPersistedMaterielSurLeTerrain(expectedMaterielSurLeTerrain)
        );
    }
}
