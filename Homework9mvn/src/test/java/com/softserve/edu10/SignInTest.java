package com.softserve.edu10;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignInTest {
    private static final String BASE_URL = "http://localhost:4205/#/greenCity";
    private static final long IMPLICITLY_WAIT_SECONDS = 10L;
    private static final long TWO_SECOND_DELAY = 2;
    private WebDriver driver;
    private static WebDriverWait wait;

    private GreenCitySignIn greenCitySignIn;
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
    @BeforeAll
    public  void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TWO_SECOND_DELAY));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        driver.manage().window().maximize();
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
       greenCitySignIn = new GreenCitySignIn(driver, wait);
       greenCitySignIn.openSignInPage(BASE_URL);
       ((JavascriptExecutor) driver).executeScript("window.localStorage.setItem('language', 'en');");
       driver.navigate().refresh();
        switchLanguageToEnglish();
        greenCitySignIn.clickSignInButton();
        waitElementToBeVisible(By.tagName("app-auth-modal"));
    }
    @AfterEach
    public void tearThis()  {
        List<WebElement> userProfile = driver.findElements(By.cssSelector("a.header_user-name"));
            if (!userProfile.isEmpty()) {
                logOut();
            }
            driver.manage().deleteAllCookies();

    }
    @Test
    public void verifyTitle() {
        Assertions.assertEquals("Welcome back!", greenCitySignIn.getWelcomeText());
        Assertions.assertEquals("Please enter your details to sign in.", greenCitySignIn.getSignInDetailsText());
        Assertions.assertTrue(greenCitySignIn.isForgotPasswordVisible(), "Forgot password link should be visible.");
        Assertions.assertTrue(greenCitySignIn.isSignUpLinkVisible(), "Sign-up link should be visible.");
        Assertions.assertTrue(greenCitySignIn.isGoogleSignInButtonVisible(), "Google Sign-In button should be visible.");
        Assertions.assertFalse(greenCitySignIn.isSubmitButtonEnabled(), "Sign In button should be disabled by default.");
    }
    @ParameterizedTest
    @CsvSource({ "Roman.tsvyk.pb.2018@lpnu.ua,090198_Ts"})
    public void signInPositive(String email, String password) {
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertTrue(driver.getCurrentUrl().contains("profile"), "URL does not contain 'profile'.");
        Assertions.assertEquals("Oleksandr", driver.findElement(By.cssSelector(".name")).getText(),
                "User name does not match.");
    }
    @ParameterizedTest
    @CsvSource({ "sdgnlanglkgn,090193",
    "@,vvhkvhk",
    "bebebeb@,hahaha",
            "kristina@gmail.con,ha_Tshf3"
    })
    public void signInNegativeBoth(String email, String password) {
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Please check that your e-mail address is indicated correctly", greenCitySignIn.getErrorEmailText(),
                String.format("Unexpected error message for email: %s", email));
        Assertions.assertEquals("Password must be at least 8 characters long without spaces", greenCitySignIn.getErrorPasswordText(),
                String.format("Unexpected error message for password: %s", password));
    }
    @ParameterizedTest
    @CsvSource({
            "kristina@gmail.com,ha_Tshf3"
    })
    public void signInNonExistentUser(String email, String password) {
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Bad email or password", greenCitySignIn.getGeneralErrorText());
    }
    @ParameterizedTest
    @CsvSource({ "sdgnlanglkgn,090193_Gh",
            "@gmail.com,13_gfhThgf",
            "bebebeb@,hahaha&65Ty",
            "bebebebhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh@,rt5H&jkhfd"})
    public void signInWrongEmailGoodPassword(String email, String password) {
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Please check that your e-mail address is indicated correctly", greenCitySignIn.getErrorEmailText(),
                String.format("Unexpected error message for email: %s", email));

    }
    @ParameterizedTest
    @CsvSource({ "sdgnlanglkgn,_ _ _ __",
            "@,!@#$%^&*()_!@#$%^&*()!@#$%^&*((!@#$%^&*((#@$%^&**#$%^^&&",
            "bebebeb@,5 7 1 fgagr31419",
            "bebebebhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh@,ha--------                    haha"})
    public void signInLongPassword(String email, String password) {
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Please check that your e-mail address is indicated correctly", greenCitySignIn.getErrorEmailText(),
                String.format("Unexpected error message for email: %s", email));
        Assertions.assertEquals("Password must be less than 20 characters long without spaces.", greenCitySignIn.getErrorPasswordText(),
                String.format("Unexpected error message for password: %s", password));
    }
    @ParameterizedTest
    @CsvSource({ "Roman.tsvyk.pb.2018@lpnu.ua,090193",
            "Roman.tsvyk.pb.2018@lpnu.ua,@#657"})
    public void signInGoodEmailWrongPassword(String email, String password) {
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Password must be at least 8 characters long without spaces",greenCitySignIn.getErrorPasswordText());
    }
    @Test
    public void signInEmptyCredentials() {
        String email="";
        String password="";
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Please fill all red fields",greenCitySignIn.getGeneralErrorText());
    }
    @Test
    public void signInEmptyEmail() {
        String email="";
        String password="ert$_Ythfd";
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Email is required",greenCitySignIn.getErrorEmailText());
    }
    @Test
    public void signInEmptyPassword() {
        String email="kristina@gmail.com";
        String password="";
        greenCitySignIn.inputCredentials(email, password);
        greenCitySignIn.clickSubmitButton();
        Assertions.assertEquals("Password is required",greenCitySignIn.getErrorPasswordText());
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
