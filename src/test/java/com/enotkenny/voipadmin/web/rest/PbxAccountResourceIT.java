package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.PbxAccount;
import com.enotkenny.voipadmin.domain.SipAccount;
import com.enotkenny.voipadmin.repository.PbxAccountRepository;
import com.enotkenny.voipadmin.service.PbxAccountService;
import com.enotkenny.voipadmin.service.dto.PbxAccountDTO;
import com.enotkenny.voipadmin.service.mapper.PbxAccountMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.PbxAccountCriteria;
import com.enotkenny.voipadmin.service.PbxAccountQueryService;

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
 * Integration tests for the {@link PbxAccountResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class PbxAccountResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PBX_ID = "AAAAAAAAAA";
    private static final String UPDATED_PBX_ID = "BBBBBBBBBB";

    @Autowired
    private PbxAccountRepository pbxAccountRepository;

    @Autowired
    private PbxAccountMapper pbxAccountMapper;

    @Autowired
    private PbxAccountService pbxAccountService;

    @Autowired
    private PbxAccountQueryService pbxAccountQueryService;

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

    private MockMvc restPbxAccountMockMvc;

    private PbxAccount pbxAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PbxAccountResource pbxAccountResource = new PbxAccountResource(pbxAccountService, pbxAccountQueryService);
        this.restPbxAccountMockMvc = MockMvcBuilders.standaloneSetup(pbxAccountResource)
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
    public static PbxAccount createEntity(EntityManager em) {
        PbxAccount pbxAccount = new PbxAccount()
            .username(DEFAULT_USERNAME)
            .pbxId(DEFAULT_PBX_ID);
        return pbxAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PbxAccount createUpdatedEntity(EntityManager em) {
        PbxAccount pbxAccount = new PbxAccount()
            .username(UPDATED_USERNAME)
            .pbxId(UPDATED_PBX_ID);
        return pbxAccount;
    }

    @BeforeEach
    public void initTest() {
        pbxAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPbxAccount() throws Exception {
        int databaseSizeBeforeCreate = pbxAccountRepository.findAll().size();

        // Create the PbxAccount
        PbxAccountDTO pbxAccountDTO = pbxAccountMapper.toDto(pbxAccount);
        restPbxAccountMockMvc.perform(post("/api/pbx-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pbxAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the PbxAccount in the database
        List<PbxAccount> pbxAccountList = pbxAccountRepository.findAll();
        assertThat(pbxAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PbxAccount testPbxAccount = pbxAccountList.get(pbxAccountList.size() - 1);
        assertThat(testPbxAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testPbxAccount.getPbxId()).isEqualTo(DEFAULT_PBX_ID);
    }

    @Test
    @Transactional
    public void createPbxAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pbxAccountRepository.findAll().size();

        // Create the PbxAccount with an existing ID
        pbxAccount.setId(1L);
        PbxAccountDTO pbxAccountDTO = pbxAccountMapper.toDto(pbxAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPbxAccountMockMvc.perform(post("/api/pbx-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pbxAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PbxAccount in the database
        List<PbxAccount> pbxAccountList = pbxAccountRepository.findAll();
        assertThat(pbxAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPbxAccounts() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pbxAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].pbxId").value(hasItem(DEFAULT_PBX_ID)));
    }
    
    @Test
    @Transactional
    public void getPbxAccount() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get the pbxAccount
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts/{id}", pbxAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pbxAccount.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.pbxId").value(DEFAULT_PBX_ID));
    }


    @Test
    @Transactional
    public void getPbxAccountsByIdFiltering() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        Long id = pbxAccount.getId();

        defaultPbxAccountShouldBeFound("id.equals=" + id);
        defaultPbxAccountShouldNotBeFound("id.notEquals=" + id);

        defaultPbxAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPbxAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultPbxAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPbxAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPbxAccountsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where username equals to DEFAULT_USERNAME
        defaultPbxAccountShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the pbxAccountList where username equals to UPDATED_USERNAME
        defaultPbxAccountShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where username not equals to DEFAULT_USERNAME
        defaultPbxAccountShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the pbxAccountList where username not equals to UPDATED_USERNAME
        defaultPbxAccountShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultPbxAccountShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the pbxAccountList where username equals to UPDATED_USERNAME
        defaultPbxAccountShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where username is not null
        defaultPbxAccountShouldBeFound("username.specified=true");

        // Get all the pbxAccountList where username is null
        defaultPbxAccountShouldNotBeFound("username.specified=false");
    }
                @Test
    @Transactional
    public void getAllPbxAccountsByUsernameContainsSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where username contains DEFAULT_USERNAME
        defaultPbxAccountShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the pbxAccountList where username contains UPDATED_USERNAME
        defaultPbxAccountShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where username does not contain DEFAULT_USERNAME
        defaultPbxAccountShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the pbxAccountList where username does not contain UPDATED_USERNAME
        defaultPbxAccountShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }


    @Test
    @Transactional
    public void getAllPbxAccountsByPbxIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where pbxId equals to DEFAULT_PBX_ID
        defaultPbxAccountShouldBeFound("pbxId.equals=" + DEFAULT_PBX_ID);

        // Get all the pbxAccountList where pbxId equals to UPDATED_PBX_ID
        defaultPbxAccountShouldNotBeFound("pbxId.equals=" + UPDATED_PBX_ID);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByPbxIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where pbxId not equals to DEFAULT_PBX_ID
        defaultPbxAccountShouldNotBeFound("pbxId.notEquals=" + DEFAULT_PBX_ID);

        // Get all the pbxAccountList where pbxId not equals to UPDATED_PBX_ID
        defaultPbxAccountShouldBeFound("pbxId.notEquals=" + UPDATED_PBX_ID);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByPbxIdIsInShouldWork() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where pbxId in DEFAULT_PBX_ID or UPDATED_PBX_ID
        defaultPbxAccountShouldBeFound("pbxId.in=" + DEFAULT_PBX_ID + "," + UPDATED_PBX_ID);

        // Get all the pbxAccountList where pbxId equals to UPDATED_PBX_ID
        defaultPbxAccountShouldNotBeFound("pbxId.in=" + UPDATED_PBX_ID);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByPbxIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where pbxId is not null
        defaultPbxAccountShouldBeFound("pbxId.specified=true");

        // Get all the pbxAccountList where pbxId is null
        defaultPbxAccountShouldNotBeFound("pbxId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPbxAccountsByPbxIdContainsSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where pbxId contains DEFAULT_PBX_ID
        defaultPbxAccountShouldBeFound("pbxId.contains=" + DEFAULT_PBX_ID);

        // Get all the pbxAccountList where pbxId contains UPDATED_PBX_ID
        defaultPbxAccountShouldNotBeFound("pbxId.contains=" + UPDATED_PBX_ID);
    }

    @Test
    @Transactional
    public void getAllPbxAccountsByPbxIdNotContainsSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        // Get all the pbxAccountList where pbxId does not contain DEFAULT_PBX_ID
        defaultPbxAccountShouldNotBeFound("pbxId.doesNotContain=" + DEFAULT_PBX_ID);

        // Get all the pbxAccountList where pbxId does not contain UPDATED_PBX_ID
        defaultPbxAccountShouldBeFound("pbxId.doesNotContain=" + UPDATED_PBX_ID);
    }


    @Test
    @Transactional
    public void getAllPbxAccountsBySipAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);
        SipAccount sipAccount = SipAccountResourceIT.createEntity(em);
        em.persist(sipAccount);
        em.flush();
        pbxAccount.setSipAccount(sipAccount);
        sipAccount.setPbxAccount(pbxAccount);
        pbxAccountRepository.saveAndFlush(pbxAccount);
        Long sipAccountId = sipAccount.getId();

        // Get all the pbxAccountList where sipAccount equals to sipAccountId
        defaultPbxAccountShouldBeFound("sipAccountId.equals=" + sipAccountId);

        // Get all the pbxAccountList where sipAccount equals to sipAccountId + 1
        defaultPbxAccountShouldNotBeFound("sipAccountId.equals=" + (sipAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPbxAccountShouldBeFound(String filter) throws Exception {
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pbxAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].pbxId").value(hasItem(DEFAULT_PBX_ID)));

        // Check, that the count call also returns 1
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPbxAccountShouldNotBeFound(String filter) throws Exception {
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPbxAccount() throws Exception {
        // Get the pbxAccount
        restPbxAccountMockMvc.perform(get("/api/pbx-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePbxAccount() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        int databaseSizeBeforeUpdate = pbxAccountRepository.findAll().size();

        // Update the pbxAccount
        PbxAccount updatedPbxAccount = pbxAccountRepository.findById(pbxAccount.getId()).get();
        // Disconnect from session so that the updates on updatedPbxAccount are not directly saved in db
        em.detach(updatedPbxAccount);
        updatedPbxAccount
            .username(UPDATED_USERNAME)
            .pbxId(UPDATED_PBX_ID);
        PbxAccountDTO pbxAccountDTO = pbxAccountMapper.toDto(updatedPbxAccount);

        restPbxAccountMockMvc.perform(put("/api/pbx-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pbxAccountDTO)))
            .andExpect(status().isOk());

        // Validate the PbxAccount in the database
        List<PbxAccount> pbxAccountList = pbxAccountRepository.findAll();
        assertThat(pbxAccountList).hasSize(databaseSizeBeforeUpdate);
        PbxAccount testPbxAccount = pbxAccountList.get(pbxAccountList.size() - 1);
        assertThat(testPbxAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPbxAccount.getPbxId()).isEqualTo(UPDATED_PBX_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPbxAccount() throws Exception {
        int databaseSizeBeforeUpdate = pbxAccountRepository.findAll().size();

        // Create the PbxAccount
        PbxAccountDTO pbxAccountDTO = pbxAccountMapper.toDto(pbxAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPbxAccountMockMvc.perform(put("/api/pbx-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pbxAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PbxAccount in the database
        List<PbxAccount> pbxAccountList = pbxAccountRepository.findAll();
        assertThat(pbxAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePbxAccount() throws Exception {
        // Initialize the database
        pbxAccountRepository.saveAndFlush(pbxAccount);

        int databaseSizeBeforeDelete = pbxAccountRepository.findAll().size();

        // Delete the pbxAccount
        restPbxAccountMockMvc.perform(delete("/api/pbx-accounts/{id}", pbxAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PbxAccount> pbxAccountList = pbxAccountRepository.findAll();
        assertThat(pbxAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
