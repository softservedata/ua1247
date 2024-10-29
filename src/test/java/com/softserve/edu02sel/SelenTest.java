package com.softserve.edu02sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SelenTest {
    private static final String BASE_URL = "https://www.google.com.ua/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        //
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        //
        //driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        driver.manage().window().maximize();
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        if (driver != null) {
            driver.quit(); // close()
        }
        System.out.println("@AfterAll executed");
    }

    @BeforeEach
    public void setupThis() throws InterruptedException {
        driver.get(BASE_URL);
        Thread.sleep(2000); // For Presentation
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis() throws InterruptedException {
        // Sign out
        // Clear session
        Thread.sleep(4000); // For Presentation
        System.out.println("\t@AfterEach executed");
    }

    @Test
    public void checkGoogleMac() throws InterruptedException {
        System.out.println("Start ...");
        //
        driver.findElement(By.name("q")).sendKeys("mac");
        Thread.sleep(2000); // For Presentation
        //
        //driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        //
        WebElement mac = driver.findElement(By.xpath("//h3[text()='Mac - Apple (UA)']/.."));
        System.out.println("mac.href = " + mac.getAttribute("href"));
        Assertions.assertEquals("https://www.apple.com/ua/mac/", mac.getAttribute("href"));
    }
}
