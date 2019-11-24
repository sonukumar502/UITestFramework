package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebElement;

public class GenericMethods {
	
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

}
