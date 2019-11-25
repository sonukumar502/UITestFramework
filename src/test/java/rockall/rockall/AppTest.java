package rockall.rockall;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import utilities.Driver;
import utilities.ExcelUtils;
import utilities.ExtentReport;
import utilities.GenericMethods;

public class AppTest {
	public static WebDriver driver;
	ExtentReport ex= new ExtentReport();
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception {
		browser = GenericMethods.getValueFromPropertiesFile("BROWSER");
		ex.startReport(System.getProperty("os.name"), browser);
		driver=Driver.getDriver(browser);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test(dataProvider = "currencyConversionData")
	public void testParameterWithXML(String tcName,String amt, String srcCurr, String targetCurr) throws Exception {
		ex.test = ex.extent.createTest(tcName, "");
		driver.get(GenericMethods.getValueFromPropertiesFile("URL"));
		HomePage home = new HomePage(driver);
		home.enterAmount(amt);
		home.selectSourceCurrency(srcCurr);
		home.selectTargetCurrency(targetCurr);
		home.convert();
		home.validateConversion();
		}

	@AfterMethod
	public void afterEveryTest(ITestResult result) throws Exception{
		ex.getResult(result);
	}
	
	@AfterClass
	public void tearDown() {
		if (driver != null) {
			System.out.println("Closing browser");
			driver.quit();
			ex.endTest();
					}
	}

	@DataProvider
	public Object[][] currencyConversionData() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray("src/resources/CurrencyConversionData.xlsx", "Sheet1");
		return (testObjArray);
	}

}
