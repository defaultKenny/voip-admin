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

import com.enotkenny.voipadmin.domain.ResponsiblePerson;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.ResponsiblePersonRepository;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonCriteria;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonDTO;
import com.enotkenny.voipadmin.service.mapper.ResponsiblePersonMapper;

/**
 * Service for executing complex queries for {@link ResponsiblePerson} entities in the database.
 * The main input is a {@link ResponsiblePersonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsiblePersonDTO} or a {@link Page} of {@link ResponsiblePersonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsiblePersonQueryService extends QueryService<ResponsiblePerson> {

    private final Logger log = LoggerFactory.getLogger(ResponsiblePersonQueryService.class);

    private final ResponsiblePersonRepository responsiblePersonRepository;

    private final ResponsiblePersonMapper responsiblePersonMapper;

    public ResponsiblePersonQueryService(ResponsiblePersonRepository responsiblePersonRepository, ResponsiblePersonMapper responsiblePersonMapper) {
        this.responsiblePersonRepository = responsiblePersonRepository;
        this.responsiblePersonMapper = responsiblePersonMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsiblePersonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsiblePersonDTO> findByCriteria(ResponsiblePersonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResponsiblePerson> specification = createSpecification(criteria);
        return responsiblePersonMapper.toDto(responsiblePersonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsiblePersonDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsiblePersonDTO> findByCriteria(ResponsiblePersonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResponsiblePerson> specification = createSpecification(criteria);
        return responsiblePersonRepository.findAll(specification, page)
            .map(responsiblePersonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsiblePersonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResponsiblePerson> specification = createSpecification(criteria);
        return responsiblePersonRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsiblePersonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResponsiblePerson> createSpecification(ResponsiblePersonCriteria criteria) {
        Specification<ResponsiblePerson> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResponsiblePerson_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ResponsiblePerson_.code));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), ResponsiblePerson_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), ResponsiblePerson_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), ResponsiblePerson_.lastName));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), ResponsiblePerson_.position));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), ResponsiblePerson_.department));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), ResponsiblePerson_.location));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceId(),
                    root -> root.join(ResponsiblePerson_.devices, JoinType.LEFT).get(Device_.id)));
            }
        }
        return specification;
    }
}
