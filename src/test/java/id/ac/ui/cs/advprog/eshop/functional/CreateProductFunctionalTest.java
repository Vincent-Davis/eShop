package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProduct_Success(ChromeDriver driver) {
        // 1️⃣ Navigasi ke halaman Create Product
        driver.get(baseUrl + "/create");

        // 2️⃣ Isi form produk
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        nameInput.sendKeys("Shampoo XYZ");
        quantityInput.sendKeys("50");

        // 3️⃣ Klik tombol Submit
        submitButton.click();

        // 4️⃣ Verifikasi redirect ke halaman Product List
        assertEquals(baseUrl + "/list", driver.getCurrentUrl());

        // 5️⃣ Pastikan produk muncul di daftar
        WebElement productTable = driver.findElement(By.tagName("table"));
        assertTrue(productTable.getText().contains("Shampoo XYZ"));
        assertTrue(productTable.getText().contains("50"));
    }

    @Test
    void testCreateProduct_Fail_EmptyName(ChromeDriver driver) {
        // 1️⃣ Navigasi ke halaman Create Product
        driver.get(baseUrl + "/create");

        // 2️⃣ Isi form tanpa nama produk
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        quantityInput.sendKeys("30");

        // 3️⃣ Klik tombol Submit
        submitButton.click();

        // 4️⃣ Pastikan masih di halaman Create Product (validasi gagal)
        assertEquals(baseUrl + "/create", driver.getCurrentUrl());
    }

    @Test
    void testCreateProduct_Fail_EmptyQuantity(ChromeDriver driver) {
        // 1️⃣ Navigasi ke halaman Create Product
        driver.get(baseUrl + "/create");

        // 2️⃣ Isi form nama produk
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        nameInput.sendKeys("Body Wash ABC");

        // 3️⃣ Hapus nilai default di quantity (backspace sekali)
        quantityInput.clear();

        // 4️⃣ Klik tombol Submit
        submitButton.click();

        // 5️⃣ Pastikan masih di halaman Create Product (validasi gagal)
        assertEquals(baseUrl + "/create", driver.getCurrentUrl());
    }

}
