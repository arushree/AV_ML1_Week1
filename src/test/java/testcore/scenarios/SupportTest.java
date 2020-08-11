package testcore.scenarios;

import agent.AgentFactory;
import agent.IAgent;
import central.AutomationCentral;
import central.Configuration;
import io.qameta.allure.Attachment;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Optional;
import testcore.pages.Landing.Steps.LandingPageSteps;
import utils.DataTable;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.*;

public class SupportTest extends FileAppender {
    protected static Logger logger = AutomationCentral.getLogger();
    protected static String testClassName = null;
    private Configuration conf = null;
    protected IAgent agent;
    private ITestContext context = null;
    protected String testName = null;
    private DataTable dataTable = null;
    private DataTable defaultDataTable = null;
    private int Testcount = 0;
    private String testCase = null;
    private Map<String, String> testData = new HashMap<>();
    private List<HashMap<String, String>> listOfHashMap = new ArrayList<HashMap<String, String>>();
    private Map<String, String> customerInfoTestData = new HashMap<>();
    private final static Map<String, String> commonTestData = new HashMap<>();


    @BeforeSuite(alwaysRun = true)
    public void runOncePerSuite(ITestContext context) throws Exception {
        AutomationCentral.INSTANCE.init();
        //reading all common data once per suite from the common data excel workbook
        defaultDataTable = new DataTable();
        commonTestData.putAll(defaultDataTable.readExcelFromMultiTab("Sample"));

    }

    @BeforeTest(alwaysRun = true)
    public void runOncePerContext(ITestContext context) throws Exception {
        AutomationCentral.INSTANCE.registerContext(context);
    }

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void runOncePerClass(@Optional("browser") String browser, ITestContext context) throws Exception {
        if (!browser.equals("browser")) {
            System.setProperty("browser", browser);
        }
        this.context = context;
        this.conf = AutomationCentral.INSTANCE.getContextConfig(context);
        /*DataSheet name will be retrieved from the scenarios package name*/
        String dataSheet = null;
        String platform = System.getProperty("platform");
        if (platform.equals("DESKTOP_WEB") || platform.equals("ANDROID_WEB")) {
            dataSheet = this.getClass().getName().split("\\.")[2];
        } else if (System.getProperty("platform").equals("ANDROID")) {
            dataSheet = this.getClass().getName().split("\\.")[3];
        }
        String currentClassName = this.getClass().getSimpleName();
        dataTable = new DataTable(dataSheet);
        dataTable.capturesRowOfTestCasesInSheet(currentClassName);

        testClassName = currentClassName;
        AutomationCentral.INSTANCE.initLoggerFile();
        logger = AutomationCentral.getLogger();
    }

    @BeforeMethod(alwaysRun = true)
    public void runOncePerMethod(ITestContext context, Method method) throws Exception {
        testName = method.getName();
        logger.info("--------------------- TEST: " + testName + "PLATFORM: " + this.conf.getPlatform() + "------------------");

        if (!testName.equals(testCase)) {
            Testcount = 0;
            if (!listOfHashMap.isEmpty()) {
                listOfHashMap.clear();
            }
            listOfHashMap = dataTable.preProcessAllTestData(testName);
        }
        agent = null;
        if (!testData.isEmpty()) {
            testData.clear();
        }
        testData.putAll(listOfHashMap.get(Testcount++));
        this.testData.put("testName", testName);
    }


    /*THIS IS THE STARTING POINT OF UI TEST - All test methods should call application() method to start with
     * e.g. application().login()*/
    public LandingPageSteps application() throws Exception {
        if (System.getProperty("platform").equals("DESKTOP_WEB")) {
            agent = AgentFactory.createAgent(this.conf);
            agent.getWebDriver().manage().window().maximize();
        } else if (System.getProperty("platform").equals("ANDROID_WEB")) {
            agent = AgentFactory.createAgent(this.conf);
        }
        SessionId sessionId = ((RemoteWebDriver) agent.getWebDriver()).getSessionId();
        logger.debug("Session ID for test: " + testName + " -----> " + sessionId);
        return new LandingPageSteps(this.conf, agent, testData).createInstance();
    }

