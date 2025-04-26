package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GeneralPage {
    private final By tabLogin = By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']");
    private final By tabBookTicket = By.xpath("//div[@id='menu']//a[@href='/Page/BookTicketPage.cshtml']");
    private final By tabMyTicket = By.xpath("//div[@id='menu']//a[@href='/Page/ManageTicket.cshtml']");
    private final By tabChangePassword = By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']");
    private final By tabRegister = By.xpath("//div[@id='menu']//a[@href='/Account/Register.cshtml']");
    private final By tabTimeTable = By.xpath("//div[@id='menu']//a[@href='TrainTimeListPage.cshtml']");
    private final By linkForgotPassword = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']"); // Thêm locator cho link Forgot Password

    private final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    private final By lblWelcomeMessage = By.xpath("//div[@id='content']/h1");
    private final By lblSuccessful = By.xpath("//div[@id='content']/p");
    private final By lblLoginErrorMsg = By.xpath("//p[@class='message error LoginForm']");

    protected WebElement getTabLogin() {
        return Constant.WEBDRIVER.findElement(tabLogin);
    }

    protected WebElement getTabRegister() {
        return Constant.WEBDRIVER.findElement(tabRegister);
    }

    protected WebElement getTabBookTicket() {
        return Constant.WEBDRIVER.findElement(tabBookTicket);
    }

    protected WebElement getTabMyTicket() {
        return Constant.WEBDRIVER.findElement(tabMyTicket);
    }

    protected WebElement getTabChangePassword() {
        return Constant.WEBDRIVER.findElement(tabChangePassword);
    }

    protected WebElement geTimeTable() {
        return Constant.WEBDRIVER.findElement(tabTimeTable);
    }

    protected WebElement getTabLogout() {
        return Constant.WEBDRIVER.findElement(tabLogout);
    }

    public WebElement getLblWelcomeMessage() {
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }

    public WebElement getLblSuccessful() {
        return Constant.WEBDRIVER.findElement(lblSuccessful);
    }

    public LoginPage gotoLoginPage() {
        this.getTabLogin().click();
        return new LoginPage();
    }

    public BookTicketPage gotoBookTicketPage() {
        this.getTabBookTicket().click();
        return new BookTicketPage();
    }

    public MyTicketPage gotoMyTicket() {
        this.getTabMyTicket().click();
        return new MyTicketPage();
    }

    public ChangePasswordPage gotoChangePassword() {
        this.getTabChangePassword().click();
        return new ChangePasswordPage();
    }

    public RegisterPage gotoRegister() {
        this.getTabRegister().click();
        return new RegisterPage();
    }

    public TimeTablePage gotoTimeTable() {
        this.geTimeTable().click();
        return new TimeTablePage();
    }

    public ForgotPasswordPage gotoForgotPasswordPage() {
        Constant.WEBDRIVER.findElement(linkForgotPassword).click();
        return new ForgotPasswordPage();
    }

    public GeneralPage LogOut() {
        this.getTabLogout().click();
        return new GeneralPage();
    }
}