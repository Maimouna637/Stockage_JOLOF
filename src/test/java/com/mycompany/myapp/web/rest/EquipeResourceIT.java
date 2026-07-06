package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.EquipeAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Equipe;
import com.mycompany.myapp.repository.EquipeRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link EquipeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EquipeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/equipes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipeMockMvc;

    private Equipe equipe;

    private Equipe insertedEquipe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createEntity() {
        return new Equipe().nom(DEFAULT_NOM).prenom(DEFAULT_PRENOM);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipe createUpdatedEntity() {
        return new Equipe().nom(UPDATED_NOM).prenom(UPDATED_PRENOM);
    }

    @BeforeEach
    void initTest() {
        equipe = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEquipe != null) {
            equipeRepository.delete(insertedEquipe);
            insertedEquipe = null;
        }
    }

    @Test
    @Transactional
    void createEquipe() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Equipe
        var returnedEquipe = om.readValue(
            restEquipeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipe)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Equipe.class
        );

        // Validate the Equipe in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEquipeUpdatableFieldsEquals(returnedEquipe, getPersistedEquipe(returnedEquipe));

        insertedEquipe = returnedEquipe;
    }

    @Test
    @Transactional
    void createEquipeWithExistingId() throws Exception {
        // Create the Equipe with an existing ID
        equipe.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipe)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEquipes() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        // Get all the equipeList
        restEquipeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)));
    }

    @Test
    @Transactional
    void getEquipe() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        // Get the equipe
        restEquipeMockMvc
            .perform(get(ENTITY_API_URL_ID, equipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM));
    }

    @Test
    @Transactional
    void getNonExistingEquipe() throws Exception {
        // Get the equipe
        restEquipeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEquipe() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipe
        Equipe updatedEquipe = equipeRepository.findById(equipe.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEquipe are not directly saved in db
        em.detach(updatedEquipe);
        updatedEquipe.nom(UPDATED_NOM).prenom(UPDATED_PRENOM);

        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEquipe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEquipeToMatchAllProperties(updatedEquipe);
    }

    @Test
    @Transactional
    void putNonExistingEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(put(ENTITY_API_URL_ID, equipe.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipe)))
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(equipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(equipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEquipeWithPatch() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipe using partial update
        Equipe partialUpdatedEquipe = new Equipe();
        partialUpdatedEquipe.setId(equipe.getId());

        partialUpdatedEquipe.nom(UPDATED_NOM).prenom(UPDATED_PRENOM);

        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEquipe, equipe), getPersistedEquipe(equipe));
    }

    @Test
    @Transactional
    void fullUpdateEquipeWithPatch() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the equipe using partial update
        Equipe partialUpdatedEquipe = new Equipe();
        partialUpdatedEquipe.setId(equipe.getId());

        partialUpdatedEquipe.nom(UPDATED_NOM).prenom(UPDATED_PRENOM);

        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEquipe.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEquipe))
            )
            .andExpect(status().isOk());

        // Validate the Equipe in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEquipeUpdatableFieldsEquals(partialUpdatedEquipe, getPersistedEquipe(partialUpdatedEquipe));
    }

    @Test
    @Transactional
    void patchNonExistingEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, equipe.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(equipe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEquipe() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        equipe.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEquipeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(equipe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Equipe in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEquipe() throws Exception {
        // Initialize the database
        insertedEquipe = equipeRepository.saveAndFlush(equipe);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the equipe
        restEquipeMockMvc
            .perform(delete(ENTITY_API_URL_ID, equipe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return equipeRepository.count();
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

    protected Equipe getPersistedEquipe(Equipe equipe) {
        return equipeRepository.findById(equipe.getId()).orElseThrow();
    }

    protected void assertPersistedEquipeToMatchAllProperties(Equipe expectedEquipe) {
        assertEquipeAllPropertiesEquals(expectedEquipe, getPersistedEquipe(expectedEquipe));
    }

    protected void assertPersistedEquipeToMatchUpdatableProperties(Equipe expectedEquipe) {
        assertEquipeAllUpdatablePropertiesEquals(expectedEquipe, getPersistedEquipe(expectedEquipe));
    }
}
