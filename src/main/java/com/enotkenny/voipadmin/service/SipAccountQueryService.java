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

import com.enotkenny.voipadmin.domain.SipAccount;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.SipAccountRepository;
import com.enotkenny.voipadmin.service.dto.SipAccountCriteria;
import com.enotkenny.voipadmin.service.dto.SipAccountDTO;
import com.enotkenny.voipadmin.service.mapper.SipAccountMapper;

/**
 * Service for executing complex queries for {@link SipAccount} entities in the database.
 * The main input is a {@link SipAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SipAccountDTO} or a {@link Page} of {@link SipAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SipAccountQueryService extends QueryService<SipAccount> {

    private final Logger log = LoggerFactory.getLogger(SipAccountQueryService.class);

    private final SipAccountRepository sipAccountRepository;

    private final SipAccountMapper sipAccountMapper;

    public SipAccountQueryService(SipAccountRepository sipAccountRepository, SipAccountMapper sipAccountMapper) {
        this.sipAccountRepository = sipAccountRepository;
        this.sipAccountMapper = sipAccountMapper;
    }

    /**
     * Return a {@link List} of {@link SipAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SipAccountDTO> findByCriteria(SipAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SipAccount> specification = createSpecification(criteria);
        return sipAccountMapper.toDto(sipAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SipAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SipAccountDTO> findByCriteria(SipAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SipAccount> specification = createSpecification(criteria);
        return sipAccountRepository.findAll(specification, page)
            .map(sipAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SipAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SipAccount> specification = createSpecification(criteria);
        return sipAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link SipAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SipAccount> createSpecification(SipAccountCriteria criteria) {
        Specification<SipAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SipAccount_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), SipAccount_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), SipAccount_.password));
            }
            if (criteria.getLineEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getLineEnabled(), SipAccount_.lineEnabled));
            }
            if (criteria.getLineNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNumber(), SipAccount_.lineNumber));
            }
            if (criteria.getIsManuallyCreated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsManuallyCreated(), SipAccount_.isManuallyCreated));
            }
            if (criteria.getPbxAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getPbxAccountId(),
                    root -> root.join(SipAccount_.pbxAccount, JoinType.LEFT).get(PbxAccount_.id)));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceId(),
                    root -> root.join(SipAccount_.device, JoinType.LEFT).get(Device_.id)));
            }
        }
        return specification;
    }
}
