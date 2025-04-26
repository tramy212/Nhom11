package testcases.Railway;

import common.Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Railway.*;

import java.time.Duration;

public class LoginTest {

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
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();

        // Điều hướng đến trang Login
        LoginPage loginPage = homePage.gotoLoginPage();

        // Đăng nhập
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Chờ trang Home tải xong và tiêu đề xuất hiện
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']/h1")));

        // Lấy tiêu đề sau khi trang đã tải xong
        String actualMsg = welcomeMessage.getText();
        String expectedMsg = "Welcome to Safe Railway";

        System.out.println("Actual welcome message: '" + actualMsg + "'");

        // Kiểm tra tiêu đề
        if (!actualMsg.equals(expectedMsg)) {
            // Nếu tiêu đề không khớp, kiểm tra thông báo lỗi trên trang Login
            try {
                WebElement errorMessage = loginPage.getLblLoginErrorMsg();
                String errorMsg = errorMessage.getText();
                Assert.fail("Login failed with error message: " + errorMsg);
            } catch (Exception e) {
                Assert.fail("Welcome message is not displayed as expected. Actual: " + actualMsg + ", Expected: " + expectedMsg);
            }
        }

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank Username textbox");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", Constant.PASSWORD);
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "There was a problem with your login and/or errors exist in your form.";

        System.out.println("Actual error message: '" + actualMsg + "'");

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, "123456");
        String actualMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedMsg = "Invalid username or password. Please try again.";

        System.out.println("Actual error message: '" + actualMsg + "'");

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC04() {
        System.out.println("TC04 - Login page displays when un-logged User clicks on Book ticket tab");

        HomePage homePage = new HomePage();
        homePage.open();

        homePage.gotoBookTicketPage();

        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(200));
        WebElement loginHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']/h1")));

        String actualMsg = loginHeader.getText();
        String expectedMsg = "Login page";
        System.out.println("Navigated to page with header: '" + actualMsg + "'");

        Assert.assertEquals(actualMsg, expectedMsg, "User was not redirected to Login Page as expected");
    }

    @Test
    public void TC05() {
        System.out.println("TC05 - System shows message when user enters wrong password several times");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        String expectedMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        for (int i = 0; i < 5; i++) {
            loginPage.login(Constant.USERNAME, "wrongpassword");
            String actualMsg = loginPage.getLblLoginErrorMsg().getText();
            System.out.println("Attempt " + (i + 1) + ": Actual error message: '" + actualMsg + "'");

            if (i == 4) {
                Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected after 5 failed attempts.");
            }
        }
    }

    @Test
    public void TC06() {
        System.out.println("TC06 - Additional pages display once user logged in");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(30));

        // Kiểm tra các tab hiển thị sau khi đăng nhập
        homePage.gotoMyTicket(); // Điều hướng đến trang My Ticket
        MyTicketPage myTicketPage = new MyTicketPage();
        String actualMyTicketPage = myTicketPage.getLblWelcomeMessage().getText();
        String expectedMyTicketPage = "Manage ticket"; // Đã sửa ở câu trước
        Assert.assertEquals(actualMyTicketPage, expectedMyTicketPage, "User was not redirected to My Ticket page");

        // Kiểm tra tab Change Password và chuyển hướng
        homePage.gotoChangePassword(); // Điều hướng đến trang Change Password
        ChangePasswordPage changePasswordPage = new ChangePasswordPage();
        String actualChangePasswordPage = ChangePasswordPage.getLblWelcomeMessage().getText();
        String expectedChangePasswordPage = "Change password"; // Sửa từ "Change Password" thành "Change password"
        Assert.assertEquals(actualChangePasswordPage, expectedChangePasswordPage, "User was not redirected to Change Password page");

        // Kiểm tra tab Logout và chuyển hướng sau khi logout
        homePage.LogOut(); // Điều hướng logout
        String actualLogoutPage = homePage.getLblWelcomeMessage().getText();
        String expectedLogoutPage = "Welcome to Safe Railway"; // Cập nhật tiêu đề thực tế nếu khác
        Assert.assertEquals(actualLogoutPage, expectedLogoutPage, "User was not redirected to Login page after logout");
    }

    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account hasn't been activated");

        HomePage homePage = new HomePage();
        homePage.open();

        String randomEmail = "nguyenlethanhlinh" + System.currentTimeMillis() + "@gmail.com";
        String password = "Tlinh080604@";
        String confirmPassword = "Tlinh080604@";
        String pid = "13456789";

        RegisterPage registerPage = homePage.gotoRegister();
        registerPage.register(randomEmail, password, confirmPassword, pid);

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(randomEmail, password);

        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "Invalid username or password. Please try again.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }
}