package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaterielEnStock;
import com.mycompany.myapp.repository.MaterielEnStockRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterielEnStock}.
 */
@RestController
@RequestMapping("/api/materiel-en-stocks")
@Transactional
public class MaterielEnStockResource {

    private static final Logger LOG = LoggerFactory.getLogger(MaterielEnStockResource.class);

    private static final String ENTITY_NAME = "materielEnStock";

    @Value("${jhipster.clientApp.name:stockage}")
    private String applicationName;

    private final MaterielEnStockRepository materielEnStockRepository;

    public MaterielEnStockResource(MaterielEnStockRepository materielEnStockRepository) {
        this.materielEnStockRepository = materielEnStockRepository;
    }

    /**
     * {@code POST  /materiel-en-stocks} : Create a new materielEnStock.
     *
     * @param materielEnStock the materielEnStock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materielEnStock, or with status {@code 400 (Bad Request)} if the materielEnStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MaterielEnStock> createMaterielEnStock(@RequestBody MaterielEnStock materielEnStock) throws URISyntaxException {
        LOG.debug("REST request to save MaterielEnStock : {}", materielEnStock);
        if (materielEnStock.getId() != null) {
            throw new BadRequestAlertException("A new materielEnStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        materielEnStock = materielEnStockRepository.save(materielEnStock);
        return ResponseEntity.created(new URI("/api/materiel-en-stocks/" + materielEnStock.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, materielEnStock.getId().toString()))
            .body(materielEnStock);
    }

    /**
     * {@code PUT  /materiel-en-stocks/:id} : Updates an existing materielEnStock.
     *
     * @param id the id of the materielEnStock to save.
     * @param materielEnStock the materielEnStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materielEnStock,
     * or with status {@code 400 (Bad Request)} if the materielEnStock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materielEnStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaterielEnStock> updateMaterielEnStock(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterielEnStock materielEnStock
    ) throws URISyntaxException {
        LOG.debug("REST request to update MaterielEnStock : {}, {}", id, materielEnStock);
        if (materielEnStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materielEnStock.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materielEnStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        materielEnStock = materielEnStockRepository.save(materielEnStock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materielEnStock.getId().toString()))
            .body(materielEnStock);
    }

    /**
     * {@code PATCH  /materiel-en-stocks/:id} : Partial updates given fields of an existing materielEnStock, field will ignore if it is null
     *
     * @param id the id of the materielEnStock to save.
     * @param materielEnStock the materielEnStock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materielEnStock,
     * or with status {@code 400 (Bad Request)} if the materielEnStock is not valid,
     * or with status {@code 404 (Not Found)} if the materielEnStock is not found,
     * or with status {@code 500 (Internal Server Error)} if the materielEnStock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterielEnStock> partialUpdateMaterielEnStock(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterielEnStock materielEnStock
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MaterielEnStock partially : {}, {}", id, materielEnStock);
        if (materielEnStock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materielEnStock.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materielEnStockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterielEnStock> result = materielEnStockRepository
            .findById(materielEnStock.getId())
            .map(existingMaterielEnStock -> {
                updateIfPresent(existingMaterielEnStock::setDateReception, materielEnStock.getDateReception());
                updateIfPresent(existingMaterielEnStock::setQuantite, materielEnStock.getQuantite());
                updateIfPresent(existingMaterielEnStock::setMateriel, materielEnStock.getMateriel());
                updateIfPresent(existingMaterielEnStock::setNoserie, materielEnStock.getNoserie());

                return existingMaterielEnStock;
            })
            .map(materielEnStockRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materielEnStock.getId().toString())
        );
    }

    /**
     * {@code GET  /materiel-en-stocks} : get all the Materiel En Stocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Materiel En Stocks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MaterielEnStock>> getAllMaterielEnStocks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MaterielEnStocks");
        Page<MaterielEnStock> page = materielEnStockRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /materiel-en-stocks/:id} : get the "id" materielEnStock.
     *
     * @param id the id of the materielEnStock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materielEnStock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaterielEnStock> getMaterielEnStock(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MaterielEnStock : {}", id);
        Optional<MaterielEnStock> materielEnStock = materielEnStockRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materielEnStock);
    }

    /**
     * {@code DELETE  /materiel-en-stocks/:id} : delete the "id" materielEnStock.
     *
     * @param id the id of the materielEnStock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterielEnStock(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MaterielEnStock : {}", id);
        materielEnStockRepository.deleteById(id);
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
