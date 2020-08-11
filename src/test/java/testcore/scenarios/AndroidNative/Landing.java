package testcore.scenarios.AndroidNative;

import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testcore.scenarios.SupportTest;
import java.util.HashMap;
import java.util.List;

public class Landing extends SupportTest {

    @Test(dataProvider = "myDataProvider", enabled = true, description = "Verify the header options in Landing Page")
    public void VerifyHeaderOptionsTest(HashMap<String, String> row) throws Exception {
        SoftAssert softAssert = new SoftAssert();
        mobileApplication(row)
                .onLandingPage()
                .navigateToLoginBySkipIntro()
                .clickOnKuveraLogo()
                .navigateToHeaderOptions(softAssert)
                .verifyInvestSubHeaderOptions(softAssert)
                .verifyFeaturesSubHeaderOptions(softAssert);
        softAssert.assertAll();
    }

    @DataProvider(name = "myDataProvider")
    public Object[][] myDataProvider(ITestNGMethod context) throws Exception {
        String testName = context.getMethodName();
        List<HashMap<String, String>> dataVal = getDataIterator(testName);
        return dataVal.stream()
                .map(data -> new Object[]{data})
                .toArray(Object[][]::new);
    }
}
