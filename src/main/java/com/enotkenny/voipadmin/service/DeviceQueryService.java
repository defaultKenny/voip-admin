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

import com.enotkenny.voipadmin.domain.Device;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.DeviceRepository;
import com.enotkenny.voipadmin.service.dto.DeviceCriteria;
import com.enotkenny.voipadmin.service.dto.DeviceDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceMapper;

/**
 * Service for executing complex queries for {@link Device} entities in the database.
 * The main input is a {@link DeviceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeviceDTO} or a {@link Page} of {@link DeviceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeviceQueryService extends QueryService<Device> {

    private final Logger log = LoggerFactory.getLogger(DeviceQueryService.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    public DeviceQueryService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    /**
     * Return a {@link List} of {@link DeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceDTO> findByCriteria(DeviceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Device> specification = createSpecification(criteria);
        return deviceMapper.toDto(deviceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeviceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findByCriteria(DeviceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Device> specification = createSpecification(criteria);
        return deviceRepository.findAll(specification, page)
            .map(deviceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeviceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Device> specification = createSpecification(criteria);
        return deviceRepository.count(specification);
    }

    /**
     * Function to convert {@link DeviceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Device> createSpecification(DeviceCriteria criteria) {
        Specification<Device> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Device_.id));
            }
            if (criteria.getMac() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMac(), Device_.mac));
            }
            if (criteria.getInventory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInventory(), Device_.inventory));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Device_.location));
            }
            if (criteria.getHostname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHostname(), Device_.hostname));
            }
            if (criteria.getWebAccessLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebAccessLogin(), Device_.webAccessLogin));
            }
            if (criteria.getWebAccessPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebAccessPassword(), Device_.webAccessPassword));
            }
            if (criteria.getDhcpEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getDhcpEnabled(), Device_.dhcpEnabled));
            }
            if (criteria.getIpAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIpAddress(), Device_.ipAddress));
            }
            if (criteria.getSubnetMask() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubnetMask(), Device_.subnetMask));
            }
            if (criteria.getDefaultGateway() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultGateway(), Device_.defaultGateway));
            }
            if (criteria.getDns1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDns1(), Device_.dns1));
            }
            if (criteria.getDns2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDns2(), Device_.dns2));
            }
            if (criteria.getProvUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvUrl(), Device_.provUrl));
            }
            if (criteria.getProvProtocol() != null) {
                specification = specification.and(buildSpecification(criteria.getProvProtocol(), Device_.provProtocol));
            }
            if (criteria.getSipAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getSipAccountId(),
                    root -> root.join(Device_.sipAccounts, JoinType.LEFT).get(SipAccount_.id)));
            }
            if (criteria.getDeviceSettingId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceSettingId(),
                    root -> root.join(Device_.deviceSettings, JoinType.LEFT).get(DeviceSetting_.id)));
            }
            if (criteria.getDeviceModelId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceModelId(),
                    root -> root.join(Device_.deviceModel, JoinType.LEFT).get(DeviceModel_.id)));
            }
            if (criteria.getResponsiblePersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getResponsiblePersonId(),
                    root -> root.join(Device_.responsiblePerson, JoinType.LEFT).get(ResponsiblePerson_.id)));
            }
        }
        return specification;
    }
}
