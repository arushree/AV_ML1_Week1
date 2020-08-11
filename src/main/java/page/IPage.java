package page;

import java.util.List;

import org.openqa.selenium.WebElement;

import agent.IAgent;
import agent.internal.IWindowActions;
import control.IControl;
import enums.Platform;
import pagedef.Identifier;

public interface IPage extends IWindowActions {
	IControl getControl(String name) throws Exception;

	IAgent getAgent();

	Platform getPlatform();

	Identifier getIdentifier(String name);

	String pageName();

}
