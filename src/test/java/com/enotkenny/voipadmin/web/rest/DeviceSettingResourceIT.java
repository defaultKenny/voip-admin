package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.DeviceSetting;
import com.enotkenny.voipadmin.domain.Device;
import com.enotkenny.voipadmin.domain.AdditionalOption;
import com.enotkenny.voipadmin.repository.DeviceSettingRepository;
import com.enotkenny.voipadmin.service.DeviceSettingService;
import com.enotkenny.voipadmin.service.dto.DeviceSettingDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceSettingMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.DeviceSettingCriteria;
import com.enotkenny.voipadmin.service.DeviceSettingQueryService;

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
 * Integration tests for the {@link DeviceSettingResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class DeviceSettingResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private DeviceSettingRepository deviceSettingRepository;

    @Autowired
    private DeviceSettingMapper deviceSettingMapper;

    @Autowired
    private DeviceSettingService deviceSettingService;

    @Autowired
    private DeviceSettingQueryService deviceSettingQueryService;

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

    private MockMvc restDeviceSettingMockMvc;

    private DeviceSetting deviceSetting;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceSettingResource deviceSettingResource = new DeviceSettingResource(deviceSettingService, deviceSettingQueryService);
        this.restDeviceSettingMockMvc = MockMvcBuilders.standaloneSetup(deviceSettingResource)
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
    public static DeviceSetting createEntity(EntityManager em) {
        DeviceSetting deviceSetting = new DeviceSetting()
            .value(DEFAULT_VALUE);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        deviceSetting.setDevice(device);
        // Add required entity
        AdditionalOption additionalOption;
        if (TestUtil.findAll(em, AdditionalOption.class).isEmpty()) {
            additionalOption = AdditionalOptionResourceIT.createEntity(em);
            em.persist(additionalOption);
            em.flush();
        } else {
            additionalOption = TestUtil.findAll(em, AdditionalOption.class).get(0);
        }
        deviceSetting.setAdditionalOption(additionalOption);
        return deviceSetting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceSetting createUpdatedEntity(EntityManager em) {
        DeviceSetting deviceSetting = new DeviceSetting()
            .value(UPDATED_VALUE);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createUpdatedEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        deviceSetting.setDevice(device);
        // Add required entity
        AdditionalOption additionalOption;
        if (TestUtil.findAll(em, AdditionalOption.class).isEmpty()) {
            additionalOption = AdditionalOptionResourceIT.createUpdatedEntity(em);
            em.persist(additionalOption);
            em.flush();
        } else {
            additionalOption = TestUtil.findAll(em, AdditionalOption.class).get(0);
        }
        deviceSetting.setAdditionalOption(additionalOption);
        return deviceSetting;
    }

    @BeforeEach
    public void initTest() {
        deviceSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceSetting() throws Exception {
        int databaseSizeBeforeCreate = deviceSettingRepository.findAll().size();

        // Create the DeviceSetting
        DeviceSettingDTO deviceSettingDTO = deviceSettingMapper.toDto(deviceSetting);
        restDeviceSettingMockMvc.perform(post("/api/device-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the DeviceSetting in the database
        List<DeviceSetting> deviceSettingList = deviceSettingRepository.findAll();
        assertThat(deviceSettingList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceSetting testDeviceSetting = deviceSettingList.get(deviceSettingList.size() - 1);
        assertThat(testDeviceSetting.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createDeviceSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceSettingRepository.findAll().size();

        // Create the DeviceSetting with an existing ID
        deviceSetting.setId(1L);
        DeviceSettingDTO deviceSettingDTO = deviceSettingMapper.toDto(deviceSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceSettingMockMvc.perform(post("/api/device-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceSetting in the database
        List<DeviceSetting> deviceSettingList = deviceSettingRepository.findAll();
        assertThat(deviceSettingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeviceSettings() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList
        restDeviceSettingMockMvc.perform(get("/api/device-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getDeviceSetting() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get the deviceSetting
        restDeviceSettingMockMvc.perform(get("/api/device-settings/{id}", deviceSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceSetting.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getDeviceSettingsByIdFiltering() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        Long id = deviceSetting.getId();

        defaultDeviceSettingShouldBeFound("id.equals=" + id);
        defaultDeviceSettingShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceSettingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDeviceSettingsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList where value equals to DEFAULT_VALUE
        defaultDeviceSettingShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the deviceSettingList where value equals to UPDATED_VALUE
        defaultDeviceSettingShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllDeviceSettingsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList where value not equals to DEFAULT_VALUE
        defaultDeviceSettingShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the deviceSettingList where value not equals to UPDATED_VALUE
        defaultDeviceSettingShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllDeviceSettingsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultDeviceSettingShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the deviceSettingList where value equals to UPDATED_VALUE
        defaultDeviceSettingShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllDeviceSettingsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList where value is not null
        defaultDeviceSettingShouldBeFound("value.specified=true");

        // Get all the deviceSettingList where value is null
        defaultDeviceSettingShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeviceSettingsByValueContainsSomething() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList where value contains DEFAULT_VALUE
        defaultDeviceSettingShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the deviceSettingList where value contains UPDATED_VALUE
        defaultDeviceSettingShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllDeviceSettingsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        // Get all the deviceSettingList where value does not contain DEFAULT_VALUE
        defaultDeviceSettingShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the deviceSettingList where value does not contain UPDATED_VALUE
        defaultDeviceSettingShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllDeviceSettingsByDeviceIsEqualToSomething() throws Exception {
        // Get already existing entity
        Device device = deviceSetting.getDevice();
        deviceSettingRepository.saveAndFlush(deviceSetting);
        Long deviceId = device.getId();

        // Get all the deviceSettingList where device equals to deviceId
        defaultDeviceSettingShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the deviceSettingList where device equals to deviceId + 1
        defaultDeviceSettingShouldNotBeFound("deviceId.equals=" + (deviceId + 1));
    }


    @Test
    @Transactional
    public void getAllDeviceSettingsByAdditionalOptionIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdditionalOption additionalOption = deviceSetting.getAdditionalOption();
        deviceSettingRepository.saveAndFlush(deviceSetting);
        Long additionalOptionId = additionalOption.getId();

        // Get all the deviceSettingList where additionalOption equals to additionalOptionId
        defaultDeviceSettingShouldBeFound("additionalOptionId.equals=" + additionalOptionId);

        // Get all the deviceSettingList where additionalOption equals to additionalOptionId + 1
        defaultDeviceSettingShouldNotBeFound("additionalOptionId.equals=" + (additionalOptionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceSettingShouldBeFound(String filter) throws Exception {
        restDeviceSettingMockMvc.perform(get("/api/device-settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restDeviceSettingMockMvc.perform(get("/api/device-settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceSettingShouldNotBeFound(String filter) throws Exception {
        restDeviceSettingMockMvc.perform(get("/api/device-settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceSettingMockMvc.perform(get("/api/device-settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDeviceSetting() throws Exception {
        // Get the deviceSetting
        restDeviceSettingMockMvc.perform(get("/api/device-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceSetting() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        int databaseSizeBeforeUpdate = deviceSettingRepository.findAll().size();

        // Update the deviceSetting
        DeviceSetting updatedDeviceSetting = deviceSettingRepository.findById(deviceSetting.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceSetting are not directly saved in db
        em.detach(updatedDeviceSetting);
        updatedDeviceSetting
            .value(UPDATED_VALUE);
        DeviceSettingDTO deviceSettingDTO = deviceSettingMapper.toDto(updatedDeviceSetting);

        restDeviceSettingMockMvc.perform(put("/api/device-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceSettingDTO)))
            .andExpect(status().isOk());

        // Validate the DeviceSetting in the database
        List<DeviceSetting> deviceSettingList = deviceSettingRepository.findAll();
        assertThat(deviceSettingList).hasSize(databaseSizeBeforeUpdate);
        DeviceSetting testDeviceSetting = deviceSettingList.get(deviceSettingList.size() - 1);
        assertThat(testDeviceSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceSetting() throws Exception {
        int databaseSizeBeforeUpdate = deviceSettingRepository.findAll().size();

        // Create the DeviceSetting
        DeviceSettingDTO deviceSettingDTO = deviceSettingMapper.toDto(deviceSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceSettingMockMvc.perform(put("/api/device-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceSetting in the database
        List<DeviceSetting> deviceSettingList = deviceSettingRepository.findAll();
        assertThat(deviceSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceSetting() throws Exception {
        // Initialize the database
        deviceSettingRepository.saveAndFlush(deviceSetting);

        int databaseSizeBeforeDelete = deviceSettingRepository.findAll().size();

        // Delete the deviceSetting
        restDeviceSettingMockMvc.perform(delete("/api/device-settings/{id}", deviceSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceSetting> deviceSettingList = deviceSettingRepository.findAll();
        assertThat(deviceSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