    public LandingPageSteps application(HashMap<String, String> row) throws Exception {
        if (System.getProperty("platform").equals("DESKTOP_WEB")) {
            agent = AgentFactory.createAgent(this.conf);
            agent.getWebDriver().manage().window().maximize();
        } else if (System.getProperty("platform").equals("ANDROID_WEB")) {
            agent = AgentFactory.createAgent(this.conf);
        }
        SessionId sessionId = ((RemoteWebDriver) agent.getWebDriver()).getSessionId();
        logger.debug("Session ID for test: " + row + " -----> " + sessionId);
        row.putAll(customerInfoTestData);
        return new LandingPageSteps(this.conf, agent, row).createInstance();
    }

    public LandingPageSteps mobileApplication() throws Exception {
        agent = AgentFactory.createAgent(this.conf);
        SessionId sessionId = ((RemoteWebDriver) agent.getWebDriver()).getSessionId();
        logger.debug("Session ID for test: " + testName + " -----> " + sessionId);
        return new LandingPageSteps(this.conf, agent, testData).createInstance();
    }

    public LandingPageSteps mobileApplication(HashMap<String, String> row) throws Exception {
        agent = AgentFactory.createAgent(this.conf);
        SessionId sessionId = ((RemoteWebDriver) agent.getWebDriver()).getSessionId();
        logger.debug("Session ID for test: " + testName + " -----> " + sessionId);
        // reading common data
        logger.info("Reading Common data and adding to test data");
        row.putAll(commonTestData);
        return new LandingPageSteps(this.conf, agent, row).createInstance();
    }

    public List<HashMap<String, String>> getDataIterator(String testName) throws Exception {
        return dataTable.preProcessAllTestData(testName);
    }

    // this method will reomve the duplicate keys present in the common data because preference is given to test case data
    // if duplicate key is found in the common data this method removes it
    public Map<String, String> removeDuplicateKeyValues(Set<String> testCaseData, Map<String, String> commonData) {
        for (String testCaseDataKey : testCaseData) {
            if (commonData.containsKey(testCaseDataKey)) {
                commonData.remove(testCaseDataKey);
            }
        }
        return commonData;
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result, ITestContext context) throws Exception {
        logger.info("On tear down");
        testCase = testName;
        if (agent != null) {
            if (ITestResult.FAILURE == result.getStatus()) {
                logger.info("^^^^^^^^^ TEST STATUS: " + testName + ": FAILURE ^^^^^^^^^");
                File scrShotFile = agent.takeSnapShot(testName);
                byte[] scrShot = Files.readAllBytes(scrShotFile.toPath());
                attachScreenshotAllure(scrShot);
            } else if (ITestResult.SUCCESS == result.getStatus()) {
                logger.info("^^^^^^^^^ TEST STATUS: " + testName + ": SUCCESS ^^^^^^^^^");
                File scrShotFile = agent.takeSnapShot(testName);
                byte[] scrShot = Files.readAllBytes(scrShotFile.toPath());
                attachScreenshotAllure(scrShot);
            }
            try {
                //Agent logger can be added here
            } catch (Exception e) {
                e.printStackTrace();
            }
            agent.quit();
        }
        logger.info("**** TEST: " + testName + " COMPLETED. ****");
    }

    protected String getTestInfoMessage(String stage, String method) {
        return String.format("Test method [%s] %s", method, stage);
    }

    protected String getTestStartInfoMessage(String method) {
        return getTestInfoMessage("start", method);
    }

    protected String getTestEndInfoMessage(String method) {
        return getTestInfoMessage("end", method);
    }

    @Attachment(value = "screenshot", type = "image/jpg")
    public byte[] attachScreenshotAllure(byte[] screenShot) {
        return screenShot;
    }
}
