package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RegisterPage {
    private final By _txtEmail = By.xpath("//input[@id='email']");;
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By _txtPid = By.xpath("//input[@id='pid']");

    private final By _btnRegister = By.xpath("//input[@value='Register']");
    private final By _lblRegisterErrorMsg = By.xpath("//p[@class='message error']");
    private final By _lblRegister = By.xpath("//div[@id='content']/p");
    private final By _lblValidPassErr = By.xpath("//label[@class='validation-error' and text() = 'Invalid password length']");
    private final By _lblValidConfirmPassErr = By.xpath("//label[@class='validation-error' and text() = 'The two passwords do not match']");
    private final By _lblValidIdErr = By.xpath("//label[@class='validation-error' and text() = 'Invalid ID length']");



    public WebElement getTxtEmail()
    {
        return Constant.WEBDRIVER.findElement(_txtEmail);
    }
    public WebElement getPassword()
    {
        return Constant.WEBDRIVER.findElement(_txtPassword);
    }
    public WebElement getConfirmPassword()
    {
        return Constant.WEBDRIVER.findElement(_txtConfirmPassword);
    }
    public WebElement getPid()
    {
        return Constant.WEBDRIVER.findElement(_txtPid);
    }
    public WebElement getBtnRegister()
    {
        return Constant.WEBDRIVER.findElement(_btnRegister);
    }
    public WebElement getValidPassErr(){
        return Constant.WEBDRIVER.findElement(_lblValidPassErr);
    }
    public WebElement getValidConfirmPassErr(){
        return Constant.WEBDRIVER.findElement(_lblValidConfirmPassErr);
    }
    public WebElement getValidIdErr(){
        return Constant.WEBDRIVER.findElement(_lblValidIdErr);
    }
    public WebElement getLblRegister()
    {
        return Constant.WEBDRIVER.findElement(_lblRegister);
    }
    public WebElement getLblRegisterErr()
    {
        return Constant.WEBDRIVER.findElement(_lblRegisterErrorMsg);
    }
    public RegisterPage register(String username, String password, String confirmpassword, String pid)
    {
        this.getTxtEmail().sendKeys(username);
        this.getPassword().sendKeys(password);
        this.getConfirmPassword().sendKeys(confirmpassword);
        this.getPid().sendKeys(pid);
        this.getBtnRegister().click();

        return new RegisterPage();
    }
}
