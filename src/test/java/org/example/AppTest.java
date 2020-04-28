package org.example;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest
{

    private WebDriver driver;

    @Before
    public void setUp(){
        driver = new ChromeDriver();
    }

    @After
    public void tearDown(){
        driver.close();
    }

}
