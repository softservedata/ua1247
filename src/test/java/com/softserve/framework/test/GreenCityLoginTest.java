package com.softserve.framework.test;

import com.softserve.framework.data.TesterUser;
import com.softserve.framework.data.TesterUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import java.util.stream.Stream;

public class GreenCityLoginTest extends TestRunner {

    private static Stream<Arguments> provideTesterUsers() {
        return Stream.of(
                //Arguments.of(TesterUserRepository.getValidUser())
                Arguments.of(TesterUserRepository.getValidUserSecret())
        );
    }

    @ParameterizedTest(name = "{index} => testerUser={0}")
    @MethodSource("provideTesterUsers")
    public void checkLogin(TesterUser testerUser) {
        //logger.info("Start checkLogin() with testerUser = " + testerUser);
        //
        loadApplication();
        presentationSleep(); // For Presentation
        //
        greencityGuest.signIn(testerUser);
        presentationSleep(); // For Presentation
        //
        // get Username
        String actualUserName = greencityLogged.getUsername();
        String expectedUserName = testerUser.getUsername();
        presentationSleep(); // For Presentation ONLY
        //
        // Check
        Assertions.assertEquals(expectedUserName, actualUserName);
        presentationSleep(); // For Presentation ONLY
        //
        System.out.println("\t\tTest testUi() executed");
    }
}
