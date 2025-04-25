package pageObjects.Railway;

import common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TimeTablePage {
    public WebElement getBookLink(String depart, String arrive) {
        String xpath = String.format("//tr[td[2]='%s' and td[3]='%s']//a[text()='book ticket']", depart, arrive);
        return Constant.WEBDRIVER.findElement(By.xpath(xpath));
    }

}
