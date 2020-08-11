package testcore.pages.androidNative;

import agent.IAgent;
import central.Configuration;
import testcore.pages.AllPages;

import java.util.Map;

public class LandingPage extends AllPages {

    public LandingPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
        super(conf, agent, testData);
    }

    @Override
    public String pageName(){
        return testcore.pages.androidNative.LandingPage.class.getSimpleName();
    }
}
