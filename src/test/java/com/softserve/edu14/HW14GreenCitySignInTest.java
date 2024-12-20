package com.softserve.edu14;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HW14GreenCitySignInTest {

    private static final String BASE_URL = "http://localhost:4205/#/greenCity";
    private static final long IMPLICITLY_WAIT_SECONDS = 1L;
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(HW14GreenCitySignInTest.class);

    @FindBy(css = "img.ubs-header-sing-in-img")
    private WebElement signInButton;

    @FindBy(css = ".ng-star-inserted > h1")
    private WebElement welcomeText;
    @FindBy(css = ".ng-star-inserted > h2")
    private WebElement signInDetailsText;

    @FindBy(css = "form > label[for ='email']")
    private WebElement emailLabel;
    @FindBy(css = "input#email")
    private WebElement emailPlaceholder;
    @FindBy(css = "form > label[for ='password']")
    private WebElement passwordLabel;
    @FindBy(css = "input#password")
    private WebElement passwordPlaceholder;

    @FindBy(id = "email")
    private WebElement emailInput;
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(css = ".greenStyle")
    private WebElement signInSubmitButton;
    @FindBy(css = "p.name")
    private WebElement name;

    @FindBy(css = "li .header_user-name")
    private WebElement menu;
    @FindBy(css = "li[aria-label='sign-out'] a")
    private WebElement signOut;

    @FindBy(css = ".alert-general-error")
    private WebElement errorMessage;

    @FindBy(xpath = "//*[@id='pass-err-msg']/app-error/div")
    private WebElement errorPassword;

    @FindBy(xpath = "//*[@id='email-err-msg']/app-error/div")
    private WebElement errorEmail;

    @FindBy(css = "img[alt='close button']")
    private WebElement closeButton;

    @BeforeAll
    public void setUp() {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
        logger.info("Setup completed. Browser launched.");
    }

    @BeforeEach
    public void setupEach() {
        driver.get(BASE_URL);
        logger.info("Navigated to " + BASE_URL);
    }

    @AfterEach
    public void tearThis() throws InterruptedException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            if (name.isDisplayed()) {
                wait.until(ExpectedConditions.visibilityOf(menu));
                menu.click();

                wait.until(ExpectedConditions.visibilityOf(signOut));
                signOut.click();

                logger.info("Sign out completed.");
            }
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            logger.warn("Sign out not required or timed out: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage());
        } finally {
            driver.manage().deleteAllCookies();
            logger.info("@AfterEach executed");
        }
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
        logger.info("@AfterAll executed");
    }

    @DisplayName("Verify that title is correct")
    @Test
    public void verifyTitle() {
        try {
            logger.info("Verifying the title of the page.");
            assertEquals("GreenCity", driver.getTitle());
            logger.info("Title is " + driver.getTitle());
        } catch (Exception ex) {
            logger.error("An error occurred: " + ex.getMessage());
        }
    }

    @DisplayName("Verify that error message is displayed and 'Sing in' button is disabled with invalid email")
    @ParameterizedTest
    @CsvSource(value = {
            "samplestesgreencity.com:Перевірте, чи правильно вказано вашу адресу електронної пошти",
            "'':Введіть пошту",
            "'      ':Введіть пошту"

    }, delimiter = ':')
    public void emailNotValid(String email, String message) {

        try {
            logger.info("Starting email validation test with input: " + email);
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            signInButton.click();
            emailInput.sendKeys(email);
            emailLabel.click();
            wait.until(ExpectedConditions.visibilityOf(errorEmail));
            assertThat(errorEmail.getText(), containsString(message));
            logger.info("'" + message + "'" + " error message appears after inputting invalid email");
            assertEquals(signInSubmitButton.getAttribute("disabled"),("true"));
            signInSubmitButton.click();
            wait.until(ExpectedConditions.invisibilityOf(name));
            logger.info("'Sing in' button is disabled");
            closeButton.click();

        } catch (Exception ex) {
            logger.error("An error occurred: " + ex.getMessage());
        }
    }

    @DisplayName("Verify that error message is displayed and 'Sign in' button is disabled with invalid password")
    @ParameterizedTest
    @CsvSource({
            "'', EMPTY_PASSWORD",
            "'     ', SHORT_PASSWORD",
            "1, SHORT_PASSWORD",
            "1234567, SHORT_PASSWORD",
            "123456789012345678901, LONG_PASSWORD"
    })
    public void passwordNotValid(String password, PasswordErrorMessages errorMessage) {
        try {
            logger.info("Starting password validation test with input: " + password);
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
            passwordInput.sendKeys(password);
            passwordLabel.click();
            wait.until(ExpectedConditions.visibilityOf(errorPassword));
            assertThat(errorPassword.getText(), containsString(errorMessage.getMessage()));
            logger.info("'" + errorMessage.getMessage() + "'" + " error message appears after inputting invalid password.");
            assertEquals(signInSubmitButton.getAttribute("disabled"),("true"));
            logger.info("'Sign in' button is disabled.");
            if (closeButton.isDisplayed()) {
                closeButton.click();
            }
        } catch (Exception ex) {
            logger.error("An error occurred: " + ex.getMessage());
        }
    }

    @DisplayName("Verify that error message is displayed and 'Sing in' button is disabled with blank email and password")
    @ParameterizedTest
    @CsvSource({
            "'', '', Введіть пошту, Будь ласка заповніть всі обов'язкові поля"

    })
    public void signInValid(String email, String password, String emailMessage, String passwordMessage) {

        try {
            logger.info("Starting validation test with blank email and password.");
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            signInButton.click();

            emailInput.sendKeys(email);
            emailLabel.click();
            wait.until(ExpectedConditions.visibilityOf(errorEmail));
            assertThat(errorEmail.getText(), containsString(emailMessage));
            logger.info("'" + emailMessage + "'" + " error message appears after inputting invalid email");

            passwordInput.sendKeys(password);
            passwordLabel.click();

            wait.until(ExpectedConditions.elementToBeClickable(signInSubmitButton));
            signInSubmitButton.click();

            wait.until(ExpectedConditions.invisibilityOf(name));
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            assertThat(errorMessage.getText(), containsString(passwordMessage));
            logger.info("'" + passwordMessage + "'" + " error message appears after inputting invalid email and password");

            if (closeButton.isDisplayed()) {
                closeButton.click();
            }
        } catch (Exception ex) {
            logger.error("An error occurred: " + ex.getMessage());
        }
    }

    @DisplayName("Verify that user is signed in successfully with valid email and password")
    @ParameterizedTest
    @MethodSource("credentialsForValidSignIn")
    public void signInValid(String email, String password, String userName) {
        try {
            logger.info("Starting sign-in test with valid credentials.");
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            signInButton.click();
            emailInput.sendKeys(email);
            passwordInput.sendKeys(password);
            signInSubmitButton.click();
            wait.until(ExpectedConditions.visibilityOf(name));
            assertEquals(userName, name.getText());
            logger.info("User is signed in successfully with: " + email);
        } catch (Exception ex) {
            logger.error("An error occurred: " + ex.getMessage());
        }
    }

    private Stream<Arguments> credentialsForValidSignIn() {
        return Stream.of(
                Arguments.of("emailfortest@gmail.com", "Qwerty!2345", "NataliiaHrusha")
        );
    }

}
