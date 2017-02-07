package Maven_Automation.Maven_Automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;



interface reusableMethods{
	//Declaring a variables to store constant values
	//public static final String URL = "file://C:/Chandra/Selenium_Automation/CreateAccount.htm";
	//Declaring a method to create an excel file to store the result (pass/fail status)
	public String resultFile(String mainModule,String subModule) throws IOException;
	//Declaring a method to start a Browser
	//public DefaultSelenium openBrowser()throws Exception;
	public WebDriver openBrowser(String Url)throws Exception;
	//Declaring a method to close the Browser
	public void closeBrowser(WebDriver driver) throws Exception;
	//Declaring a method to return a test data sheet path
	public String testDataPath(String module_Name,String subModule_Name) throws Exception;
	//Declaring a method to write the test results into an excel sheet.
	//public void updateTestResult(module_Name,subModule_Name)throws Exception;//,testCaseId,strDesc,strExpres,strActres
	public void updateTestResult(String module_Name,String subModule_Name,String testCaseId,String strDesc,String strExpres,String strActres,String strStatus,String result_Path)throws Exception;
	//Declaring a method to read the object repository values from Object Repository propertiels file and 
	//Declaring a method to buiding the tag value
	public By getbjectLocator(String locatorName);
	
}
//Declaring a class which will implement the interface (reusable script)
public class globalMethods implements reusableMethods {
	
	//Declaring a local variables to store the run time values
	//public DefaultSelenium selenium;
	public String file_Path,test_Res_Path;
	public File file,file1,file2;
	//public WebDriver driver;
	//public WebDriver driver = new ChromeDriver();	
	//This method is useful to create a excel file and save into the local drive.
	public String resultFile(String mainModule,String subModule) throws IOException{
		try{
			file_Path=System.getProperty("user.dir");
			if (subModule.isEmpty()) {
				file_Path=file_Path+"\\Result\\"+mainModule+"\\";
				file = new File(file_Path);
				if(file.exists()==false){ 
					file.mkdir(); 
		        } 
				file_Path=file_Path+mainModule+".xlsx";
				test_Res_Path=file_Path;
			}else{
				file_Path=file_Path+"\\Result\\"+mainModule+"\\"+subModule+"\\";
				file = new File(file_Path); 
				if(file.exists()==false){ 
					file.mkdirs(); 
		        } 
				file_Path=file_Path+mainModule+"_"+subModule+".xlsx";
				test_Res_Path=file_Path;
			}
			file1 = new File(test_Res_Path);
			if (!file1.exists()){
				XSSFWorkbook wb = new XSSFWorkbook(); 
				XSSFSheet resultsheet=wb.createSheet("Result");
				XSSFRow row= resultsheet.createRow(0);
				row.createCell(0).setCellValue("TestcaseID");
				row.createCell(1).setCellValue("Step_Description");
				row.createCell(2).setCellValue("Expected_Result");
				row.createCell(3).setCellValue("Actual_Result");
				row.createCell(4).setCellValue("Status");
				FileOutputStream fileOut = new FileOutputStream(file1); 
		        wb.write(fileOut); 
		        fileOut.close(); 
			    file_Path ="";
			    }
			
		}catch (Exception e){
			System.out.println("Please check the result path:"+e);
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		}
		return test_Res_Path;
		
	}
	//This method is useful to update the test result into the excel sheet.
	public void updateTestResult(String module_Name,String subModule_Name,String testCaseId,String strDesc,String strExpres,String strActres,String strStatus,String result_Path){
		
		try{
			XSSFWorkbook wb = new  XSSFWorkbook(new FileInputStream(result_Path));
		    XSSFSheet sheet1 = wb.getSheetAt(0);
		    int lastrowno=sheet1.getLastRowNum();
		    XSSFRow row= sheet1.createRow(lastrowno+1);
		    row.createCell(0).setCellValue(testCaseId);
		    row.createCell(1).setCellValue(strDesc);
		    row.createCell(2).setCellValue(strExpres);
		    row.createCell(3).setCellValue(strActres);
		    row.createCell(4).setCellValue(strStatus);
		    file2 = new File(result_Path);
		    FileOutputStream fileOut = new FileOutputStream(file2); 
	        wb.write(fileOut); 
	        fileOut.close();
		    
		}catch (Exception e){
			System.out.println(e);
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		}
	}
	//This method is useful to return the test data path based on the module name
	public String testDataPath(String module_Name,String subModule_Name) throws Exception{
		String testdataPath=System.getProperty("user.dir")+"\\Maven_Automation"+"\\TestData\\";
		testdataPath =testdataPath+module_Name+".xlsx";
		return testdataPath;
	}
	public WebDriver openBrowser(String URL) throws Exception{
		
		//driver = new InternetExplorerDriver();
		//WebDriver driver = new FirefoxDriver();
		//String baseUrl = "file://C:/Selenium_Automation/CreateAccount.htm";
		//String baseUrl="https://ozone.oakton.com.au";
		//driver.get(URL);
		System.out.println("Before creating the chromedriver object");
		WebDriver driver1 = new ChromeDriver();
		System.out.println("after creating the chromedriver object");
		driver1.manage().window().maximize();
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Runtime.getRuntime().exec("C:\\Automation\\Selenium_Automation\\Handling_Window.exe");
		//driver.manage().window().maximize();
		driver1.navigate().to(URL);
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver1;
		
	}
	//This method is useful to close the Browser and stop the selenium server.
	public void closeBrowser(WebDriver driver){
		try{
			Thread.sleep(5000);
			driver.close();
			/*driver.findElement(By.id("welcomeMenuBox")).click();
			Thread.sleep(2000);
			boolean a=driver.findElement(By.id("zz5_Menu_t")).isDisplayed();
			if(a=true)
            {
                            Thread.sleep(2000);
                            driver.findElement(By.linkText("Sign Out")).click();
                            driver.close();
                            driver.quit();
                            
            }
			else
            {
                            System.out.println("Signout not visible");
			                driver.close();
			                driver.quit(); 
				      
		    }*/
			
		}
			
            catch (Exception e)
            {
			System.out.println("Not able to close the Browser");
			driver.close();
            driver.quit();
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		    }
		
    }
	public By getbjectLocator(String locatorName){
		By locator=null;
		try{
			FileInputStream stream;
			String RepositoryFile;
			RepositoryFile=System.getProperty("user.dir");
			Properties propertyFile = new Properties();
            RepositoryFile =RepositoryFile+ "\\TestData\\ObjectRepository.properties";
            stream = new FileInputStream(RepositoryFile);
			propertyFile.load(stream);
			String locatorProperty = propertyFile.getProperty(locatorName);
			String locatorType = locatorProperty.split(":")[0];
			String locatorValue = locatorProperty.split(":")[1];
            switch(locatorType)
			{
			case "Id":
				locator = By.id(locatorValue);
				break;
			case "Name":
				locator = By.name(locatorValue);
				break;
			case "CssSelector":
				locator = By.cssSelector(locatorValue);
				break;
			case "LinkText":
				locator = By.linkText(locatorValue);
				break;
			case "PartialLinkText":
				locator = By.partialLinkText(locatorValue);
				break;
			case "TagName":
				locator = By.tagName(locatorValue);
				break;
			case "Xpath":
				locator = By.xpath(locatorValue);
				break;
			}
			
		}catch (Exception e)
        {
		System.out.println("Not able to read the webelement value from objectrepository.properties file");
		e.printStackTrace();
		System.err.println("Error: " + e.getMessage());
	    }
		System.out.println("The value of loacator is"+" "+locator);
		return locator;
		
	}
	
}


