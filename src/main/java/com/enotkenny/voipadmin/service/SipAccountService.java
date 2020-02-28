package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.SipAccount;
import com.enotkenny.voipadmin.repository.SipAccountRepository;
import com.enotkenny.voipadmin.service.dto.SipAccountDTO;
import com.enotkenny.voipadmin.service.mapper.SipAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SipAccount}.
 */
@Service
@Transactional
public class SipAccountService {

    private final Logger log = LoggerFactory.getLogger(SipAccountService.class);

    private final SipAccountRepository sipAccountRepository;

    private final SipAccountMapper sipAccountMapper;

    public SipAccountService(SipAccountRepository sipAccountRepository, SipAccountMapper sipAccountMapper) {
        this.sipAccountRepository = sipAccountRepository;
        this.sipAccountMapper = sipAccountMapper;
    }

    /**
     * Save a sipAccount.
     *
     * @param sipAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public SipAccountDTO save(SipAccountDTO sipAccountDTO) {
        log.debug("Request to save SipAccount : {}", sipAccountDTO);
        SipAccount sipAccount = sipAccountMapper.toEntity(sipAccountDTO);
        sipAccount = sipAccountRepository.save(sipAccount);
        return sipAccountMapper.toDto(sipAccount);
    }

    /**
     * Get all the sipAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SipAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SipAccounts");
        return sipAccountRepository.findAll(pageable)
            .map(sipAccountMapper::toDto);
    }


    /**
     * Get one sipAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SipAccountDTO> findOne(Long id) {
        log.debug("Request to get SipAccount : {}", id);
        return sipAccountRepository.findById(id)
            .map(sipAccountMapper::toDto);
    }

    /**
     * Delete the sipAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SipAccount : {}", id);
        sipAccountRepository.deleteById(id);
    }
}
