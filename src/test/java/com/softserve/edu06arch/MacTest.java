package com.softserve.edu06arch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class MacTest extends TestRunnerGoogle {

    @Test
    public void checkGoogleMac() {
        System.out.println("Start ...");
        //
        GoogleMod gm = loadApplication()
                .typeSearch("mac")
                .startSearch();
        //
        Assertions.assertEquals("mac", gm.getSearchFieldText());
        //
        //WebElement mac = driver.findElement(By.xpath("//h3[text()='Mac - Apple (UA)']/.."));
        //System.out.println("mac.href = " + mac.getAttribute("href"));
        //Assertions.assertEquals("https://www.apple.com/ua/mac/", mac.getAttribute("href"));
    }

}
