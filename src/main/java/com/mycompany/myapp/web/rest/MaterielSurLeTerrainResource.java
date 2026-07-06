package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaterielSurLeTerrain;
import com.mycompany.myapp.repository.MaterielSurLeTerrainRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterielSurLeTerrain}.
 */
@RestController
@RequestMapping("/api/materiel-sur-le-terrains")
@Transactional
public class MaterielSurLeTerrainResource {

    private static final Logger LOG = LoggerFactory.getLogger(MaterielSurLeTerrainResource.class);

    private static final String ENTITY_NAME = "materielSurLeTerrain";

    @Value("${jhipster.clientApp.name:stockage}")
    private String applicationName;

    private final MaterielSurLeTerrainRepository materielSurLeTerrainRepository;

    public MaterielSurLeTerrainResource(MaterielSurLeTerrainRepository materielSurLeTerrainRepository) {
        this.materielSurLeTerrainRepository = materielSurLeTerrainRepository;
    }

    /**
     * {@code POST  /materiel-sur-le-terrains} : Create a new materielSurLeTerrain.
     *
     * @param materielSurLeTerrain the materielSurLeTerrain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materielSurLeTerrain, or with status {@code 400 (Bad Request)} if the materielSurLeTerrain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MaterielSurLeTerrain> createMaterielSurLeTerrain(@RequestBody MaterielSurLeTerrain materielSurLeTerrain)
        throws URISyntaxException {
        LOG.debug("REST request to save MaterielSurLeTerrain : {}", materielSurLeTerrain);
        if (materielSurLeTerrain.getId() != null) {
            throw new BadRequestAlertException("A new materielSurLeTerrain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        materielSurLeTerrain = materielSurLeTerrainRepository.save(materielSurLeTerrain);
        return ResponseEntity.created(new URI("/api/materiel-sur-le-terrains/" + materielSurLeTerrain.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, materielSurLeTerrain.getId().toString()))
            .body(materielSurLeTerrain);
    }

    /**
     * {@code PUT  /materiel-sur-le-terrains/:id} : Updates an existing materielSurLeTerrain.
     *
     * @param id the id of the materielSurLeTerrain to save.
     * @param materielSurLeTerrain the materielSurLeTerrain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materielSurLeTerrain,
     * or with status {@code 400 (Bad Request)} if the materielSurLeTerrain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materielSurLeTerrain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaterielSurLeTerrain> updateMaterielSurLeTerrain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterielSurLeTerrain materielSurLeTerrain
    ) throws URISyntaxException {
        LOG.debug("REST request to update MaterielSurLeTerrain : {}, {}", id, materielSurLeTerrain);
        if (materielSurLeTerrain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materielSurLeTerrain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materielSurLeTerrainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        materielSurLeTerrain = materielSurLeTerrainRepository.save(materielSurLeTerrain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materielSurLeTerrain.getId().toString()))
            .body(materielSurLeTerrain);
    }

    /**
     * {@code PATCH  /materiel-sur-le-terrains/:id} : Partial updates given fields of an existing materielSurLeTerrain, field will ignore if it is null
     *
     * @param id the id of the materielSurLeTerrain to save.
     * @param materielSurLeTerrain the materielSurLeTerrain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materielSurLeTerrain,
     * or with status {@code 400 (Bad Request)} if the materielSurLeTerrain is not valid,
     * or with status {@code 404 (Not Found)} if the materielSurLeTerrain is not found,
     * or with status {@code 500 (Internal Server Error)} if the materielSurLeTerrain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterielSurLeTerrain> partialUpdateMaterielSurLeTerrain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterielSurLeTerrain materielSurLeTerrain
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MaterielSurLeTerrain partially : {}, {}", id, materielSurLeTerrain);
        if (materielSurLeTerrain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materielSurLeTerrain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materielSurLeTerrainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterielSurLeTerrain> result = materielSurLeTerrainRepository
            .findById(materielSurLeTerrain.getId())
            .map(existingMaterielSurLeTerrain -> {
                updateIfPresent(existingMaterielSurLeTerrain::setDateAffectattion, materielSurLeTerrain.getDateAffectattion());
                updateIfPresent(existingMaterielSurLeTerrain::setMateriel1, materielSurLeTerrain.getMateriel1());
                updateIfPresent(existingMaterielSurLeTerrain::setSerie1, materielSurLeTerrain.getSerie1());
                updateIfPresent(existingMaterielSurLeTerrain::setEtat1, materielSurLeTerrain.getEtat1());
                updateIfPresent(existingMaterielSurLeTerrain::setNombre1, materielSurLeTerrain.getNombre1());
                updateIfPresent(existingMaterielSurLeTerrain::setMateriel2, materielSurLeTerrain.getMateriel2());
                updateIfPresent(existingMaterielSurLeTerrain::setSerie2, materielSurLeTerrain.getSerie2());
                updateIfPresent(existingMaterielSurLeTerrain::setEtat2, materielSurLeTerrain.getEtat2());
                updateIfPresent(existingMaterielSurLeTerrain::setNombre2, materielSurLeTerrain.getNombre2());
                updateIfPresent(existingMaterielSurLeTerrain::setMateriel3, materielSurLeTerrain.getMateriel3());
                updateIfPresent(existingMaterielSurLeTerrain::setSerie3, materielSurLeTerrain.getSerie3());
                updateIfPresent(existingMaterielSurLeTerrain::setEtat3, materielSurLeTerrain.getEtat3());
                updateIfPresent(existingMaterielSurLeTerrain::setNombre3, materielSurLeTerrain.getNombre3());
                updateIfPresent(existingMaterielSurLeTerrain::setMateriel4, materielSurLeTerrain.getMateriel4());
                updateIfPresent(existingMaterielSurLeTerrain::setSerie4, materielSurLeTerrain.getSerie4());
                updateIfPresent(existingMaterielSurLeTerrain::setEtat4, materielSurLeTerrain.getEtat4());
                updateIfPresent(existingMaterielSurLeTerrain::setNombre4, materielSurLeTerrain.getNombre4());

                return existingMaterielSurLeTerrain;
            })
            .map(materielSurLeTerrainRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materielSurLeTerrain.getId().toString())
        );
    }

    /**
     * {@code GET  /materiel-sur-le-terrains} : get all the Materiel Sur Le Terrains.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Materiel Sur Le Terrains in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MaterielSurLeTerrain>> getAllMaterielSurLeTerrains(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of MaterielSurLeTerrains");
        Page<MaterielSurLeTerrain> page = materielSurLeTerrainRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /materiel-sur-le-terrains/:id} : get the "id" materielSurLeTerrain.
     *
     * @param id the id of the materielSurLeTerrain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materielSurLeTerrain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaterielSurLeTerrain> getMaterielSurLeTerrain(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MaterielSurLeTerrain : {}", id);
        Optional<MaterielSurLeTerrain> materielSurLeTerrain = materielSurLeTerrainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materielSurLeTerrain);
    }

    /**
     * {@code DELETE  /materiel-sur-le-terrains/:id} : delete the "id" materielSurLeTerrain.
     *
     * @param id the id of the materielSurLeTerrain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterielSurLeTerrain(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MaterielSurLeTerrain : {}", id);
        materielSurLeTerrainRepository.deleteById(id);
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
