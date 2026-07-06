package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.MaterielEnStockAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaterielEnStock;
import com.mycompany.myapp.domain.enumeration.Models;
import com.mycompany.myapp.repository.MaterielEnStockRepository;
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
 * Integration tests for the {@link MaterielEnStockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterielEnStockResourceIT {

    private static final LocalDate DEFAULT_DATE_RECEPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEPTION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final Models DEFAULT_MATERIEL = Models.AI9;
    private static final Models UPDATED_MATERIEL = Models.HT5MM;

    private static final String DEFAULT_NOSERIE = "AAAAAAAAAA";
    private static final String UPDATED_NOSERIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materiel-en-stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MaterielEnStockRepository materielEnStockRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterielEnStockMockMvc;

    private MaterielEnStock materielEnStock;

    private MaterielEnStock insertedMaterielEnStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterielEnStock createEntity() {
        return new MaterielEnStock()
            .dateReception(DEFAULT_DATE_RECEPTION)
            .quantite(DEFAULT_QUANTITE)
            .materiel(DEFAULT_MATERIEL)
            .noserie(DEFAULT_NOSERIE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterielEnStock createUpdatedEntity() {
        return new MaterielEnStock()
            .dateReception(UPDATED_DATE_RECEPTION)
            .quantite(UPDATED_QUANTITE)
            .materiel(UPDATED_MATERIEL)
            .noserie(UPDATED_NOSERIE);
    }

    @BeforeEach
    void initTest() {
        materielEnStock = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMaterielEnStock != null) {
            materielEnStockRepository.delete(insertedMaterielEnStock);
            insertedMaterielEnStock = null;
        }
    }

    @Test
    @Transactional
    void createMaterielEnStock() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MaterielEnStock
        var returnedMaterielEnStock = om.readValue(
            restMaterielEnStockMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielEnStock)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MaterielEnStock.class
        );

        // Validate the MaterielEnStock in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMaterielEnStockUpdatableFieldsEquals(returnedMaterielEnStock, getPersistedMaterielEnStock(returnedMaterielEnStock));

        insertedMaterielEnStock = returnedMaterielEnStock;
    }

    @Test
    @Transactional
    void createMaterielEnStockWithExistingId() throws Exception {
        // Create the MaterielEnStock with an existing ID
        materielEnStock.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterielEnStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielEnStock)))
            .andExpect(status().isBadRequest());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterielEnStocks() throws Exception {
        // Initialize the database
        insertedMaterielEnStock = materielEnStockRepository.saveAndFlush(materielEnStock);

        // Get all the materielEnStockList
        restMaterielEnStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materielEnStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateReception").value(hasItem(DEFAULT_DATE_RECEPTION.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].materiel").value(hasItem(DEFAULT_MATERIEL.toString())))
            .andExpect(jsonPath("$.[*].noserie").value(hasItem(DEFAULT_NOSERIE)));
    }

    @Test
    @Transactional
    void getMaterielEnStock() throws Exception {
        // Initialize the database
        insertedMaterielEnStock = materielEnStockRepository.saveAndFlush(materielEnStock);

        // Get the materielEnStock
        restMaterielEnStockMockMvc
            .perform(get(ENTITY_API_URL_ID, materielEnStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materielEnStock.getId().intValue()))
            .andExpect(jsonPath("$.dateReception").value(DEFAULT_DATE_RECEPTION.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.materiel").value(DEFAULT_MATERIEL.toString()))
            .andExpect(jsonPath("$.noserie").value(DEFAULT_NOSERIE));
    }

    @Test
    @Transactional
    void getNonExistingMaterielEnStock() throws Exception {
        // Get the materielEnStock
        restMaterielEnStockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterielEnStock() throws Exception {
        // Initialize the database
        insertedMaterielEnStock = materielEnStockRepository.saveAndFlush(materielEnStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielEnStock
        MaterielEnStock updatedMaterielEnStock = materielEnStockRepository.findById(materielEnStock.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMaterielEnStock are not directly saved in db
        em.detach(updatedMaterielEnStock);
        updatedMaterielEnStock
            .dateReception(UPDATED_DATE_RECEPTION)
            .quantite(UPDATED_QUANTITE)
            .materiel(UPDATED_MATERIEL)
            .noserie(UPDATED_NOSERIE);

        restMaterielEnStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterielEnStock.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMaterielEnStock))
            )
            .andExpect(status().isOk());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMaterielEnStockToMatchAllProperties(updatedMaterielEnStock);
    }

    @Test
    @Transactional
    void putNonExistingMaterielEnStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielEnStock.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielEnStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materielEnStock.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materielEnStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterielEnStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielEnStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielEnStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(materielEnStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterielEnStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielEnStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielEnStockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(materielEnStock)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterielEnStockWithPatch() throws Exception {
        // Initialize the database
        insertedMaterielEnStock = materielEnStockRepository.saveAndFlush(materielEnStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielEnStock using partial update
        MaterielEnStock partialUpdatedMaterielEnStock = new MaterielEnStock();
        partialUpdatedMaterielEnStock.setId(materielEnStock.getId());

        partialUpdatedMaterielEnStock.quantite(UPDATED_QUANTITE);

        restMaterielEnStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterielEnStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaterielEnStock))
            )
            .andExpect(status().isOk());

        // Validate the MaterielEnStock in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaterielEnStockUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMaterielEnStock, materielEnStock),
            getPersistedMaterielEnStock(materielEnStock)
        );
    }

    @Test
    @Transactional
    void fullUpdateMaterielEnStockWithPatch() throws Exception {
        // Initialize the database
        insertedMaterielEnStock = materielEnStockRepository.saveAndFlush(materielEnStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the materielEnStock using partial update
        MaterielEnStock partialUpdatedMaterielEnStock = new MaterielEnStock();
        partialUpdatedMaterielEnStock.setId(materielEnStock.getId());

        partialUpdatedMaterielEnStock
            .dateReception(UPDATED_DATE_RECEPTION)
            .quantite(UPDATED_QUANTITE)
            .materiel(UPDATED_MATERIEL)
            .noserie(UPDATED_NOSERIE);

        restMaterielEnStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterielEnStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMaterielEnStock))
            )
            .andExpect(status().isOk());

        // Validate the MaterielEnStock in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMaterielEnStockUpdatableFieldsEquals(
            partialUpdatedMaterielEnStock,
            getPersistedMaterielEnStock(partialUpdatedMaterielEnStock)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMaterielEnStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielEnStock.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterielEnStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materielEnStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materielEnStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterielEnStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielEnStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielEnStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(materielEnStock))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterielEnStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        materielEnStock.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterielEnStockMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(materielEnStock)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterielEnStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterielEnStock() throws Exception {
        // Initialize the database
        insertedMaterielEnStock = materielEnStockRepository.saveAndFlush(materielEnStock);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the materielEnStock
        restMaterielEnStockMockMvc
            .perform(delete(ENTITY_API_URL_ID, materielEnStock.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return materielEnStockRepository.count();
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

    protected MaterielEnStock getPersistedMaterielEnStock(MaterielEnStock materielEnStock) {
        return materielEnStockRepository.findById(materielEnStock.getId()).orElseThrow();
    }

    protected void assertPersistedMaterielEnStockToMatchAllProperties(MaterielEnStock expectedMaterielEnStock) {
        assertMaterielEnStockAllPropertiesEquals(expectedMaterielEnStock, getPersistedMaterielEnStock(expectedMaterielEnStock));
    }

    protected void assertPersistedMaterielEnStockToMatchUpdatableProperties(MaterielEnStock expectedMaterielEnStock) {
        assertMaterielEnStockAllUpdatablePropertiesEquals(expectedMaterielEnStock, getPersistedMaterielEnStock(expectedMaterielEnStock));
    }
}
