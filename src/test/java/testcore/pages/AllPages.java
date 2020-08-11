package testcore.pages;

import agent.IAgent;
import central.Configuration;
import enums.ConfigType;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import testcore.pages.Landing.Steps.LandingPageSteps;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;


public class AllPages extends BasePage {

    public static String usernameKey = null;
    public static String usernameEnteredDuringSignUp = null;
    public static String mobileNumberEnteredDuringSignUp = null;

    private static TouchAction touch;

    public AllPages(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
        super(conf, agent, testData);
    }

    @Override
    public String pageName() {
        return AllPages.class.getSimpleName();
    }

    public LandingPageSteps onLandingPage() throws Exception {
        return new LandingPageSteps(getConfig(), getAgent(), getTestData()).createInstance();
    }

    public boolean checkIfFormButtonIsDisabledByDefault() throws Exception {
        return this.getAgent().getWebDriver().findElement(By.tagName("mat-form-field")).getAttribute("class").contains("ng-invalid");
    }

    public void clickEnter() throws Exception {
        Actions actions = new Actions(this.getAgent().getWebDriver());
        actions.sendKeys(Keys.ENTER).build().perform();
    }

    public void clickTab(int length) throws Exception {
        Actions actions = new Actions(this.getAgent().getWebDriver());

        for (int i = 1; i <= length; i++) {
            actions.sendKeys(Keys.TAB).build().perform();
        }
    }

    public void clickBackspace() throws Exception {
        Actions actions = new Actions(this.getAgent().getWebDriver());
        actions.sendKeys(Keys.BACK_SPACE).build().perform();
    }

    public void closeWindowTab() throws Exception {
        pressKeyboardKeysCTRLW();
    }

