package pageObjectModel;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

import testCase.TestAutomation;



public class Login extends TestAutomation{
	
	private static final Row Null = null;
	public  WebDriver driver;
	public WebElement	elm;
	public  WebDriverWait wait;
	Logger log=Logger.getLogger("Login");
	
	By signin =By.xpath("//*[@class='login']");
	By email=By.id("email");
	By password=By.id("passwd");
	By login_btn=By.id("SubmitLogin");
	By signout =By.xpath("//*[@class='logout']");
	By account_name=By.xpath("//*[@class='account']/span");
	
	//Signup
	By email_create=By.id("email_create");
	By create_account=By.id("SubmitCreate");
	By male_radiobutton=By.id("id_gender1");
	By cust_firsname=By.id("customer_firstname");
	By cust_lastname=By.id("customer_lastname");
	By cust_email=By.id("email");
	By cust_password=By.id("passwd");
	By DOB_days=By.id("days");
	By DOB_month=By.id("months");
	By DOB_year=By.id("years");
	By address_firstname=By.id("firstname");
	By address_lastname=By.id("lastname");
	By address_field=By.id("address1");
	By city_field=By.id("city");
	By state_drpdwn=By.id("id_state");
	By postal_code_field=By.id("postcode");
	By phone_field=By.id("phone");
	By SubmitApplication=By.id("submitAccount");
	By AccountCreationInfo=By.xpath("//*[@class='info-account']");
	
	TestAutomation setup=new TestAutomation();
	
	public Login(WebDriver in_driver) {
		driver=in_driver;
		wait=new WebDriverWait(driver, 20);
	}
	
	  
	public void login_validations(String mail,String pass,String Firstname,String Lastname) throws Exception {
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(signin));
		driver.findElement(signin).click();
		log.debug("Testing");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(email));
		driver.findElement(email).sendKeys(mail);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(password));
		driver.findElement(password).sendKeys(pass);
		driver.findElement(login_btn).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(signout));
		Reporter.log("Logged in successfully");
		if(driver.findElement(signout).isDisplayed()) {
			test.log(LogStatus.PASS,"Logged in successfully");
		}
		else {
			test.log(LogStatus.FAIL, "Login failed");
			getScreenhot(driver, "InvoiceFailure");
		}
			
	}
	
	public WebElement btnfind(WebDriver driver) {
		elm=driver.findElement(signin);
		return elm;
	}
	
	public void signUp(String Firstname, String Lastname,String email, String password, String address, String city, String postalcode, String phone) throws Exception {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(signin));
		btnfind(driver).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(email_create));
		driver.findElement(email_create).sendKeys(email);
		driver.findElement(create_account).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(male_radiobutton));
		if(driver.findElement(male_radiobutton).isDisplayed()) {
			driver.findElement(male_radiobutton).click();
			driver.findElement(cust_firsname).sendKeys(Firstname);
			driver.findElement(cust_lastname).sendKeys(Lastname);
			driver.findElement(cust_password).sendKeys(password);
			
			Select days_drop_down=new Select(driver.findElement(DOB_days));
			days_drop_down.selectByValue("8");
			
			Select month_drop_down=new Select(driver.findElement(DOB_month));
			month_drop_down.selectByValue("9");
			
			Select year_drop_down=new Select(driver.findElement(DOB_year));
			year_drop_down.selectByValue("1949");
			
			driver.findElement(address_firstname).sendKeys(Firstname);
			driver.findElement(address_lastname).sendKeys(Lastname);
			driver.findElement(address_field).sendKeys(address);
			driver.findElement(city_field).sendKeys(city);
			
			Select state_drop_down=new Select(driver.findElement(state_drpdwn));
			state_drop_down.selectByVisibleText(city);
			driver.findElement(postal_code_field).sendKeys(postalcode);
			driver.findElement(phone_field).sendKeys(phone);
			driver.findElement(SubmitApplication).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(AccountCreationInfo));
			if(driver.findElement(AccountCreationInfo).isDisplayed()) {
				Reporter.log("Signed up successfully");
				test.log(LogStatus.PASS, "Signed in successfully");
				driver.findElement(signout).click();
			}
			else {
				test.log(LogStatus.FAIL, "Please use unique values");
				getScreenhot(driver, "InvoiceFailure");
			}
			
			
			
		}
		
	}
	
	
	
	
}