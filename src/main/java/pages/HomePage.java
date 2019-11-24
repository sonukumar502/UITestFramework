package pages;

import org.apache.commons.compress.archivers.zip.GeneralPurposeBit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utilities.GenericMethods;

public class HomePage{
	
	WebDriver driver;
	
	@FindBy(id = "cc-amount")
	private WebElement amountTxt;
	
	@FindBy(xpath = "//*[@data-id='sourceCurrency']")
	private WebElement sourceCurrencyTxt;
	
	@FindBy(xpath = "(//*[@role='listbox'])[1]")
	private WebElement sourceCurrencyList;
	
	@FindBy(xpath = "//*[@data-id='targetCurrency']")
	private WebElement targetCurrencyTxt;
	
	@FindBy(xpath = "(//*[@role='listbox'])[2]")
	private WebElement targetCurrencyList;
	
	@FindBy(id = "convert")
	private WebElement convertButton;

	@FindBy(xpath = "//*[@class='text-success']")
	private WebElement converisonRate;
	
	@FindBy(id = "cc-amount-to")
	private WebElement convertedValue;
	
	public HomePage(WebDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }
	
	String conversionAmt=null;
	public void enterAmount(String amt){
		conversionAmt=amt;
		GenericMethods.clearText(amountTxt);
		GenericMethods.enterText(amountTxt, amt);
	}
	
	public void selectSourceCurrency(String srcCurr){
		GenericMethods.enterText(sourceCurrencyTxt, srcCurr);
		GenericMethods.click(sourceCurrencyList);
	}
	
	public void selectTargetCurrency(String targetCurr){
		GenericMethods.enterText(targetCurrencyTxt, targetCurr);
		GenericMethods.click(targetCurrencyList);
	}
	
	public void convert(){
		GenericMethods.click(convertButton);
	}
	
	public void validateConversion(){
		float conversionAmtFloat=Float.valueOf(conversionAmt);
		float conversionRate=Float.valueOf(converisonRate.getText());
		float convertedValueFloat=Float.valueOf(convertedValue.getAttribute("value"));
		float expectedConvertedValue=conversionRate*conversionAmtFloat;
		if(convertedValueFloat==expectedConvertedValue){
			System.out.println("Converted value is as expected");
			Assert.assertTrue(true);
		}
		else{
			System.out.println("Converted value is not as expected. Expected: "+expectedConvertedValue+" Actual: "+convertedValueFloat);
			Assert.assertTrue(false);
		}
		
	}

}
