package rockall.rockall;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;
import utilities.ExcelUtils;
import utilities.GenericMethods;

public class AppTest {
	WebDriver driver;
	static ExtentTest test;
	static ExtentReports report;
	
	@BeforeClass
	public static void startTest(){
		report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
		test = report.startTest("ExtentDemo");
 
	}
	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception {
		// Check if parameter passed from TestNG is 'firefox'
		browser = GenericMethods.getValueFromPropertiesFile("BROWSER");
		if (browser.equalsIgnoreCase("firefox")) {
			// create firefox instance
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		// Check if parameter passed as 'chrome'
		else if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-gpu");
			driver = new ChromeDriver(options);
		}
		// Check if parameter passed as 'IE'
		else if (browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			/*File ieFile=new File("src/resources/driver/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", ieFile.getAbsolutePath());*/
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("enableElementCacheCleanup", true);
			capabilities.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver(capabilities);
		} else {
			// If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test(dataProvider = "currencyConversionData")
	public void testParameterWithXML(String amt, String srcCurr, String targetCurr) throws InterruptedException, IOException {
		driver.get(GenericMethods.getValueFromPropertiesFile("URL"));
		HomePage home = new HomePage(driver);
		home.enterAmount(amt);
		home.selectSourceCurrency(srcCurr);
		home.selectTargetCurrency(targetCurr);
		home.convert();
		home.validateConversion();
		test.log(LogStatus.PASS, "Navigated to the specified URL");
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			report.endTest(test);
			report.flush();
			System.out.println("Closing browser");
			driver.quit();
					}
	}

	@DataProvider
	public Object[][] currencyConversionData() throws Exception {
		Object[][] testObjArray = ExcelUtils.getTableArray("src/resources/CurrencyConversionData.xlsx", "Sheet1");
		return (testObjArray);
	}

}
