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
import org.openqa.selenium.NoSuchElementException;

public class BookTicketTest {

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
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");

        HomePage homePage = new HomePage();
        homePage.open();

        // Đăng nhập và kiểm tra đăng nhập thành công
        LoginPage loginPage = homePage.gotoLoginPage();
        homePage = loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        // Kiểm tra xem đăng nhập có thành công không
        try {
            WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']/h1")));
            String actualWelcomeMsg = welcomeMessage.getText();
            String expectedWelcomeMsg = "Welcome to Safe Railway"; // Cập nhật tiêu đề thực tế nếu khác
            Assert.assertEquals(actualWelcomeMsg, expectedWelcomeMsg, "Login failed: Welcome message not displayed as expected");
        } catch (Exception e) {
            // Nếu đăng nhập thất bại, kiểm tra thông báo lỗi
            WebElement errorMessage = loginPage.getLblLoginErrorMsg();
            String errorMsg = errorMessage.getText();
            Assert.fail("Login failed with error message: " + errorMsg);
        }

        // Điều hướng đến trang Book Ticket
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();

        // Chờ dropdown ngày xuất hiện trước khi đặt vé
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name = 'Date']")));

        // Đặt vé
        bookTicketPage.bookTicket("4/16/2025", "Sài Gòn", "Nha Trang", "Soft bed with air conditioner", "1");

        // Kiểm tra thông báo thành công
        String actualMsg = homePage.getLblWelcomeMessage().getText();
        String expectedMsg = "Ticket booked successfully!";
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