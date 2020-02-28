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

import com.enotkenny.voipadmin.domain.DeviceModel;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.DeviceModelRepository;
import com.enotkenny.voipadmin.service.dto.DeviceModelCriteria;
import com.enotkenny.voipadmin.service.dto.DeviceModelDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceModelMapper;

/**
 * Service for executing complex queries for {@link DeviceModel} entities in the database.
 * The main input is a {@link DeviceModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeviceModelDTO} or a {@link Page} of {@link DeviceModelDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeviceModelQueryService extends QueryService<DeviceModel> {

    private final Logger log = LoggerFactory.getLogger(DeviceModelQueryService.class);

    private final DeviceModelRepository deviceModelRepository;

    private final DeviceModelMapper deviceModelMapper;

    public DeviceModelQueryService(DeviceModelRepository deviceModelRepository, DeviceModelMapper deviceModelMapper) {
        this.deviceModelRepository = deviceModelRepository;
        this.deviceModelMapper = deviceModelMapper;
    }

    /**
     * Return a {@link List} of {@link DeviceModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceModelDTO> findByCriteria(DeviceModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeviceModel> specification = createSpecification(criteria);
        return deviceModelMapper.toDto(deviceModelRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeviceModelDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceModelDTO> findByCriteria(DeviceModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeviceModel> specification = createSpecification(criteria);
        return deviceModelRepository.findAll(specification, page)
            .map(deviceModelMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeviceModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeviceModel> specification = createSpecification(criteria);
        return deviceModelRepository.count(specification);
    }

    /**
     * Function to convert {@link DeviceModelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeviceModel> createSpecification(DeviceModelCriteria criteria) {
        Specification<DeviceModel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeviceModel_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DeviceModel_.name));
            }
            if (criteria.getIsConfigurable() != null) {
                specification = specification.and(buildSpecification(criteria.getIsConfigurable(), DeviceModel_.isConfigurable));
            }
            if (criteria.getLinesCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLinesCount(), DeviceModel_.linesCount));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceId(),
                    root -> root.join(DeviceModel_.devices, JoinType.LEFT).get(Device_.id)));
            }
            if (criteria.getAdditionalOptionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdditionalOptionsId(),
                    root -> root.join(DeviceModel_.additionalOptions, JoinType.LEFT).get(AdditionalOption_.id)));
            }
            if (criteria.getDeviceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceTypeId(),
                    root -> root.join(DeviceModel_.deviceType, JoinType.LEFT).get(DeviceType_.id)));
            }
        }
        return specification;
    }
}
