package testcases.Railway;

import common.Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Railway.*;

public class RegisterTest {

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
    public void TC07() {
        System.out.println("TC07 - User can create new account");

        HomePage homePage = new HomePage();
        homePage.open();

        String randomEmail = "nguyenlethanhlinh" + System.currentTimeMillis() + "@gmail.com";
        String password = "Tlinh080604@";
        String confirmPassword = "Tlinh080604@";
        String pid = "13456789";

        RegisterPage registerPage = homePage.gotoRegister();
        registerPage.register(randomEmail, password, confirmPassword, pid);

        String actualMsg = homePage.getLblSuccessful().getText();
        String expectedMsg = "Registration Confirmed! You can now log in to the site.";

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC10() {
        System.out.println("TC10 - User can't create account with Confirm password is not the same with Password");

        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegister();
        registerPage.register("dvwww@example.com", "dvv@123456", "dvv1@123456", "134567899999");
        String actualMsg = registerPage.getLblRegisterErr().getText();
        String expectedMsg = "There're errors in the form. Please correct the errors and try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC11() {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");

        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegister();
        registerPage.register("dvwww@example.com", "", "dvv1@123456", "");
        String actualMsg = registerPage.getLblRegisterErr().getText();
        String expectedMsg = "There're errors in the form. Please correct the errors and try again.";

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
        String actualMsg2 = registerPage.getValidPassErr().getText();
        String expectedMsg2 = "Invalid password length";

        Assert.assertEquals(actualMsg2, expectedMsg2, "Welcome message is not displayed as expected");
        String actualMsg3 = registerPage.getValidIdErr().getText();
        String expectedMsg3 = "Invalid ID length";

        Assert.assertEquals(actualMsg3, expectedMsg3, "Welcome message is not displayed as expected");
    }
}