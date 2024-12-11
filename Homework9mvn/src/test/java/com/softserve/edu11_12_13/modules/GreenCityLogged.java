package com.softserve.edu11_12_13.modules;



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
