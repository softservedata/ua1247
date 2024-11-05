package com.softserve.edu02sel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Start {
    private final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";

    private void takeScreenShot(WebDriver driver) throws IOException {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./" + currentTime + "_screenshot.png"));
    }

    @Test
    public void checkOpen() throws Exception {
        WebDriverManager.chromedriver().setup();
        System.out.println("WebDriverManager done");
        //
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        if ((System.getenv("IS_HEADLESS") != null)
                && (System.getenv("IS_HEADLESS").equals("true"))) {
            options.addArguments("--headless");
            System.out.println("\tadd --headless");
        }
        WebDriver driver = new ChromeDriver(options);
        System.out.println("Chrome started");
        //
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://speak-ukrainian.org.ua/club/27");
        System.out.println("driver.get done");
        //
        driver.findElement(By.xpath("//a[@href='/clubs']")).click();
        Thread.sleep(1000);
        System.out.println("clubs done");
        //
        takeScreenShot(driver);
        Thread.sleep(8000);
        driver.quit();
    }
}
