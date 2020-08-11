package testcore.pages.androidNative.Landing.Steps;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import org.testng.asserts.SoftAssert;
import testcore.pages.androidNative.LandingPage;
import testcore.pages.Landing.Steps.LandingPageSteps;
import java.util.Map;

public class AndroidNativeLandingPageSteps extends LandingPage {

    public AndroidNativeLandingPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
        super(conf, agent, testData);
    }

    public LandingPageSteps navigateToLoginBySkipIntro() throws Exception {
        // click on Skip
        getControl("skipTxt").click();
        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();
    }

    public LandingPageSteps clickOnKuveraLogo() throws Exception {
        // Click on logo
        getControl("kuveraLogo").click();
        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();
    }

    public LandingPageSteps navigateToHeaderOptions(SoftAssert softAssert) throws Exception {
        // Click on logo
        if (getControl("closeButton").isVisible()) {
            getControl("closeButton").click();
        }
        getControl("hamburgerMenuIcon").click();
        // Verify Invest and its sub headers options
        softAssert.assertTrue(getControl("investTxt").isVisible(), "'Invest' option is not displayed");
        softAssert.assertTrue(getControl("loansTxt").isVisible(), "'Loans' sub header option is not displayed");
        softAssert.assertTrue(getControl("remitTxt").isVisible(), "'Remit' sub header option is not displayed");
        softAssert.assertTrue(getControl("featuresTxt").isVisible(), "'Features' header option is not displayed");
        softAssert.assertTrue(getControl("aboutTxt").isVisible(), "'About' sub header option is not displayed");
        softAssert.assertTrue(getControl("blogTxt").isVisible(), "'Blog' header option is not displayed");
        softAssert.assertTrue(getControl("loginTxt").isVisible(), "'Login' sub header option is not displayed");
        softAssert.assertTrue(getControl("signupTxt").isVisible(), "'Sign up' header option is not displayed");
        takeSnapShot();
        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();
    }

    public LandingPageSteps verifyInvestSubHeaderOptions(SoftAssert softAssert) throws Exception {
        getControl("investDropdown").click();
        takeSnapShot();
        softAssert.assertTrue(getControl("AmazonSaveShopTxt").isVisible(), "'AmazonSave & Shop' sub header option is not displayed");
        softAssert.assertTrue(getControl("goldTxt").isVisible(), "'Gold' sub header option is not displayed");
        softAssert.assertTrue(getControl("mutualFundTxt").isVisible(), "'Mutual Fund' sub header option is not displayed");
        softAssert.assertTrue(getControl("saveSmartTxt").isVisible(), "'SaveSmart' sub header option is not displayed");
        softAssert.assertTrue(getControl("stocksTxt").isVisible(), "'Stocks' sub header option is not displayed");
        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();
    }
    public LandingPageSteps verifyFeaturesSubHeaderOptions(SoftAssert softAssert) throws Exception {
        getControl("featuresTxt").click();
        takeSnapShot();
        softAssert.assertTrue(getControl("setAGoalTxt").isVisible(), "'Set a Goal' sub header option is not displayed");
        softAssert.assertTrue(getControl("tradeSmartTxt").isVisible(), "'TradeSmart' sub header option is not displayed");
        softAssert.assertTrue(getControl("familyAccountTxt").isVisible(), "'Family Account' sub header option is not displayed");
        softAssert.assertTrue(getControl("taxHarvestingTxt").isVisible(), "'Tax Harvesting ' sub header option is not displayed");
        softAssert.assertTrue(getControl("saveTaxesTxt").isVisible(), "'Save Taxes' sub header option is not displayed");
        softAssert.assertTrue(getControl("consolidateTxt").isVisible(),"'Consolidate' sub header option is not displayed");

        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();
    }
    public LandingPageSteps verifyOnEachFundHouse(SoftAssert softAssert) throws Exception {
        // Click on logo
        if (getControl("closeButton").isVisible()) {
            getControl("closeButton").click();
        }
        swipeDown(9);
        getControl("adityaBirla").click();
        takeSnapShot();
        softAssert.assertTrue(getControl("adityaBirlaTxt").isVisible(), "'Aditya Birla Sun Life Mutual Fund'MF is not displayed");
        softAssert.assertTrue(getControl("adityaBirlaWebsite").isVisible(), "'Aditya Birla Sun Life Mutual Fund' Website is not displayed");
        softAssert.assertTrue(getControl("adityaBirlaAddress").isVisible(), "'Aditya Birla Sun Life Mutual Fund' Address is not displayed");
        softAssert.assertTrue(getControl("adityaBirlaPhoneNumber").isVisible(), "'Aditya Birla Sun Life Mutual Fund' PhoneNumber is not displayed");

        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();

    }


}
