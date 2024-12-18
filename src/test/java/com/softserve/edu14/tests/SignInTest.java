package com.softserve.edu14.tests;

import com.softserve.edu14.data.TestUser;
import com.softserve.edu14.modules.SignInPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import static com.softserve.edu14.utils.Utils.takeScreenShot;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignInTest extends TestsRunner {
    private SignInPage signInPage;

    @BeforeEach
    public void init() {
        try {
            super.init();
            signInPage = new SignInPage();
            signInPage.openSignInPage("/greenCity")
                    .switchLanguageToEnglish()
                    .clickSignInButton();
        } catch (RuntimeException  exception) {
            takeScreenShot("SignInTest_init");
            logger.error("@BeforeEach error: ", exception);
        }
    }

    @Test
    public void testSignInFormUI() {
        logger.info("Testing sign-in form UI elements visibility.");

        Assertions.assertEquals("Welcome back!", signInPage.getWelcomeText());
        Assertions.assertEquals("Please enter your details to sign in.", signInPage.getSignInDetailsText());
        Assertions.assertTrue(signInPage.isForgotPasswordVisible());
        Assertions.assertTrue(signInPage.isSignUpLinkVisible());
        Assertions.assertTrue(signInPage.isGoogleSignInButtonVisible());
        Assertions.assertFalse(signInPage.isSubmitButtonEnabled());
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#validUser")
    public void testPositiveSignIn(TestUser user) {
        logger.info("Signing in START with valid credentials: " + user);

        signInPage.inputCredentials(user.email(), user.password())
                .clickSubmitButton();

        Assertions.assertTrue(signInPage.validateUserLoggedIn(), "The user is not logged in");
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#userWithEmptyFields")
    public void testEmptyFieldsValidation(TestUser user, String errorText) {
        logger.info("Signing in with empty fields of credentials: " + user + ". Expected error: " + errorText);

        signInPage.inputCredentials(user.email(), user.password());

        Assertions.assertFalse(signInPage.isSubmitButtonClickable());
        Assertions.assertEquals(errorText, signInPage.getGeneralErrorText());
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#userWithEmptyPassword")
    public void testPasswordFieldEmpty(TestUser user, String errorText) {
        logger.info("Signing in with empty password: " + user + ". Expected error: " + errorText);

        signInPage.inputCredentials(user.email(), user.password())
                .clickSubmitButton();

        Assertions.assertEquals(errorText, signInPage.getErrorPasswordText());
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#userWithInvalidPassword")
    public void testIncorrectPasswordField(TestUser user, String errorText) {
        logger.info("Signing in with incorrect password: " + user + ". Expected error: " + errorText);

        signInPage.inputCredentials(user.email(), user.password())
                .clickSubmitButton();

        Assertions.assertEquals(errorText, signInPage.getGeneralErrorText());
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#userWithEmptyEmail")
    public void testEmailFieldEmpty(TestUser user, String errorText) {
        logger.info("Signing in with empty email: " + user + ". Expected error: " + errorText);

        signInPage.inputCredentials(user.email(), user.password());

        Assertions.assertEquals(errorText, signInPage.getErrorEmailText());
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#userWithIllegalEmail")
    public void testEmailValidationNegative(TestUser user, String errorText) {
        logger.info("Signing in with illegal email: " + user + ". Expected error: " + errorText);

        signInPage.inputCredentials(user.email(), user.password())
                .clickSubmitButton();

        Assertions.assertEquals(errorText, signInPage.getErrorEmailText());
    }

    @ParameterizedTest
    @MethodSource("com.softserve.edu14.data.TestUserRepository#userWithIllegalPassword")
    public void testPasswordValidationNegative(TestUser user, String errorText) {
        logger.info("Signing in with illegal password: " + user + ". Expected error: " + errorText);

        signInPage.inputCredentials(user.email(), user.password())
                .clickSubmitButton();

        Assertions.assertEquals(errorText, signInPage.getErrorPasswordText());
    }


    @Test
    public void testCloseSignInWindow() {
        logger.info("Test operation of 'close' button in the signing in modal window");

        signInPage.closeModal();

        Assertions.assertTrue(signInPage.waitElementToDisappear(By.tagName("app-auth-modal")));
    }

    @Test
    public void testShowHidePassword() {
        logger.info("Test operation of the 'eye' password button in signing in modal window");

        Assertions.assertEquals("password", signInPage.getPasswordInputAttributeType(),
                "The password is not hidden");

        signInPage.clickShowHideButtonElement();
        Assertions.assertEquals("text", signInPage.getPasswordInputAttributeType(),
                "The password is not shown");

        signInPage.clickShowHideButtonElement();
        Assertions.assertEquals("password", signInPage.getPasswordInputAttributeType(),
                "The password is not hidden");
    }

    @Test
    public void testForgotPasswordIsClickable() {
        logger.info("Test operation of the 'forgot password' button in signing in modal window");

        signInPage.clickForgotPassword();

        Assertions.assertTrue(signInPage.waitElementToBePresent(By.tagName("app-restore-password")).isDisplayed(),
                "The 'ForgotPassword' link in not clickable.");
    }

    @Test
    public void testSignInButtonIsClickable() {
        logger.info("Test operation of the 'SIGN IN' button on the GreenCity page");

        signInPage.clickSignUpLink();

        Assertions.assertTrue(signInPage.waitElementToBePresent(By.tagName("app-sign-up")).isDisplayed());
    }

}
