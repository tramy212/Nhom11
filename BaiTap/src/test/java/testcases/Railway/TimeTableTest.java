package testcases.Railway;

import common.Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Railway.*;

public class TimeTableTest {

    public void waitFor5Seconds() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");

        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC15() {
        System.out.println("TC15 - User can open Book ticket page by clicking on Book ticket link in Train timetable page");

        HomePage homePage = new HomePage();
        homePage.open();

        homePage.gotoLoginPage().login(Constant.USERNAME, Constant.PASSWORD);
        TimeTablePage timeTablePage = homePage.gotoTimeTable();
        waitFor5Seconds();
        timeTablePage.getBookLink("Huế", "Sài Gòn").click();

        String actualMsg = homePage.getLblWelcomeMessage().getText();
        String expectedMsg = "Book ticket";

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }
}