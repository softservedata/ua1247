package com.softserve.edu11_12_13.test;

import com.softserve.edu11_12_13.data.TesterUser1;
import com.softserve.edu11_12_13.data.TesterUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GreenCityLoginTest extends TestRunner {
@Test
    public void verifyTitle() {
        Assertions.assertEquals("Welcome back!", greenCity.getWelcomeText());
        Assertions.assertEquals("Please enter your details to sign in.", greenCity.getSignInDetailsText());
        Assertions.assertTrue(greenCity.isForgotPasswordVisible(), "Forgot password link should be visible.");
        Assertions.assertTrue(greenCity.isSignUpLinkVisible(), "Sign-up link should be visible.");
        Assertions.assertTrue(greenCity.isGoogleSignInButtonVisible(), "Google Sign-In button should be visible.");
        Assertions.assertFalse(greenCity.isSubmitButtonEnabled(), "Sign In button should be disabled by default.");
    }
    private static Stream<Arguments> provideTesterUsers() {
        return Stream.of(
                Arguments.of(TesterUserRepository.getValidUser())

        );
    }
    @ParameterizedTest(name = "{index} => testerUser={0}")
    @MethodSource("provideTesterUsers")
    public void checkLogin(TesterUser1 testerUser) {
        greenCity.inputCredentials(testerUser.getEmail(), testerUser.getPassword());
        greenCity.clickSubmitButton();
        String actualUserName = greencityLogged.getUsername();
        String expectedUserName = testerUser.getUsername();
        presentationSleep();

        Assertions.assertEquals(expectedUserName, actualUserName);
        presentationSleep();

        System.out.println("\t\tTest testUi() executed");
    }
    @ParameterizedTest
    @CsvSource({ "sdgnlanglkgn,090193",
            "@,vvhkvhk",
            "bebebeb@,hahaha"

    })
    public void signInNegativeBoth(String email, String password) {
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Please check that your e-mail address is indicated correctly", greenCity.getErrorEmailText(),
                String.format("Unexpected error message for email: %s", email));
        Assertions.assertEquals("Password must be at least 8 characters long without spaces", greenCity.getErrorPasswordText(),
                String.format("Unexpected error message for password: %s", password));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void signInNonExistentUserCSVFile(String email, String password) {
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Bad email or password", greenCity.getGeneralErrorText());
    }

    @ParameterizedTest
    @CsvSource({
            "kristina@gmail.com,ha_Tshf3"
    })

    public void signInNonExistentUser(String email, String password) {
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Bad email or password", greenCity.getGeneralErrorText());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/dataWrongEmail.csv")
    public void signInWrongEmailGoodPassword(String email, String password) {
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Please check that your e-mail address is indicated correctly", greenCity.getErrorEmailText(),
                String.format("Unexpected error message for email: %s", email));

    }
    //Цей тест знайшов багу - пароль з пробілами
    @ParameterizedTest
    @CsvFileSource(resources = "/dataLongPassword.csv")
    public void signInLongPassword(String email, String password) {
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Please check that your e-mail address is indicated correctly", greenCity.getErrorEmailText(),
                String.format("Unexpected error message for email: %s", email));
        Assertions.assertEquals("Password must be less than 20 characters long without spaces.", greenCity.getErrorPasswordText(),
                String.format("Unexpected error message for password: %s", password));
    }
    @ParameterizedTest
    @CsvSource({ "Roman.tsvyk.pb.2018@lpnu.ua,090193",
            "Roman.tsvyk.pb.2018@lpnu.ua,@#657"})
    public void signInGoodEmailWrongPassword(String email, String password) {
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Password must be at least 8 characters long without spaces",greenCity.getErrorPasswordText());
    }
    @Test
    public void signInEmptyCredentials() {
        String email="";
        String password="";
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Please fill all red fields",greenCity.getGeneralErrorText());
    }
    @Test
    public void signInEmptyEmail() {
        String email="";
        String password="ert$_Ythfd";
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Email is required",greenCity.getErrorEmailText());
    }
    @Test
    public void signInEmptyPassword() {
        String email="kristina@gmail.com";
        String password="";
        greenCity.inputCredentials(email, password);
        greenCity.clickSubmitButton();
        Assertions.assertEquals("Password is required",greenCity.getErrorPasswordText());
    }
}
