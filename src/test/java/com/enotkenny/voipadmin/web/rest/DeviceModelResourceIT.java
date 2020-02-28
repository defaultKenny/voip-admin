package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.DeviceModel;
import com.enotkenny.voipadmin.domain.Device;
import com.enotkenny.voipadmin.domain.AdditionalOption;
import com.enotkenny.voipadmin.domain.DeviceType;
import com.enotkenny.voipadmin.repository.DeviceModelRepository;
import com.enotkenny.voipadmin.service.DeviceModelService;
import com.enotkenny.voipadmin.service.dto.DeviceModelDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceModelMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.DeviceModelCriteria;
import com.enotkenny.voipadmin.service.DeviceModelQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.enotkenny.voipadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeviceModelResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class DeviceModelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CONFIGURABLE = false;
    private static final Boolean UPDATED_IS_CONFIGURABLE = true;

    private static final Integer DEFAULT_LINES_COUNT = 1;
    private static final Integer UPDATED_LINES_COUNT = 2;
    private static final Integer SMALLER_LINES_COUNT = 1 - 1;

    private static final byte[] DEFAULT_CONFIG_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONFIG_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONFIG_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONFIG_FILE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FIRMWARE_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FIRMWARE_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FIRMWARE_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FIRMWARE_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private DeviceModelRepository deviceModelRepository;

    @Mock
    private DeviceModelRepository deviceModelRepositoryMock;

    @Autowired
    private DeviceModelMapper deviceModelMapper;

    @Mock
    private DeviceModelService deviceModelServiceMock;

    @Autowired
    private DeviceModelService deviceModelService;

    @Autowired
    private DeviceModelQueryService deviceModelQueryService;

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

    private MockMvc restDeviceModelMockMvc;

    private DeviceModel deviceModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceModelResource deviceModelResource = new DeviceModelResource(deviceModelService, deviceModelQueryService);
        this.restDeviceModelMockMvc = MockMvcBuilders.standaloneSetup(deviceModelResource)
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
    public static DeviceModel createEntity(EntityManager em) {
        DeviceModel deviceModel = new DeviceModel()
            .name(DEFAULT_NAME)
            .isConfigurable(DEFAULT_IS_CONFIGURABLE)
            .linesCount(DEFAULT_LINES_COUNT)
            .configFile(DEFAULT_CONFIG_FILE)
            .configFileContentType(DEFAULT_CONFIG_FILE_CONTENT_TYPE)
            .firmwareFile(DEFAULT_FIRMWARE_FILE)
            .firmwareFileContentType(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE);
        // Add required entity
        DeviceType deviceType;
        if (TestUtil.findAll(em, DeviceType.class).isEmpty()) {
            deviceType = DeviceTypeResourceIT.createEntity(em);
            em.persist(deviceType);
            em.flush();
        } else {
            deviceType = TestUtil.findAll(em, DeviceType.class).get(0);
        }
        deviceModel.setDeviceType(deviceType);
        return deviceModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceModel createUpdatedEntity(EntityManager em) {
        DeviceModel deviceModel = new DeviceModel()
            .name(UPDATED_NAME)
            .isConfigurable(UPDATED_IS_CONFIGURABLE)
            .linesCount(UPDATED_LINES_COUNT)
            .configFile(UPDATED_CONFIG_FILE)
            .configFileContentType(UPDATED_CONFIG_FILE_CONTENT_TYPE)
            .firmwareFile(UPDATED_FIRMWARE_FILE)
            .firmwareFileContentType(UPDATED_FIRMWARE_FILE_CONTENT_TYPE);
        // Add required entity
        DeviceType deviceType;
        if (TestUtil.findAll(em, DeviceType.class).isEmpty()) {
            deviceType = DeviceTypeResourceIT.createUpdatedEntity(em);
            em.persist(deviceType);
            em.flush();
        } else {
            deviceType = TestUtil.findAll(em, DeviceType.class).get(0);
        }
        deviceModel.setDeviceType(deviceType);
        return deviceModel;
    }

    @BeforeEach
    public void initTest() {
        deviceModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceModel() throws Exception {
        int databaseSizeBeforeCreate = deviceModelRepository.findAll().size();

        // Create the DeviceModel
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);
        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceModel testDeviceModel = deviceModelList.get(deviceModelList.size() - 1);
        assertThat(testDeviceModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeviceModel.isIsConfigurable()).isEqualTo(DEFAULT_IS_CONFIGURABLE);
        assertThat(testDeviceModel.getLinesCount()).isEqualTo(DEFAULT_LINES_COUNT);
        assertThat(testDeviceModel.getConfigFile()).isEqualTo(DEFAULT_CONFIG_FILE);
        assertThat(testDeviceModel.getConfigFileContentType()).isEqualTo(DEFAULT_CONFIG_FILE_CONTENT_TYPE);
        assertThat(testDeviceModel.getFirmwareFile()).isEqualTo(DEFAULT_FIRMWARE_FILE);
        assertThat(testDeviceModel.getFirmwareFileContentType()).isEqualTo(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDeviceModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceModelRepository.findAll().size();

        // Create the DeviceModel with an existing ID
        deviceModel.setId(1L);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceModelRepository.findAll().size();
        // set the field null
        deviceModel.setName(null);

        // Create the DeviceModel, which fails.
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        restDeviceModelMockMvc.perform(post("/api/device-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeviceModels() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList
        restDeviceModelMockMvc.perform(get("/api/device-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isConfigurable").value(hasItem(DEFAULT_IS_CONFIGURABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].linesCount").value(hasItem(DEFAULT_LINES_COUNT)))
            .andExpect(jsonPath("$.[*].configFileContentType").value(hasItem(DEFAULT_CONFIG_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].configFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONFIG_FILE))))
            .andExpect(jsonPath("$.[*].firmwareFileContentType").value(hasItem(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].firmwareFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIRMWARE_FILE))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDeviceModelsWithEagerRelationshipsIsEnabled() throws Exception {
        DeviceModelResource deviceModelResource = new DeviceModelResource(deviceModelServiceMock, deviceModelQueryService);
        when(deviceModelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDeviceModelMockMvc = MockMvcBuilders.standaloneSetup(deviceModelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDeviceModelMockMvc.perform(get("/api/device-models?eagerload=true"))
        .andExpect(status().isOk());

        verify(deviceModelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDeviceModelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        DeviceModelResource deviceModelResource = new DeviceModelResource(deviceModelServiceMock, deviceModelQueryService);
            when(deviceModelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDeviceModelMockMvc = MockMvcBuilders.standaloneSetup(deviceModelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDeviceModelMockMvc.perform(get("/api/device-models?eagerload=true"))
        .andExpect(status().isOk());

            verify(deviceModelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get the deviceModel
        restDeviceModelMockMvc.perform(get("/api/device-models/{id}", deviceModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isConfigurable").value(DEFAULT_IS_CONFIGURABLE.booleanValue()))
            .andExpect(jsonPath("$.linesCount").value(DEFAULT_LINES_COUNT))
            .andExpect(jsonPath("$.configFileContentType").value(DEFAULT_CONFIG_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.configFile").value(Base64Utils.encodeToString(DEFAULT_CONFIG_FILE)))
            .andExpect(jsonPath("$.firmwareFileContentType").value(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.firmwareFile").value(Base64Utils.encodeToString(DEFAULT_FIRMWARE_FILE)));
    }


    @Test
    @Transactional
    public void getDeviceModelsByIdFiltering() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        Long id = deviceModel.getId();

        defaultDeviceModelShouldBeFound("id.equals=" + id);
        defaultDeviceModelShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceModelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceModelShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceModelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceModelShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDeviceModelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where name equals to DEFAULT_NAME
        defaultDeviceModelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deviceModelList where name equals to UPDATED_NAME
        defaultDeviceModelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where name not equals to DEFAULT_NAME
        defaultDeviceModelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the deviceModelList where name not equals to UPDATED_NAME
        defaultDeviceModelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeviceModelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deviceModelList where name equals to UPDATED_NAME
        defaultDeviceModelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where name is not null
        defaultDeviceModelShouldBeFound("name.specified=true");

        // Get all the deviceModelList where name is null
        defaultDeviceModelShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeviceModelsByNameContainsSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where name contains DEFAULT_NAME
        defaultDeviceModelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deviceModelList where name contains UPDATED_NAME
        defaultDeviceModelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where name does not contain DEFAULT_NAME
        defaultDeviceModelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deviceModelList where name does not contain UPDATED_NAME
        defaultDeviceModelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDeviceModelsByIsConfigurableIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where isConfigurable equals to DEFAULT_IS_CONFIGURABLE
        defaultDeviceModelShouldBeFound("isConfigurable.equals=" + DEFAULT_IS_CONFIGURABLE);

        // Get all the deviceModelList where isConfigurable equals to UPDATED_IS_CONFIGURABLE
        defaultDeviceModelShouldNotBeFound("isConfigurable.equals=" + UPDATED_IS_CONFIGURABLE);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByIsConfigurableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where isConfigurable not equals to DEFAULT_IS_CONFIGURABLE
        defaultDeviceModelShouldNotBeFound("isConfigurable.notEquals=" + DEFAULT_IS_CONFIGURABLE);

        // Get all the deviceModelList where isConfigurable not equals to UPDATED_IS_CONFIGURABLE
        defaultDeviceModelShouldBeFound("isConfigurable.notEquals=" + UPDATED_IS_CONFIGURABLE);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByIsConfigurableIsInShouldWork() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where isConfigurable in DEFAULT_IS_CONFIGURABLE or UPDATED_IS_CONFIGURABLE
        defaultDeviceModelShouldBeFound("isConfigurable.in=" + DEFAULT_IS_CONFIGURABLE + "," + UPDATED_IS_CONFIGURABLE);

        // Get all the deviceModelList where isConfigurable equals to UPDATED_IS_CONFIGURABLE
        defaultDeviceModelShouldNotBeFound("isConfigurable.in=" + UPDATED_IS_CONFIGURABLE);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByIsConfigurableIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where isConfigurable is not null
        defaultDeviceModelShouldBeFound("isConfigurable.specified=true");

        // Get all the deviceModelList where isConfigurable is null
        defaultDeviceModelShouldNotBeFound("isConfigurable.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount equals to DEFAULT_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.equals=" + DEFAULT_LINES_COUNT);

        // Get all the deviceModelList where linesCount equals to UPDATED_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.equals=" + UPDATED_LINES_COUNT);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount not equals to DEFAULT_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.notEquals=" + DEFAULT_LINES_COUNT);

        // Get all the deviceModelList where linesCount not equals to UPDATED_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.notEquals=" + UPDATED_LINES_COUNT);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsInShouldWork() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount in DEFAULT_LINES_COUNT or UPDATED_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.in=" + DEFAULT_LINES_COUNT + "," + UPDATED_LINES_COUNT);

        // Get all the deviceModelList where linesCount equals to UPDATED_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.in=" + UPDATED_LINES_COUNT);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount is not null
        defaultDeviceModelShouldBeFound("linesCount.specified=true");

        // Get all the deviceModelList where linesCount is null
        defaultDeviceModelShouldNotBeFound("linesCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount is greater than or equal to DEFAULT_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.greaterThanOrEqual=" + DEFAULT_LINES_COUNT);

        // Get all the deviceModelList where linesCount is greater than or equal to UPDATED_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.greaterThanOrEqual=" + UPDATED_LINES_COUNT);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount is less than or equal to DEFAULT_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.lessThanOrEqual=" + DEFAULT_LINES_COUNT);

        // Get all the deviceModelList where linesCount is less than or equal to SMALLER_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.lessThanOrEqual=" + SMALLER_LINES_COUNT);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount is less than DEFAULT_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.lessThan=" + DEFAULT_LINES_COUNT);

        // Get all the deviceModelList where linesCount is less than UPDATED_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.lessThan=" + UPDATED_LINES_COUNT);
    }

    @Test
    @Transactional
    public void getAllDeviceModelsByLinesCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        // Get all the deviceModelList where linesCount is greater than DEFAULT_LINES_COUNT
        defaultDeviceModelShouldNotBeFound("linesCount.greaterThan=" + DEFAULT_LINES_COUNT);

        // Get all the deviceModelList where linesCount is greater than SMALLER_LINES_COUNT
        defaultDeviceModelShouldBeFound("linesCount.greaterThan=" + SMALLER_LINES_COUNT);
    }


    @Test
    @Transactional
    public void getAllDeviceModelsByDeviceIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);
        Device device = DeviceResourceIT.createEntity(em);
        em.persist(device);
        em.flush();
        deviceModel.addDevice(device);
        deviceModelRepository.saveAndFlush(deviceModel);
        Long deviceId = device.getId();

        // Get all the deviceModelList where device equals to deviceId
        defaultDeviceModelShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the deviceModelList where device equals to deviceId + 1
        defaultDeviceModelShouldNotBeFound("deviceId.equals=" + (deviceId + 1));
    }


    @Test
    @Transactional
    public void getAllDeviceModelsByAdditionalOptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);
        AdditionalOption additionalOptions = AdditionalOptionResourceIT.createEntity(em);
        em.persist(additionalOptions);
        em.flush();
        deviceModel.addAdditionalOptions(additionalOptions);
        deviceModelRepository.saveAndFlush(deviceModel);
        Long additionalOptionsId = additionalOptions.getId();

        // Get all the deviceModelList where additionalOptions equals to additionalOptionsId
        defaultDeviceModelShouldBeFound("additionalOptionsId.equals=" + additionalOptionsId);

        // Get all the deviceModelList where additionalOptions equals to additionalOptionsId + 1
        defaultDeviceModelShouldNotBeFound("additionalOptionsId.equals=" + (additionalOptionsId + 1));
    }


    @Test
    @Transactional
    public void getAllDeviceModelsByDeviceTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        DeviceType deviceType = deviceModel.getDeviceType();
        deviceModelRepository.saveAndFlush(deviceModel);
        Long deviceTypeId = deviceType.getId();

        // Get all the deviceModelList where deviceType equals to deviceTypeId
        defaultDeviceModelShouldBeFound("deviceTypeId.equals=" + deviceTypeId);

        // Get all the deviceModelList where deviceType equals to deviceTypeId + 1
        defaultDeviceModelShouldNotBeFound("deviceTypeId.equals=" + (deviceTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceModelShouldBeFound(String filter) throws Exception {
        restDeviceModelMockMvc.perform(get("/api/device-models?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isConfigurable").value(hasItem(DEFAULT_IS_CONFIGURABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].linesCount").value(hasItem(DEFAULT_LINES_COUNT)))
            .andExpect(jsonPath("$.[*].configFileContentType").value(hasItem(DEFAULT_CONFIG_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].configFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONFIG_FILE))))
            .andExpect(jsonPath("$.[*].firmwareFileContentType").value(hasItem(DEFAULT_FIRMWARE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].firmwareFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_FIRMWARE_FILE))));

        // Check, that the count call also returns 1
        restDeviceModelMockMvc.perform(get("/api/device-models/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceModelShouldNotBeFound(String filter) throws Exception {
        restDeviceModelMockMvc.perform(get("/api/device-models?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceModelMockMvc.perform(get("/api/device-models/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDeviceModel() throws Exception {
        // Get the deviceModel
        restDeviceModelMockMvc.perform(get("/api/device-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Update the deviceModel
        DeviceModel updatedDeviceModel = deviceModelRepository.findById(deviceModel.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceModel are not directly saved in db
        em.detach(updatedDeviceModel);
        updatedDeviceModel
            .name(UPDATED_NAME)
            .isConfigurable(UPDATED_IS_CONFIGURABLE)
            .linesCount(UPDATED_LINES_COUNT)
            .configFile(UPDATED_CONFIG_FILE)
            .configFileContentType(UPDATED_CONFIG_FILE_CONTENT_TYPE)
            .firmwareFile(UPDATED_FIRMWARE_FILE)
            .firmwareFileContentType(UPDATED_FIRMWARE_FILE_CONTENT_TYPE);
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(updatedDeviceModel);

        restDeviceModelMockMvc.perform(put("/api/device-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeUpdate);
        DeviceModel testDeviceModel = deviceModelList.get(deviceModelList.size() - 1);
        assertThat(testDeviceModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeviceModel.isIsConfigurable()).isEqualTo(UPDATED_IS_CONFIGURABLE);
        assertThat(testDeviceModel.getLinesCount()).isEqualTo(UPDATED_LINES_COUNT);
        assertThat(testDeviceModel.getConfigFile()).isEqualTo(UPDATED_CONFIG_FILE);
        assertThat(testDeviceModel.getConfigFileContentType()).isEqualTo(UPDATED_CONFIG_FILE_CONTENT_TYPE);
        assertThat(testDeviceModel.getFirmwareFile()).isEqualTo(UPDATED_FIRMWARE_FILE);
        assertThat(testDeviceModel.getFirmwareFileContentType()).isEqualTo(UPDATED_FIRMWARE_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceModel() throws Exception {
        int databaseSizeBeforeUpdate = deviceModelRepository.findAll().size();

        // Create the DeviceModel
        DeviceModelDTO deviceModelDTO = deviceModelMapper.toDto(deviceModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceModelMockMvc.perform(put("/api/device-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceModel in the database
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceModel() throws Exception {
        // Initialize the database
        deviceModelRepository.saveAndFlush(deviceModel);

        int databaseSizeBeforeDelete = deviceModelRepository.findAll().size();

        // Delete the deviceModel
        restDeviceModelMockMvc.perform(delete("/api/device-models/{id}", deviceModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceModel> deviceModelList = deviceModelRepository.findAll();
        assertThat(deviceModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
