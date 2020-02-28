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

import com.enotkenny.voipadmin.domain.AdditionalOption;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.AdditionalOptionRepository;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionCriteria;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionDTO;
import com.enotkenny.voipadmin.service.mapper.AdditionalOptionMapper;

/**
 * Service for executing complex queries for {@link AdditionalOption} entities in the database.
 * The main input is a {@link AdditionalOptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdditionalOptionDTO} or a {@link Page} of {@link AdditionalOptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdditionalOptionQueryService extends QueryService<AdditionalOption> {

    private final Logger log = LoggerFactory.getLogger(AdditionalOptionQueryService.class);

    private final AdditionalOptionRepository additionalOptionRepository;

    private final AdditionalOptionMapper additionalOptionMapper;

    public AdditionalOptionQueryService(AdditionalOptionRepository additionalOptionRepository, AdditionalOptionMapper additionalOptionMapper) {
        this.additionalOptionRepository = additionalOptionRepository;
        this.additionalOptionMapper = additionalOptionMapper;
    }

    /**
     * Return a {@link List} of {@link AdditionalOptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdditionalOptionDTO> findByCriteria(AdditionalOptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdditionalOption> specification = createSpecification(criteria);
        return additionalOptionMapper.toDto(additionalOptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdditionalOptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdditionalOptionDTO> findByCriteria(AdditionalOptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdditionalOption> specification = createSpecification(criteria);
        return additionalOptionRepository.findAll(specification, page)
            .map(additionalOptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdditionalOptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdditionalOption> specification = createSpecification(criteria);
        return additionalOptionRepository.count(specification);
    }

    /**
     * Function to convert {@link AdditionalOptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdditionalOption> createSpecification(AdditionalOptionCriteria criteria) {
        Specification<AdditionalOption> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdditionalOption_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), AdditionalOption_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AdditionalOption_.description));
            }
            if (criteria.getDeviceSettingId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceSettingId(),
                    root -> root.join(AdditionalOption_.deviceSettings, JoinType.LEFT).get(DeviceSetting_.id)));
            }
            if (criteria.getDeviceModelsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceModelsId(),
                    root -> root.join(AdditionalOption_.deviceModels, JoinType.LEFT).get(DeviceModel_.id)));
            }
        }
        return specification;
    }
}
