package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Equipe;
import com.mycompany.myapp.repository.EquipeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Equipe}.
 */
@RestController
@RequestMapping("/api/equipes")
@Transactional
public class EquipeResource {

    private static final Logger LOG = LoggerFactory.getLogger(EquipeResource.class);

    private static final String ENTITY_NAME = "equipe";

    @Value("${jhipster.clientApp.name:stockage}")
    private String applicationName;

    private final EquipeRepository equipeRepository;

    public EquipeResource(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    /**
     * {@code POST  /equipes} : Create a new equipe.
     *
     * @param equipe the equipe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipe, or with status {@code 400 (Bad Request)} if the equipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Equipe> createEquipe(@RequestBody Equipe equipe) throws URISyntaxException {
        LOG.debug("REST request to save Equipe : {}", equipe);
        if (equipe.getId() != null) {
            throw new BadRequestAlertException("A new equipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        equipe = equipeRepository.save(equipe);
        return ResponseEntity.created(new URI("/api/equipes/" + equipe.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, equipe.getId().toString()))
            .body(equipe);
    }

    /**
     * {@code PUT  /equipes/:id} : Updates an existing equipe.
     *
     * @param id the id of the equipe to save.
     * @param equipe the equipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipe,
     * or with status {@code 400 (Bad Request)} if the equipe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Equipe> updateEquipe(@PathVariable(value = "id", required = false) final Long id, @RequestBody Equipe equipe)
        throws URISyntaxException {
        LOG.debug("REST request to update Equipe : {}, {}", id, equipe);
        if (equipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        equipe = equipeRepository.save(equipe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipe.getId().toString()))
            .body(equipe);
    }

    /**
     * {@code PATCH  /equipes/:id} : Partial updates given fields of an existing equipe, field will ignore if it is null
     *
     * @param id the id of the equipe to save.
     * @param equipe the equipe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipe,
     * or with status {@code 400 (Bad Request)} if the equipe is not valid,
     * or with status {@code 404 (Not Found)} if the equipe is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Equipe> partialUpdateEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Equipe equipe
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Equipe partially : {}, {}", id, equipe);
        if (equipe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Equipe> result = equipeRepository
            .findById(equipe.getId())
            .map(existingEquipe -> {
                updateIfPresent(existingEquipe::setNom, equipe.getNom());
                updateIfPresent(existingEquipe::setPrenom, equipe.getPrenom());

                return existingEquipe;
            })
            .map(equipeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipe.getId().toString())
        );
    }

    /**
     * {@code GET  /equipes} : get all the Equipes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Equipes in body.
     */
    @GetMapping("")
    public List<Equipe> getAllEquipes() {
        LOG.debug("REST request to get all Equipes");
        return equipeRepository.findAll();
    }

    /**
     * {@code GET  /equipes/:id} : get the "id" equipe.
     *
     * @param id the id of the equipe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Equipe> getEquipe(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Equipe : {}", id);
        Optional<Equipe> equipe = equipeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipe);
    }

    /**
     * {@code DELETE  /equipes/:id} : delete the "id" equipe.
     *
     * @param id the id of the equipe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Equipe : {}", id);
        equipeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
