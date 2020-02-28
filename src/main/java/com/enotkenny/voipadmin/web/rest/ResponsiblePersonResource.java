package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.service.ResponsiblePersonService;
import com.enotkenny.voipadmin.web.rest.errors.BadRequestAlertException;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonDTO;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonCriteria;
import com.enotkenny.voipadmin.service.ResponsiblePersonQueryService;

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
 * REST controller for managing {@link com.enotkenny.voipadmin.domain.ResponsiblePerson}.
 */
@RestController
@RequestMapping("/api")
public class ResponsiblePersonResource {

    private final Logger log = LoggerFactory.getLogger(ResponsiblePersonResource.class);

    private static final String ENTITY_NAME = "responsiblePerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsiblePersonService responsiblePersonService;

    private final ResponsiblePersonQueryService responsiblePersonQueryService;

    public ResponsiblePersonResource(ResponsiblePersonService responsiblePersonService, ResponsiblePersonQueryService responsiblePersonQueryService) {
        this.responsiblePersonService = responsiblePersonService;
        this.responsiblePersonQueryService = responsiblePersonQueryService;
    }

    /**
     * {@code POST  /responsible-people} : Create a new responsiblePerson.
     *
     * @param responsiblePersonDTO the responsiblePersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsiblePersonDTO, or with status {@code 400 (Bad Request)} if the responsiblePerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsible-people")
    public ResponseEntity<ResponsiblePersonDTO> createResponsiblePerson(@Valid @RequestBody ResponsiblePersonDTO responsiblePersonDTO) throws URISyntaxException {
        log.debug("REST request to save ResponsiblePerson : {}", responsiblePersonDTO);
        if (responsiblePersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsiblePerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsiblePersonDTO result = responsiblePersonService.save(responsiblePersonDTO);
        return ResponseEntity.created(new URI("/api/responsible-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsible-people} : Updates an existing responsiblePerson.
     *
     * @param responsiblePersonDTO the responsiblePersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsiblePersonDTO,
     * or with status {@code 400 (Bad Request)} if the responsiblePersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsiblePersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsible-people")
    public ResponseEntity<ResponsiblePersonDTO> updateResponsiblePerson(@Valid @RequestBody ResponsiblePersonDTO responsiblePersonDTO) throws URISyntaxException {
        log.debug("REST request to update ResponsiblePerson : {}", responsiblePersonDTO);
        if (responsiblePersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResponsiblePersonDTO result = responsiblePersonService.save(responsiblePersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsiblePersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /responsible-people} : get all the responsiblePeople.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsiblePeople in body.
     */
    @GetMapping("/responsible-people")
    public ResponseEntity<List<ResponsiblePersonDTO>> getAllResponsiblePeople(ResponsiblePersonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ResponsiblePeople by criteria: {}", criteria);
        Page<ResponsiblePersonDTO> page = responsiblePersonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /responsible-people/count} : count all the responsiblePeople.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/responsible-people/count")
    public ResponseEntity<Long> countResponsiblePeople(ResponsiblePersonCriteria criteria) {
        log.debug("REST request to count ResponsiblePeople by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsiblePersonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsible-people/:id} : get the "id" responsiblePerson.
     *
     * @param id the id of the responsiblePersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsiblePersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsible-people/{id}")
    public ResponseEntity<ResponsiblePersonDTO> getResponsiblePerson(@PathVariable Long id) {
        log.debug("REST request to get ResponsiblePerson : {}", id);
        Optional<ResponsiblePersonDTO> responsiblePersonDTO = responsiblePersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsiblePersonDTO);
    }

    /**
     * {@code DELETE  /responsible-people/:id} : delete the "id" responsiblePerson.
     *
     * @param id the id of the responsiblePersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsible-people/{id}")
    public ResponseEntity<Void> deleteResponsiblePerson(@PathVariable Long id) {
        log.debug("REST request to delete ResponsiblePerson : {}", id);
        responsiblePersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
