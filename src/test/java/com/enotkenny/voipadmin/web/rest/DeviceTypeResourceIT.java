package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.DeviceType;
import com.enotkenny.voipadmin.domain.DeviceModel;
import com.enotkenny.voipadmin.repository.DeviceTypeRepository;
import com.enotkenny.voipadmin.service.DeviceTypeService;
import com.enotkenny.voipadmin.service.dto.DeviceTypeDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceTypeMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.DeviceTypeCriteria;
import com.enotkenny.voipadmin.service.DeviceTypeQueryService;

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
 * Integration tests for the {@link DeviceTypeResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class DeviceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private DeviceTypeQueryService deviceTypeQueryService;

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

    private MockMvc restDeviceTypeMockMvc;

    private DeviceType deviceType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceTypeResource deviceTypeResource = new DeviceTypeResource(deviceTypeService, deviceTypeQueryService);
        this.restDeviceTypeMockMvc = MockMvcBuilders.standaloneSetup(deviceTypeResource)
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
    public static DeviceType createEntity(EntityManager em) {
        DeviceType deviceType = new DeviceType()
            .name(DEFAULT_NAME);
        return deviceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceType createUpdatedEntity(EntityManager em) {
        DeviceType deviceType = new DeviceType()
            .name(UPDATED_NAME);
        return deviceType;
    }

    @BeforeEach
    public void initTest() {
        deviceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceType() throws Exception {
        int databaseSizeBeforeCreate = deviceTypeRepository.findAll().size();

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);
        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeviceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceTypeRepository.findAll().size();

        // Create the DeviceType with an existing ID
        deviceType.setId(1L);
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceTypeRepository.findAll().size();
        // set the field null
        deviceType.setName(null);

        // Create the DeviceType, which fails.
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        restDeviceTypeMockMvc.perform(post("/api/device-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceTypes() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList
        restDeviceTypeMockMvc.perform(get("/api/device-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get the deviceType
        restDeviceTypeMockMvc.perform(get("/api/device-types/{id}", deviceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getDeviceTypesByIdFiltering() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        Long id = deviceType.getId();

        defaultDeviceTypeShouldBeFound("id.equals=" + id);
        defaultDeviceTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDeviceTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name equals to DEFAULT_NAME
        defaultDeviceTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name equals to UPDATED_NAME
        defaultDeviceTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name not equals to DEFAULT_NAME
        defaultDeviceTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name not equals to UPDATED_NAME
        defaultDeviceTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeviceTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deviceTypeList where name equals to UPDATED_NAME
        defaultDeviceTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name is not null
        defaultDeviceTypeShouldBeFound("name.specified=true");

        // Get all the deviceTypeList where name is null
        defaultDeviceTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeviceTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name contains DEFAULT_NAME
        defaultDeviceTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name contains UPDATED_NAME
        defaultDeviceTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        // Get all the deviceTypeList where name does not contain DEFAULT_NAME
        defaultDeviceTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deviceTypeList where name does not contain UPDATED_NAME
        defaultDeviceTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDeviceTypesByDeviceModelIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);
        DeviceModel deviceModel = DeviceModelResourceIT.createEntity(em);
        em.persist(deviceModel);
        em.flush();
        deviceType.addDeviceModel(deviceModel);
        deviceTypeRepository.saveAndFlush(deviceType);
        Long deviceModelId = deviceModel.getId();

        // Get all the deviceTypeList where deviceModel equals to deviceModelId
        defaultDeviceTypeShouldBeFound("deviceModelId.equals=" + deviceModelId);

        // Get all the deviceTypeList where deviceModel equals to deviceModelId + 1
        defaultDeviceTypeShouldNotBeFound("deviceModelId.equals=" + (deviceModelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceTypeShouldBeFound(String filter) throws Exception {
        restDeviceTypeMockMvc.perform(get("/api/device-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restDeviceTypeMockMvc.perform(get("/api/device-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceTypeShouldNotBeFound(String filter) throws Exception {
        restDeviceTypeMockMvc.perform(get("/api/device-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceTypeMockMvc.perform(get("/api/device-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDeviceType() throws Exception {
        // Get the deviceType
        restDeviceTypeMockMvc.perform(get("/api/device-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Update the deviceType
        DeviceType updatedDeviceType = deviceTypeRepository.findById(deviceType.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceType are not directly saved in db
        em.detach(updatedDeviceType);
        updatedDeviceType
            .name(UPDATED_NAME);
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(updatedDeviceType);

        restDeviceTypeMockMvc.perform(put("/api/device-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
        DeviceType testDeviceType = deviceTypeList.get(deviceTypeList.size() - 1);
        assertThat(testDeviceType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceType() throws Exception {
        int databaseSizeBeforeUpdate = deviceTypeRepository.findAll().size();

        // Create the DeviceType
        DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDto(deviceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceTypeMockMvc.perform(put("/api/device-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceType in the database
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceType() throws Exception {
        // Initialize the database
        deviceTypeRepository.saveAndFlush(deviceType);

        int databaseSizeBeforeDelete = deviceTypeRepository.findAll().size();

        // Delete the deviceType
        restDeviceTypeMockMvc.perform(delete("/api/device-types/{id}", deviceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceType> deviceTypeList = deviceTypeRepository.findAll();
        assertThat(deviceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
