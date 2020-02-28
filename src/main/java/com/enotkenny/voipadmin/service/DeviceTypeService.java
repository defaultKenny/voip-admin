package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.DeviceType;
import com.enotkenny.voipadmin.repository.DeviceTypeRepository;
import com.enotkenny.voipadmin.service.dto.DeviceTypeDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceType}.
 */
@Service
@Transactional
public class DeviceTypeService {

    private final Logger log = LoggerFactory.getLogger(DeviceTypeService.class);

    private final DeviceTypeRepository deviceTypeRepository;

    private final DeviceTypeMapper deviceTypeMapper;

    public DeviceTypeService(DeviceTypeRepository deviceTypeRepository, DeviceTypeMapper deviceTypeMapper) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.deviceTypeMapper = deviceTypeMapper;
    }

    /**
     * Save a deviceType.
     *
     * @param deviceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public DeviceTypeDTO save(DeviceTypeDTO deviceTypeDTO) {
        log.debug("Request to save DeviceType : {}", deviceTypeDTO);
        DeviceType deviceType = deviceTypeMapper.toEntity(deviceTypeDTO);
        deviceType = deviceTypeRepository.save(deviceType);
        return deviceTypeMapper.toDto(deviceType);
    }

    /**
     * Get all the deviceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceTypes");
        return deviceTypeRepository.findAll(pageable)
            .map(deviceTypeMapper::toDto);
    }


    /**
     * Get one deviceType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeviceTypeDTO> findOne(Long id) {
        log.debug("Request to get DeviceType : {}", id);
        return deviceTypeRepository.findById(id)
            .map(deviceTypeMapper::toDto);
    }

    /**
     * Delete the deviceType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeviceType : {}", id);
        deviceTypeRepository.deleteById(id);
    }
}
