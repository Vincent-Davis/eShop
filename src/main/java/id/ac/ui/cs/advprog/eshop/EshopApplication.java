package id.ac.ui.cs.advprog.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EshopApplication {


    private EshopApplication() {
        // PMD Fix : This utility class has a non-private constructor
    }

    public static void main(String[] args) {
        SpringApplication.run(EshopApplication.class, args);
    }

}
