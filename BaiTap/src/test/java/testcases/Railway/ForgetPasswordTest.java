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

public class ForgetPasswordTest {

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
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");

        HomePage homePage = new HomePage();
        homePage.open();
        // Test case này chưa hoàn thiện trong code gốc, bạn có thể cần bổ sung logic kiểm tra lỗi khi reset token trống.
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");

        HomePage homePage = new HomePage();
        homePage.open();

        // Điều hướng đến trang Login trước
        LoginPage loginPage = homePage.gotoLoginPage();

        // Sau đó điều hướng đến trang Forgot Password
        ForgotPasswordPage forgotPasswordPage = loginPage.gotoForgotPasswordPage();

        // Nhập email và gửi yêu cầu reset mật khẩu
        String email = Constant.USERNAME; // Sử dụng email của tài khoản đã tạo
        forgotPasswordPage.sendResetInstructions(email);

        // Giả lập việc click vào link reset mật khẩu (vì không thể tự động mở email)
        // Giả sử bạn có thể điều hướng trực tiếp đến trang reset mật khẩu với token
        // Trong thực tế, bạn cần lấy token từ email và điều hướng đến URL reset
        // Ví dụ: Constant.WEBDRIVER.navigate().to("URL_RESET_PASSWORD_WITH_TOKEN");
        // Ở đây, tôi giả lập bước này bằng cách nhập mật khẩu mới trực tiếp

        // Nhập mật khẩu mới và xác nhận mật khẩu không khớp
        forgotPasswordPage.resetPassword("Dovanvu@2004", "Dovanvu222@2004", "fake-token"); // Token giả lập

        // Kiểm tra thông báo lỗi
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='message error']")));
        String actualMsg = errorMessage.getText();
        String expectedMsg = "Could not reset password. Please correct the errors and try again.";
        Assert.assertEquals(actualMsg, expectedMsg, "Error message is not displayed as expected");

        String actualMsg2 = forgotPasswordPage.getLblConfirmPasswordError().getText();
        String expectedMsg2 = "The password confirmation did not match the new password.";
        Assert.assertEquals(actualMsg2, expectedMsg2, "Confirm password error message is not displayed as expected");
    }
}