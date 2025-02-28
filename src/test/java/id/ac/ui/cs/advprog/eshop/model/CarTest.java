package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(5);
    }

    @Test
    void testGetCarId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Avanza", car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Silver", car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(5, car.getCarQuantity());
    }
}