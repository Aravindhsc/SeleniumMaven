package testCase;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import pageObjectModel.Login;
import pageObjectModel.SearchPage;

public class TestAutomation {
	public WebDriver driver;
	public static ExtentReports reports;
	public static ExtentTest test;
	String url=System.getProperty("url");
	public Sheet signUp;
	public Sheet loginsheet;
	public FileInputStream fis;
	public HSSFWorkbook WB;
	public File file;
	public String SearchText="Dress";
	
	public WebDriver getdriver() {
		return driver;
	}
	
		  @BeforeSuite
		  @Parameters({"Browser","url"})
		  public void setup(String Browser,String url) throws IOException {		  	
	  		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
	  		File downloadFolder = new File("Invoice");
	  		downloadFolder.mkdir();
	  		
	  		Map<String,Object> preferences= new HashMap<>();
	  		preferences.put("profile.default_content_settings.popups", 0);
	  		preferences.put("download.default_directory","LearningSeleniumMaven\\Invoice\\");
	  		ChromeOptions options = new ChromeOptions();
	  		options.setExperimentalOption("prefs",preferences);
	  		driver = new ChromeDriver(options);

		  	driver.manage().window().maximize();
			driver.navigate().to(System.getProperty("url"));
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			reports=new ExtentReports(System.getProperty("user.dir")+"\\Report\\Extenreport.html");
			
			file=new File("Testdata.xls");
			fis=new FileInputStream(file);
			WB= new HSSFWorkbook(fis);
			signUp=WB.getSheet("SignUp");
			loginsheet=WB.getSheet("Login");
  		  }
  
		  @Test(dataProvider="Signup data")
		  public void Test01(String Firstname, String Lastname,String email, String password, String address, String city, String postalcode, String phone) throws Exception{
			  Login login=new Login(driver);
			  test=reports.startTest("Login");
			  login.signUp(Firstname, Lastname, email, password, address, city, postalcode, phone);
			  login.login_validations(email,password,Firstname,Lastname);
		  }
		  
		  @Test()
		  public void Test02() {
			  SearchPage search=new SearchPage(driver);
			  test=reports.startTest("SearchPage");
			  search.searchItems(SearchText);
		  }
	  
		  @Test()
		  public void Test03() {
			  SearchPage search=new SearchPage(driver);
			  test=reports.startTest("SearchPage");
			  search.purchaseItem();
		  }
		  
		  @Test()
		  public void Test04() throws Exception {
			  SearchPage search=new SearchPage(driver);
			  test=reports.startTest("SearchPage");
			  search.downloadInvoice();
		  }

		  @Test()
		  public void Test05() throws Exception{			//failure scenarios
			  Login login=new Login(driver);
			  test=reports.startTest("Login");
			  login.login_validations("Selenium123@gmail.com","Password123","Selenium","Assignment");		  
		  }
		  
		  @DataProvider(name="Signup data")
			public Object get_signup_data() throws IOException {
				Row row=signUp.getRow(1);
				int rownum=signUp.getLastRowNum()-signUp.getFirstRowNum();
				int colnum=row.getLastCellNum();
				Object object[][]=new Object[rownum][colnum];
				for (int i=0;i<rownum;i++) {
					row=signUp.getRow(i+1);
					String data = null;
						for(int j=0;j<row.getLastCellNum();j++) {
							Cell cell=row.getCell(j);
							if(cell.getCellType()==CellType.STRING) {
							    data = cell.getStringCellValue(); 
								object[i][j]=data;
							}
							else if(cell.getCellType()==CellType.NUMERIC) {
							    data = String.valueOf((int)cell.getNumericCellValue());
							   
								object[i][j]=data;
						}	
						}
				}
				return object;
			}
		  	
		  
		  public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
				String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
		                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
				String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
				File finalDestination = new File(destination);
				FileUtils.copyFile(source, finalDestination);
				return destination;
			}
  
		  @AfterSuite
		  public void Teardown() {
			  reports.flush();
			  driver.quit();
		  }
}
