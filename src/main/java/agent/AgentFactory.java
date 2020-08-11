package agent;

import agent.internal.MobileAgentFactory;
import org.apache.log4j.Logger;

import agent.internal.WebAgentFactory;
import central.Configuration;
import central.AutomationCentral;
import enums.Platform;

public class AgentFactory {
	private static Logger logger = AutomationCentral.getLogger();

	public synchronized static IAgent createAgent(Configuration config) throws Exception {
		String msg = String.format("Creating agent for: %s", config.getPlatform());
		try {
			IAgent agent = null;
			if (config.getPlatform() == Platform.DESKTOP_WEB) {
				agent = WebAgentFactory.createAgent(config);
			}else {
				agent = MobileAgentFactory.createAgent(config);
			}
			return agent;
		} catch (Exception e) {
			logger.debug(String.format("Failure in %s", msg));
			throw e;
		}
	}
}
