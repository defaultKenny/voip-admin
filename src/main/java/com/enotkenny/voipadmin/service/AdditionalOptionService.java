package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.AdditionalOption;
import com.enotkenny.voipadmin.repository.AdditionalOptionRepository;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionDTO;
import com.enotkenny.voipadmin.service.mapper.AdditionalOptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdditionalOption}.
 */
@Service
@Transactional
public class AdditionalOptionService {

    private final Logger log = LoggerFactory.getLogger(AdditionalOptionService.class);

    private final AdditionalOptionRepository additionalOptionRepository;

    private final AdditionalOptionMapper additionalOptionMapper;

    public AdditionalOptionService(AdditionalOptionRepository additionalOptionRepository, AdditionalOptionMapper additionalOptionMapper) {
        this.additionalOptionRepository = additionalOptionRepository;
        this.additionalOptionMapper = additionalOptionMapper;
    }

    /**
     * Save a additionalOption.
     *
     * @param additionalOptionDTO the entity to save.
     * @return the persisted entity.
     */
    public AdditionalOptionDTO save(AdditionalOptionDTO additionalOptionDTO) {
        log.debug("Request to save AdditionalOption : {}", additionalOptionDTO);
        AdditionalOption additionalOption = additionalOptionMapper.toEntity(additionalOptionDTO);
        additionalOption = additionalOptionRepository.save(additionalOption);
        return additionalOptionMapper.toDto(additionalOption);
    }

    /**
     * Get all the additionalOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdditionalOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdditionalOptions");
        return additionalOptionRepository.findAll(pageable)
            .map(additionalOptionMapper::toDto);
    }


    /**
     * Get one additionalOption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdditionalOptionDTO> findOne(Long id) {
        log.debug("Request to get AdditionalOption : {}", id);
        return additionalOptionRepository.findById(id)
            .map(additionalOptionMapper::toDto);
    }

    /**
     * Delete the additionalOption by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdditionalOption : {}", id);
        additionalOptionRepository.deleteById(id);
    }
}
