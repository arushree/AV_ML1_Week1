package testcore.controls.common;

import control.WebControl;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import page.IPage;

import java.io.File;

public class TextboxControl extends WebControl {

    public TextboxControl(String name, IPage page, WebElement element) {
        super(name, page, element);
    }

    @Override
    public void enterText(String text) throws Exception {
        try {
            this.waitUntilClickable();
            WebElement element = this.getRawWebElement();
            element.clear();
            element.click();
            element.sendKeys(text);
        } catch (Exception e) {
            throwControlActionException(e);
        }
    }

    @Override
    public void enterValue(String text) throws Exception {
        enterText(text);
    }

    @Override
    public String getValue() throws Exception {
        try {
            String msg = String.format("Getting text for %s", this.getControlBasicInfoString());
            this.waitUntilVisible();
            String text = this.getRawWebElement().getAttribute("value");
            this.getAgent().takeConditionalSnapShot();
            return text;
        } catch (Exception e) {
            throwControlActionException(e);
        }

        return null;
    }

    public void clear_via_javascript() throws Exception {
        this.getRawWebElement().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }

    public String getErrorMessage() {
        return this.getRawWebElement().findElement(By.xpath("./../..//following-sibling::div//mat-error")).getText();

    }

}

	

