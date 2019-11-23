package rockall.rockall;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        WebDriver driver = null;
        /*WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); 
        options.addArguments("enable-automation"); 
        options.addArguments("--no-sandbox"); 
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation"); 
        options.addArguments("--disable-gpu"); 
        driver = new ChromeDriver(options); 
        driver.get("https://www.google.com/"); */
        /*WebDriverManager.iedriver().setup();
        driver= new InternetExplorerDriver();
        driver.get("https://www.google.com/");
        driver.quit();*/
        /*WebDriverManager.firefoxdriver().setup();
        driver= new FirefoxDriver();
        driver.get("https://www.google.com/");
        driver.quit();*/
    }
}
