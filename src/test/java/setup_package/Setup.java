package setup_package;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import pageObjectModel.Login;

public class Setup {
	WebDriver driver;
	public static ExtentReports reports;
	public static ExtentTest test;
	
	
	
	public WebDriver getdriver() {
		return driver;
	}
	
	
	
  @Parameters({"Browser","URL"})
  @BeforeTest
  public void setup(@Optional("FireFox")String Browser,String URL) throws IOException {
	
	//	  	ChromeOptions option = new ChromeOptions();
	//	  	option.addArguments("--ignore-certificate-errors");
	//	  java -jar D:\Selenium\selenium-server-standalone-2.53.0.jar -role node -hub 
	//	  http://10.123.74.64:4444/grid/register/ -browser browserName=firefox -port 5555
		  	System.setProperty("webdriver.chrome.driver", "D:\\Eclipse_Workspace\\LearningSeleniumMaven\\lib\\chromedriver.exe");
		  	driver=new ChromeDriver();
		  	driver.manage().window().maximize();
			driver.navigate().to(URL);
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			reports=new ExtentReports(System.getProperty("user.dir")+"\\Report\\Extenreport.html");
			test=reports.startTest("Login");
			
			//Test Data setup
			

}

  
  @Parameters({"Username","Password"})
  @Test(priority=0)
  public void Test001(String username,String password) throws IOException {
	  Login login=new Login(driver);
	  login.login_validations(username,password);
//	  login.signUp();
  }
  @Test(dataProvider="Signup data",priority=1)
  public void Test002(String Firstname, String Lastname,String email, String password, String address, String city, String postalcode, String phone) throws IOException{
	  Login login=new Login(driver);
	  login.signUp(Firstname, Lastname, email, password, address, city, postalcode, phone);
  }
  
  @AfterTest
  public void Teardown() {
	  reports.flush();
	  driver.quit();
  }

  @DataProvider(name="Signup data")
	public Object get_signup_data() throws IOException {
	    Login login=new Login(driver);
		System.out.println("Inside signUp");
		login.setupExcel();
		Row row=login.signUp.getRow(0);
		int rownum=login.signUp.getLastRowNum()-login.signUp.getFirstRowNum();
		int colnum=row.getLastCellNum();
		Object object[][]=new Object[rownum+1][colnum];
		System.out.println("Rownum"+rownum);
		
		for (int i=1;i<=rownum;i++) {
			row=login.signUp.getRow(i);
			String data = null;
			System.out.println(row.getLastCellNum());
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
				System.out.println(object[i][j]+"||"+i+j);
				}
				System.out.println();
		}
		return object;
	}
  
}
