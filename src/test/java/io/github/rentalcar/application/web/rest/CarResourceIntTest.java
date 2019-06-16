package io.github.rentalcar.application.web.rest;

import io.github.rentalcar.application.CarRentalSystemApp;

import io.github.rentalcar.application.domain.Car;
import io.github.rentalcar.application.repository.CarRepository;
import io.github.rentalcar.application.service.CarService;
import io.github.rentalcar.application.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.github.rentalcar.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.rentalcar.application.domain.enumeration.TransmissionType;
/**
 * Test class for the CarResource REST controller.
 *
 * @see CarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarRentalSystemApp.class)
public class CarResourceIntTest {

    private static final String DEFAULT_LICENSE_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_PLATE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final TransmissionType DEFAULT_TRANSMISSION = TransmissionType.MANUAL;
    private static final TransmissionType UPDATED_TRANSMISSION = TransmissionType.AUTOMATIC;

    private static final Boolean DEFAULT_AIRCONDITION = false;
    private static final Boolean UPDATED_AIRCONDITION = true;

    private static final Integer DEFAULT_DOORS = 1;
    private static final Integer UPDATED_DOORS = 2;

    private static final String DEFAULT_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final byte[] DEFAULT_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_2_CONTENT_TYPE = "image/png";

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

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

    private MockMvc restCarMockMvc;

    private Car car;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarResource carResource = new CarResource(carService);
        this.restCarMockMvc = MockMvcBuilders.standaloneSetup(carResource)
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
    public static Car createEntity(EntityManager em) {
        Car car = new Car()
            .licensePlate(DEFAULT_LICENSE_PLATE)
            .name(DEFAULT_NAME)
            .transmission(DEFAULT_TRANSMISSION)
            .aircondition(DEFAULT_AIRCONDITION)
            .doors(DEFAULT_DOORS)
            .keywords(DEFAULT_KEYWORDS)
            .description(DEFAULT_DESCRIPTION)
            .rating(DEFAULT_RATING)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateModified(DEFAULT_DATE_MODIFIED)
            .sortOrder(DEFAULT_SORT_ORDER)
            .image_1(DEFAULT_IMAGE_1)
            .image_1ContentType(DEFAULT_IMAGE_1_CONTENT_TYPE)
            .image_2(DEFAULT_IMAGE_2)
            .image_2ContentType(DEFAULT_IMAGE_2_CONTENT_TYPE);
        return car;
    }

    @Before
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate + 1);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getLicensePlate()).isEqualTo(DEFAULT_LICENSE_PLATE);
        assertThat(testCar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar.getTransmission()).isEqualTo(DEFAULT_TRANSMISSION);
        assertThat(testCar.isAircondition()).isEqualTo(DEFAULT_AIRCONDITION);
        assertThat(testCar.getDoors()).isEqualTo(DEFAULT_DOORS);
        assertThat(testCar.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testCar.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCar.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testCar.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testCar.getDateModified()).isEqualTo(DEFAULT_DATE_MODIFIED);
        assertThat(testCar.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testCar.getImage_1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testCar.getImage_1ContentType()).isEqualTo(DEFAULT_IMAGE_1_CONTENT_TYPE);
        assertThat(testCar.getImage_2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testCar.getImage_2ContentType()).isEqualTo(DEFAULT_IMAGE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car with an existing ID
        car.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLicensePlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setLicensePlate(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setName(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransmissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setTransmission(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAirconditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setAircondition(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDoorsIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setDoors(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCars() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get all the carList
        restCarMockMvc.perform(get("/api/cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
            .andExpect(jsonPath("$.[*].licensePlate").value(hasItem(DEFAULT_LICENSE_PLATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].transmission").value(hasItem(DEFAULT_TRANSMISSION.toString())))
            .andExpect(jsonPath("$.[*].aircondition").value(hasItem(DEFAULT_AIRCONDITION.booleanValue())))
            .andExpect(jsonPath("$.[*].doors").value(hasItem(DEFAULT_DOORS)))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateModified").value(hasItem(DEFAULT_DATE_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].image_1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image_1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image_2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image_2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))));
    }
    
    @Test
    @Transactional
    public void getCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId().intValue()))
            .andExpect(jsonPath("$.licensePlate").value(DEFAULT_LICENSE_PLATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.transmission").value(DEFAULT_TRANSMISSION.toString()))
            .andExpect(jsonPath("$.aircondition").value(DEFAULT_AIRCONDITION.booleanValue()))
            .andExpect(jsonPath("$.doors").value(DEFAULT_DOORS))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateModified").value(DEFAULT_DATE_MODIFIED.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.image_1ContentType").value(DEFAULT_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.image_1").value(Base64Utils.encodeToString(DEFAULT_IMAGE_1)))
            .andExpect(jsonPath("$.image_2ContentType").value(DEFAULT_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.image_2").value(Base64Utils.encodeToString(DEFAULT_IMAGE_2)));
    }

    @Test
    @Transactional
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar() throws Exception {
        // Initialize the database
        carService.save(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Car updatedCar = carRepository.findById(car.getId()).get();
        // Disconnect from session so that the updates on updatedCar are not directly saved in db
        em.detach(updatedCar);
        updatedCar
            .licensePlate(UPDATED_LICENSE_PLATE)
            .name(UPDATED_NAME)
            .transmission(UPDATED_TRANSMISSION)
            .aircondition(UPDATED_AIRCONDITION)
            .doors(UPDATED_DOORS)
            .keywords(UPDATED_KEYWORDS)
            .description(UPDATED_DESCRIPTION)
            .rating(UPDATED_RATING)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateModified(UPDATED_DATE_MODIFIED)
            .sortOrder(UPDATED_SORT_ORDER)
            .image_1(UPDATED_IMAGE_1)
            .image_1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image_2(UPDATED_IMAGE_2)
            .image_2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE);

        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCar)))
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getLicensePlate()).isEqualTo(UPDATED_LICENSE_PLATE);
        assertThat(testCar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar.getTransmission()).isEqualTo(UPDATED_TRANSMISSION);
        assertThat(testCar.isAircondition()).isEqualTo(UPDATED_AIRCONDITION);
        assertThat(testCar.getDoors()).isEqualTo(UPDATED_DOORS);
        assertThat(testCar.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testCar.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCar.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testCar.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testCar.getDateModified()).isEqualTo(UPDATED_DATE_MODIFIED);
        assertThat(testCar.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testCar.getImage_1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testCar.getImage_1ContentType()).isEqualTo(UPDATED_IMAGE_1_CONTENT_TYPE);
        assertThat(testCar.getImage_2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testCar.getImage_2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Create the Car

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCar() throws Exception {
        // Initialize the database
        carService.save(car);

        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Get the car
        restCarMockMvc.perform(delete("/api/cars/{id}", car.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car.class);
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(car1.getId());
        assertThat(car1).isEqualTo(car2);
        car2.setId(2L);
        assertThat(car1).isNotEqualTo(car2);
        car1.setId(null);
        assertThat(car1).isNotEqualTo(car2);
    }
}
