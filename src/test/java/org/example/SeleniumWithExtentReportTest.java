package org.example;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class SeleniumWithExtentReportTest {

    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    @BeforeTest
    public void startReport(){
        report = new ExtentReports(
                System.getProperty("user.dir") + "/test-output/Report.html",
                true
        );
        report
                .addSystemInfo("Host Name", "QA")
                .addSystemInfo("Tester", "Tadas");
        report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-report.xml"));
    }

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

    @Test
    public void testQATitle(){
        test = report.startTest("Verifying the title of QA website");
        driver.manage().window().maximize();
        test.log(LogStatus.INFO, "Started chrome browser and made it fullscreen");
        driver.get("https://www.qa.com");
        test.log(LogStatus.INFO, "Navigating to the QA website");
        assertEquals(driver.getTitle(), "Virtual and online classes in technology, project management and leadership | QA");
        test.log(LogStatus.PASS, "The title was exactly the same");
    }

    @Test
    public void takeScreenShow() throws IOException {
        test = report.startTest("Checking QA website logo is displayed");
        driver.manage().window().maximize();
        test.log(LogStatus.INFO, "Started chrome browser and made it fullscreen");
        driver.get("https://www.qa.com");
        test.log(LogStatus.INFO, "Navigating to the QA website");
        WebElement logo = driver.findElement(By.id("Path_582"));
        assertTrue(logo.isDisplayed());
        File picture = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(picture, new File(System.getProperty("user.dir") + "/test-output/logoPage.jpg"));
        test.log(LogStatus.PASS, "The logo was present", "<img src=logoPage.jpg>");
    }

    @Test
    public void jsExample() throws InterruptedException {
        test = report.startTest("Starting JS example test");
        driver.manage().window().maximize();
        test.log(LogStatus.INFO, "Started chrome browser and made it fullscreen");
        driver.get("https://www.qa.com");
        test.log(LogStatus.INFO, "Navigating to the QA website");
        if (driver instanceof JavascriptExecutor){
            ((JavascriptExecutor) driver).executeScript("alert('Hello Everybody')");
            sleep(2000);
            driver.switchTo().alert().accept();
            sleep(2000);
        } else {
            System.out.println("Can't execute JS");
        }
    }


    @AfterMethod
    public void getResult(ITestResult result){
        driver.close();
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(LogStatus.FAIL, "Test has failed " + result.getName());
            test.log(LogStatus.FAIL, "Test has failed " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(LogStatus.PASS, "Test has passed " + result.getName());
        }
        report.endTest(test);
    }

    @AfterTest
    public void endReport(){
        report.flush();
        report.close();
    }


}
