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
        //WebElement macComputer = driver.findElement(By.xpath("//h3[contains(text(), 'Mac (computer)')]/.."));
        //System.out.println("mac.href = " + macComputer.getAttribute("href"));
        //Assertions.assertEquals("https://en.wikipedia.org/wiki/Mac_(computer)", macComputer.getAttribute("href"));
        //
        WebElement macComputer = driver.findElement(By.xpath("//h3[contains(text(), 'Mac (computer)')]/.."));
        System.out.println("mac.href = " + macComputer.getAttribute("href"));
        Assertions.assertEquals("https://en.wikipedia.org/wiki/Mac_(computer)", macComputer.getAttribute("href"));
    }

}
