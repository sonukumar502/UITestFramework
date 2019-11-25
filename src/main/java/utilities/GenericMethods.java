package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import rockall.rockall.AppTest;

public class GenericMethods {
	WebDriver driver;
	public static void click(WebElement we){
		we.click();
	}
	
	public static void enterText(WebElement we, String txt){
		we.sendKeys(txt);;
	}
	
	public static void clearText(WebElement we){
		we.clear();
	}
	
	public static String getValueFromPropertiesFile(String key) throws IOException{
		InputStream input = new FileInputStream("src/resources/Config.properties");
		Properties prop = new Properties();
		prop.load(input);
		String value=prop.getProperty(key);
		return value;
	}
	
	public String takeScreenshot() throws Exception {
		driver=Driver.driver;
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		Date date= new Date();
		long time = date.getTime();
		String filePath = System.getProperty("user.dir") + "\\target\\screenshots\\"+time+".png";
		File DestFile = new File(filePath.toString());
		FileUtils.copyFile(SrcFile, DestFile);
		return filePath;
	}
	

}
