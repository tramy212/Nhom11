//package pageObjects.Railway;
//
//import common.Constant.Constant;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//
//public class MyTicketPage {
//    private final By lblWelcomeMessage = By.xpath("//div[@id='content']/h1");
//    public WebElement getLblWelcomeMessage(){
//        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
//    }
//}
//

package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyTicketPage {
    private final By lblWelcomeMessage = By.xpath("//div[@id='content']/h1");
    private final By ticketDetails = By.xpath("//div[@class='ticket-details']");

    // Lấy thông tin vé
    public WebElement getLblWelcomeMessage(){
        return Constant.WEBDRIVER.findElement(lblWelcomeMessage);
    }

    // Tạo phương thức để lấy vé theo ngày, điểm đi, điểm đến
    public WebElement getTicketByDetails(String date, String from, String to) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

        // XPath tìm vé dựa trên ngày, điểm đi và điểm đến
        String xpath = "//div[contains(text(), '" + date + "')]//following-sibling::div[contains(text(), '" + from + "')]//following-sibling::div[contains(text(), '" + to + "')]";

        // Tìm và trả về phần tử vé (WebElement) nếu tồn tại
        WebElement ticket = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        return ticket;
    }
}
