package com.softserve.edu11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
@ExtendWith(RunnerSuccess.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestRunner {
        private static final String BASE_URL = "http://localhost:4205/#/greenCity";
        private static final long IMPLICITLY_WAIT_SECONDS = 10L;
        private static final long TWO_SECOND_DELAY = 3;
        protected static Boolean isTestSuccessful = false;
        private final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
        private WebDriver driver;
        private static WebDriverWait wait;
    protected static GreenCityLogged greencityLogged;

        protected GreenCity greenCity;
        public static void presentationSleep() {
            presentationSleep(1);
        }

        // Overload
        public static void presentationSleep(int seconds) {
            try {
                Thread.sleep(seconds * TWO_SECOND_DELAY); // For Presentation ONLY
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    private void takeScreenShot() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./" + currentTime + "_screenshot.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // Use Custom Exception
        }
    }
    private void takePageSource() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        String pageSource = driver.getPageSource();
        byte[] strToBytes = pageSource.getBytes();
        Path path = Paths.get("./" + currentTime + "_source.html");
        try {
            Files.write(path, strToBytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        @BeforeAll
        public  void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(TWO_SECOND_DELAY));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
            driver.manage().window().maximize();
            greencityLogged = new GreenCityLogged(driver);
        }
        @AfterAll
        public void tear() {
            if (driver != null) {
                driver.quit(); // close()
            }
            System.out.println("@AfterAll executed");
        }
        @BeforeEach
        public void setupThis() {
            greenCity = new GreenCity(driver, wait);
            greenCity.openSignInPage(BASE_URL);
            ((JavascriptExecutor) driver).executeScript("window.localStorage.setItem('language', 'en');");
            driver.navigate().refresh();
            switchLanguageToEnglish();
            greenCity.clickSignInButton();
            waitElementToBeVisible(By.tagName("app-auth-modal"));
        }
        @AfterEach
        public void tearThis(TestInfo testInfo)  {
            List<WebElement> userProfile = driver.findElements(By.cssSelector("a.header_user-name"));
            if (!userProfile.isEmpty()) {
                logOut();
            }
            if (!isTestSuccessful) {
                System.out.println("\t\t\tgetTestMethod = " + testInfo.getTestMethod());
                System.out.println("\t\t\tgetDisplayName = " + testInfo.getDisplayName());
                // delete session
                takeScreenShot();
                takePageSource(); // Default sources
                // TODO JS sources
            }
            driver.manage().deleteAllCookies();


        }

        private static WebElement waitElementToBeClickable(By locator) {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        }

        private  void waitElementToBeVisible(By locator) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        }

        private  boolean waitElementToDisappear(By locator) {
            try {
                return wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
            } catch (TimeoutException e) {
                return false;
            }
        }
        private void switchLanguageToEnglish() {
            WebElement languageSwitcher = driver.findElement(By.cssSelector("ul.header_lang-switcher-wrp"));
            String currentLanguage = languageSwitcher.getText();
            if (currentLanguage.equalsIgnoreCase("ua")) {
                languageSwitcher.click();
                waitElementToBeClickable(By.xpath("//li/span[text()='En']")).click();
            }
        }


        private void logOut() {
            driver.findElement(By.cssSelector("a.header_user-name")).click();
            driver.findElement(By.cssSelector("li[aria-label='sign-out']")).click();
        }


    }

