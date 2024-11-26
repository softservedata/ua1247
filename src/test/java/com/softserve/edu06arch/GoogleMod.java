package com.softserve.edu06arch;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleMod {

    private WebDriver driver;

    public GoogleMod(WebDriver driver) {
        this.driver = driver;
    }

    // Atomic operation

    // search field
    private WebElement getSearchField() {
        return driver.findElement(By.name("q"));
    }

    public String getSearchFieldText() {
        return getSearchField().getAttribute("value");
    }

    private void clickSearchField() {
        getSearchField().click();
    }

    private void clearSearchField() {
        getSearchField().clear();
    }

    private void sendKeysSearchField(String text) {
        getSearchField().sendKeys(text);
    }

    // Functional

    public void typeSearchField(String text) {
        clickSearchField();
        clearSearchField();
        sendKeysSearchField(text);
    }

    public void startSearchField() {
        getSearchField().sendKeys(Keys.ENTER);
    }

    // Business Logic
    /*
    public void searchByText(String text) {
        typeSearchField(text);
        startSearchField()
    }
    */

    public GoogleMod typeSearch(String text) {
        typeSearchField(text);
        TestRunnerGoogle.presentationSleep(); // For Presentation
        return this;
    }

    public GoogleMod startSearch() {
        startSearchField();
        TestRunnerGoogle.presentationSleep(); // For Presentation
        return this;
    }
}
