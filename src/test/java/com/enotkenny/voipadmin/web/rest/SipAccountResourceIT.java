package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.SipAccount;
import com.enotkenny.voipadmin.domain.PbxAccount;
import com.enotkenny.voipadmin.domain.Device;
import com.enotkenny.voipadmin.repository.SipAccountRepository;
import com.enotkenny.voipadmin.service.SipAccountService;
import com.enotkenny.voipadmin.service.dto.SipAccountDTO;
import com.enotkenny.voipadmin.service.mapper.SipAccountMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.SipAccountCriteria;
import com.enotkenny.voipadmin.service.SipAccountQueryService;

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
 * Integration tests for the {@link SipAccountResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class SipAccountResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LINE_ENABLED = false;
    private static final Boolean UPDATED_LINE_ENABLED = true;

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;
    private static final Integer SMALLER_LINE_NUMBER = 1 - 1;

    private static final Boolean DEFAULT_IS_MANUALLY_CREATED = false;
    private static final Boolean UPDATED_IS_MANUALLY_CREATED = true;

    @Autowired
    private SipAccountRepository sipAccountRepository;

    @Autowired
    private SipAccountMapper sipAccountMapper;

    @Autowired
    private SipAccountService sipAccountService;

    @Autowired
    private SipAccountQueryService sipAccountQueryService;

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

    private MockMvc restSipAccountMockMvc;

    private SipAccount sipAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SipAccountResource sipAccountResource = new SipAccountResource(sipAccountService, sipAccountQueryService);
        this.restSipAccountMockMvc = MockMvcBuilders.standaloneSetup(sipAccountResource)
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
    public static SipAccount createEntity(EntityManager em) {
        SipAccount sipAccount = new SipAccount()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .lineEnabled(DEFAULT_LINE_ENABLED)
            .lineNumber(DEFAULT_LINE_NUMBER)
            .isManuallyCreated(DEFAULT_IS_MANUALLY_CREATED);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        sipAccount.setDevice(device);
        return sipAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SipAccount createUpdatedEntity(EntityManager em) {
        SipAccount sipAccount = new SipAccount()
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .lineEnabled(UPDATED_LINE_ENABLED)
            .lineNumber(UPDATED_LINE_NUMBER)
            .isManuallyCreated(UPDATED_IS_MANUALLY_CREATED);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createUpdatedEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        sipAccount.setDevice(device);
        return sipAccount;
    }

    @BeforeEach
    public void initTest() {
        sipAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSipAccount() throws Exception {
        int databaseSizeBeforeCreate = sipAccountRepository.findAll().size();

        // Create the SipAccount
        SipAccountDTO sipAccountDTO = sipAccountMapper.toDto(sipAccount);
        restSipAccountMockMvc.perform(post("/api/sip-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sipAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the SipAccount in the database
        List<SipAccount> sipAccountList = sipAccountRepository.findAll();
        assertThat(sipAccountList).hasSize(databaseSizeBeforeCreate + 1);
        SipAccount testSipAccount = sipAccountList.get(sipAccountList.size() - 1);
        assertThat(testSipAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSipAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSipAccount.isLineEnabled()).isEqualTo(DEFAULT_LINE_ENABLED);
        assertThat(testSipAccount.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testSipAccount.isIsManuallyCreated()).isEqualTo(DEFAULT_IS_MANUALLY_CREATED);
    }

    @Test
    @Transactional
    public void createSipAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sipAccountRepository.findAll().size();

        // Create the SipAccount with an existing ID
        sipAccount.setId(1L);
        SipAccountDTO sipAccountDTO = sipAccountMapper.toDto(sipAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSipAccountMockMvc.perform(post("/api/sip-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sipAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SipAccount in the database
        List<SipAccount> sipAccountList = sipAccountRepository.findAll();
        assertThat(sipAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSipAccounts() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList
        restSipAccountMockMvc.perform(get("/api/sip-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sipAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].lineEnabled").value(hasItem(DEFAULT_LINE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].isManuallyCreated").value(hasItem(DEFAULT_IS_MANUALLY_CREATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSipAccount() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get the sipAccount
        restSipAccountMockMvc.perform(get("/api/sip-accounts/{id}", sipAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sipAccount.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.lineEnabled").value(DEFAULT_LINE_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.isManuallyCreated").value(DEFAULT_IS_MANUALLY_CREATED.booleanValue()));
    }


    @Test
    @Transactional
    public void getSipAccountsByIdFiltering() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        Long id = sipAccount.getId();

        defaultSipAccountShouldBeFound("id.equals=" + id);
        defaultSipAccountShouldNotBeFound("id.notEquals=" + id);

        defaultSipAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSipAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultSipAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSipAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSipAccountsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where username equals to DEFAULT_USERNAME
        defaultSipAccountShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the sipAccountList where username equals to UPDATED_USERNAME
        defaultSipAccountShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where username not equals to DEFAULT_USERNAME
        defaultSipAccountShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the sipAccountList where username not equals to UPDATED_USERNAME
        defaultSipAccountShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultSipAccountShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the sipAccountList where username equals to UPDATED_USERNAME
        defaultSipAccountShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where username is not null
        defaultSipAccountShouldBeFound("username.specified=true");

        // Get all the sipAccountList where username is null
        defaultSipAccountShouldNotBeFound("username.specified=false");
    }
                @Test
    @Transactional
    public void getAllSipAccountsByUsernameContainsSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where username contains DEFAULT_USERNAME
        defaultSipAccountShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the sipAccountList where username contains UPDATED_USERNAME
        defaultSipAccountShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where username does not contain DEFAULT_USERNAME
        defaultSipAccountShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the sipAccountList where username does not contain UPDATED_USERNAME
        defaultSipAccountShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }


    @Test
    @Transactional
    public void getAllSipAccountsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where password equals to DEFAULT_PASSWORD
        defaultSipAccountShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the sipAccountList where password equals to UPDATED_PASSWORD
        defaultSipAccountShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where password not equals to DEFAULT_PASSWORD
        defaultSipAccountShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the sipAccountList where password not equals to UPDATED_PASSWORD
        defaultSipAccountShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultSipAccountShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the sipAccountList where password equals to UPDATED_PASSWORD
        defaultSipAccountShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where password is not null
        defaultSipAccountShouldBeFound("password.specified=true");

        // Get all the sipAccountList where password is null
        defaultSipAccountShouldNotBeFound("password.specified=false");
    }
                @Test
    @Transactional
    public void getAllSipAccountsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where password contains DEFAULT_PASSWORD
        defaultSipAccountShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the sipAccountList where password contains UPDATED_PASSWORD
        defaultSipAccountShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where password does not contain DEFAULT_PASSWORD
        defaultSipAccountShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the sipAccountList where password does not contain UPDATED_PASSWORD
        defaultSipAccountShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllSipAccountsByLineEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineEnabled equals to DEFAULT_LINE_ENABLED
        defaultSipAccountShouldBeFound("lineEnabled.equals=" + DEFAULT_LINE_ENABLED);

        // Get all the sipAccountList where lineEnabled equals to UPDATED_LINE_ENABLED
        defaultSipAccountShouldNotBeFound("lineEnabled.equals=" + UPDATED_LINE_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineEnabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineEnabled not equals to DEFAULT_LINE_ENABLED
        defaultSipAccountShouldNotBeFound("lineEnabled.notEquals=" + DEFAULT_LINE_ENABLED);

        // Get all the sipAccountList where lineEnabled not equals to UPDATED_LINE_ENABLED
        defaultSipAccountShouldBeFound("lineEnabled.notEquals=" + UPDATED_LINE_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineEnabled in DEFAULT_LINE_ENABLED or UPDATED_LINE_ENABLED
        defaultSipAccountShouldBeFound("lineEnabled.in=" + DEFAULT_LINE_ENABLED + "," + UPDATED_LINE_ENABLED);

        // Get all the sipAccountList where lineEnabled equals to UPDATED_LINE_ENABLED
        defaultSipAccountShouldNotBeFound("lineEnabled.in=" + UPDATED_LINE_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineEnabled is not null
        defaultSipAccountShouldBeFound("lineEnabled.specified=true");

        // Get all the sipAccountList where lineEnabled is null
        defaultSipAccountShouldNotBeFound("lineEnabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber equals to DEFAULT_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.equals=" + DEFAULT_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.equals=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber not equals to DEFAULT_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.notEquals=" + DEFAULT_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber not equals to UPDATED_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.notEquals=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsInShouldWork() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber in DEFAULT_LINE_NUMBER or UPDATED_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.in=" + DEFAULT_LINE_NUMBER + "," + UPDATED_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.in=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber is not null
        defaultSipAccountShouldBeFound("lineNumber.specified=true");

        // Get all the sipAccountList where lineNumber is null
        defaultSipAccountShouldNotBeFound("lineNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber is greater than or equal to DEFAULT_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.greaterThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber is greater than or equal to UPDATED_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.greaterThanOrEqual=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber is less than or equal to DEFAULT_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.lessThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber is less than or equal to SMALLER_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.lessThanOrEqual=" + SMALLER_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber is less than DEFAULT_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.lessThan=" + DEFAULT_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber is less than UPDATED_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.lessThan=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByLineNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where lineNumber is greater than DEFAULT_LINE_NUMBER
        defaultSipAccountShouldNotBeFound("lineNumber.greaterThan=" + DEFAULT_LINE_NUMBER);

        // Get all the sipAccountList where lineNumber is greater than SMALLER_LINE_NUMBER
        defaultSipAccountShouldBeFound("lineNumber.greaterThan=" + SMALLER_LINE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSipAccountsByIsManuallyCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where isManuallyCreated equals to DEFAULT_IS_MANUALLY_CREATED
        defaultSipAccountShouldBeFound("isManuallyCreated.equals=" + DEFAULT_IS_MANUALLY_CREATED);

        // Get all the sipAccountList where isManuallyCreated equals to UPDATED_IS_MANUALLY_CREATED
        defaultSipAccountShouldNotBeFound("isManuallyCreated.equals=" + UPDATED_IS_MANUALLY_CREATED);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByIsManuallyCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where isManuallyCreated not equals to DEFAULT_IS_MANUALLY_CREATED
        defaultSipAccountShouldNotBeFound("isManuallyCreated.notEquals=" + DEFAULT_IS_MANUALLY_CREATED);

        // Get all the sipAccountList where isManuallyCreated not equals to UPDATED_IS_MANUALLY_CREATED
        defaultSipAccountShouldBeFound("isManuallyCreated.notEquals=" + UPDATED_IS_MANUALLY_CREATED);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByIsManuallyCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where isManuallyCreated in DEFAULT_IS_MANUALLY_CREATED or UPDATED_IS_MANUALLY_CREATED
        defaultSipAccountShouldBeFound("isManuallyCreated.in=" + DEFAULT_IS_MANUALLY_CREATED + "," + UPDATED_IS_MANUALLY_CREATED);

        // Get all the sipAccountList where isManuallyCreated equals to UPDATED_IS_MANUALLY_CREATED
        defaultSipAccountShouldNotBeFound("isManuallyCreated.in=" + UPDATED_IS_MANUALLY_CREATED);
    }

    @Test
    @Transactional
    public void getAllSipAccountsByIsManuallyCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        // Get all the sipAccountList where isManuallyCreated is not null
        defaultSipAccountShouldBeFound("isManuallyCreated.specified=true");

        // Get all the sipAccountList where isManuallyCreated is null
        defaultSipAccountShouldNotBeFound("isManuallyCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllSipAccountsByPbxAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);
        PbxAccount pbxAccount = PbxAccountResourceIT.createEntity(em);
        em.persist(pbxAccount);
        em.flush();
        sipAccount.setPbxAccount(pbxAccount);
        sipAccountRepository.saveAndFlush(sipAccount);
        Long pbxAccountId = pbxAccount.getId();

        // Get all the sipAccountList where pbxAccount equals to pbxAccountId
        defaultSipAccountShouldBeFound("pbxAccountId.equals=" + pbxAccountId);

        // Get all the sipAccountList where pbxAccount equals to pbxAccountId + 1
        defaultSipAccountShouldNotBeFound("pbxAccountId.equals=" + (pbxAccountId + 1));
    }


    @Test
    @Transactional
    public void getAllSipAccountsByDeviceIsEqualToSomething() throws Exception {
        // Get already existing entity
        Device device = sipAccount.getDevice();
        sipAccountRepository.saveAndFlush(sipAccount);
        Long deviceId = device.getId();

        // Get all the sipAccountList where device equals to deviceId
        defaultSipAccountShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the sipAccountList where device equals to deviceId + 1
        defaultSipAccountShouldNotBeFound("deviceId.equals=" + (deviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSipAccountShouldBeFound(String filter) throws Exception {
        restSipAccountMockMvc.perform(get("/api/sip-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sipAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].lineEnabled").value(hasItem(DEFAULT_LINE_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].isManuallyCreated").value(hasItem(DEFAULT_IS_MANUALLY_CREATED.booleanValue())));

        // Check, that the count call also returns 1
        restSipAccountMockMvc.perform(get("/api/sip-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSipAccountShouldNotBeFound(String filter) throws Exception {
        restSipAccountMockMvc.perform(get("/api/sip-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSipAccountMockMvc.perform(get("/api/sip-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSipAccount() throws Exception {
        // Get the sipAccount
        restSipAccountMockMvc.perform(get("/api/sip-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSipAccount() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        int databaseSizeBeforeUpdate = sipAccountRepository.findAll().size();

        // Update the sipAccount
        SipAccount updatedSipAccount = sipAccountRepository.findById(sipAccount.getId()).get();
        // Disconnect from session so that the updates on updatedSipAccount are not directly saved in db
        em.detach(updatedSipAccount);
        updatedSipAccount
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .lineEnabled(UPDATED_LINE_ENABLED)
            .lineNumber(UPDATED_LINE_NUMBER)
            .isManuallyCreated(UPDATED_IS_MANUALLY_CREATED);
        SipAccountDTO sipAccountDTO = sipAccountMapper.toDto(updatedSipAccount);

        restSipAccountMockMvc.perform(put("/api/sip-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sipAccountDTO)))
            .andExpect(status().isOk());

        // Validate the SipAccount in the database
        List<SipAccount> sipAccountList = sipAccountRepository.findAll();
        assertThat(sipAccountList).hasSize(databaseSizeBeforeUpdate);
        SipAccount testSipAccount = sipAccountList.get(sipAccountList.size() - 1);
        assertThat(testSipAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSipAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSipAccount.isLineEnabled()).isEqualTo(UPDATED_LINE_ENABLED);
        assertThat(testSipAccount.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testSipAccount.isIsManuallyCreated()).isEqualTo(UPDATED_IS_MANUALLY_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingSipAccount() throws Exception {
        int databaseSizeBeforeUpdate = sipAccountRepository.findAll().size();

        // Create the SipAccount
        SipAccountDTO sipAccountDTO = sipAccountMapper.toDto(sipAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSipAccountMockMvc.perform(put("/api/sip-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sipAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SipAccount in the database
        List<SipAccount> sipAccountList = sipAccountRepository.findAll();
        assertThat(sipAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSipAccount() throws Exception {
        // Initialize the database
        sipAccountRepository.saveAndFlush(sipAccount);

        int databaseSizeBeforeDelete = sipAccountRepository.findAll().size();

        // Delete the sipAccount
        restSipAccountMockMvc.perform(delete("/api/sip-accounts/{id}", sipAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SipAccount> sipAccountList = sipAccountRepository.findAll();
        assertThat(sipAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
