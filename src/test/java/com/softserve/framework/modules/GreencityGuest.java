package com.softserve.framework.modules;

import com.softserve.framework.data.LoginDto;
import com.softserve.framework.data.TesterUser;
import com.softserve.framework.test.TestRunner;
import com.softserve.framework.utils.LocalStorageJS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GreencityGuest {

    private WebDriver driver;
    //
    private GreencityPost greencityPost;
    private LocalStorageJS localStorageJS;

    public GreencityGuest(WebDriver driver) {
        this.driver = driver;
        this.greencityPost = new GreencityPost();
        this.localStorageJS = new LocalStorageJS(driver);
    }

    public void setLocalStorage(LoginDto loginDto) {
        localStorageJS.setItemInLocalStorage("accessToken", loginDto.getAccessToken());
        localStorageJS.setItemInLocalStorage("language", "en");
        localStorageJS.setItemInLocalStorage("name", loginDto.getName());
        localStorageJS.setItemInLocalStorage("refreshToken", loginDto.getRefreshToken());
        localStorageJS.setItemInLocalStorage("userId", String.valueOf(loginDto.getUserId()));
    }

    //public void signIn(String email, String password) {
    public void signIn(TesterUser testerUser) {
        //logger.debug("Start signIn with user = " + user);
        //
        // Click Login Button
        driver.findElement(By.cssSelector("app-ubs .ubs-header-sing-in-img")).click();
        TestRunner.presentationSleep(); // For Presentation ONLY
        //
        // Fill email input
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(testerUser.getEmail());
        TestRunner.presentationSleep(); // For Presentation ONLY
        //
        // Fill password input
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(testerUser.getPassword());
        TestRunner.presentationSleep(); // For Presentation ONLY
        //
        // Press Button Login
        //driver.findElement(By.cssSelector("button[type='submit']")).click();
        //TestRunner.presentationSleep(); // For Presentation ONLY
        //
        //logger.debug("Done signIn with user = " + user);
        //
        LoginDto loginDto = greencityPost.login(testerUser);
        TestRunner.presentationSleep(); // For Presentation ONLY
        //
        setLocalStorage(loginDto);
        TestRunner.presentationSleep(); // For Presentation ONLY
        //
        driver.navigate().refresh();
        TestRunner.presentationSleep(); // For Presentation ONLY
    }

}
