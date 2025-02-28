package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    // POSITIVE TEST CASES

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Honda Civic");
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
    }

    @Test
    void testFindById_WhenCarExists() {
        Car car = new Car();
        car.setCarId("999");
        carRepository.create(car);

        Car foundCar = carRepository.findById("999");
        assertEquals(car.getCarId(), foundCar.getCarId());
    }

    @Test
    void testUpdate_WhenCarExists() {
        // Setup existing car
        Car originalCar = new Car();
        originalCar.setCarId("123");
        originalCar.setCarName("Original Name");
        carRepository.create(originalCar);

        // Update data
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Name");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(10);

        // Execute update
        Car result = carRepository.update("123", updatedCar);

        // Verify
        assertNotNull(result);
        assertEquals("Updated Name", result.getCarName());
        assertEquals("Red", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testDelete_WhenCarExists() {
        Car car = new Car();
        car.setCarId("456");
        carRepository.create(car);

        carRepository.delete("456");
        assertNull(carRepository.findById("456"));
    }

    // NEGATIVE TEST CASES

    @Test
    void testFindById_WhenCarNotExists() {
        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdate_WhenCarNotExists() {
        // Setup non-existent car
        Car updatedCar = new Car();
        updatedCar.setCarName("Ghost Car");

        // Execute update
        Car result = carRepository.update("non-existent-id", updatedCar);

        // Verify
        assertNull(result);
        assertEquals(0, getRepositorySize()); // Repository remains empty
    }

    @Test
    void testDelete_WhenCarNotExists() {
        // Initial state
        int initialSize = getRepositorySize();

        // Execute delete
        carRepository.delete("non-existent-id");

        // Verify no changes
        assertEquals(initialSize, getRepositorySize());
    }

    @Test
    void testCreate_WithDuplicateId() {
        // Create first car
        Car car1 = new Car();
        car1.setCarId("dupe-id");
        carRepository.create(car1);

        // Create second car with same ID
        Car car2 = new Car();
        car2.setCarId("dupe-id");
        carRepository.create(car2);

        // Verify both exist
        assertEquals(2, getRepositorySize());
    }

    @Test
    void testAutoGenerateId_WhenIdNotProvided() {
        Car car = new Car();
        car.setCarName("Toyota Avanza");
        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar.getCarId());
        assertFalse(createdCar.getCarId().isEmpty());
    }

    // HELPER METHOD
    private int getRepositorySize() {
        Iterator<Car> iterator = carRepository.findAll();
        int size = 0;
        while (iterator.hasNext()) {
            iterator.next();
            size++;
        }
        return size;
    }
}