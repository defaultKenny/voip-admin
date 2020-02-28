package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.AdditionalOption;
import com.enotkenny.voipadmin.domain.DeviceSetting;
import com.enotkenny.voipadmin.domain.DeviceModel;
import com.enotkenny.voipadmin.repository.AdditionalOptionRepository;
import com.enotkenny.voipadmin.service.AdditionalOptionService;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionDTO;
import com.enotkenny.voipadmin.service.mapper.AdditionalOptionMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionCriteria;
import com.enotkenny.voipadmin.service.AdditionalOptionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.enotkenny.voipadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdditionalOptionResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class AdditionalOptionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AdditionalOptionRepository additionalOptionRepository;

    @Autowired
    private AdditionalOptionMapper additionalOptionMapper;

    @Autowired
    private AdditionalOptionService additionalOptionService;

    @Autowired
    private AdditionalOptionQueryService additionalOptionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAdditionalOptionMockMvc;

    private AdditionalOption additionalOption;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdditionalOptionResource additionalOptionResource = new AdditionalOptionResource(additionalOptionService, additionalOptionQueryService);
        this.restAdditionalOptionMockMvc = MockMvcBuilders.standaloneSetup(additionalOptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalOption createEntity(EntityManager em) {
        AdditionalOption additionalOption = new AdditionalOption()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return additionalOption;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalOption createUpdatedEntity(EntityManager em) {
        AdditionalOption additionalOption = new AdditionalOption()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return additionalOption;
    }

    @BeforeEach
    public void initTest() {
        additionalOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdditionalOption() throws Exception {
        int databaseSizeBeforeCreate = additionalOptionRepository.findAll().size();

        // Create the AdditionalOption
        AdditionalOptionDTO additionalOptionDTO = additionalOptionMapper.toDto(additionalOption);
        restAdditionalOptionMockMvc.perform(post("/api/additional-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the AdditionalOption in the database
        List<AdditionalOption> additionalOptionList = additionalOptionRepository.findAll();
        assertThat(additionalOptionList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalOption testAdditionalOption = additionalOptionList.get(additionalOptionList.size() - 1);
        assertThat(testAdditionalOption.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAdditionalOption.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAdditionalOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = additionalOptionRepository.findAll().size();

        // Create the AdditionalOption with an existing ID
        additionalOption.setId(1L);
        AdditionalOptionDTO additionalOptionDTO = additionalOptionMapper.toDto(additionalOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalOptionMockMvc.perform(post("/api/additional-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalOption in the database
        List<AdditionalOption> additionalOptionList = additionalOptionRepository.findAll();
        assertThat(additionalOptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalOptionRepository.findAll().size();
        // set the field null
        additionalOption.setCode(null);

        // Create the AdditionalOption, which fails.
        AdditionalOptionDTO additionalOptionDTO = additionalOptionMapper.toDto(additionalOption);

        restAdditionalOptionMockMvc.perform(post("/api/additional-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalOptionDTO)))
            .andExpect(status().isBadRequest());

        List<AdditionalOption> additionalOptionList = additionalOptionRepository.findAll();
        assertThat(additionalOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptions() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList
        restAdditionalOptionMockMvc.perform(get("/api/additional-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAdditionalOption() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get the additionalOption
        restAdditionalOptionMockMvc.perform(get("/api/additional-options/{id}", additionalOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(additionalOption.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getAdditionalOptionsByIdFiltering() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        Long id = additionalOption.getId();

        defaultAdditionalOptionShouldBeFound("id.equals=" + id);
        defaultAdditionalOptionShouldNotBeFound("id.notEquals=" + id);

        defaultAdditionalOptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdditionalOptionShouldNotBeFound("id.greaterThan=" + id);

        defaultAdditionalOptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdditionalOptionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdditionalOptionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where code equals to DEFAULT_CODE
        defaultAdditionalOptionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the additionalOptionList where code equals to UPDATED_CODE
        defaultAdditionalOptionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where code not equals to DEFAULT_CODE
        defaultAdditionalOptionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the additionalOptionList where code not equals to UPDATED_CODE
        defaultAdditionalOptionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAdditionalOptionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the additionalOptionList where code equals to UPDATED_CODE
        defaultAdditionalOptionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where code is not null
        defaultAdditionalOptionShouldBeFound("code.specified=true");

        // Get all the additionalOptionList where code is null
        defaultAdditionalOptionShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdditionalOptionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where code contains DEFAULT_CODE
        defaultAdditionalOptionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the additionalOptionList where code contains UPDATED_CODE
        defaultAdditionalOptionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where code does not contain DEFAULT_CODE
        defaultAdditionalOptionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the additionalOptionList where code does not contain UPDATED_CODE
        defaultAdditionalOptionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllAdditionalOptionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where description equals to DEFAULT_DESCRIPTION
        defaultAdditionalOptionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the additionalOptionList where description equals to UPDATED_DESCRIPTION
        defaultAdditionalOptionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where description not equals to DEFAULT_DESCRIPTION
        defaultAdditionalOptionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the additionalOptionList where description not equals to UPDATED_DESCRIPTION
        defaultAdditionalOptionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdditionalOptionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the additionalOptionList where description equals to UPDATED_DESCRIPTION
        defaultAdditionalOptionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where description is not null
        defaultAdditionalOptionShouldBeFound("description.specified=true");

        // Get all the additionalOptionList where description is null
        defaultAdditionalOptionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdditionalOptionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where description contains DEFAULT_DESCRIPTION
        defaultAdditionalOptionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the additionalOptionList where description contains UPDATED_DESCRIPTION
        defaultAdditionalOptionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdditionalOptionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        // Get all the additionalOptionList where description does not contain DEFAULT_DESCRIPTION
        defaultAdditionalOptionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the additionalOptionList where description does not contain UPDATED_DESCRIPTION
        defaultAdditionalOptionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdditionalOptionsByDeviceSettingIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);
        DeviceSetting deviceSetting = DeviceSettingResourceIT.createEntity(em);
        em.persist(deviceSetting);
        em.flush();
        additionalOption.addDeviceSetting(deviceSetting);
        additionalOptionRepository.saveAndFlush(additionalOption);
        Long deviceSettingId = deviceSetting.getId();

        // Get all the additionalOptionList where deviceSetting equals to deviceSettingId
        defaultAdditionalOptionShouldBeFound("deviceSettingId.equals=" + deviceSettingId);

        // Get all the additionalOptionList where deviceSetting equals to deviceSettingId + 1
        defaultAdditionalOptionShouldNotBeFound("deviceSettingId.equals=" + (deviceSettingId + 1));
    }


    @Test
    @Transactional
    public void getAllAdditionalOptionsByDeviceModelsIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);
        DeviceModel deviceModels = DeviceModelResourceIT.createEntity(em);
        em.persist(deviceModels);
        em.flush();
        additionalOption.addDeviceModels(deviceModels);
        additionalOptionRepository.saveAndFlush(additionalOption);
        Long deviceModelsId = deviceModels.getId();

        // Get all the additionalOptionList where deviceModels equals to deviceModelsId
        defaultAdditionalOptionShouldBeFound("deviceModelsId.equals=" + deviceModelsId);

        // Get all the additionalOptionList where deviceModels equals to deviceModelsId + 1
        defaultAdditionalOptionShouldNotBeFound("deviceModelsId.equals=" + (deviceModelsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdditionalOptionShouldBeFound(String filter) throws Exception {
        restAdditionalOptionMockMvc.perform(get("/api/additional-options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restAdditionalOptionMockMvc.perform(get("/api/additional-options/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdditionalOptionShouldNotBeFound(String filter) throws Exception {
        restAdditionalOptionMockMvc.perform(get("/api/additional-options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdditionalOptionMockMvc.perform(get("/api/additional-options/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdditionalOption() throws Exception {
        // Get the additionalOption
        restAdditionalOptionMockMvc.perform(get("/api/additional-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdditionalOption() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        int databaseSizeBeforeUpdate = additionalOptionRepository.findAll().size();

        // Update the additionalOption
        AdditionalOption updatedAdditionalOption = additionalOptionRepository.findById(additionalOption.getId()).get();
        // Disconnect from session so that the updates on updatedAdditionalOption are not directly saved in db
        em.detach(updatedAdditionalOption);
        updatedAdditionalOption
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        AdditionalOptionDTO additionalOptionDTO = additionalOptionMapper.toDto(updatedAdditionalOption);

        restAdditionalOptionMockMvc.perform(put("/api/additional-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalOptionDTO)))
            .andExpect(status().isOk());

        // Validate the AdditionalOption in the database
        List<AdditionalOption> additionalOptionList = additionalOptionRepository.findAll();
        assertThat(additionalOptionList).hasSize(databaseSizeBeforeUpdate);
        AdditionalOption testAdditionalOption = additionalOptionList.get(additionalOptionList.size() - 1);
        assertThat(testAdditionalOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAdditionalOption.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAdditionalOption() throws Exception {
        int databaseSizeBeforeUpdate = additionalOptionRepository.findAll().size();

        // Create the AdditionalOption
        AdditionalOptionDTO additionalOptionDTO = additionalOptionMapper.toDto(additionalOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalOptionMockMvc.perform(put("/api/additional-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(additionalOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalOption in the database
        List<AdditionalOption> additionalOptionList = additionalOptionRepository.findAll();
        assertThat(additionalOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdditionalOption() throws Exception {
        // Initialize the database
        additionalOptionRepository.saveAndFlush(additionalOption);

        int databaseSizeBeforeDelete = additionalOptionRepository.findAll().size();

        // Delete the additionalOption
        restAdditionalOptionMockMvc.perform(delete("/api/additional-options/{id}", additionalOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdditionalOption> additionalOptionList = additionalOptionRepository.findAll();
        assertThat(additionalOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
