package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BookTicketPage {
    private final By dateSelect = By.xpath("//select[@name = 'Date']");
    private final By departStationSelect = By.xpath("//select[@name = 'DepartStation']");
    private final By arriveStationSelect = By.xpath("//select[@name = 'ArriveStation']");
    private final By seatTypeSelect = By.xpath("//select[@name = 'SeatType']");
    private final By ticketAmountSelect = By.xpath("//select[@name = 'TicketAmount']");
    private final By bookTicketBtn = By.xpath("//input[@value='Book ticket']");

    public WebElement selectDate()
    {
        return Constant.WEBDRIVER.findElement(dateSelect);
    }
    public WebElement selectDepartStation()
    {
        return Constant.WEBDRIVER.findElement(departStationSelect);
    }
    public WebElement selectArriveStation()
    {
        return Constant.WEBDRIVER.findElement(arriveStationSelect);
    }
    public WebElement selectSeatType()
    {
        return Constant.WEBDRIVER.findElement(seatTypeSelect);
    }
    public WebElement selectTicketAmount()
    {
        return Constant.WEBDRIVER.findElement(ticketAmountSelect);
    }
    public WebElement getBtnBookTicket()
    {
        return Constant.WEBDRIVER.findElement(bookTicketBtn);
    }
    public BookTicketPage bookTicket(String date, String depart, String arrive, String seat, String amount) {
        new Select(selectDate()).selectByVisibleText(date);
        new Select(selectDepartStation()).selectByVisibleText(depart);
        new Select(selectArriveStation()).selectByVisibleText(arrive);
        new Select(selectSeatType()).selectByVisibleText(seat);
        new Select(selectTicketAmount()).selectByVisibleText(amount);
        getBtnBookTicket().click();
        return new BookTicketPage();
    }
}
