package control;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import page.IPage;

public class MobileControl extends WebControl {

    private MobileElement mobileElement =null;

    public MobileControl(String name, IPage page, MobileElement element) {
        super(name, page, element);
        this.mobileElement = element;
    }

    @Override
    public void enterValue(String text) throws Exception {

    }

    @Override
    public boolean isSelected() throws Exception {
        if(thisControlElement().getAttribute("checked").equals("true")){
            return true;
        }else{
            return false;
        }
    }
}
