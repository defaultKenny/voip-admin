package com.enotkenny.voipadmin.web.rest;

import com.enotkenny.voipadmin.VoipAdminApp;
import com.enotkenny.voipadmin.domain.ResponsiblePerson;
import com.enotkenny.voipadmin.domain.Device;
import com.enotkenny.voipadmin.repository.ResponsiblePersonRepository;
import com.enotkenny.voipadmin.service.ResponsiblePersonService;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonDTO;
import com.enotkenny.voipadmin.service.mapper.ResponsiblePersonMapper;
import com.enotkenny.voipadmin.web.rest.errors.ExceptionTranslator;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonCriteria;
import com.enotkenny.voipadmin.service.ResponsiblePersonQueryService;

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
 * Integration tests for the {@link ResponsiblePersonResource} REST controller.
 */
@SpringBootTest(classes = VoipAdminApp.class)
public class ResponsiblePersonResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ResponsiblePersonRepository responsiblePersonRepository;

    @Autowired
    private ResponsiblePersonMapper responsiblePersonMapper;

    @Autowired
    private ResponsiblePersonService responsiblePersonService;

    @Autowired
    private ResponsiblePersonQueryService responsiblePersonQueryService;

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

    private MockMvc restResponsiblePersonMockMvc;

    private ResponsiblePerson responsiblePerson;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsiblePersonResource responsiblePersonResource = new ResponsiblePersonResource(responsiblePersonService, responsiblePersonQueryService);
        this.restResponsiblePersonMockMvc = MockMvcBuilders.standaloneSetup(responsiblePersonResource)
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
    public static ResponsiblePerson createEntity(EntityManager em) {
        ResponsiblePerson responsiblePerson = new ResponsiblePerson()
            .code(DEFAULT_CODE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .position(DEFAULT_POSITION)
            .department(DEFAULT_DEPARTMENT)
            .location(DEFAULT_LOCATION);
        return responsiblePerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsiblePerson createUpdatedEntity(EntityManager em) {
        ResponsiblePerson responsiblePerson = new ResponsiblePerson()
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .position(UPDATED_POSITION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION);
        return responsiblePerson;
    }

    @BeforeEach
    public void initTest() {
        responsiblePerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsiblePerson() throws Exception {
        int databaseSizeBeforeCreate = responsiblePersonRepository.findAll().size();

        // Create the ResponsiblePerson
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);
        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isCreated());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsiblePerson testResponsiblePerson = responsiblePersonList.get(responsiblePersonList.size() - 1);
        assertThat(testResponsiblePerson.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testResponsiblePerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testResponsiblePerson.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testResponsiblePerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testResponsiblePerson.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testResponsiblePerson.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testResponsiblePerson.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createResponsiblePersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsiblePersonRepository.findAll().size();

        // Create the ResponsiblePerson with an existing ID
        responsiblePerson.setId(1L);
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsiblePersonRepository.findAll().size();
        // set the field null
        responsiblePerson.setCode(null);

        // Create the ResponsiblePerson, which fails.
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);

        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsiblePersonRepository.findAll().size();
        // set the field null
        responsiblePerson.setLastName(null);

        // Create the ResponsiblePerson, which fails.
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);

        restResponsiblePersonMockMvc.perform(post("/api/responsible-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeople() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsiblePerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }
    
    @Test
    @Transactional
    public void getResponsiblePerson() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get the responsiblePerson
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people/{id}", responsiblePerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsiblePerson.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }


    @Test
    @Transactional
    public void getResponsiblePeopleByIdFiltering() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        Long id = responsiblePerson.getId();

        defaultResponsiblePersonShouldBeFound("id.equals=" + id);
        defaultResponsiblePersonShouldNotBeFound("id.notEquals=" + id);

        defaultResponsiblePersonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResponsiblePersonShouldNotBeFound("id.greaterThan=" + id);

        defaultResponsiblePersonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResponsiblePersonShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where code equals to DEFAULT_CODE
        defaultResponsiblePersonShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the responsiblePersonList where code equals to UPDATED_CODE
        defaultResponsiblePersonShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where code not equals to DEFAULT_CODE
        defaultResponsiblePersonShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the responsiblePersonList where code not equals to UPDATED_CODE
        defaultResponsiblePersonShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where code in DEFAULT_CODE or UPDATED_CODE
        defaultResponsiblePersonShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the responsiblePersonList where code equals to UPDATED_CODE
        defaultResponsiblePersonShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where code is not null
        defaultResponsiblePersonShouldBeFound("code.specified=true");

        // Get all the responsiblePersonList where code is null
        defaultResponsiblePersonShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByCodeContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where code contains DEFAULT_CODE
        defaultResponsiblePersonShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the responsiblePersonList where code contains UPDATED_CODE
        defaultResponsiblePersonShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where code does not contain DEFAULT_CODE
        defaultResponsiblePersonShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the responsiblePersonList where code does not contain UPDATED_CODE
        defaultResponsiblePersonShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where firstName equals to DEFAULT_FIRST_NAME
        defaultResponsiblePersonShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the responsiblePersonList where firstName equals to UPDATED_FIRST_NAME
        defaultResponsiblePersonShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where firstName not equals to DEFAULT_FIRST_NAME
        defaultResponsiblePersonShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the responsiblePersonList where firstName not equals to UPDATED_FIRST_NAME
        defaultResponsiblePersonShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultResponsiblePersonShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the responsiblePersonList where firstName equals to UPDATED_FIRST_NAME
        defaultResponsiblePersonShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where firstName is not null
        defaultResponsiblePersonShouldBeFound("firstName.specified=true");

        // Get all the responsiblePersonList where firstName is null
        defaultResponsiblePersonShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where firstName contains DEFAULT_FIRST_NAME
        defaultResponsiblePersonShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the responsiblePersonList where firstName contains UPDATED_FIRST_NAME
        defaultResponsiblePersonShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where firstName does not contain DEFAULT_FIRST_NAME
        defaultResponsiblePersonShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the responsiblePersonList where firstName does not contain UPDATED_FIRST_NAME
        defaultResponsiblePersonShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultResponsiblePersonShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the responsiblePersonList where middleName equals to UPDATED_MIDDLE_NAME
        defaultResponsiblePersonShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultResponsiblePersonShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the responsiblePersonList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultResponsiblePersonShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultResponsiblePersonShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the responsiblePersonList where middleName equals to UPDATED_MIDDLE_NAME
        defaultResponsiblePersonShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where middleName is not null
        defaultResponsiblePersonShouldBeFound("middleName.specified=true");

        // Get all the responsiblePersonList where middleName is null
        defaultResponsiblePersonShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where middleName contains DEFAULT_MIDDLE_NAME
        defaultResponsiblePersonShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the responsiblePersonList where middleName contains UPDATED_MIDDLE_NAME
        defaultResponsiblePersonShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultResponsiblePersonShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the responsiblePersonList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultResponsiblePersonShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where lastName equals to DEFAULT_LAST_NAME
        defaultResponsiblePersonShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the responsiblePersonList where lastName equals to UPDATED_LAST_NAME
        defaultResponsiblePersonShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where lastName not equals to DEFAULT_LAST_NAME
        defaultResponsiblePersonShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the responsiblePersonList where lastName not equals to UPDATED_LAST_NAME
        defaultResponsiblePersonShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultResponsiblePersonShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the responsiblePersonList where lastName equals to UPDATED_LAST_NAME
        defaultResponsiblePersonShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where lastName is not null
        defaultResponsiblePersonShouldBeFound("lastName.specified=true");

        // Get all the responsiblePersonList where lastName is null
        defaultResponsiblePersonShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByLastNameContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where lastName contains DEFAULT_LAST_NAME
        defaultResponsiblePersonShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the responsiblePersonList where lastName contains UPDATED_LAST_NAME
        defaultResponsiblePersonShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where lastName does not contain DEFAULT_LAST_NAME
        defaultResponsiblePersonShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the responsiblePersonList where lastName does not contain UPDATED_LAST_NAME
        defaultResponsiblePersonShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where position equals to DEFAULT_POSITION
        defaultResponsiblePersonShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the responsiblePersonList where position equals to UPDATED_POSITION
        defaultResponsiblePersonShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where position not equals to DEFAULT_POSITION
        defaultResponsiblePersonShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the responsiblePersonList where position not equals to UPDATED_POSITION
        defaultResponsiblePersonShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultResponsiblePersonShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the responsiblePersonList where position equals to UPDATED_POSITION
        defaultResponsiblePersonShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where position is not null
        defaultResponsiblePersonShouldBeFound("position.specified=true");

        // Get all the responsiblePersonList where position is null
        defaultResponsiblePersonShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByPositionContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where position contains DEFAULT_POSITION
        defaultResponsiblePersonShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the responsiblePersonList where position contains UPDATED_POSITION
        defaultResponsiblePersonShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where position does not contain DEFAULT_POSITION
        defaultResponsiblePersonShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the responsiblePersonList where position does not contain UPDATED_POSITION
        defaultResponsiblePersonShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where department equals to DEFAULT_DEPARTMENT
        defaultResponsiblePersonShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the responsiblePersonList where department equals to UPDATED_DEPARTMENT
        defaultResponsiblePersonShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByDepartmentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where department not equals to DEFAULT_DEPARTMENT
        defaultResponsiblePersonShouldNotBeFound("department.notEquals=" + DEFAULT_DEPARTMENT);

        // Get all the responsiblePersonList where department not equals to UPDATED_DEPARTMENT
        defaultResponsiblePersonShouldBeFound("department.notEquals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultResponsiblePersonShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the responsiblePersonList where department equals to UPDATED_DEPARTMENT
        defaultResponsiblePersonShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where department is not null
        defaultResponsiblePersonShouldBeFound("department.specified=true");

        // Get all the responsiblePersonList where department is null
        defaultResponsiblePersonShouldNotBeFound("department.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByDepartmentContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where department contains DEFAULT_DEPARTMENT
        defaultResponsiblePersonShouldBeFound("department.contains=" + DEFAULT_DEPARTMENT);

        // Get all the responsiblePersonList where department contains UPDATED_DEPARTMENT
        defaultResponsiblePersonShouldNotBeFound("department.contains=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where department does not contain DEFAULT_DEPARTMENT
        defaultResponsiblePersonShouldNotBeFound("department.doesNotContain=" + DEFAULT_DEPARTMENT);

        // Get all the responsiblePersonList where department does not contain UPDATED_DEPARTMENT
        defaultResponsiblePersonShouldBeFound("department.doesNotContain=" + UPDATED_DEPARTMENT);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where location equals to DEFAULT_LOCATION
        defaultResponsiblePersonShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the responsiblePersonList where location equals to UPDATED_LOCATION
        defaultResponsiblePersonShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where location not equals to DEFAULT_LOCATION
        defaultResponsiblePersonShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the responsiblePersonList where location not equals to UPDATED_LOCATION
        defaultResponsiblePersonShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultResponsiblePersonShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the responsiblePersonList where location equals to UPDATED_LOCATION
        defaultResponsiblePersonShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where location is not null
        defaultResponsiblePersonShouldBeFound("location.specified=true");

        // Get all the responsiblePersonList where location is null
        defaultResponsiblePersonShouldNotBeFound("location.specified=false");
    }
                @Test
    @Transactional
    public void getAllResponsiblePeopleByLocationContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where location contains DEFAULT_LOCATION
        defaultResponsiblePersonShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the responsiblePersonList where location contains UPDATED_LOCATION
        defaultResponsiblePersonShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllResponsiblePeopleByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        // Get all the responsiblePersonList where location does not contain DEFAULT_LOCATION
        defaultResponsiblePersonShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the responsiblePersonList where location does not contain UPDATED_LOCATION
        defaultResponsiblePersonShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }


    @Test
    @Transactional
    public void getAllResponsiblePeopleByDeviceIsEqualToSomething() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);
        Device device = DeviceResourceIT.createEntity(em);
        em.persist(device);
        em.flush();
        responsiblePerson.addDevice(device);
        responsiblePersonRepository.saveAndFlush(responsiblePerson);
        Long deviceId = device.getId();

        // Get all the responsiblePersonList where device equals to deviceId
        defaultResponsiblePersonShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the responsiblePersonList where device equals to deviceId + 1
        defaultResponsiblePersonShouldNotBeFound("deviceId.equals=" + (deviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResponsiblePersonShouldBeFound(String filter) throws Exception {
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsiblePerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));

        // Check, that the count call also returns 1
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResponsiblePersonShouldNotBeFound(String filter) throws Exception {
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResponsiblePerson() throws Exception {
        // Get the responsiblePerson
        restResponsiblePersonMockMvc.perform(get("/api/responsible-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsiblePerson() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        int databaseSizeBeforeUpdate = responsiblePersonRepository.findAll().size();

        // Update the responsiblePerson
        ResponsiblePerson updatedResponsiblePerson = responsiblePersonRepository.findById(responsiblePerson.getId()).get();
        // Disconnect from session so that the updates on updatedResponsiblePerson are not directly saved in db
        em.detach(updatedResponsiblePerson);
        updatedResponsiblePerson
            .code(UPDATED_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .position(UPDATED_POSITION)
            .department(UPDATED_DEPARTMENT)
            .location(UPDATED_LOCATION);
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(updatedResponsiblePerson);

        restResponsiblePersonMockMvc.perform(put("/api/responsible-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isOk());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeUpdate);
        ResponsiblePerson testResponsiblePerson = responsiblePersonList.get(responsiblePersonList.size() - 1);
        assertThat(testResponsiblePerson.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testResponsiblePerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testResponsiblePerson.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testResponsiblePerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testResponsiblePerson.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testResponsiblePerson.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testResponsiblePerson.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsiblePerson() throws Exception {
        int databaseSizeBeforeUpdate = responsiblePersonRepository.findAll().size();

        // Create the ResponsiblePerson
        ResponsiblePersonDTO responsiblePersonDTO = responsiblePersonMapper.toDto(responsiblePerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsiblePersonMockMvc.perform(put("/api/responsible-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsiblePersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResponsiblePerson in the database
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsiblePerson() throws Exception {
        // Initialize the database
        responsiblePersonRepository.saveAndFlush(responsiblePerson);

        int databaseSizeBeforeDelete = responsiblePersonRepository.findAll().size();

        // Delete the responsiblePerson
        restResponsiblePersonMockMvc.perform(delete("/api/responsible-people/{id}", responsiblePerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsiblePerson> responsiblePersonList = responsiblePersonRepository.findAll();
        assertThat(responsiblePersonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
