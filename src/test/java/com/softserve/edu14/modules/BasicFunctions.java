package com.softserve.edu14.modules;

import com.softserve.edu14.tests.TestsRunner;
import com.softserve.edu14.utils.ElementNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.softserve.edu14.tests.TestsRunner.IMPLICIT_WAIT_SECONDS;
import static com.softserve.edu14.utils.ConfigLoader.getProperty;
import static com.softserve.edu14.utils.Utils.isUserCurrentlyLoggedIn;


public abstract class BasicFunctions {
    public WebDriver driver = TestsRunner.driver.get();
    public WebDriverWait wait = TestsRunner.wait.get();
    protected static final String BASE_URL = getProperty("BASE_URL");
    protected final Logger logger;

    public BasicFunctions() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public WebElement getElement(By locator) {
        return waitElementToBePresent(locator);
    }

    public BasicFunctions openPage(String path) {
        String resultPath = buildUrl(path);
        driver.get(resultPath);
        return this;
    }

    private String buildUrl(String path) {
        return BASE_URL + (path.startsWith("/") ? path : "/" + path);
    }

    public BasicFunctions inputText(By locator, String text) {
        WebElement element = getElement(locator);
        element.clear();
        element.click();
        element.sendKeys(text);
        element.sendKeys(Keys.TAB);
        return this;
    }

    public BasicFunctions clickElement(By locator) {
        waitElementToBeClickable(locator).click();
        return this;
    }

    public WebElement waitElementToBePresent(By locator) {
        return waitForElement(locator, ExpectedConditions::presenceOfElementLocated);
    }

    public WebElement waitElementToBeClickable(By locator) {
        return waitForElement(locator, ExpectedConditions::elementToBeClickable);
    }

    public boolean waitElementToDisappear(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (elements.isEmpty()) {
            return true;
        }
        return  handleWait(() -> wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)), locator);
    }

    private WebElement waitForElement(By locator, Function<By, ExpectedCondition<WebElement>> condition) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            return elements.get(0);
        }
        WebElement el = handleWait(() -> wait.until(condition.apply(locator)), locator);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        return el;
    }

    private <T> T handleWait(Supplier<T> waitAction, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            return waitAction.get();
        } catch (TimeoutException e) {
            throw new ElementNotFoundException(locator, driver, e);
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        }
    }

    public BasicFunctions switchLanguageToEnglish() {
        WebElement languageSwitcher = getElement(By.cssSelector("ul.header_lang-switcher-wrp"));
        if (languageSwitcher.getText().equalsIgnoreCase("ua")) {
            languageSwitcher.click();
            waitElementToBePresent(By.xpath("//li/span[text()='En']")).click();
        }
        return this;
    }

    public boolean validateUserLoggedIn() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        waitElementToBePresent(By.cssSelector(".name"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        return isUserCurrentlyLoggedIn();
    }
}
