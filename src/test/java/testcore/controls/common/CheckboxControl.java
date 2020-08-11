package testcore.controls.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class CheckboxControl extends WebControl {
	
	public CheckboxControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}
	
	@Override
	public void click() throws InterruptedException {
		this.getRawWebElement().click();
	}
	
	@Override
	public void checkboxCheck() throws Exception {
		String xpath = "./parent::div[contains(@class, 'mat-checkbox-inner-container')]";
		if((this.getRawWebElement().getAttribute("aria-checked") != "true")){
			this.getRawWebElement().findElement(By.xpath(xpath)).click();
		}
	}

	@Override
	public void checkboxUnCheck() throws Exception {
		String xpath = "./parent::div[contains(@class, 'mat-checkbox-inner-container')]";
		if((this.getRawWebElement().getAttribute("aria-checked") == "true")){
			this.getRawWebElement().findElement(By.xpath(xpath)).click();
		}
	}

	@Override
	public boolean isSelected(){
		return this.getRawWebElement().isSelected();
	}

}