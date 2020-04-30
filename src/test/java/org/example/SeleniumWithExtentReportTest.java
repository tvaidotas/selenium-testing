package org.example;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class SeleniumWithExtentReportTest {

    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    @BeforeTest
    public void startReport(){
        report = new ExtentReports(
                System.getProperty("user.dir" + "/test-output/Report.html"),
                true
        );
        report
                .addSystemInfo("Host Name", "QA")
                .addSystemInfo("Tester", "Tadas");
        report.loadConfig(new File(System.getProperty("user.dir" + "\\extent-report.xml")));
    }


}
