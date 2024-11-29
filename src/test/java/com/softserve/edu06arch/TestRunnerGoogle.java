package com.softserve.edu06arch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@ExtendWith(RunnerSuccessful.class)
public abstract class TestRunnerGoogle {
    private static final String BASE_URL = "https://www.google.com.ua/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static final Long ONE_SECOND_DELAY = 1000L;
    private final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    protected static Boolean isTestSuccessful = false;
    //
    //protected static GoogleMod googleMod;
    protected static WebDriver driver;

    protected static void presentationSleep() {
        presentationSleep(1);
    }

    // Overload
    protected static void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
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
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        //
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        driver = new ChromeDriver(options);
        //
        //driver = new ChromeDriver();
        //
        // in CMD: echo %SystemDrive%%HOMEPATH%
        String userProfile = System.getenv("SystemDrive")
                + System.getenv("HOMEPATH")
                + "\\AppData\\Local\\Google\\Chrome\\User Data";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-web-security");
        //options.addArguments("--ignore-certificate-errors");
        options.addArguments("--user-data-dir=" + userProfile);
        driver = new ChromeDriver(options);
        //
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        //driver.manage().window().maximize();
        //
        //googleMod = new GoogleMod(driver);
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
        //driver.get(BASE_URL);
        Thread.sleep(2000); // For Presentation
        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis(TestInfo testInfo) throws InterruptedException {
        if (!isTestSuccessful) {
            System.out.println("\t\t\tgetTestMethod = " + testInfo.getTestMethod());
            System.out.println("\t\t\tgetDisplayName = " + testInfo.getDisplayName());
            // delete session
            takeScreenShot();
            takePageSource(); // Default sources
            // TODO JS sources
        }
        // Sign out
        // delete All Cookies;
        driver.manage().deleteAllCookies();
        // Clear session
        // delete All Tokens;
        //localStorageJS.clearLocalStorage();
        //localStorageJS.removeItemFromLocalStorage("accessToken");
        //localStorageJS.removeItemFromLocalStorage("refreshToken");
        Thread.sleep(4000); // For Presentation
        System.out.println("\t@AfterEach executed");
    }

    /*
    private void closePopup() {
        presentationSleep(); // For Presentation ONLY
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        long timeStart = System.currentTimeMillis();
        List<WebElement> footerButton = driver.findElements(By.xpath("//footer[contains(@class,'cookie')]//button"));
        System.out.println("***footerButton.size() = " + footerButton.size());
        System.out.println("***time = " + (System.currentTimeMillis() - timeStart));
        if (footerButton.size() > 0) {
            footerButton.get(0).click();
            presentationSleep(); // For Presentation ONLY
        }
        //driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
    }
    */

    protected GoogleMod loadApplication() {
        driver.get(BASE_URL);
        //closePopup();
        TestRunnerGoogle.presentationSleep(); // For Presentation
        return new GoogleMod(driver);
    }
}
