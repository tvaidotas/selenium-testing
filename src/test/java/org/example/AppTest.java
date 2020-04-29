package org.example;

import com.google.common.base.Function;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.*;
import static java.util.concurrent.TimeUnit.*;
import static org.junit.Assert.assertTrue;

public class AppTest
{

    private WebDriver driver;

    @Before
    public void setUp(){
        driver = new ChromeDriver();
    }

    @Test
    public void seleniumExampleTest() throws InterruptedException{
        driver.manage().window().maximize();
        sleep(2000);
        driver.get("http://www.google.com");
        sleep(1000);
        WebElement googleSearchBar = driver.findElement(By.name("q"));
        googleSearchBar.sendKeys("funny dog pics");
        sleep(1000);
        googleSearchBar.submit();
        WebElement linkToPictures = driver.findElement(By.partialLinkText("Images for funny dog"));
        linkToPictures.click();
        sleep(1000);
        WebElement imagesLink = driver.findElement(By.className("NZmxZe"));
        assertTrue(imagesLink.isDisplayed());
    }

    @Test
    public void seleniumExampleSendingKeyboardKeysTest() throws InterruptedException{
        driver.manage().window().maximize();
        sleep(2000);
        driver.get("http://www.google.com");
        sleep(1000);
        WebElement googleSearchBar = driver.findElement(By.name("q"));
        googleSearchBar.sendKeys("funny dog pics");
        sleep(1000);
        googleSearchBar.sendKeys(Keys.ENTER);
        WebElement linkToPictures = driver.findElement(By.partialLinkText("Images for funny dog"));
        linkToPictures.click();
        sleep(1000);
        WebElement imagesLink = driver.findElement(By.className("NZmxZe"));
        assertTrue(imagesLink.isDisplayed());
    }

    @Test
    public void seleniumExampleSendingMultipleKeyboardKeysTest() throws InterruptedException{
        driver.manage().window().maximize();
        sleep(2000);
        driver.get("http://www.google.com");
        sleep(1000);
        WebElement googleSearchBar = driver.findElement(By.name("q"));
        googleSearchBar.sendKeys(Keys.chord("funny ", "dog ", "pics", Keys.ENTER));
        sleep(1000);
        WebElement linkToPictures = driver.findElement(By.partialLinkText("Images for funny dog"));
        linkToPictures.click();
        sleep(1000);
        WebElement imagesLink = driver.findElement(By.className("NZmxZe"));
        assertTrue(imagesLink.isDisplayed());
        Actions action = new Actions(driver);
        action.moveByOffset(50, 50).clickAndHold().moveByOffset(0, 50).release().perform();
    }

    @Test
    public void seleniumExampleMouseActionsTest() throws InterruptedException{
        driver.manage().window().maximize();
        sleep(2000);
        driver.get("https://www.youidraw.com/apps/painter/");
        sleep(1000);
        Actions action = new Actions(driver);
        action.moveByOffset(750, 450).clickAndHold().moveByOffset(0, 50).release().perform();
        sleep(5000);
    }

    @Test
    public void implicitWaitExample(){
        // bad way of doing it
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.google.com");
        WebElement searchBar = driver.findElement(By.name("q"));
        assertTrue(searchBar.isDisplayed());
    }

    @Test
    public void explicitWaitExample(){
        driver.get("http://www.google.com");
        WebElement searchBar = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        assertTrue(searchBar.isDisplayed());
    }

    @Test
    public void fluentWaitExample(){
        driver.get("http://www.google.com");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(5, SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement searchBar = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.name("q"));
            }
        });
        assertTrue(searchBar.isDisplayed());
    }

    @After
    public void tearDown(){
        driver.close();
    }

}
