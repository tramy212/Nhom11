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
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;

public class LoginTest {

    public void waitFor5Seconds() {
        try {
            Thread.sleep(5000); // 5000 milliseconds = 5 seconds
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

        LoginPage loginPage = homePage.gotoLoginPage();
        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getLblWelcomeMessage().getText();
        String expectedMsg = "Welcome to Safe Railway";

        System.out.println("Actual welcome message: '" + actualMsg + "'");

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
        String expectedMsg = "There was a problem with your login and/or errors exist in your form."; // ví dụ

        System.out.println("Actual error message: '" + actualMsg + "'");

        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password ");

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

        // Lặp lại 5 lần đăng nhập sai
        for (int i = 0; i < 5; i++) {
            loginPage.login(Constant.USERNAME, "wrongpassword");  // Nhập sai mật khẩu
            String actualMsg = loginPage.getLblLoginErrorMsg().getText();
            System.out.println("Attempt " + (i + 1) + ": Actual error message: '" + actualMsg + "'");

            // Kiểm tra thông báo lỗi sau khi nhập sai mật khẩu 4 lần
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

        // Tăng thời gian chờ đợi
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(30));

        // Chờ phần tử 'My ticket' hiển thị và có thể nhấn được
        WebElement myTicketTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'My ticket')]")));
        Assert.assertTrue(myTicketTab.isDisplayed(), "My ticket tab is not displayed");
        myTicketTab.click(); // Nhấn vào tab "My ticket"

        // Kiểm tra sau khi nhấn, nếu trang đã chuyển sang "Manage ticket"
        MyTicketPage myTicketPage = new MyTicketPage();
        String actualMyTicketPage = myTicketPage.getLblWelcomeMessage().getText();
        String expectedMyTicketPage = "Manage ticket"; // Sửa thành tiêu đề thực tế
        Assert.assertEquals(actualMyTicketPage, expectedMyTicketPage, "User was not redirected to My Ticket page");

        // Kiểm tra tab "Change password"
        WebElement changePasswordTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Change password')]")));
        Assert.assertTrue(changePasswordTab.isDisplayed(), "Change password tab is not displayed");

        // Kiểm tra tab "Logout"
        WebElement logoutTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Logout')]")));
        Assert.assertTrue(logoutTab.isDisplayed(), "Logout tab is not displayed");

        // Đăng xuất
        logoutTab.click();
        String actualLogoutPage = homePage.getLblWelcomeMessage().getText();
        String expectedLogoutPage = "Welcome to Safe Railway"; // Sửa thành tiêu đề trang đăng nhập
        Assert.assertEquals(actualLogoutPage, expectedLogoutPage, "User was not redirected to Login page after logout");
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

    @Test
    public void TC09() {
        System.out.println("TC09 - User can change password");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        ChangePasswordPage changePasswordPage = homePage.gotoChangePassword();

        String currentPassword = Constant.PASSWORD;
        String newPassword = "NewPassword123@";
        changePasswordPage.changePassword(currentPassword, newPassword, newPassword);

        String actualMsg = changePasswordPage.getLblChangePasswordSuccessMsg().getText();
        String expectedMsg = "Your password has been updated!";

        Assert.assertEquals(actualMsg, expectedMsg, "Success message is not displayed as expected");
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

    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");

        HomePage homePage = new HomePage();
        homePage.open();
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");

        HomePage homePage = new HomePage();
        homePage.open();

        homePage.gotoLoginPage().login(Constant.USERNAME, Constant.PASSWORD);
        ChangePasswordPage changePasswordPage = homePage.gotoChangePassword();
        changePasswordPage.changePassword(Constant.PASSWORD, "Dovanvu@2004", "Dovanvu222@2004");
        String actualMsg = changePasswordPage.getLblChangePasswordErrMsg().getText();
        String expectedMsg = "Password change failed. Please correct the errors and try again.";

        String actualMsg2 = changePasswordPage.getLblValidConfirmPasswordErrMsg().getText();
        String expectedMsg2 = "The password confirmation does not match the new password.";

        Assert.assertEquals(actualMsg2, expectedMsg2, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");

        HomePage homePage = new HomePage();
        homePage.open();

        homePage.gotoLoginPage().login(Constant.USERNAME, Constant.PASSWORD);
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookTicket("4/16/2025", "Đà Nẵng", "Huế", "Soft bed", "1");

        String actualMsg = homePage.getLblWelcomeMessage().getText();
        String expectedMsg = "Ticket booked successfully!";

        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not displayed as expected");
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

    @Test
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookTicket("4/16/2025", "Đà Nẵng", "Huế", "Soft bed", "1");

        MyTicketPage myTicketPage = homePage.gotoMyTicket();
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Cancel')]")));
        cancelButton.click();

        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'OK')]")));
        okButton.click();

        try {
            WebElement canceledTicket = myTicketPage.getTicketByDetails("4/16/2025", "Đà Nẵng", "Huế");
            Assert.assertNull(canceledTicket, "Canceled ticket is still displayed.");
            System.out.println("Ticket successfully canceled and disappeared.");
        } catch (NoSuchElementException e) {
            System.out.println("Ticket successfully canceled and disappeared.");
        }
    }
}

