package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.Device;
import com.enotkenny.voipadmin.domain.SipAccount;
import com.enotkenny.voipadmin.domain.DeviceSetting;
import com.enotkenny.voipadmin.domain.DeviceModel;
import com.enotkenny.voipadmin.domain.ResponsiblePerson;
import com.enotkenny.voipadmin.repository.DeviceRepository;
import com.enotkenny.voipadmin.service.DeviceService;
import com.enotkenny.voipadmin.service.dto.DeviceDTO;
import com.enotkenny.voipadmin.service.mapper.DeviceMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.DeviceCriteria;
import com.enotkenny.voipadmin.service.DeviceQueryService;

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

import com.enotkenny.voipadmin.domain.enumeration.ProvProtocol;
/**
 * Integration tests for the {@link DeviceResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class DeviceResourceIT {

    private static final String DEFAULT_MAC = "AAAAAAAAAA";
    private static final String UPDATED_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_INVENTORY = "AAAAAAAAAA";
    private static final String UPDATED_INVENTORY = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_HOSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_ACCESS_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_WEB_ACCESS_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_ACCESS_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_WEB_ACCESS_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DHCP_ENABLED = false;
    private static final Boolean UPDATED_DHCP_ENABLED = true;

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SUBNET_MASK = "AAAAAAAAAA";
    private static final String UPDATED_SUBNET_MASK = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_GATEWAY = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_GATEWAY = "BBBBBBBBBB";

    private static final String DEFAULT_DNS_1 = "AAAAAAAAAA";
    private static final String UPDATED_DNS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DNS_2 = "AAAAAAAAAA";
    private static final String UPDATED_DNS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PROV_URL = "AAAAAAAAAA";
    private static final String UPDATED_PROV_URL = "BBBBBBBBBB";

    private static final ProvProtocol DEFAULT_PROV_PROTOCOL = ProvProtocol.FTP;
    private static final ProvProtocol UPDATED_PROV_PROTOCOL = ProvProtocol.TFTP;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceQueryService deviceQueryService;

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

    private MockMvc restDeviceMockMvc;

    private Device device;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceResource deviceResource = new DeviceResource(deviceService, deviceQueryService);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource)
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
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
            .mac(DEFAULT_MAC)
            .inventory(DEFAULT_INVENTORY)
            .location(DEFAULT_LOCATION)
            .hostname(DEFAULT_HOSTNAME)
            .webAccessLogin(DEFAULT_WEB_ACCESS_LOGIN)
            .webAccessPassword(DEFAULT_WEB_ACCESS_PASSWORD)
            .dhcpEnabled(DEFAULT_DHCP_ENABLED)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .subnetMask(DEFAULT_SUBNET_MASK)
            .defaultGateway(DEFAULT_DEFAULT_GATEWAY)
            .dns1(DEFAULT_DNS_1)
            .dns2(DEFAULT_DNS_2)
            .provUrl(DEFAULT_PROV_URL)
            .provProtocol(DEFAULT_PROV_PROTOCOL);
        // Add required entity
        DeviceModel deviceModel;
        if (TestUtil.findAll(em, DeviceModel.class).isEmpty()) {
            deviceModel = DeviceModelResourceIT.createEntity(em);
            em.persist(deviceModel);
            em.flush();
        } else {
            deviceModel = TestUtil.findAll(em, DeviceModel.class).get(0);
        }
        device.setDeviceModel(deviceModel);
        return device;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createUpdatedEntity(EntityManager em) {
        Device device = new Device()
            .mac(UPDATED_MAC)
            .inventory(UPDATED_INVENTORY)
            .location(UPDATED_LOCATION)
            .hostname(UPDATED_HOSTNAME)
            .webAccessLogin(UPDATED_WEB_ACCESS_LOGIN)
            .webAccessPassword(UPDATED_WEB_ACCESS_PASSWORD)
            .dhcpEnabled(UPDATED_DHCP_ENABLED)
            .ipAddress(UPDATED_IP_ADDRESS)
            .subnetMask(UPDATED_SUBNET_MASK)
            .defaultGateway(UPDATED_DEFAULT_GATEWAY)
            .dns1(UPDATED_DNS_1)
            .dns2(UPDATED_DNS_2)
            .provUrl(UPDATED_PROV_URL)
            .provProtocol(UPDATED_PROV_PROTOCOL);
        // Add required entity
        DeviceModel deviceModel;
        if (TestUtil.findAll(em, DeviceModel.class).isEmpty()) {
            deviceModel = DeviceModelResourceIT.createUpdatedEntity(em);
            em.persist(deviceModel);
            em.flush();
        } else {
            deviceModel = TestUtil.findAll(em, DeviceModel.class).get(0);
        }
        device.setDeviceModel(deviceModel);
        return device;
    }

    @BeforeEach
    public void initTest() {
        device = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getMac()).isEqualTo(DEFAULT_MAC);
        assertThat(testDevice.getInventory()).isEqualTo(DEFAULT_INVENTORY);
        assertThat(testDevice.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testDevice.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testDevice.getWebAccessLogin()).isEqualTo(DEFAULT_WEB_ACCESS_LOGIN);
        assertThat(testDevice.getWebAccessPassword()).isEqualTo(DEFAULT_WEB_ACCESS_PASSWORD);
        assertThat(testDevice.isDhcpEnabled()).isEqualTo(DEFAULT_DHCP_ENABLED);
        assertThat(testDevice.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testDevice.getSubnetMask()).isEqualTo(DEFAULT_SUBNET_MASK);
        assertThat(testDevice.getDefaultGateway()).isEqualTo(DEFAULT_DEFAULT_GATEWAY);
        assertThat(testDevice.getDns1()).isEqualTo(DEFAULT_DNS_1);
        assertThat(testDevice.getDns2()).isEqualTo(DEFAULT_DNS_2);
        assertThat(testDevice.getProvUrl()).isEqualTo(DEFAULT_PROV_URL);
        assertThat(testDevice.getProvProtocol()).isEqualTo(DEFAULT_PROV_PROTOCOL);
    }

    @Test
    @Transactional
    public void createDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device with an existing ID
        device.setId(1L);
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMacIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setMac(null);

        // Create the Device, which fails.
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInventoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setInventory(null);

        // Create the Device, which fails.
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].mac").value(hasItem(DEFAULT_MAC)))
            .andExpect(jsonPath("$.[*].inventory").value(hasItem(DEFAULT_INVENTORY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].webAccessLogin").value(hasItem(DEFAULT_WEB_ACCESS_LOGIN)))
            .andExpect(jsonPath("$.[*].webAccessPassword").value(hasItem(DEFAULT_WEB_ACCESS_PASSWORD)))
            .andExpect(jsonPath("$.[*].dhcpEnabled").value(hasItem(DEFAULT_DHCP_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].subnetMask").value(hasItem(DEFAULT_SUBNET_MASK)))
            .andExpect(jsonPath("$.[*].defaultGateway").value(hasItem(DEFAULT_DEFAULT_GATEWAY)))
            .andExpect(jsonPath("$.[*].dns1").value(hasItem(DEFAULT_DNS_1)))
            .andExpect(jsonPath("$.[*].dns2").value(hasItem(DEFAULT_DNS_2)))
            .andExpect(jsonPath("$.[*].provUrl").value(hasItem(DEFAULT_PROV_URL)))
            .andExpect(jsonPath("$.[*].provProtocol").value(hasItem(DEFAULT_PROV_PROTOCOL.toString())));
    }
    
    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.mac").value(DEFAULT_MAC))
            .andExpect(jsonPath("$.inventory").value(DEFAULT_INVENTORY))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME))
            .andExpect(jsonPath("$.webAccessLogin").value(DEFAULT_WEB_ACCESS_LOGIN))
            .andExpect(jsonPath("$.webAccessPassword").value(DEFAULT_WEB_ACCESS_PASSWORD))
            .andExpect(jsonPath("$.dhcpEnabled").value(DEFAULT_DHCP_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS))
            .andExpect(jsonPath("$.subnetMask").value(DEFAULT_SUBNET_MASK))
            .andExpect(jsonPath("$.defaultGateway").value(DEFAULT_DEFAULT_GATEWAY))
            .andExpect(jsonPath("$.dns1").value(DEFAULT_DNS_1))
            .andExpect(jsonPath("$.dns2").value(DEFAULT_DNS_2))
            .andExpect(jsonPath("$.provUrl").value(DEFAULT_PROV_URL))
            .andExpect(jsonPath("$.provProtocol").value(DEFAULT_PROV_PROTOCOL.toString()));
    }


    @Test
    @Transactional
    public void getDevicesByIdFiltering() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        Long id = device.getId();

        defaultDeviceShouldBeFound("id.equals=" + id);
        defaultDeviceShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDevicesByMacIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where mac equals to DEFAULT_MAC
        defaultDeviceShouldBeFound("mac.equals=" + DEFAULT_MAC);

        // Get all the deviceList where mac equals to UPDATED_MAC
        defaultDeviceShouldNotBeFound("mac.equals=" + UPDATED_MAC);
    }

    @Test
    @Transactional
    public void getAllDevicesByMacIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where mac not equals to DEFAULT_MAC
        defaultDeviceShouldNotBeFound("mac.notEquals=" + DEFAULT_MAC);

        // Get all the deviceList where mac not equals to UPDATED_MAC
        defaultDeviceShouldBeFound("mac.notEquals=" + UPDATED_MAC);
    }

    @Test
    @Transactional
    public void getAllDevicesByMacIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where mac in DEFAULT_MAC or UPDATED_MAC
        defaultDeviceShouldBeFound("mac.in=" + DEFAULT_MAC + "," + UPDATED_MAC);

        // Get all the deviceList where mac equals to UPDATED_MAC
        defaultDeviceShouldNotBeFound("mac.in=" + UPDATED_MAC);
    }

    @Test
    @Transactional
    public void getAllDevicesByMacIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where mac is not null
        defaultDeviceShouldBeFound("mac.specified=true");

        // Get all the deviceList where mac is null
        defaultDeviceShouldNotBeFound("mac.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByMacContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where mac contains DEFAULT_MAC
        defaultDeviceShouldBeFound("mac.contains=" + DEFAULT_MAC);

        // Get all the deviceList where mac contains UPDATED_MAC
        defaultDeviceShouldNotBeFound("mac.contains=" + UPDATED_MAC);
    }

    @Test
    @Transactional
    public void getAllDevicesByMacNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where mac does not contain DEFAULT_MAC
        defaultDeviceShouldNotBeFound("mac.doesNotContain=" + DEFAULT_MAC);

        // Get all the deviceList where mac does not contain UPDATED_MAC
        defaultDeviceShouldBeFound("mac.doesNotContain=" + UPDATED_MAC);
    }


    @Test
    @Transactional
    public void getAllDevicesByInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where inventory equals to DEFAULT_INVENTORY
        defaultDeviceShouldBeFound("inventory.equals=" + DEFAULT_INVENTORY);

        // Get all the deviceList where inventory equals to UPDATED_INVENTORY
        defaultDeviceShouldNotBeFound("inventory.equals=" + UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllDevicesByInventoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where inventory not equals to DEFAULT_INVENTORY
        defaultDeviceShouldNotBeFound("inventory.notEquals=" + DEFAULT_INVENTORY);

        // Get all the deviceList where inventory not equals to UPDATED_INVENTORY
        defaultDeviceShouldBeFound("inventory.notEquals=" + UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllDevicesByInventoryIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where inventory in DEFAULT_INVENTORY or UPDATED_INVENTORY
        defaultDeviceShouldBeFound("inventory.in=" + DEFAULT_INVENTORY + "," + UPDATED_INVENTORY);

        // Get all the deviceList where inventory equals to UPDATED_INVENTORY
        defaultDeviceShouldNotBeFound("inventory.in=" + UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllDevicesByInventoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where inventory is not null
        defaultDeviceShouldBeFound("inventory.specified=true");

        // Get all the deviceList where inventory is null
        defaultDeviceShouldNotBeFound("inventory.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByInventoryContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where inventory contains DEFAULT_INVENTORY
        defaultDeviceShouldBeFound("inventory.contains=" + DEFAULT_INVENTORY);

        // Get all the deviceList where inventory contains UPDATED_INVENTORY
        defaultDeviceShouldNotBeFound("inventory.contains=" + UPDATED_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllDevicesByInventoryNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where inventory does not contain DEFAULT_INVENTORY
        defaultDeviceShouldNotBeFound("inventory.doesNotContain=" + DEFAULT_INVENTORY);

        // Get all the deviceList where inventory does not contain UPDATED_INVENTORY
        defaultDeviceShouldBeFound("inventory.doesNotContain=" + UPDATED_INVENTORY);
    }


    @Test
    @Transactional
    public void getAllDevicesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where location equals to DEFAULT_LOCATION
        defaultDeviceShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the deviceList where location equals to UPDATED_LOCATION
        defaultDeviceShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllDevicesByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where location not equals to DEFAULT_LOCATION
        defaultDeviceShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the deviceList where location not equals to UPDATED_LOCATION
        defaultDeviceShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllDevicesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultDeviceShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the deviceList where location equals to UPDATED_LOCATION
        defaultDeviceShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllDevicesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where location is not null
        defaultDeviceShouldBeFound("location.specified=true");

        // Get all the deviceList where location is null
        defaultDeviceShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByLocationContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where location contains DEFAULT_LOCATION
        defaultDeviceShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the deviceList where location contains UPDATED_LOCATION
        defaultDeviceShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllDevicesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where location does not contain DEFAULT_LOCATION
        defaultDeviceShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the deviceList where location does not contain UPDATED_LOCATION
        defaultDeviceShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllDevicesByHostnameIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where hostname equals to DEFAULT_HOSTNAME
        defaultDeviceShouldBeFound("hostname.equals=" + DEFAULT_HOSTNAME);

        // Get all the deviceList where hostname equals to UPDATED_HOSTNAME
        defaultDeviceShouldNotBeFound("hostname.equals=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    public void getAllDevicesByHostnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where hostname not equals to DEFAULT_HOSTNAME
        defaultDeviceShouldNotBeFound("hostname.notEquals=" + DEFAULT_HOSTNAME);

        // Get all the deviceList where hostname not equals to UPDATED_HOSTNAME
        defaultDeviceShouldBeFound("hostname.notEquals=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    public void getAllDevicesByHostnameIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where hostname in DEFAULT_HOSTNAME or UPDATED_HOSTNAME
        defaultDeviceShouldBeFound("hostname.in=" + DEFAULT_HOSTNAME + "," + UPDATED_HOSTNAME);

        // Get all the deviceList where hostname equals to UPDATED_HOSTNAME
        defaultDeviceShouldNotBeFound("hostname.in=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    public void getAllDevicesByHostnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where hostname is not null
        defaultDeviceShouldBeFound("hostname.specified=true");

        // Get all the deviceList where hostname is null
        defaultDeviceShouldNotBeFound("hostname.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByHostnameContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where hostname contains DEFAULT_HOSTNAME
        defaultDeviceShouldBeFound("hostname.contains=" + DEFAULT_HOSTNAME);

        // Get all the deviceList where hostname contains UPDATED_HOSTNAME
        defaultDeviceShouldNotBeFound("hostname.contains=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    public void getAllDevicesByHostnameNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where hostname does not contain DEFAULT_HOSTNAME
        defaultDeviceShouldNotBeFound("hostname.doesNotContain=" + DEFAULT_HOSTNAME);

        // Get all the deviceList where hostname does not contain UPDATED_HOSTNAME
        defaultDeviceShouldBeFound("hostname.doesNotContain=" + UPDATED_HOSTNAME);
    }


    @Test
    @Transactional
    public void getAllDevicesByWebAccessLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessLogin equals to DEFAULT_WEB_ACCESS_LOGIN
        defaultDeviceShouldBeFound("webAccessLogin.equals=" + DEFAULT_WEB_ACCESS_LOGIN);

        // Get all the deviceList where webAccessLogin equals to UPDATED_WEB_ACCESS_LOGIN
        defaultDeviceShouldNotBeFound("webAccessLogin.equals=" + UPDATED_WEB_ACCESS_LOGIN);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessLogin not equals to DEFAULT_WEB_ACCESS_LOGIN
        defaultDeviceShouldNotBeFound("webAccessLogin.notEquals=" + DEFAULT_WEB_ACCESS_LOGIN);

        // Get all the deviceList where webAccessLogin not equals to UPDATED_WEB_ACCESS_LOGIN
        defaultDeviceShouldBeFound("webAccessLogin.notEquals=" + UPDATED_WEB_ACCESS_LOGIN);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessLoginIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessLogin in DEFAULT_WEB_ACCESS_LOGIN or UPDATED_WEB_ACCESS_LOGIN
        defaultDeviceShouldBeFound("webAccessLogin.in=" + DEFAULT_WEB_ACCESS_LOGIN + "," + UPDATED_WEB_ACCESS_LOGIN);

        // Get all the deviceList where webAccessLogin equals to UPDATED_WEB_ACCESS_LOGIN
        defaultDeviceShouldNotBeFound("webAccessLogin.in=" + UPDATED_WEB_ACCESS_LOGIN);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessLogin is not null
        defaultDeviceShouldBeFound("webAccessLogin.specified=true");

        // Get all the deviceList where webAccessLogin is null
        defaultDeviceShouldNotBeFound("webAccessLogin.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByWebAccessLoginContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessLogin contains DEFAULT_WEB_ACCESS_LOGIN
        defaultDeviceShouldBeFound("webAccessLogin.contains=" + DEFAULT_WEB_ACCESS_LOGIN);

        // Get all the deviceList where webAccessLogin contains UPDATED_WEB_ACCESS_LOGIN
        defaultDeviceShouldNotBeFound("webAccessLogin.contains=" + UPDATED_WEB_ACCESS_LOGIN);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessLoginNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessLogin does not contain DEFAULT_WEB_ACCESS_LOGIN
        defaultDeviceShouldNotBeFound("webAccessLogin.doesNotContain=" + DEFAULT_WEB_ACCESS_LOGIN);

        // Get all the deviceList where webAccessLogin does not contain UPDATED_WEB_ACCESS_LOGIN
        defaultDeviceShouldBeFound("webAccessLogin.doesNotContain=" + UPDATED_WEB_ACCESS_LOGIN);
    }


    @Test
    @Transactional
    public void getAllDevicesByWebAccessPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessPassword equals to DEFAULT_WEB_ACCESS_PASSWORD
        defaultDeviceShouldBeFound("webAccessPassword.equals=" + DEFAULT_WEB_ACCESS_PASSWORD);

        // Get all the deviceList where webAccessPassword equals to UPDATED_WEB_ACCESS_PASSWORD
        defaultDeviceShouldNotBeFound("webAccessPassword.equals=" + UPDATED_WEB_ACCESS_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessPassword not equals to DEFAULT_WEB_ACCESS_PASSWORD
        defaultDeviceShouldNotBeFound("webAccessPassword.notEquals=" + DEFAULT_WEB_ACCESS_PASSWORD);

        // Get all the deviceList where webAccessPassword not equals to UPDATED_WEB_ACCESS_PASSWORD
        defaultDeviceShouldBeFound("webAccessPassword.notEquals=" + UPDATED_WEB_ACCESS_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessPassword in DEFAULT_WEB_ACCESS_PASSWORD or UPDATED_WEB_ACCESS_PASSWORD
        defaultDeviceShouldBeFound("webAccessPassword.in=" + DEFAULT_WEB_ACCESS_PASSWORD + "," + UPDATED_WEB_ACCESS_PASSWORD);

        // Get all the deviceList where webAccessPassword equals to UPDATED_WEB_ACCESS_PASSWORD
        defaultDeviceShouldNotBeFound("webAccessPassword.in=" + UPDATED_WEB_ACCESS_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessPassword is not null
        defaultDeviceShouldBeFound("webAccessPassword.specified=true");

        // Get all the deviceList where webAccessPassword is null
        defaultDeviceShouldNotBeFound("webAccessPassword.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByWebAccessPasswordContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessPassword contains DEFAULT_WEB_ACCESS_PASSWORD
        defaultDeviceShouldBeFound("webAccessPassword.contains=" + DEFAULT_WEB_ACCESS_PASSWORD);

        // Get all the deviceList where webAccessPassword contains UPDATED_WEB_ACCESS_PASSWORD
        defaultDeviceShouldNotBeFound("webAccessPassword.contains=" + UPDATED_WEB_ACCESS_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllDevicesByWebAccessPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where webAccessPassword does not contain DEFAULT_WEB_ACCESS_PASSWORD
        defaultDeviceShouldNotBeFound("webAccessPassword.doesNotContain=" + DEFAULT_WEB_ACCESS_PASSWORD);

        // Get all the deviceList where webAccessPassword does not contain UPDATED_WEB_ACCESS_PASSWORD
        defaultDeviceShouldBeFound("webAccessPassword.doesNotContain=" + UPDATED_WEB_ACCESS_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllDevicesByDhcpEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dhcpEnabled equals to DEFAULT_DHCP_ENABLED
        defaultDeviceShouldBeFound("dhcpEnabled.equals=" + DEFAULT_DHCP_ENABLED);

        // Get all the deviceList where dhcpEnabled equals to UPDATED_DHCP_ENABLED
        defaultDeviceShouldNotBeFound("dhcpEnabled.equals=" + UPDATED_DHCP_ENABLED);
    }

    @Test
    @Transactional
    public void getAllDevicesByDhcpEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dhcpEnabled not equals to DEFAULT_DHCP_ENABLED
        defaultDeviceShouldNotBeFound("dhcpEnabled.notEquals=" + DEFAULT_DHCP_ENABLED);

        // Get all the deviceList where dhcpEnabled not equals to UPDATED_DHCP_ENABLED
        defaultDeviceShouldBeFound("dhcpEnabled.notEquals=" + UPDATED_DHCP_ENABLED);
    }

    @Test
    @Transactional
    public void getAllDevicesByDhcpEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dhcpEnabled in DEFAULT_DHCP_ENABLED or UPDATED_DHCP_ENABLED
        defaultDeviceShouldBeFound("dhcpEnabled.in=" + DEFAULT_DHCP_ENABLED + "," + UPDATED_DHCP_ENABLED);

        // Get all the deviceList where dhcpEnabled equals to UPDATED_DHCP_ENABLED
        defaultDeviceShouldNotBeFound("dhcpEnabled.in=" + UPDATED_DHCP_ENABLED);
    }

    @Test
    @Transactional
    public void getAllDevicesByDhcpEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dhcpEnabled is not null
        defaultDeviceShouldBeFound("dhcpEnabled.specified=true");

        // Get all the deviceList where dhcpEnabled is null
        defaultDeviceShouldNotBeFound("dhcpEnabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevicesByIpAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where ipAddress equals to DEFAULT_IP_ADDRESS
        defaultDeviceShouldBeFound("ipAddress.equals=" + DEFAULT_IP_ADDRESS);

        // Get all the deviceList where ipAddress equals to UPDATED_IP_ADDRESS
        defaultDeviceShouldNotBeFound("ipAddress.equals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDevicesByIpAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where ipAddress not equals to DEFAULT_IP_ADDRESS
        defaultDeviceShouldNotBeFound("ipAddress.notEquals=" + DEFAULT_IP_ADDRESS);

        // Get all the deviceList where ipAddress not equals to UPDATED_IP_ADDRESS
        defaultDeviceShouldBeFound("ipAddress.notEquals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDevicesByIpAddressIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where ipAddress in DEFAULT_IP_ADDRESS or UPDATED_IP_ADDRESS
        defaultDeviceShouldBeFound("ipAddress.in=" + DEFAULT_IP_ADDRESS + "," + UPDATED_IP_ADDRESS);

        // Get all the deviceList where ipAddress equals to UPDATED_IP_ADDRESS
        defaultDeviceShouldNotBeFound("ipAddress.in=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDevicesByIpAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where ipAddress is not null
        defaultDeviceShouldBeFound("ipAddress.specified=true");

        // Get all the deviceList where ipAddress is null
        defaultDeviceShouldNotBeFound("ipAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByIpAddressContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where ipAddress contains DEFAULT_IP_ADDRESS
        defaultDeviceShouldBeFound("ipAddress.contains=" + DEFAULT_IP_ADDRESS);

        // Get all the deviceList where ipAddress contains UPDATED_IP_ADDRESS
        defaultDeviceShouldNotBeFound("ipAddress.contains=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllDevicesByIpAddressNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where ipAddress does not contain DEFAULT_IP_ADDRESS
        defaultDeviceShouldNotBeFound("ipAddress.doesNotContain=" + DEFAULT_IP_ADDRESS);

        // Get all the deviceList where ipAddress does not contain UPDATED_IP_ADDRESS
        defaultDeviceShouldBeFound("ipAddress.doesNotContain=" + UPDATED_IP_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllDevicesBySubnetMaskIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where subnetMask equals to DEFAULT_SUBNET_MASK
        defaultDeviceShouldBeFound("subnetMask.equals=" + DEFAULT_SUBNET_MASK);

        // Get all the deviceList where subnetMask equals to UPDATED_SUBNET_MASK
        defaultDeviceShouldNotBeFound("subnetMask.equals=" + UPDATED_SUBNET_MASK);
    }

    @Test
    @Transactional
    public void getAllDevicesBySubnetMaskIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where subnetMask not equals to DEFAULT_SUBNET_MASK
        defaultDeviceShouldNotBeFound("subnetMask.notEquals=" + DEFAULT_SUBNET_MASK);

        // Get all the deviceList where subnetMask not equals to UPDATED_SUBNET_MASK
        defaultDeviceShouldBeFound("subnetMask.notEquals=" + UPDATED_SUBNET_MASK);
    }

    @Test
    @Transactional
    public void getAllDevicesBySubnetMaskIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where subnetMask in DEFAULT_SUBNET_MASK or UPDATED_SUBNET_MASK
        defaultDeviceShouldBeFound("subnetMask.in=" + DEFAULT_SUBNET_MASK + "," + UPDATED_SUBNET_MASK);

        // Get all the deviceList where subnetMask equals to UPDATED_SUBNET_MASK
        defaultDeviceShouldNotBeFound("subnetMask.in=" + UPDATED_SUBNET_MASK);
    }

    @Test
    @Transactional
    public void getAllDevicesBySubnetMaskIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where subnetMask is not null
        defaultDeviceShouldBeFound("subnetMask.specified=true");

        // Get all the deviceList where subnetMask is null
        defaultDeviceShouldNotBeFound("subnetMask.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesBySubnetMaskContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where subnetMask contains DEFAULT_SUBNET_MASK
        defaultDeviceShouldBeFound("subnetMask.contains=" + DEFAULT_SUBNET_MASK);

        // Get all the deviceList where subnetMask contains UPDATED_SUBNET_MASK
        defaultDeviceShouldNotBeFound("subnetMask.contains=" + UPDATED_SUBNET_MASK);
    }

    @Test
    @Transactional
    public void getAllDevicesBySubnetMaskNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where subnetMask does not contain DEFAULT_SUBNET_MASK
        defaultDeviceShouldNotBeFound("subnetMask.doesNotContain=" + DEFAULT_SUBNET_MASK);

        // Get all the deviceList where subnetMask does not contain UPDATED_SUBNET_MASK
        defaultDeviceShouldBeFound("subnetMask.doesNotContain=" + UPDATED_SUBNET_MASK);
    }


    @Test
    @Transactional
    public void getAllDevicesByDefaultGatewayIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where defaultGateway equals to DEFAULT_DEFAULT_GATEWAY
        defaultDeviceShouldBeFound("defaultGateway.equals=" + DEFAULT_DEFAULT_GATEWAY);

        // Get all the deviceList where defaultGateway equals to UPDATED_DEFAULT_GATEWAY
        defaultDeviceShouldNotBeFound("defaultGateway.equals=" + UPDATED_DEFAULT_GATEWAY);
    }

    @Test
    @Transactional
    public void getAllDevicesByDefaultGatewayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where defaultGateway not equals to DEFAULT_DEFAULT_GATEWAY
        defaultDeviceShouldNotBeFound("defaultGateway.notEquals=" + DEFAULT_DEFAULT_GATEWAY);

        // Get all the deviceList where defaultGateway not equals to UPDATED_DEFAULT_GATEWAY
        defaultDeviceShouldBeFound("defaultGateway.notEquals=" + UPDATED_DEFAULT_GATEWAY);
    }

    @Test
    @Transactional
    public void getAllDevicesByDefaultGatewayIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where defaultGateway in DEFAULT_DEFAULT_GATEWAY or UPDATED_DEFAULT_GATEWAY
        defaultDeviceShouldBeFound("defaultGateway.in=" + DEFAULT_DEFAULT_GATEWAY + "," + UPDATED_DEFAULT_GATEWAY);

        // Get all the deviceList where defaultGateway equals to UPDATED_DEFAULT_GATEWAY
        defaultDeviceShouldNotBeFound("defaultGateway.in=" + UPDATED_DEFAULT_GATEWAY);
    }

    @Test
    @Transactional
    public void getAllDevicesByDefaultGatewayIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where defaultGateway is not null
        defaultDeviceShouldBeFound("defaultGateway.specified=true");

        // Get all the deviceList where defaultGateway is null
        defaultDeviceShouldNotBeFound("defaultGateway.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByDefaultGatewayContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where defaultGateway contains DEFAULT_DEFAULT_GATEWAY
        defaultDeviceShouldBeFound("defaultGateway.contains=" + DEFAULT_DEFAULT_GATEWAY);

        // Get all the deviceList where defaultGateway contains UPDATED_DEFAULT_GATEWAY
        defaultDeviceShouldNotBeFound("defaultGateway.contains=" + UPDATED_DEFAULT_GATEWAY);
    }

    @Test
    @Transactional
    public void getAllDevicesByDefaultGatewayNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where defaultGateway does not contain DEFAULT_DEFAULT_GATEWAY
        defaultDeviceShouldNotBeFound("defaultGateway.doesNotContain=" + DEFAULT_DEFAULT_GATEWAY);

        // Get all the deviceList where defaultGateway does not contain UPDATED_DEFAULT_GATEWAY
        defaultDeviceShouldBeFound("defaultGateway.doesNotContain=" + UPDATED_DEFAULT_GATEWAY);
    }


    @Test
    @Transactional
    public void getAllDevicesByDns1IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns1 equals to DEFAULT_DNS_1
        defaultDeviceShouldBeFound("dns1.equals=" + DEFAULT_DNS_1);

        // Get all the deviceList where dns1 equals to UPDATED_DNS_1
        defaultDeviceShouldNotBeFound("dns1.equals=" + UPDATED_DNS_1);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns1 not equals to DEFAULT_DNS_1
        defaultDeviceShouldNotBeFound("dns1.notEquals=" + DEFAULT_DNS_1);

        // Get all the deviceList where dns1 not equals to UPDATED_DNS_1
        defaultDeviceShouldBeFound("dns1.notEquals=" + UPDATED_DNS_1);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns1IsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns1 in DEFAULT_DNS_1 or UPDATED_DNS_1
        defaultDeviceShouldBeFound("dns1.in=" + DEFAULT_DNS_1 + "," + UPDATED_DNS_1);

        // Get all the deviceList where dns1 equals to UPDATED_DNS_1
        defaultDeviceShouldNotBeFound("dns1.in=" + UPDATED_DNS_1);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns1IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns1 is not null
        defaultDeviceShouldBeFound("dns1.specified=true");

        // Get all the deviceList where dns1 is null
        defaultDeviceShouldNotBeFound("dns1.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByDns1ContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns1 contains DEFAULT_DNS_1
        defaultDeviceShouldBeFound("dns1.contains=" + DEFAULT_DNS_1);

        // Get all the deviceList where dns1 contains UPDATED_DNS_1
        defaultDeviceShouldNotBeFound("dns1.contains=" + UPDATED_DNS_1);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns1NotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns1 does not contain DEFAULT_DNS_1
        defaultDeviceShouldNotBeFound("dns1.doesNotContain=" + DEFAULT_DNS_1);

        // Get all the deviceList where dns1 does not contain UPDATED_DNS_1
        defaultDeviceShouldBeFound("dns1.doesNotContain=" + UPDATED_DNS_1);
    }


    @Test
    @Transactional
    public void getAllDevicesByDns2IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns2 equals to DEFAULT_DNS_2
        defaultDeviceShouldBeFound("dns2.equals=" + DEFAULT_DNS_2);

        // Get all the deviceList where dns2 equals to UPDATED_DNS_2
        defaultDeviceShouldNotBeFound("dns2.equals=" + UPDATED_DNS_2);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns2 not equals to DEFAULT_DNS_2
        defaultDeviceShouldNotBeFound("dns2.notEquals=" + DEFAULT_DNS_2);

        // Get all the deviceList where dns2 not equals to UPDATED_DNS_2
        defaultDeviceShouldBeFound("dns2.notEquals=" + UPDATED_DNS_2);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns2IsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns2 in DEFAULT_DNS_2 or UPDATED_DNS_2
        defaultDeviceShouldBeFound("dns2.in=" + DEFAULT_DNS_2 + "," + UPDATED_DNS_2);

        // Get all the deviceList where dns2 equals to UPDATED_DNS_2
        defaultDeviceShouldNotBeFound("dns2.in=" + UPDATED_DNS_2);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns2IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns2 is not null
        defaultDeviceShouldBeFound("dns2.specified=true");

        // Get all the deviceList where dns2 is null
        defaultDeviceShouldNotBeFound("dns2.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByDns2ContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns2 contains DEFAULT_DNS_2
        defaultDeviceShouldBeFound("dns2.contains=" + DEFAULT_DNS_2);

        // Get all the deviceList where dns2 contains UPDATED_DNS_2
        defaultDeviceShouldNotBeFound("dns2.contains=" + UPDATED_DNS_2);
    }

    @Test
    @Transactional
    public void getAllDevicesByDns2NotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where dns2 does not contain DEFAULT_DNS_2
        defaultDeviceShouldNotBeFound("dns2.doesNotContain=" + DEFAULT_DNS_2);

        // Get all the deviceList where dns2 does not contain UPDATED_DNS_2
        defaultDeviceShouldBeFound("dns2.doesNotContain=" + UPDATED_DNS_2);
    }


    @Test
    @Transactional
    public void getAllDevicesByProvUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provUrl equals to DEFAULT_PROV_URL
        defaultDeviceShouldBeFound("provUrl.equals=" + DEFAULT_PROV_URL);

        // Get all the deviceList where provUrl equals to UPDATED_PROV_URL
        defaultDeviceShouldNotBeFound("provUrl.equals=" + UPDATED_PROV_URL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provUrl not equals to DEFAULT_PROV_URL
        defaultDeviceShouldNotBeFound("provUrl.notEquals=" + DEFAULT_PROV_URL);

        // Get all the deviceList where provUrl not equals to UPDATED_PROV_URL
        defaultDeviceShouldBeFound("provUrl.notEquals=" + UPDATED_PROV_URL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvUrlIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provUrl in DEFAULT_PROV_URL or UPDATED_PROV_URL
        defaultDeviceShouldBeFound("provUrl.in=" + DEFAULT_PROV_URL + "," + UPDATED_PROV_URL);

        // Get all the deviceList where provUrl equals to UPDATED_PROV_URL
        defaultDeviceShouldNotBeFound("provUrl.in=" + UPDATED_PROV_URL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provUrl is not null
        defaultDeviceShouldBeFound("provUrl.specified=true");

        // Get all the deviceList where provUrl is null
        defaultDeviceShouldNotBeFound("provUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllDevicesByProvUrlContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provUrl contains DEFAULT_PROV_URL
        defaultDeviceShouldBeFound("provUrl.contains=" + DEFAULT_PROV_URL);

        // Get all the deviceList where provUrl contains UPDATED_PROV_URL
        defaultDeviceShouldNotBeFound("provUrl.contains=" + UPDATED_PROV_URL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvUrlNotContainsSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provUrl does not contain DEFAULT_PROV_URL
        defaultDeviceShouldNotBeFound("provUrl.doesNotContain=" + DEFAULT_PROV_URL);

        // Get all the deviceList where provUrl does not contain UPDATED_PROV_URL
        defaultDeviceShouldBeFound("provUrl.doesNotContain=" + UPDATED_PROV_URL);
    }


    @Test
    @Transactional
    public void getAllDevicesByProvProtocolIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provProtocol equals to DEFAULT_PROV_PROTOCOL
        defaultDeviceShouldBeFound("provProtocol.equals=" + DEFAULT_PROV_PROTOCOL);

        // Get all the deviceList where provProtocol equals to UPDATED_PROV_PROTOCOL
        defaultDeviceShouldNotBeFound("provProtocol.equals=" + UPDATED_PROV_PROTOCOL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvProtocolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provProtocol not equals to DEFAULT_PROV_PROTOCOL
        defaultDeviceShouldNotBeFound("provProtocol.notEquals=" + DEFAULT_PROV_PROTOCOL);

        // Get all the deviceList where provProtocol not equals to UPDATED_PROV_PROTOCOL
        defaultDeviceShouldBeFound("provProtocol.notEquals=" + UPDATED_PROV_PROTOCOL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvProtocolIsInShouldWork() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provProtocol in DEFAULT_PROV_PROTOCOL or UPDATED_PROV_PROTOCOL
        defaultDeviceShouldBeFound("provProtocol.in=" + DEFAULT_PROV_PROTOCOL + "," + UPDATED_PROV_PROTOCOL);

        // Get all the deviceList where provProtocol equals to UPDATED_PROV_PROTOCOL
        defaultDeviceShouldNotBeFound("provProtocol.in=" + UPDATED_PROV_PROTOCOL);
    }

    @Test
    @Transactional
    public void getAllDevicesByProvProtocolIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList where provProtocol is not null
        defaultDeviceShouldBeFound("provProtocol.specified=true");

        // Get all the deviceList where provProtocol is null
        defaultDeviceShouldNotBeFound("provProtocol.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevicesBySipAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        SipAccount sipAccount = SipAccountResourceIT.createEntity(em);
        em.persist(sipAccount);
        em.flush();
        device.addSipAccount(sipAccount);
        deviceRepository.saveAndFlush(device);
        Long sipAccountId = sipAccount.getId();

        // Get all the deviceList where sipAccount equals to sipAccountId
        defaultDeviceShouldBeFound("sipAccountId.equals=" + sipAccountId);

        // Get all the deviceList where sipAccount equals to sipAccountId + 1
        defaultDeviceShouldNotBeFound("sipAccountId.equals=" + (sipAccountId + 1));
    }


    @Test
    @Transactional
    public void getAllDevicesByDeviceSettingIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        DeviceSetting deviceSetting = DeviceSettingResourceIT.createEntity(em);
        em.persist(deviceSetting);
        em.flush();
        device.addDeviceSetting(deviceSetting);
        deviceRepository.saveAndFlush(device);
        Long deviceSettingId = deviceSetting.getId();

        // Get all the deviceList where deviceSetting equals to deviceSettingId
        defaultDeviceShouldBeFound("deviceSettingId.equals=" + deviceSettingId);

        // Get all the deviceList where deviceSetting equals to deviceSettingId + 1
        defaultDeviceShouldNotBeFound("deviceSettingId.equals=" + (deviceSettingId + 1));
    }


    @Test
    @Transactional
    public void getAllDevicesByDeviceModelIsEqualToSomething() throws Exception {
        // Get already existing entity
        DeviceModel deviceModel = device.getDeviceModel();
        deviceRepository.saveAndFlush(device);
        Long deviceModelId = deviceModel.getId();

        // Get all the deviceList where deviceModel equals to deviceModelId
        defaultDeviceShouldBeFound("deviceModelId.equals=" + deviceModelId);

        // Get all the deviceList where deviceModel equals to deviceModelId + 1
        defaultDeviceShouldNotBeFound("deviceModelId.equals=" + (deviceModelId + 1));
    }


    @Test
    @Transactional
    public void getAllDevicesByResponsiblePersonIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);
        ResponsiblePerson responsiblePerson = ResponsiblePersonResourceIT.createEntity(em);
        em.persist(responsiblePerson);
        em.flush();
        device.setResponsiblePerson(responsiblePerson);
        deviceRepository.saveAndFlush(device);
        Long responsiblePersonId = responsiblePerson.getId();

        // Get all the deviceList where responsiblePerson equals to responsiblePersonId
        defaultDeviceShouldBeFound("responsiblePersonId.equals=" + responsiblePersonId);

        // Get all the deviceList where responsiblePerson equals to responsiblePersonId + 1
        defaultDeviceShouldNotBeFound("responsiblePersonId.equals=" + (responsiblePersonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceShouldBeFound(String filter) throws Exception {
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].mac").value(hasItem(DEFAULT_MAC)))
            .andExpect(jsonPath("$.[*].inventory").value(hasItem(DEFAULT_INVENTORY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].webAccessLogin").value(hasItem(DEFAULT_WEB_ACCESS_LOGIN)))
            .andExpect(jsonPath("$.[*].webAccessPassword").value(hasItem(DEFAULT_WEB_ACCESS_PASSWORD)))
            .andExpect(jsonPath("$.[*].dhcpEnabled").value(hasItem(DEFAULT_DHCP_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].subnetMask").value(hasItem(DEFAULT_SUBNET_MASK)))
            .andExpect(jsonPath("$.[*].defaultGateway").value(hasItem(DEFAULT_DEFAULT_GATEWAY)))
            .andExpect(jsonPath("$.[*].dns1").value(hasItem(DEFAULT_DNS_1)))
            .andExpect(jsonPath("$.[*].dns2").value(hasItem(DEFAULT_DNS_2)))
            .andExpect(jsonPath("$.[*].provUrl").value(hasItem(DEFAULT_PROV_URL)))
            .andExpect(jsonPath("$.[*].provProtocol").value(hasItem(DEFAULT_PROV_PROTOCOL.toString())));

        // Check, that the count call also returns 1
        restDeviceMockMvc.perform(get("/api/devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceShouldNotBeFound(String filter) throws Exception {
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceMockMvc.perform(get("/api/devices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findById(device.getId()).get();
        // Disconnect from session so that the updates on updatedDevice are not directly saved in db
        em.detach(updatedDevice);
        updatedDevice
            .mac(UPDATED_MAC)
            .inventory(UPDATED_INVENTORY)
            .location(UPDATED_LOCATION)
            .hostname(UPDATED_HOSTNAME)
            .webAccessLogin(UPDATED_WEB_ACCESS_LOGIN)
            .webAccessPassword(UPDATED_WEB_ACCESS_PASSWORD)
            .dhcpEnabled(UPDATED_DHCP_ENABLED)
            .ipAddress(UPDATED_IP_ADDRESS)
            .subnetMask(UPDATED_SUBNET_MASK)
            .defaultGateway(UPDATED_DEFAULT_GATEWAY)
            .dns1(UPDATED_DNS_1)
            .dns2(UPDATED_DNS_2)
            .provUrl(UPDATED_PROV_URL)
            .provProtocol(UPDATED_PROV_PROTOCOL);
        DeviceDTO deviceDTO = deviceMapper.toDto(updatedDevice);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getMac()).isEqualTo(UPDATED_MAC);
        assertThat(testDevice.getInventory()).isEqualTo(UPDATED_INVENTORY);
        assertThat(testDevice.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testDevice.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testDevice.getWebAccessLogin()).isEqualTo(UPDATED_WEB_ACCESS_LOGIN);
        assertThat(testDevice.getWebAccessPassword()).isEqualTo(UPDATED_WEB_ACCESS_PASSWORD);
        assertThat(testDevice.isDhcpEnabled()).isEqualTo(UPDATED_DHCP_ENABLED);
        assertThat(testDevice.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testDevice.getSubnetMask()).isEqualTo(UPDATED_SUBNET_MASK);
        assertThat(testDevice.getDefaultGateway()).isEqualTo(UPDATED_DEFAULT_GATEWAY);
        assertThat(testDevice.getDns1()).isEqualTo(UPDATED_DNS_1);
        assertThat(testDevice.getDns2()).isEqualTo(UPDATED_DNS_2);
        assertThat(testDevice.getProvUrl()).isEqualTo(UPDATED_PROV_URL);
        assertThat(testDevice.getProvProtocol()).isEqualTo(UPDATED_PROV_PROTOCOL);
    }

    @Test
    @Transactional
    public void updateNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Create the Device
        DeviceDTO deviceDTO = deviceMapper.toDto(device);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Delete the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
