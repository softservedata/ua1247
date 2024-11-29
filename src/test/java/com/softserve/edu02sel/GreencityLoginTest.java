package com.softserve.edu02sel;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class GreencityLoginTest {

    private static final String BASE_URL = "https://www.greencity.cx.ua/#/ubs";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static WebDriver driver;
    public static DriverService service;
    public static ChromeOptions options;

    @BeforeAll
    public static void setup() throws IOException {
        WebDriverManager.chromedriver().setup();
        //
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        driver = new ChromeDriver(options);
        //
        /*
        // https://peter.sh/experiments/chromium-command-line-switches/
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        //options.
        driver = new ChromeDriver(options);
        */
        //
        driver = new ChromeDriver();
        //
        /*
        service = new ChromeDriverService.Builder()
                // .usingDriverExecutable(new File("./lib/chromedriver.exe"))
                // .usingAnyFreePort()
                .usingPort(8888).build();
        service.start();
        //
        options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        //
        driver = new ChromeDriver((ChromeDriverService) service, options);
        */
        //
        //DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        //driver = new RemoteWebDriver(service.getUrl(), capabilities);
        //
        //driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        driver.manage().window().maximize();
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    public static void tear() {
        if (driver != null) {
            //driver.quit(); // close()
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
    public void checkSignIn() throws InterruptedException, IOException {
        System.out.println("Start ...");
        //
        driver.findElement(By.cssSelector("app-ubs .ubs-header-sing-in-img.ng-star-inserted")).click();
        Thread.sleep(1000); // For Presentation
        //
        // /*
        String handle = driver.getWindowHandle();
        //service.stop();
        System.out.println("service.stop() ...");
        //TimeUnit.SECONDS.sleep(8000);
        Thread.sleep(8000); // For Presentation
        //service.start();
        //driver = new ChromeDriver((ChromeDriverService) service, options);
        //driver.switchTo().window(handle);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        Thread.sleep(4000); // For Presentation
        // */
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
        /*
        ((JavascriptExecutor)driver)
                .executeScript("document.querySelector('button.ubsStyle').removeAttribute('disabled')");
        ((JavascriptExecutor)driver)
                .executeScript("document.querySelector('button.ubsStyle').click()");
        //driver.findElement(By.id("button.ubsStyle")).click();
        */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("window.localStorage.setItem('%s','%s');", "key123", "value123"));
        //Assertions.assertEquals("https://www.apple.com/ua/mac/", mac.getAttribute("href"));
    }
}
