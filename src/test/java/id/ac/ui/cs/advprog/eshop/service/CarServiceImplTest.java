package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Car car = new Car();
        when(carRepository.create(car)).thenReturn(car);

        Car result = carService.create(car);
        assertEquals(car, result);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        Car car2 = new Car();
        List<Car> cars = Arrays.asList(car1, car2);

        when(carRepository.findAll()).thenReturn(cars.iterator());

        List<Car> result = carService.findAll();
        assertEquals(2, result.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarId("123");
        when(carRepository.findById("123")).thenReturn(car);

        Car result = carService.findById("123");
        assertEquals(car, result);
        verify(carRepository, times(1)).findById("123");
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarId("123");
        when(carRepository.update("123", car)).thenReturn(car);

        carService.update("123", car);
        verify(carRepository, times(1)).update("123", car);
    }

    @Test
    void testDelete() {
        doNothing().when(carRepository).delete("123");
        carService.deleteCarById("123");
        verify(carRepository, times(1)).delete("123");
    }
}