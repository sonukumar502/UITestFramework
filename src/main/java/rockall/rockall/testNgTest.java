package rockall.rockall;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testNgTest {
	WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception {
		// Check if parameter passed from TestNG is 'firefox'
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
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.
			  INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability("requireWindowFocus", true);
			driver = new InternetExplorerDriver(capabilities);
		} else {
			// If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void testParameterWithXML() throws InterruptedException {
		driver.get("https://transferwise.com/ie/currency-converter/");
		// Find user name
		WebElement amt = driver.findElement(By.id("cc-amount"));
		// Fill user name
		amt.clear();
		amt.sendKeys("10000");
		// Find password
		WebElement sourceCurrency = driver.findElement(By.xpath("//*[@data-id='sourceCurrency']"));
		// Fill password
		sourceCurrency.sendKeys("EUR");
		driver.findElement(By.xpath("(//*[@role='listbox'])[1]")).click();
		WebElement targetCurrency = driver.findElement(By.xpath("//*[@data-id='targetCurrency']"));
		// Fill password
		targetCurrency.sendKeys("GBP");
		driver.findElement(By.xpath("(//*[@role='listbox'])[2]")).click();
		WebElement convertButton = driver.findElement(By.id("convert"));
		convertButton.click();
	}
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			System.out.println("Closing browser");
			driver.quit();
		}
	}
}
