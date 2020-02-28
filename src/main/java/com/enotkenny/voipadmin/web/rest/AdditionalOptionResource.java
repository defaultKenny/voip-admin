package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.service.AdditionalOptionService;
import com.enotkenny.voipadmin.web.rest.errors.BadRequestAlertException;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionDTO;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionCriteria;
import com.enotkenny.voipadmin.service.AdditionalOptionQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.enotkenny.voipadmin.domain.AdditionalOption}.
 */
@RestController
@RequestMapping("/api")
public class AdditionalOptionResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalOptionResource.class);

    private static final String ENTITY_NAME = "additionalOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalOptionService additionalOptionService;

    private final AdditionalOptionQueryService additionalOptionQueryService;

    public AdditionalOptionResource(AdditionalOptionService additionalOptionService, AdditionalOptionQueryService additionalOptionQueryService) {
        this.additionalOptionService = additionalOptionService;
        this.additionalOptionQueryService = additionalOptionQueryService;
    }

    /**
     * {@code POST  /additional-options} : Create a new additionalOption.
     *
     * @param additionalOptionDTO the additionalOptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalOptionDTO, or with status {@code 400 (Bad Request)} if the additionalOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/additional-options")
    public ResponseEntity<AdditionalOptionDTO> createAdditionalOption(@Valid @RequestBody AdditionalOptionDTO additionalOptionDTO) throws URISyntaxException {
        log.debug("REST request to save AdditionalOption : {}", additionalOptionDTO);
        if (additionalOptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdditionalOptionDTO result = additionalOptionService.save(additionalOptionDTO);
        return ResponseEntity.created(new URI("/api/additional-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /additional-options} : Updates an existing additionalOption.
     *
     * @param additionalOptionDTO the additionalOptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalOptionDTO,
     * or with status {@code 400 (Bad Request)} if the additionalOptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalOptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/additional-options")
    public ResponseEntity<AdditionalOptionDTO> updateAdditionalOption(@Valid @RequestBody AdditionalOptionDTO additionalOptionDTO) throws URISyntaxException {
        log.debug("REST request to update AdditionalOption : {}", additionalOptionDTO);
        if (additionalOptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdditionalOptionDTO result = additionalOptionService.save(additionalOptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalOptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /additional-options} : get all the additionalOptions.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalOptions in body.
     */
    @GetMapping("/additional-options")
    public ResponseEntity<List<AdditionalOptionDTO>> getAllAdditionalOptions(AdditionalOptionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdditionalOptions by criteria: {}", criteria);
        Page<AdditionalOptionDTO> page = additionalOptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /additional-options/count} : count all the additionalOptions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/additional-options/count")
    public ResponseEntity<Long> countAdditionalOptions(AdditionalOptionCriteria criteria) {
        log.debug("REST request to count AdditionalOptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(additionalOptionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /additional-options/:id} : get the "id" additionalOption.
     *
     * @param id the id of the additionalOptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalOptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/additional-options/{id}")
    public ResponseEntity<AdditionalOptionDTO> getAdditionalOption(@PathVariable Long id) {
        log.debug("REST request to get AdditionalOption : {}", id);
        Optional<AdditionalOptionDTO> additionalOptionDTO = additionalOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalOptionDTO);
    }

    /**
     * {@code DELETE  /additional-options/:id} : delete the "id" additionalOption.
     *
     * @param id the id of the additionalOptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/additional-options/{id}")
    public ResponseEntity<Void> deleteAdditionalOption(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalOption : {}", id);
        additionalOptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