    public void pressKeyboardKeysCTRLW() throws Exception {
        getAgent().getWebDriver().findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "w"));

    }

    public void waitUntilElementClickable(By locator) throws Exception {
        waiter().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitUntilElementClickable(WebElement element) throws Exception {
        waiter().until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementVisible(By locator) throws Exception {
        waiter().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitUntilElementVisible(WebElement element) throws Exception {
        waiter().until(ExpectedConditions.visibilityOf(element));
    }


    public void waitUntilElementNotPresent(By locator) throws Exception {
        waiter().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitUntilElementNotPresent(WebElement element) throws Exception {
        waiter().until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitUntilThePresenceOfElement(By locator) throws Exception {
        waiter().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public int getElementsSize(String locatorIdentifier) throws Exception {
        By elementLocator = getByLocator(locatorIdentifier);
        return androidDriver().findElements(elementLocator).size();
    }

    public int getElementsSize(By elementLocator) throws Exception {
        return androidDriver().findElements(elementLocator).size();
    }

    public String getElementText(String locatorIdentifier) throws Exception {
        By elementLocator = getByLocator(locatorIdentifier);
        waitUntilElementVisible(elementLocator);
        return androidDriver().findElement(elementLocator).getAttribute("text");
    }

    public boolean isMobileElementEnabled(String locatorIdentifier) throws Exception {
        By elementLocator = getByLocator(locatorIdentifier);
        return Boolean.parseBoolean(androidDriver().findElement(elementLocator).getAttribute("enabled"));
    }

    public void clearAtextField(String locatorIdentifier) throws Exception {
        By elementLocator = getByLocator(locatorIdentifier);

        if (getElementsSize(locatorIdentifier) == 0) {
            swipeDownTillElement(elementLocator);
        }
        androidDriver().findElement(elementLocator).click();
        androidDriver().findElement(elementLocator).clear();

    }

    public void validateErrorMessageOfInputField(SoftAssert softAssert, String locatorIdentifier, String errorMessage) throws Exception {
        By elementLocator = getByLocator(locatorIdentifier);
        if (getElementsSize(locatorIdentifier) == 0) {
            swipeDownTillElement(elementLocator);
        }
        try {
            softAssert.assertEquals(getElementText(locatorIdentifier), errorMessage, "checking if the " + elementLocator + " error text is valid");
        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("unable to find the element with locator " + elementLocator);
        }

    }

    public void clickAnElementWithoutScrollingDown(String locatorIdentifier) throws Exception {
        getControl(locatorIdentifier).click();
    }

    public void clickAnElementWithScrollingDown(String locatorIdentifier) throws Exception {
        By byLocator = getByLocator(locatorIdentifier);
        if (androidDriver().findElements(byLocator).size() == 0) {
            swipeDownTillElement(byLocator);
        }
        waitUntilElementClickable(byLocator);
        getControl(locatorIdentifier).click();
    }


    public void waitUntilProgressBarNotVisible() throws Exception {
        By progressBarXpath = By.xpath("//*[@resource-id='android:id/progress']");
        if (androidDriver().findElements(progressBarXpath).size() > 0) {
            logger.info("Inside if of progressbar");
            waiter().until(ExpectedConditions.invisibilityOfElementLocated(progressBarXpath));
        }
    }

    //clicking
    protected ExpectedCondition<Boolean> elementFoundAndClicked(By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement el = null;
                try {
                    el = androidDriver().findElement(locator);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                el.click();
                return true;
            }
        };
    }


    public void pushToMobileDevice(String path, String fileName) throws IOException {
        String udid = System.getProperty("mobile_udid");
        String cmd = "adb -s " + udid + " push " + path + " /storage/emulated/0/" + fileName;
        System.out.println(cmd);
        Runtime.getRuntime().exec(cmd);
    }


    //swiping methods
    //Vertical Swipe by percentages
    public void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) throws Exception {
        Dimension size = androidDriver().manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
        System.out.println("Inside swipe method");

        new TouchAction(androidDriver())
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();
    }

    public void multipleVerticalSwipes(int count, double startPercentage, double endPercentage, double anchorPercentage) throws Exception {
        for (int i = 1; i <= count; i++) {
            verticalSwipeByPercentages(startPercentage, endPercentage, anchorPercentage);
        }
    }

    @Override
    public void swipeDownTillElement(String elementName) throws Exception {
        String msg = String.format("Scrolling till element - ", elementName);
        try {
            logger.info(msg);
            logger.debug(msg);
            for (int i = 0; i < 50; i++) {
                try {
                    getControl(elementName);
                    break;
                } catch (Exception e) {
                    verticalSwipeByPercentages(0.8, 0.3, 0.5);
                }

            }
            this.getAgent().takeConditionalSnapShot();
        } catch (Exception e) {
            logger.debug(String.format("Failure in %s", msg));
        }
    }


    public void swipeDownTillElement(By locator) throws Exception {
        String msg = String.format("Scrolling till element located by - ", locator.toString());
        try {
            logger.info(msg);
            logger.debug(msg);
            for (int i = 0; i < 50; i++) {
                try {
                    androidDriver().findElement(locator);
                    break;
                } catch (Exception e) {
                    verticalSwipeByPercentages(0.8, 0.3, 0.5);
                }

            }
            this.getAgent().takeConditionalSnapShot();
        } catch (Exception e) {
            logger.debug(String.format("Failure in %s", msg));
        }
    }

    public void swipeDownTillElement(By locator, double startPercentage, double endPercentage, double anchorPercentage) throws Exception {
        String msg = String.format("Scrolling till element located by - ", locator.toString());
        try {
            logger.info(msg);
            logger.debug(msg);
            for (int i = 0; i < 50; i++) {
                try {
                    androidDriver().findElement(locator);
                    break;
                } catch (Exception e) {
                    verticalSwipeByPercentages(startPercentage, endPercentage, anchorPercentage);
                }

            }
            this.getAgent().takeConditionalSnapShot();
        } catch (Exception e) {
            logger.debug(String.format("Failure in %s", msg));
        }
    }

    //Horizontal Swipe by percentages
    public void horizontalSwipeByPercentage(double startPercentage, double endPercentage, double anchorPercentage) throws Exception {
        Dimension size = (androidDriver()).manage().window().getSize();
        int anchor = (int) (size.height * anchorPercentage);
        int startPoint = (int) (size.width * startPercentage);
        int endPoint = (int) (size.width * endPercentage);

        new TouchAction(androidDriver())
                .press(point(startPoint, anchor))
                .waitAction(waitOptions(Duration.ofMillis(1000)))
                .moveTo(point(endPoint, anchor))
                .release().perform();
    }

    public void multipleHorizontalSwipes(int count, double startPercentage, double endPercentage, double anchorPercentage) throws Exception {
        for (int i = 1; i <= count; i++) {
            horizontalSwipeByPercentage(startPercentage, endPercentage, anchorPercentage);
        }
    }

    public void swipeRightTillElement(By locator) throws Exception {
        String msg = String.format("Scrolling right till element located by - ", locator.toString());
        try {
            logger.info(msg);
            logger.debug(msg);
            for (int i = 0; i < 50; i++) {
                try {
                    androidDriver().findElement(locator);
                    break;
                } catch (Exception e) {
                    horizontalSwipeByPercentage(0.8, 0.2, 0.8);
                }

            }
            this.getAgent().takeConditionalSnapShot();
        } catch (Exception e) {
            logger.debug(String.format("Failure in %s", msg));
        }
    }

    //Tap by coordinates
    public void tapByCoordinates(int x, int y) throws Exception {
        new TouchAction(androidDriver())
                .tap(point(x, y))
                .waitAction(waitOptions(Duration.ofMillis(250))).perform();
    }

    //tap by locator
    public void tapByLocator(By aLocator) throws Exception {
        touch = new TouchAction(androidDriver());
        touch.tap(TapOptions.tapOptions().withElement(ElementOption.element(androidDriver().findElement(aLocator))))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).perform();
    }


    public void changeToNative() throws Exception {
        Set<String> contexts = androidDriver().getContextHandles();
        logger.info(contexts);
        for (String context : contexts) {
            if (context.contains("NATIVE_APP")) {
                androidDriver().context(context);
                break;
            }
        }
    }

    public void clickSaveBtn() throws Exception {
        By saveXpath = getByLocator("saveBtn");
        waitUntilElementClickable(saveXpath);
        tapByLocator(saveXpath);
    }

    // selector type should be the xpath, id,.. etc strategy that is defined in json
    // selector value are the values of respective strategies
    public By getByLocator(String selectorType, String selectorValue) {
        switch (selectorType) {
            case "XPATH":
                return By.xpath(selectorValue);
            default:
                return null;
        }
    }

    public By getByLocator(String elementName) {
        String selectorType = String.valueOf(this.getIdentifier(elementName).getIdType());
        String selectorValue = this.getIdentifier(elementName).getValue();
        switch (selectorType) {
            case "XPATH":
                return By.xpath(selectorValue);
            case "ID":
                return By.id(selectorValue);
            default:
                return null;
        }
    }


    // adb commandsm methods
    public void grantAllDevicePermissionsForAgentApp() throws IOException {
        String udid = System.getProperty("mobile_udid");
        String readStorageCmd = "adb shell pm grant com.kaleidofin.kaleidofinmobile.debug android.permission.READ_EXTERNAL_STORAGE";
        String writeStorageCmd = "adb shell pm grant com.kaleidofin.kaleidofinmobile.debug android.permission.WRITE_EXTERNAL_STORAGE";
        String cameraPermission = "adb shell pm grant com.kaleidofin.kaleidofinmobile.debug android.permission.CAMERA";
        String microphonePermission = "adb shell pm grant com.kaleidofin.kaleidofinmobile.debug android.permission.RECORD_AUDIO";
        Runtime.getRuntime().exec(readStorageCmd);
        Runtime.getRuntime().exec(writeStorageCmd);
        Runtime.getRuntime().exec(cameraPermission);
        Runtime.getRuntime().exec(microphonePermission);
    }

    public void runAdbCommand(String adbCommand) throws IOException {
        Runtime.getRuntime().exec(adbCommand);
    }

    //test data related methods
    public String getRequiredTestData(String keyIdentifier) {
        String partnerName = getTestData().get("PartnerName");
        if (getTestData().containsKey(keyIdentifier)) {
            return getTestData().get(keyIdentifier);
        } else if (getTestData().containsKey("cd_" + keyIdentifier)) {
            return getTestData().get("cd_" + keyIdentifier);
        } else {
            String requiredValue = getTestData().get("cd_" + partnerName + "_" + keyIdentifier);
            return requiredValue;
        }
    }

    public void verifyBreadCrumbGoalDetails(SoftAssert softAssert) throws Exception {
        String actualBcGoalName = getElementText("breadCrumbGoalName");
        String bcGoalAmountDuration = getElementText("breadCrumbGoalAmountDuration").substring(1);
        String bcMonthlyContribution[] = bcGoalAmountDuration.split("/")[0].substring(1).split(",");
        String actualBcMonthlyContribution = "";
        for (int i = 0; i < bcMonthlyContribution.length; i++) {
            actualBcMonthlyContribution += bcMonthlyContribution[i];
        }
        String actualBcGoalDuration = bcGoalAmountDuration.split("/")[1].split("\\s")[2];
        String expectedBcGoalName = getRequiredTestData("GoalName");
        String expectedBcMonthlyContribution = getRequiredTestData("MonthlyContribution");
        String expectedBcGoalDuration = getRequiredTestData("GoalDuration");
        softAssert.assertEquals(actualBcGoalName, expectedBcGoalName, "for bread crumb goal name");
        softAssert.assertEquals(actualBcMonthlyContribution, expectedBcMonthlyContribution, "for bread crumb Monthly Contribution");
        softAssert.assertEquals(actualBcGoalDuration, expectedBcGoalDuration, "for bread crumb total Goal Duration");
    }

    /*
     * terminating an app requires more time than 500ms.
     * So it is looped for 20 times atmost increasing the time 10seconds
     * */
    public void closeAndReOpenApp() throws Exception {
        int count = 1;
        int maxRetry = 20;
        while (count <= maxRetry) {
            try {
                if (androidDriver().terminateApp(this.getConfig().getValue(ConfigType.APP_PACKAGE))) {
                    break;
                }
            } catch (Exception e) {
                count++;
                if (count == maxRetry) {
                    e.printStackTrace();
                }
            }
        }

        androidDriver().activateApp(this.getConfig().getValue(ConfigType.APP_PACKAGE));

    }


}
