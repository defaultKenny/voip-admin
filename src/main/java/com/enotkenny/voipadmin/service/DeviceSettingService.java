package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.DeviceSetting;
import com.enotkenny.voipadmin.repository.DeviceSettingRepository;
import com.enotkenny.voipadmin.service.dto.DeviceSettingDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceSetting}.
 */
@Service
@Transactional
public class DeviceSettingService {

    private final Logger log = LoggerFactory.getLogger(DeviceSettingService.class);

    private final DeviceSettingRepository deviceSettingRepository;

    private final DeviceSettingMapper deviceSettingMapper;

    public DeviceSettingService(DeviceSettingRepository deviceSettingRepository, DeviceSettingMapper deviceSettingMapper) {
        this.deviceSettingRepository = deviceSettingRepository;
        this.deviceSettingMapper = deviceSettingMapper;
    }

    /**
     * Save a deviceSetting.
     *
     * @param deviceSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public DeviceSettingDTO save(DeviceSettingDTO deviceSettingDTO) {
        log.debug("Request to save DeviceSetting : {}", deviceSettingDTO);
        DeviceSetting deviceSetting = deviceSettingMapper.toEntity(deviceSettingDTO);
        deviceSetting = deviceSettingRepository.save(deviceSetting);
        return deviceSettingMapper.toDto(deviceSetting);
    }

    /**
     * Get all the deviceSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceSettings");
        return deviceSettingRepository.findAll(pageable)
            .map(deviceSettingMapper::toDto);
    }


    /**
     * Get one deviceSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeviceSettingDTO> findOne(Long id) {
        log.debug("Request to get DeviceSetting : {}", id);
        return deviceSettingRepository.findById(id)
            .map(deviceSettingMapper::toDto);
    }

    /**
     * Delete the deviceSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeviceSetting : {}", id);
        deviceSettingRepository.deleteById(id);
    }
}
