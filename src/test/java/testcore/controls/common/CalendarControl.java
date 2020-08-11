package testcore.controls.common;


import org.openqa.selenium.WebElement;
import control.WebControl;
import page.IPage;

public class CalendarControl extends WebControl {

	public CalendarControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}


	@Override
	public void enterValue(String date_to_select) throws Exception {

	}
}

