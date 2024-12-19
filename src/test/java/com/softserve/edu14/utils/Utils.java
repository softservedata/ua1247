package com.softserve.edu14.utils;

import com.softserve.edu14.data.TestResultContextData;
import com.softserve.edu14.tests.TestsRunner;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static com.softserve.edu14.tests.TestsRunner.IMPLICIT_WAIT_SECONDS;
import static com.softserve.edu14.utils.ConfigLoader.getProperty;


public class Utils {
    public static WebDriver driver = TestsRunner.driver.get();
    public WebDriverWait wait = TestsRunner.wait.get();
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss";
    private static final String SCREENSHOT_PATH = getProperty("SCREENSHOT.PATH");

    public static boolean isUserCurrentlyLoggedIn() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        boolean isUserNamePresent = !driver.findElements(By.cssSelector(".name")).isEmpty();
        boolean isProfilePageOpened = driver.getCurrentUrl().contains("profile");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        return isUserNamePresent && isProfilePageOpened;
    }

    public static void clearLocalStorage() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
        driver.navigate().refresh();
    }

    public static String prettifyTestInfo(TestResultContextData testResultContext) {
        String testInfo = testResultContext.testInfo();
        String errorInfo = testResultContext.errorInfo();
        return String.format("""
                {
                    TEST_INFO: %s
                    ERROR_INFO: %s
                }""", testInfo, errorInfo.trim());
    }

    public static void takeScreenShot(String methodName) {
        String fileName = new StringBuilder(SCREENSHOT_PATH)
                .append(methodName)
                .append("_")
                .append(new SimpleDateFormat(TIME_TEMPLATE).format(new Date()))
                .append(".png")
                .toString();

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File targetFile = new File(fileName);

        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        try {
            if (!targetFile.getParentFile().exists() && !targetFile.getParentFile().mkdirs()) {
                throw new IOException("Failed to create directories for path: " + targetFile.getParentFile().getAbsolutePath());
            }
            FileUtils.copyFile(scrFile, targetFile);
        } catch (IOException e) {
            logger.error("Failed to handle screenshot file operation: " + targetFile.getAbsolutePath(), e);
        } catch (SecurityException e) {
            logger.error("Insufficient permissions to save screenshot: " + targetFile.getAbsolutePath(), e);
        }
    }
}
