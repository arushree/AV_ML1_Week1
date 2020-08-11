package testcore.controls.common;

import control.WebControl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import page.IPage;

import java.util.List;

public class DropdownControl extends WebControl {

    public DropdownControl(String name, IPage page, WebElement element) {
        super(name, page, element);
    }

    @Override
    public void enterValue(String value) throws Exception {
        if (this.getRawWebElement().getTagName().equals("select")) {
            selectDropDownByValue(value);
        } else {
            int count = 0;
            int maxTries = 3;
            while (true) {
                try {
                    WebElement matSelectBox = this.getRawWebElement();
                    matSelectBox.click();
                    String options_css = ".mat-option";
                    List<WebElement> options = this.getAgent().getWebDriver().findElements(By.cssSelector(options_css));
                    for (WebElement option : options) {
                        String optionText = option.findElement(By.xpath(".//span")).getText();
                        logger.info(optionText+" and "+option.getText()+" ");
                        if (option.getText().equalsIgnoreCase(value)) {
                            option.click();
                            return;
                        }
                    }
                    throw new Exception("Retry");
                } catch (Exception e) {
                    Thread.sleep(500);
                    if (++count == maxTries) {
                        Assert.fail("Unable to select dropdown value: " + value + "; Failed due to " + e);
                        throwControlActionException(e);
                    }
                }
            }
        }
    }

    public String getErrorMessage() {
        String errorMsg = this.getRawWebElement().findElement(By.xpath("./../..//following-sibling::div//mat-error")).getText();
        return errorMsg;
    }

}

