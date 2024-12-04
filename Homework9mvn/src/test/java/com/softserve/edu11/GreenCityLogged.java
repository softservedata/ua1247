package com.softserve.edu11;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GreenCityLogged {

    private WebDriver driver;
    //

    public GreenCityLogged(WebDriver driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return driver.findElement(By.cssSelector(".name")).getText();
    }
}
