package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    public void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("car"))
                .andExpect(view().name("CreateCar"));
    }

    @Test
    public void testCreateCarPost() throws Exception {
        Car dummyCar = new Car();
        dummyCar.setCarId("dummy-id");
        dummyCar.setCarName("Test Car");
        dummyCar.setCarColor("Red");
        dummyCar.setCarQuantity(10);

        when(carService.create(any(Car.class))).thenReturn(dummyCar);

        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Test Car")
                        .param("carColor", "Red")
                        .param("carQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    public void testCarListPage() throws Exception {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Test Car");
        car.setCarColor("Blue");
        car.setCarQuantity(5);

        when(carService.findAll()).thenReturn(Arrays.asList(car));

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cars"))
                .andExpect(view().name("CarList"));

        verify(carService, times(1)).findAll();
    }

    @Test
    public void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Test Car");
        car.setCarColor("Green");
        car.setCarQuantity(15);

        when(carService.findById("123")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("car"))
                .andExpect(view().name("EditCar"));

        verify(carService, times(1)).findById("123");
    }

    @Test
    public void testEditCarPost() throws Exception {
        doNothing().when(carService).update(anyString(), any(Car.class));

        mockMvc.perform(post("/car/editCar")
                        .param("carId", "123")
                        .param("carName", "Updated Car")
                        .param("carColor", "Black")
                        .param("carQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).update(anyString(), any(Car.class));
    }

    @Test
    public void testDeleteCar() throws Exception {
        doNothing().when(carService).deleteCarById("123");

        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).deleteCarById("123");
    }
}