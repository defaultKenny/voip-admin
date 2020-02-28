package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.service.SipAccountService;
import com.enotkenny.voipadmin.web.rest.errors.BadRequestAlertException;
import com.enotkenny.voipadmin.service.dto.SipAccountDTO;
import com.enotkenny.voipadmin.service.dto.SipAccountCriteria;
import com.enotkenny.voipadmin.service.SipAccountQueryService;

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
 * REST controller for managing {@link com.enotkenny.voipadmin.domain.SipAccount}.
 */
@RestController
@RequestMapping("/api")
public class SipAccountResource {

    private final Logger log = LoggerFactory.getLogger(SipAccountResource.class);

    private static final String ENTITY_NAME = "sipAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SipAccountService sipAccountService;

    private final SipAccountQueryService sipAccountQueryService;

    public SipAccountResource(SipAccountService sipAccountService, SipAccountQueryService sipAccountQueryService) {
        this.sipAccountService = sipAccountService;
        this.sipAccountQueryService = sipAccountQueryService;
    }

    /**
     * {@code POST  /sip-accounts} : Create a new sipAccount.
     *
     * @param sipAccountDTO the sipAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sipAccountDTO, or with status {@code 400 (Bad Request)} if the sipAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sip-accounts")
    public ResponseEntity<SipAccountDTO> createSipAccount(@Valid @RequestBody SipAccountDTO sipAccountDTO) throws URISyntaxException {
        log.debug("REST request to save SipAccount : {}", sipAccountDTO);
        if (sipAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new sipAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SipAccountDTO result = sipAccountService.save(sipAccountDTO);
        return ResponseEntity.created(new URI("/api/sip-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sip-accounts} : Updates an existing sipAccount.
     *
     * @param sipAccountDTO the sipAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sipAccountDTO,
     * or with status {@code 400 (Bad Request)} if the sipAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sipAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sip-accounts")
    public ResponseEntity<SipAccountDTO> updateSipAccount(@Valid @RequestBody SipAccountDTO sipAccountDTO) throws URISyntaxException {
        log.debug("REST request to update SipAccount : {}", sipAccountDTO);
        if (sipAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SipAccountDTO result = sipAccountService.save(sipAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sipAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sip-accounts} : get all the sipAccounts.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sipAccounts in body.
     */
    @GetMapping("/sip-accounts")
    public ResponseEntity<List<SipAccountDTO>> getAllSipAccounts(SipAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SipAccounts by criteria: {}", criteria);
        Page<SipAccountDTO> page = sipAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /sip-accounts/count} : count all the sipAccounts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/sip-accounts/count")
    public ResponseEntity<Long> countSipAccounts(SipAccountCriteria criteria) {
        log.debug("REST request to count SipAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(sipAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sip-accounts/:id} : get the "id" sipAccount.
     *
     * @param id the id of the sipAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sipAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sip-accounts/{id}")
    public ResponseEntity<SipAccountDTO> getSipAccount(@PathVariable Long id) {
        log.debug("REST request to get SipAccount : {}", id);
        Optional<SipAccountDTO> sipAccountDTO = sipAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sipAccountDTO);
    }

    /**
     * {@code DELETE  /sip-accounts/:id} : delete the "id" sipAccount.
     *
     * @param id the id of the sipAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sip-accounts/{id}")
    public ResponseEntity<Void> deleteSipAccount(@PathVariable Long id) {
        log.debug("REST request to delete SipAccount : {}", id);
        sipAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
