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

import com.enotkenny.voipadmin.domain.PbxAccount;
import com.enotkenny.voipadmin.domain.*; // for static metamodels
import com.enotkenny.voipadmin.repository.PbxAccountRepository;
import com.enotkenny.voipadmin.service.dto.PbxAccountCriteria;
import com.enotkenny.voipadmin.service.dto.PbxAccountDTO;
import com.enotkenny.voipadmin.service.mapper.PbxAccountMapper;

/**
 * Service for executing complex queries for {@link PbxAccount} entities in the database.
 * The main input is a {@link PbxAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PbxAccountDTO} or a {@link Page} of {@link PbxAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PbxAccountQueryService extends QueryService<PbxAccount> {

    private final Logger log = LoggerFactory.getLogger(PbxAccountQueryService.class);

    private final PbxAccountRepository pbxAccountRepository;

    private final PbxAccountMapper pbxAccountMapper;

    public PbxAccountQueryService(PbxAccountRepository pbxAccountRepository, PbxAccountMapper pbxAccountMapper) {
        this.pbxAccountRepository = pbxAccountRepository;
        this.pbxAccountMapper = pbxAccountMapper;
    }

    /**
     * Return a {@link List} of {@link PbxAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PbxAccountDTO> findByCriteria(PbxAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PbxAccount> specification = createSpecification(criteria);
        return pbxAccountMapper.toDto(pbxAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PbxAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PbxAccountDTO> findByCriteria(PbxAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PbxAccount> specification = createSpecification(criteria);
        return pbxAccountRepository.findAll(specification, page)
            .map(pbxAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PbxAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PbxAccount> specification = createSpecification(criteria);
        return pbxAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link PbxAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PbxAccount> createSpecification(PbxAccountCriteria criteria) {
        Specification<PbxAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PbxAccount_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), PbxAccount_.username));
            }
            if (criteria.getPbxId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPbxId(), PbxAccount_.pbxId));
            }
            if (criteria.getSipAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getSipAccountId(),
                    root -> root.join(PbxAccount_.sipAccount, JoinType.LEFT).get(SipAccount_.id)));
            }
        }
        return specification;
    }
}
