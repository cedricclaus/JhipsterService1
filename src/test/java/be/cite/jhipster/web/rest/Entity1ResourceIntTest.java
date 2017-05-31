package be.cite.jhipster.web.rest;

import be.cite.jhipster.Service1App;

import be.cite.jhipster.domain.Entity1;
import be.cite.jhipster.repository.Entity1Repository;
import be.cite.jhipster.service.Entity1Service;
import be.cite.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static be.cite.jhipster.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import be.cite.jhipster.domain.enumeration.ReleaseType;
/**
 * Test class for the Entity1Resource REST controller.
 *
 * @see Entity1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Service1App.class)
public class Entity1ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final ZonedDateTime DEFAULT_RELEASE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RELEASE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ReleaseType DEFAULT_TYPE = ReleaseType.beta;
    private static final ReleaseType UPDATED_TYPE = ReleaseType.rc;

    @Autowired
    private Entity1Repository entity1Repository;

    @Autowired
    private Entity1Service entity1Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEntity1MockMvc;

    private Entity1 entity1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Entity1Resource entity1Resource = new Entity1Resource(entity1Service);
        this.restEntity1MockMvc = MockMvcBuilders.standaloneSetup(entity1Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entity1 createEntity() {
        Entity1 entity1 = new Entity1()
            .name(DEFAULT_NAME)
            .size(DEFAULT_SIZE)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .type(DEFAULT_TYPE);
        return entity1;
    }

    @Before
    public void initTest() {
        entity1Repository.deleteAll();
        entity1 = createEntity();
    }

    @Test
    public void createEntity1() throws Exception {
        int databaseSizeBeforeCreate = entity1Repository.findAll().size();

        // Create the Entity1
        restEntity1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity1)))
            .andExpect(status().isCreated());

        // Validate the Entity1 in the database
        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeCreate + 1);
        Entity1 testEntity1 = entity1List.get(entity1List.size() - 1);
        assertThat(testEntity1.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntity1.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testEntity1.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testEntity1.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createEntity1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entity1Repository.findAll().size();

        // Create the Entity1 with an existing ID
        entity1.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntity1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity1)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entity1Repository.findAll().size();
        // set the field null
        entity1.setName(null);

        // Create the Entity1, which fails.

        restEntity1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity1)))
            .andExpect(status().isBadRequest());

        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = entity1Repository.findAll().size();
        // set the field null
        entity1.setType(null);

        // Create the Entity1, which fails.

        restEntity1MockMvc.perform(post("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity1)))
            .andExpect(status().isBadRequest());

        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEntity1S() throws Exception {
        // Initialize the database
        entity1Repository.save(entity1);

        // Get all the entity1List
        restEntity1MockMvc.perform(get("/api/entity-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entity1.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(sameInstant(DEFAULT_RELEASE_DATE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    public void getEntity1() throws Exception {
        // Initialize the database
        entity1Repository.save(entity1);

        // Get the entity1
        restEntity1MockMvc.perform(get("/api/entity-1-s/{id}", entity1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entity1.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.releaseDate").value(sameInstant(DEFAULT_RELEASE_DATE)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingEntity1() throws Exception {
        // Get the entity1
        restEntity1MockMvc.perform(get("/api/entity-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEntity1() throws Exception {
        // Initialize the database
        entity1Service.save(entity1);

        int databaseSizeBeforeUpdate = entity1Repository.findAll().size();

        // Update the entity1
        Entity1 updatedEntity1 = entity1Repository.findOne(entity1.getId());
        updatedEntity1
            .name(UPDATED_NAME)
            .size(UPDATED_SIZE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .type(UPDATED_TYPE);

        restEntity1MockMvc.perform(put("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntity1)))
            .andExpect(status().isOk());

        // Validate the Entity1 in the database
        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeUpdate);
        Entity1 testEntity1 = entity1List.get(entity1List.size() - 1);
        assertThat(testEntity1.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntity1.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testEntity1.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testEntity1.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingEntity1() throws Exception {
        int databaseSizeBeforeUpdate = entity1Repository.findAll().size();

        // Create the Entity1

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntity1MockMvc.perform(put("/api/entity-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entity1)))
            .andExpect(status().isCreated());

        // Validate the Entity1 in the database
        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEntity1() throws Exception {
        // Initialize the database
        entity1Service.save(entity1);

        int databaseSizeBeforeDelete = entity1Repository.findAll().size();

        // Get the entity1
        restEntity1MockMvc.perform(delete("/api/entity-1-s/{id}", entity1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entity1> entity1List = entity1Repository.findAll();
        assertThat(entity1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entity1.class);
        Entity1 entity11 = new Entity1();
        entity11.setId("id1");
        Entity1 entity12 = new Entity1();
        entity12.setId(entity11.getId());
        assertThat(entity11).isEqualTo(entity12);
        entity12.setId("id2");
        assertThat(entity11).isNotEqualTo(entity12);
        entity11.setId(null);
        assertThat(entity11).isNotEqualTo(entity12);
    }
}
