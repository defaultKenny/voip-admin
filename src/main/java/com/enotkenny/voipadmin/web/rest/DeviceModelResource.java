package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.service.DeviceModelService;
import com.enotkenny.voipadmin.web.rest.errors.BadRequestAlertException;
import com.enotkenny.voipadmin.service.dto.DeviceModelDTO;
import com.enotkenny.voipadmin.service.dto.DeviceModelCriteria;
import com.enotkenny.voipadmin.service.DeviceModelQueryService;

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
 * REST controller for managing {@link com.enotkenny.voipadmin.domain.DeviceModel}.
 */
@RestController
@RequestMapping("/api")
public class DeviceModelResource {

    private final Logger log = LoggerFactory.getLogger(DeviceModelResource.class);

    private static final String ENTITY_NAME = "deviceModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceModelService deviceModelService;

    private final DeviceModelQueryService deviceModelQueryService;

    public DeviceModelResource(DeviceModelService deviceModelService, DeviceModelQueryService deviceModelQueryService) {
        this.deviceModelService = deviceModelService;
        this.deviceModelQueryService = deviceModelQueryService;
    }

    /**
     * {@code POST  /device-models} : Create a new deviceModel.
     *
     * @param deviceModelDTO the deviceModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceModelDTO, or with status {@code 400 (Bad Request)} if the deviceModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-models")
    public ResponseEntity<DeviceModelDTO> createDeviceModel(@Valid @RequestBody DeviceModelDTO deviceModelDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceModel : {}", deviceModelDTO);
        if (deviceModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceModelDTO result = deviceModelService.save(deviceModelDTO);
        return ResponseEntity.created(new URI("/api/device-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-models} : Updates an existing deviceModel.
     *
     * @param deviceModelDTO the deviceModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceModelDTO,
     * or with status {@code 400 (Bad Request)} if the deviceModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-models")
    public ResponseEntity<DeviceModelDTO> updateDeviceModel(@Valid @RequestBody DeviceModelDTO deviceModelDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceModel : {}", deviceModelDTO);
        if (deviceModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceModelDTO result = deviceModelService.save(deviceModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-models} : get all the deviceModels.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceModels in body.
     */
    @GetMapping("/device-models")
    public ResponseEntity<List<DeviceModelDTO>> getAllDeviceModels(DeviceModelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DeviceModels by criteria: {}", criteria);
        Page<DeviceModelDTO> page = deviceModelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /device-models/count} : count all the deviceModels.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/device-models/count")
    public ResponseEntity<Long> countDeviceModels(DeviceModelCriteria criteria) {
        log.debug("REST request to count DeviceModels by criteria: {}", criteria);
        return ResponseEntity.ok().body(deviceModelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /device-models/:id} : get the "id" deviceModel.
     *
     * @param id the id of the deviceModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-models/{id}")
    public ResponseEntity<DeviceModelDTO> getDeviceModel(@PathVariable Long id) {
        log.debug("REST request to get DeviceModel : {}", id);
        Optional<DeviceModelDTO> deviceModelDTO = deviceModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceModelDTO);
    }

    /**
     * {@code DELETE  /device-models/:id} : delete the "id" deviceModel.
     *
     * @param id the id of the deviceModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-models/{id}")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable Long id) {
        log.debug("REST request to delete DeviceModel : {}", id);
        deviceModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
