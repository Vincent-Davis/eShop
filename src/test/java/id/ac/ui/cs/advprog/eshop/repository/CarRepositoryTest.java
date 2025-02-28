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
    void testFindById() {
        Car car = new Car();
        car.setCarId("999");
        carRepository.create(car);

        Car foundCar = carRepository.findById("999");
        assertEquals(car.getCarId(), foundCar.getCarId());
    }

    @Test
    void testUpdate() {
        Car originalCar = new Car();
        originalCar.setCarId("123");
        originalCar.setCarName("Original Name");
        carRepository.create(originalCar);

        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Name");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(10);

        carRepository.update("123", updatedCar);

        Car result = carRepository.findById("123");
        assertEquals("Updated Name", result.getCarName());
        assertEquals("Red", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("456");
        carRepository.create(car);

        carRepository.delete("456");
        assertNull(carRepository.findById("456"));
    }
}