package com.enotkenny.voipadmin.service;

import com.enotkenny.voipadmin.domain.ResponsiblePerson;
import com.enotkenny.voipadmin.repository.ResponsiblePersonRepository;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonDTO;
import com.enotkenny.voipadmin.service.mapper.ResponsiblePersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResponsiblePerson}.
 */
@Service
@Transactional
public class ResponsiblePersonService {

    private final Logger log = LoggerFactory.getLogger(ResponsiblePersonService.class);

    private final ResponsiblePersonRepository responsiblePersonRepository;

    private final ResponsiblePersonMapper responsiblePersonMapper;

    public ResponsiblePersonService(ResponsiblePersonRepository responsiblePersonRepository, ResponsiblePersonMapper responsiblePersonMapper) {
        this.responsiblePersonRepository = responsiblePersonRepository;
        this.responsiblePersonMapper = responsiblePersonMapper;
    }

    /**
     * Save a responsiblePerson.
     *
     * @param responsiblePersonDTO the entity to save.
     * @return the persisted entity.
     */
    public ResponsiblePersonDTO save(ResponsiblePersonDTO responsiblePersonDTO) {
        log.debug("Request to save ResponsiblePerson : {}", responsiblePersonDTO);
        ResponsiblePerson responsiblePerson = responsiblePersonMapper.toEntity(responsiblePersonDTO);
        responsiblePerson = responsiblePersonRepository.save(responsiblePerson);
        return responsiblePersonMapper.toDto(responsiblePerson);
    }

    /**
     * Get all the responsiblePeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsiblePersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsiblePeople");
        return responsiblePersonRepository.findAll(pageable)
            .map(responsiblePersonMapper::toDto);
    }


    /**
     * Get one responsiblePerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResponsiblePersonDTO> findOne(Long id) {
        log.debug("Request to get ResponsiblePerson : {}", id);
        return responsiblePersonRepository.findById(id)
            .map(responsiblePersonMapper::toDto);
    }

    /**
     * Delete the responsiblePerson by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResponsiblePerson : {}", id);
        responsiblePersonRepository.deleteById(id);
    }
}
