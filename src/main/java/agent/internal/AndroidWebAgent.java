package agent.internal;

import central.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidWebAgent extends AndroidAgent {

	public AndroidWebAgent(Configuration config, AndroidDriver<MobileElement> driver) throws Exception {
		super(config, driver);
	}

}
