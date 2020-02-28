package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.service.PbxAccountService;
import com.enotkenny.voipadmin.web.rest.errors.BadRequestAlertException;
import com.enotkenny.voipadmin.service.dto.PbxAccountDTO;
import com.enotkenny.voipadmin.service.dto.PbxAccountCriteria;
import com.enotkenny.voipadmin.service.PbxAccountQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.enotkenny.voipadmin.domain.PbxAccount}.
 */
@RestController
@RequestMapping("/api")
public class PbxAccountResource {

    private final Logger log = LoggerFactory.getLogger(PbxAccountResource.class);

    private static final String ENTITY_NAME = "pbxAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PbxAccountService pbxAccountService;

    private final PbxAccountQueryService pbxAccountQueryService;

    public PbxAccountResource(PbxAccountService pbxAccountService, PbxAccountQueryService pbxAccountQueryService) {
        this.pbxAccountService = pbxAccountService;
        this.pbxAccountQueryService = pbxAccountQueryService;
    }

    /**
     * {@code POST  /pbx-accounts} : Create a new pbxAccount.
     *
     * @param pbxAccountDTO the pbxAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pbxAccountDTO, or with status {@code 400 (Bad Request)} if the pbxAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pbx-accounts")
    public ResponseEntity<PbxAccountDTO> createPbxAccount(@RequestBody PbxAccountDTO pbxAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PbxAccount : {}", pbxAccountDTO);
        if (pbxAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new pbxAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PbxAccountDTO result = pbxAccountService.save(pbxAccountDTO);
        return ResponseEntity.created(new URI("/api/pbx-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pbx-accounts} : Updates an existing pbxAccount.
     *
     * @param pbxAccountDTO the pbxAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pbxAccountDTO,
     * or with status {@code 400 (Bad Request)} if the pbxAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pbxAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pbx-accounts")
    public ResponseEntity<PbxAccountDTO> updatePbxAccount(@RequestBody PbxAccountDTO pbxAccountDTO) throws URISyntaxException {
        log.debug("REST request to update PbxAccount : {}", pbxAccountDTO);
        if (pbxAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PbxAccountDTO result = pbxAccountService.save(pbxAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pbxAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pbx-accounts} : get all the pbxAccounts.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pbxAccounts in body.
     */
    @GetMapping("/pbx-accounts")
    public ResponseEntity<List<PbxAccountDTO>> getAllPbxAccounts(PbxAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PbxAccounts by criteria: {}", criteria);
        Page<PbxAccountDTO> page = pbxAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /pbx-accounts/count} : count all the pbxAccounts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/pbx-accounts/count")
    public ResponseEntity<Long> countPbxAccounts(PbxAccountCriteria criteria) {
        log.debug("REST request to count PbxAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(pbxAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pbx-accounts/:id} : get the "id" pbxAccount.
     *
     * @param id the id of the pbxAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pbxAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pbx-accounts/{id}")
    public ResponseEntity<PbxAccountDTO> getPbxAccount(@PathVariable Long id) {
        log.debug("REST request to get PbxAccount : {}", id);
        Optional<PbxAccountDTO> pbxAccountDTO = pbxAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pbxAccountDTO);
    }

    /**
     * {@code DELETE  /pbx-accounts/:id} : delete the "id" pbxAccount.
     *
     * @param id the id of the pbxAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pbx-accounts/{id}")
    public ResponseEntity<Void> deletePbxAccount(@PathVariable Long id) {
        log.debug("REST request to delete PbxAccount : {}", id);
        pbxAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
