package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.DeviceModel;
import com.enotkenny.voipadmin.repository.DeviceModelRepository;
import com.enotkenny.voipadmin.service.dto.DeviceModelDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceModel}.
 */
@Service
@Transactional
public class DeviceModelService {

    private final Logger log = LoggerFactory.getLogger(DeviceModelService.class);

    private final DeviceModelRepository deviceModelRepository;

    private final DeviceModelMapper deviceModelMapper;

    public DeviceModelService(DeviceModelRepository deviceModelRepository, DeviceModelMapper deviceModelMapper) {
        this.deviceModelRepository = deviceModelRepository;
        this.deviceModelMapper = deviceModelMapper;
    }

    /**
     * Save a deviceModel.
     *
     * @param deviceModelDTO the entity to save.
     * @return the persisted entity.
     */
    public DeviceModelDTO save(DeviceModelDTO deviceModelDTO) {
        log.debug("Request to save DeviceModel : {}", deviceModelDTO);
        DeviceModel deviceModel = deviceModelMapper.toEntity(deviceModelDTO);
        deviceModel = deviceModelRepository.save(deviceModel);
        return deviceModelMapper.toDto(deviceModel);
    }

    /**
     * Get all the deviceModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceModels");
        return deviceModelRepository.findAll(pageable)
            .map(deviceModelMapper::toDto);
    }

    /**
     * Get all the deviceModels with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DeviceModelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return deviceModelRepository.findAllWithEagerRelationships(pageable).map(deviceModelMapper::toDto);
    }
    

    /**
     * Get one deviceModel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeviceModelDTO> findOne(Long id) {
        log.debug("Request to get DeviceModel : {}", id);
        return deviceModelRepository.findOneWithEagerRelationships(id)
            .map(deviceModelMapper::toDto);
    }

    /**
     * Delete the deviceModel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeviceModel : {}", id);
        deviceModelRepository.deleteById(id);
    }
}
