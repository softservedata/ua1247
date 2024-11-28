package com.softserve.framework.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GreencityLogged {

    private WebDriver driver;
    //

    public GreencityLogged(WebDriver driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return driver.findElement(By.cssSelector("li.ubs-user-name")).getText();
    }
}
