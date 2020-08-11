/**
 * 
 */
package agent.internal;

import central.AutomationCentral;
import central.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class AndroidAgent extends MobileAgent {

	public AndroidAgent(Configuration config, AndroidDriver<MobileElement> driver) throws Exception {
		super(config, driver);
	}

}
