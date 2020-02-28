package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.service.DeviceSettingService;
import com.enotkenny.voipadmin.web.rest.errors.BadRequestAlertException;
import com.enotkenny.voipadmin.service.dto.DeviceSettingDTO;
import com.enotkenny.voipadmin.service.dto.DeviceSettingCriteria;
import com.enotkenny.voipadmin.service.DeviceSettingQueryService;

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
 * REST controller for managing {@link com.enotkenny.voipadmin.domain.DeviceSetting}.
 */
@RestController
@RequestMapping("/api")
public class DeviceSettingResource {

    private final Logger log = LoggerFactory.getLogger(DeviceSettingResource.class);

    private static final String ENTITY_NAME = "deviceSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceSettingService deviceSettingService;

    private final DeviceSettingQueryService deviceSettingQueryService;

    public DeviceSettingResource(DeviceSettingService deviceSettingService, DeviceSettingQueryService deviceSettingQueryService) {
        this.deviceSettingService = deviceSettingService;
        this.deviceSettingQueryService = deviceSettingQueryService;
    }

    /**
     * {@code POST  /device-settings} : Create a new deviceSetting.
     *
     * @param deviceSettingDTO the deviceSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceSettingDTO, or with status {@code 400 (Bad Request)} if the deviceSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-settings")
    public ResponseEntity<DeviceSettingDTO> createDeviceSetting(@Valid @RequestBody DeviceSettingDTO deviceSettingDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceSetting : {}", deviceSettingDTO);
        if (deviceSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceSettingDTO result = deviceSettingService.save(deviceSettingDTO);
        return ResponseEntity.created(new URI("/api/device-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-settings} : Updates an existing deviceSetting.
     *
     * @param deviceSettingDTO the deviceSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceSettingDTO,
     * or with status {@code 400 (Bad Request)} if the deviceSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-settings")
    public ResponseEntity<DeviceSettingDTO> updateDeviceSetting(@Valid @RequestBody DeviceSettingDTO deviceSettingDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceSetting : {}", deviceSettingDTO);
        if (deviceSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceSettingDTO result = deviceSettingService.save(deviceSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-settings} : get all the deviceSettings.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceSettings in body.
     */
    @GetMapping("/device-settings")
    public ResponseEntity<List<DeviceSettingDTO>> getAllDeviceSettings(DeviceSettingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DeviceSettings by criteria: {}", criteria);
        Page<DeviceSettingDTO> page = deviceSettingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /device-settings/count} : count all the deviceSettings.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/device-settings/count")
    public ResponseEntity<Long> countDeviceSettings(DeviceSettingCriteria criteria) {
        log.debug("REST request to count DeviceSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(deviceSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /device-settings/:id} : get the "id" deviceSetting.
     *
     * @param id the id of the deviceSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-settings/{id}")
    public ResponseEntity<DeviceSettingDTO> getDeviceSetting(@PathVariable Long id) {
        log.debug("REST request to get DeviceSetting : {}", id);
        Optional<DeviceSettingDTO> deviceSettingDTO = deviceSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceSettingDTO);
    }

    /**
     * {@code DELETE  /device-settings/:id} : delete the "id" deviceSetting.
     *
     * @param id the id of the deviceSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-settings/{id}")
    public ResponseEntity<Void> deleteDeviceSetting(@PathVariable Long id) {
        log.debug("REST request to delete DeviceSetting : {}", id);
        deviceSettingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
