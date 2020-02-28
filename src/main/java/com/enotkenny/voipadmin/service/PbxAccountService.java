package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.PbxAccount;
import com.enotkenny.voipadmin.repository.PbxAccountRepository;
import com.enotkenny.voipadmin.service.dto.PbxAccountDTO;
import com.enotkenny.voipadmin.service.mapper.PbxAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link PbxAccount}.
 */
@Service
@Transactional
public class PbxAccountService {

    private final Logger log = LoggerFactory.getLogger(PbxAccountService.class);

    private final PbxAccountRepository pbxAccountRepository;

    private final PbxAccountMapper pbxAccountMapper;

    public PbxAccountService(PbxAccountRepository pbxAccountRepository, PbxAccountMapper pbxAccountMapper) {
        this.pbxAccountRepository = pbxAccountRepository;
        this.pbxAccountMapper = pbxAccountMapper;
    }

    /**
     * Save a pbxAccount.
     *
     * @param pbxAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public PbxAccountDTO save(PbxAccountDTO pbxAccountDTO) {
        log.debug("Request to save PbxAccount : {}", pbxAccountDTO);
        PbxAccount pbxAccount = pbxAccountMapper.toEntity(pbxAccountDTO);
        pbxAccount = pbxAccountRepository.save(pbxAccount);
        return pbxAccountMapper.toDto(pbxAccount);
    }

    /**
     * Get all the pbxAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PbxAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PbxAccounts");
        return pbxAccountRepository.findAll(pageable)
            .map(pbxAccountMapper::toDto);
    }



    /**
    *  Get all the pbxAccounts where SipAccount is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PbxAccountDTO> findAllWhereSipAccountIsNull() {
        log.debug("Request to get all pbxAccounts where SipAccount is null");
        return StreamSupport
            .stream(pbxAccountRepository.findAll().spliterator(), false)
            .filter(pbxAccount -> pbxAccount.getSipAccount() == null)
            .map(pbxAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pbxAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PbxAccountDTO> findOne(Long id) {
        log.debug("Request to get PbxAccount : {}", id);
        return pbxAccountRepository.findById(id)
            .map(pbxAccountMapper::toDto);
    }

    /**
     * Delete the pbxAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PbxAccount : {}", id);
        pbxAccountRepository.deleteById(id);
    }
}
