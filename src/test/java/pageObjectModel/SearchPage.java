package pageObjectModel;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.Assertion;

import com.relevantcodes.extentreports.LogStatus;

import testCase.TestAutomation;

public class SearchPage extends TestAutomation{
	public  WebDriver driver;
	public WebElement	elm;
	public  WebDriverWait wait;
	
	
	@FindBy(id="search_query_top")
	WebElement searchBar;
	
	@FindBy(name="submit_search")
	WebElement searchSubmit;
	
	@FindBy(className="lighter")
	WebElement searchValue;
	
	@FindBy(className="heading-counter")
	WebElement resultCounter;
	
	@FindBy(xpath="//*[@class='right-block'][1]/h5/a")
	WebElement resultText;
	
	@FindBy(xpath="//*[@class='right-block'][1]")
	WebElement Item;
	
	@FindBy(xpath="(//*[@class='right-block'][1]//a[@title='Add to cart'])[1]")
	WebElement ItemCart;
	
	@FindBy(xpath="//*[@class='cross']/following::h2/i")
	WebElement AddedToCartMsg;
	
	@FindBy(xpath="//*[@title='Proceed to checkout']")
	WebElement ProceedToCheckout;
	
	@FindBy(xpath="(//span[contains(text(),'Proceed to checkout')])[2]")
	WebElement ProceedToCheckout1;
	
	@FindBy(xpath="//*[@id='uniform-cgv']//input")
	WebElement AgreeTerms;
	
	@FindBy(xpath="//*[@title='Pay by bank wire']")
	WebElement PayByBank;
	
	@FindBy(xpath="//span[contains(text(),'I confirm my order')]")
	WebElement ConfirmOrder;
	
	@FindBy(xpath="//*[@class='account']")
	WebElement AccountPage;
	
	@FindBy(xpath="//*[@title='Orders']")
	WebElement OrderDetails;
	
	@FindBy(xpath="//*[@title='Invoice']")
	WebElement InvoiceBtn;
	
	@FindBy(id="summary_products_quantity")
	WebElement CartSummary;
	
	@FindBy(className="address_title")
	WebElement BillingAddress;
	
	@FindBy(xpath="//*[@class='cheque-indent']/strong")
	WebElement OrderConfirmation;
	
	
	
	public SearchPage(WebDriver in_driver) {
		driver=in_driver;
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver, 20);
		
	}
	
	
	public void searchItems(String itemname) {
		wait.until(ExpectedConditions.visibilityOf(searchBar));
		searchBar.sendKeys(itemname);
		searchSubmit.click();
		
		wait.until(ExpectedConditions.visibilityOf(searchValue));
		String result=resultCounter.getText();
		int resultvalue= Integer.parseInt(result.substring(0, 1));
		String resultString=driver.findElement(By.xpath("//*[@class='right-block'][1]/h5/a")).getText();
		Assert.assertTrue((resultString.toLowerCase()).contains(itemname.toLowerCase()), "Total of "+resultvalue+" result has been found for the category "+itemname);
		Reporter.log("Logged in successfully");
		test.log(LogStatus.PASS,"Item is searched successfully");
	}
	
	public void purchaseItem() {
		Actions action=new Actions(driver);
		action.moveToElement(Item).build().perform();  // Mouse Hover
		wait.until(ExpectedConditions.visibilityOf(ItemCart));
		ItemCart.click();
		wait.until(ExpectedConditions.visibilityOf(AddedToCartMsg));
		ProceedToCheckout.click();
		wait.until(ExpectedConditions.visibilityOf(CartSummary));
		ProceedToCheckout1.click();
		wait.until(ExpectedConditions.visibilityOf(BillingAddress));
		ProceedToCheckout1.click();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		AgreeTerms.click();
		ProceedToCheckout1.click();
		wait.until(ExpectedConditions.visibilityOf(PayByBank));
		PayByBank.click();
		wait.until(ExpectedConditions.visibilityOf(ConfirmOrder));
		ConfirmOrder.click();
		wait.until(ExpectedConditions.visibilityOf(OrderConfirmation));
		String orderText=OrderConfirmation.getText();
		test.log(LogStatus.PASS,orderText);
		
	}
	
	public void downloadInvoice() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(AccountPage));
		AccountPage.click();
		wait.until(ExpectedConditions.visibilityOf(OrderDetails));
		OrderDetails.click();
		wait.until(ExpectedConditions.visibilityOf(InvoiceBtn));
		InvoiceBtn.click();
		if(isFileDownloaded("Invoice\\","IN")){
			test.log(LogStatus.PASS,"Invoice downloaded successfully");
		}
		else {
			test.log(LogStatus.FAIL,"Invoice not downloaded successfully");
			getScreenhot(driver, "InvoiceFailure");
		}
		
		
	}
	
	
	public boolean isFileDownloaded(String fileDownloadpath, String fileName) {

		boolean flag = false;

		File directory = new File(fileDownloadpath);

		File[] content = directory.listFiles();
		 for (int i = 0; i < content.length; i++) {
			 if (content[i].getName().equals(fileName)) {
				 System.out.println(content[i].getName()); 
				 return flag=true;
			 
			 }
			 else {
					 System.out.println(content[i].getName());
				 }
		}
		 	  	
		 
		 return flag;
	} 
}
