package com.enotkenny.voipadmin.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.enotkenny.voipadmin.domain.DeviceSetting;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.DeviceSettingRepository;
import com.enotkenny.voipadmin.service.dto.DeviceSettingCriteria;
import com.enotkenny.voipadmin.service.dto.DeviceSettingDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceSettingMapper;

/**
 * Service for executing complex queries for {@link DeviceSetting} entities in the database.
 * The main input is a {@link DeviceSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeviceSettingDTO} or a {@link Page} of {@link DeviceSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeviceSettingQueryService extends QueryService<DeviceSetting> {

    private final Logger log = LoggerFactory.getLogger(DeviceSettingQueryService.class);

    private final DeviceSettingRepository deviceSettingRepository;

    private final DeviceSettingMapper deviceSettingMapper;

    public DeviceSettingQueryService(DeviceSettingRepository deviceSettingRepository, DeviceSettingMapper deviceSettingMapper) {
        this.deviceSettingRepository = deviceSettingRepository;
        this.deviceSettingMapper = deviceSettingMapper;
    }

    /**
     * Return a {@link List} of {@link DeviceSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceSettingDTO> findByCriteria(DeviceSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeviceSetting> specification = createSpecification(criteria);
        return deviceSettingMapper.toDto(deviceSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeviceSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceSettingDTO> findByCriteria(DeviceSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeviceSetting> specification = createSpecification(criteria);
        return deviceSettingRepository.findAll(specification, page)
            .map(deviceSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeviceSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeviceSetting> specification = createSpecification(criteria);
        return deviceSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link DeviceSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeviceSetting> createSpecification(DeviceSettingCriteria criteria) {
        Specification<DeviceSetting> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeviceSetting_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), DeviceSetting_.value));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceId(),
                    root -> root.join(DeviceSetting_.device, JoinType.LEFT).get(Device_.id)));
            }
            if (criteria.getAdditionalOptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditionalOptionId(),
                    root -> root.join(DeviceSetting_.additionalOption, JoinType.LEFT).get(AdditionalOption_.id)));
            }
        }
        return specification;
    }
}
