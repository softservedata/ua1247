package com.softserve.edu02sel;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GreencityLoginTest {

    private static final String BASE_URL = "https://www.greencity.cx.ua/#/ubs";
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
        Thread.sleep(8000); // For Presentation
        System.out.println("\t@AfterEach executed");
    }

    @Test
    public void checkSignIn() throws InterruptedException {
        System.out.println("Start ...");
        //
        driver.findElement(By.cssSelector("app-ubs .ubs-header-sing-in-img.ng-star-inserted")).click();
        Thread.sleep(1000); // For Presentation
        //
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("ggv14884@inohm.com");
        Thread.sleep(1000); // For Presentation
        //
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Qwerty_1");
        Thread.sleep(2000); // For Presentation
        //
        /*
        WebElement shadowDiv = driver.findElement(By.cssSelector("div#turnstile-container div"));
        WebElement shadow1 = (WebElement) ((JavascriptExecutor)driver)
                .executeScript("return arguments[0].shadowRoot", shadowDiv);
        System.out.println("shadow1 = " + shadow1);
        //
        //
        WebElement shadowRoot = driver.findElement(By.cssSelector("div#turnstile-container div"))
                .getShadowRoot()
                .findElement(By.cssSelector("iframe"));
        driver.switchTo().frame(shadowRoot);
        WebElement shadowRoot2 = driver.findElement(By.cssSelector("body"))
                .getShadowRoot()
                .findElement(By.id("success-text"));
        System.out.println("shadowRoot2.getText() = " + shadowRoot2.getText());
        */
        //
        ((JavascriptExecutor)driver)
                .executeScript("document.querySelector('button.ubsStyle').removeAttribute('disabled')");
        ((JavascriptExecutor)driver)
                .executeScript("document.querySelector('button.ubsStyle').click()");
        //driver.findElement(By.id("button.ubsStyle")).click();
        //
        //Assertions.assertEquals("https://www.apple.com/ua/mac/", mac.getAttribute("href"));
    }
}
