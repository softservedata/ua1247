package com.softserve.edu14.tests;

import com.softserve.edu14.data.TestResultContextData;
import com.softserve.edu14.utils.TestResultStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.Duration;

import static com.softserve.edu14.utils.ConfigLoader.getLongProperty;
import static com.softserve.edu14.utils.ConfigLoader.getProperty;
import static com.softserve.edu14.utils.Utils.*;

@ExtendWith(TestResultStatus.class)
public abstract class TestsRunner {
    public static final long IMPLICIT_WAIT_SECONDS = getLongProperty("IMPLICIT_WAIT_SECONDS");
    protected static final long EXPLICIT_WAIT_SECONDS = getLongProperty("EXPLICIT_WAIT_SECONDS");
    protected static final long EXPLICIT_POLLING_EVERY = getLongProperty("EXPLICIT_POLLING_EVERY");
    protected static final String BASE_URL = getProperty("BASE_URL");

    public static TestResultContextData testResultContext;
    public static ThreadLocal<WebDriver> driver;
    public static ThreadLocal<WebDriverWait> wait;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    TestsRunner() {
        wait = ThreadLocal.withInitial(
                () -> {
                    WebDriverWait localWait = new WebDriverWait(driver.get(), Duration.ofSeconds(EXPLICIT_WAIT_SECONDS));
                    localWait.pollingEvery(Duration.ofMillis(EXPLICIT_POLLING_EVERY));
                    return localWait;
                });
        driver = ThreadLocal.withInitial(ChromeDriver::new);
    }

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        driver.get().manage().window().maximize();
    }

    @BeforeEach
    void init() {
        driver.get().manage().deleteAllCookies();
    }

    @AfterEach
    void tearDownEach(TestInfo testInfo) {
        if (testResultContext.isTestFailed()) {
            String methodName = testInfo.getTestMethod()
                    .map(Method::getName)
                    .orElse("unknownMethod");
            takeScreenShot(methodName);
            logger.error("Test failed, details " + prettifyTestInfo(testResultContext));
        }

        if (isUserCurrentlyLoggedIn()) {
            clearLocalStorage();
        }
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.get().quit();
        }
    }

}
