package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ChangePasswordPage {
    private static final By lblWelcomeMessage = By.xpath("//div[@id='content']/h1");
    private final By _txtCurrentPassword = By.id("currentPassword");
    private final By _txtNewPassword = By.id("newPassword");
    private final By _txtConfirmPassword = By.id("confirmPassword");

    private final By _btnChangePassword = By.xpath("//input[@value='Change Password']");
    private final By _lblChangePasswordSuccessMsg = By.xpath("//p[@class='message success']");
    private final By _lblChangePasswordErrMsg  = By.className("message error");
    private final By _lblValidConfirmPassErr = By.xpath("//label[@class='validation-error' and text() = 'The password confirmation does not match the new password.']");



    public WebElement getCurrentPassword()
    {
        return Constant.WEBDRIVER.findElement(_txtCurrentPassword);
    }
    public WebElement getNewPassword()
    {
        return Constant.WEBDRIVER.findElement(_txtNewPassword);
    }
    public WebElement getConfirmPassword()
    {
        return Constant.WEBDRIVER.findElement(_txtConfirmPassword);
    }

    public WebElement getBtnChangePassword()
    {
        return Constant.WEBDRIVER.findElement(_btnChangePassword);
    }
    public WebElement getLblChangePasswordSuccessMsg()
    {
        return Constant.WEBDRIVER.findElement(_lblChangePasswordSuccessMsg);
    }
    public WebElement getLblChangePasswordErrMsg()
    {
        return Constant.WEBDRIVER.findElement(_lblChangePasswordErrMsg);
    }
    public WebElement getLblValidConfirmPasswordErrMsg()
    {
        return Constant.WEBDRIVER.findElement(_lblValidConfirmPassErr);
    }

    public ChangePasswordPage changePassword(String currentpassword, String newpassword, String confirmpassword)
    {
        this.getCurrentPassword().sendKeys(currentpassword);
        this.getNewPassword().sendKeys(newpassword);
        this.getConfirmPassword().sendKeys(confirmpassword);
        this.getBtnChangePassword().click();
        return new ChangePasswordPage();
    }
    public static WebElement getLblWelcomeMessage(){
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }
}

