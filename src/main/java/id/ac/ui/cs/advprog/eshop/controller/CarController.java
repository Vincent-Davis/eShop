    package id.ac.ui.cs.advprog.eshop.controller;

    import id.ac.ui.cs.advprog.eshop.model.Car;
    import id.ac.ui.cs.advprog.eshop.service.CarService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @Controller
    @RequestMapping("/car")
    public class CarController {

        @Autowired
        private CarService  carservice; // Injeksi via interface (OCP)

        @GetMapping("/createCar")
        public String createCarPage(Model model) {
            Car car = new Car();
            model.addAttribute("car", car);
            return "CreateCar";
        }

        @PostMapping("/createCar")
        public String createCarPost(@ModelAttribute Car car, Model model) {
            carservice.create(car);
            return "redirect:/car/listCar";
        }

        @GetMapping("/listCar")
        public String carListPage(Model model) {
            List<Car> allCars = carservice.findAll();
            model.addAttribute("cars", allCars);
            return "CarList";
        }

        @GetMapping("/editCar/{carId}")
        public String editCarPage(@PathVariable String carId, Model model) {
            Car car = carservice.findById(carId);
            model.addAttribute("car", car);
            return "EditCar";
        }

        @PostMapping("/editCar")
        public String editCarPost(@ModelAttribute Car car, Model model) {
            carservice.update(car.getCarId(), car);
            return "redirect:/car/listCar";
        }

        @PostMapping("/deleteCar")
        public String deleteCar(@RequestParam("carId") String carId) {
            carservice.deleteCarById(carId);
            return "redirect:/car/listCar";
        }
    }