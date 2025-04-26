package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage {
    private final By txtEmail = By.id("email");
    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtResetToken = By.id("resetToken");
    private final By btnResetPassword = By.xpath("//input[@value='Reset Password']");
    private final By lblErrorMsg = By.xpath("//div[@class='message error']");
    private final By lblConfirmPasswordError = By.xpath("//label[@class='validation-error' and text() = 'The password confirmation did not match the new password.']");

    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(txtEmail);
    }

    public WebElement getTxtNewPassword() {
        return Constant.WEBDRIVER.findElement(txtNewPassword);
    }

    public WebElement getTxtConfirmPassword() {
        return Constant.WEBDRIVER.findElement(txtConfirmPassword);
    }

    public WebElement getTxtResetToken() {
        return Constant.WEBDRIVER.findElement(txtResetToken);
    }

    public WebElement getBtnSendInstructions() {
        return Constant.WEBDRIVER.findElement(btnSendInstructions);
    }

    public WebElement getBtnResetPassword() {
        return Constant.WEBDRIVER.findElement(btnResetPassword);
    }

    public WebElement getLblErrorMsg() {
        return Constant.WEBDRIVER.findElement(lblErrorMsg);
    }

    public WebElement getLblConfirmPasswordError() {
        return Constant.WEBDRIVER.findElement(lblConfirmPasswordError);
    }

    public void sendResetInstructions(String email) {
        getTxtEmail().sendKeys(email);
        getBtnSendInstructions().click();
    }

    public void resetPassword(String newPassword, String confirmPassword, String resetToken) {
        getTxtNewPassword().sendKeys(newPassword);
        getTxtConfirmPassword().sendKeys(confirmPassword);
        getTxtResetToken().sendKeys(resetToken);
        getBtnResetPassword().click();
    }
}