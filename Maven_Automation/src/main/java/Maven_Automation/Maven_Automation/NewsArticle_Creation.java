package Maven_Automation.Maven_Automation;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewsArticle_Creation {
	
	//Declaring the local variables to store the runtime data.
	String testData,runTestcase,testCaseId,strDesc,strExpres,strActres,strStatus;
	String URL,UserName,Password,NewsTitle,ShortDesc,NewsContent,IsFeatured,PublishDate;
	String ExpiryDate,Owner,ThumbnailImage,Tags,Mentions,OrganizationalUnits,Country;
	String NewsTopic,NewsType;
	int expNewsRowNumber =1;
	boolean isExceptionNews = false;
	WebDriver driver;
	int rownumber=0;
	XSSFWorkbook testdatawb;
	int testdatarown=0;
	
	public void newsCreation_DWHomePage(globalMethods globalobj,String result_Path,String module_Name,String subModule_Name){
		
		try{
			DataFormatter formatter = new DataFormatter();
			String sheetName ="NewsArticle";
			XSSFSheet s = testdatawb.getSheet(sheetName);
			testData_NewsArticle(s,rownumber,formatter);
			testData=globalobj.testDataPath(module_Name,subModule_Name);
			testdatawb =new  XSSFWorkbook(new FileInputStream(testData));
			for (rownumber = expNewsRowNumber; rownumber <=s.getLastRowNum(); rownumber++) {
				if (runTestcase.equalsIgnoreCase("Yes")){
					driver = globalobj.openBrowser(URL);
					//Open the browser by calling the method a method
					driver.findElement(globalobj.getbjectLocator("Username")).sendKeys(UserName);
					driver.findElement(globalobj.getbjectLocator("Password")).sendKeys(Password);
					driver.findElement(globalobj.getbjectLocator("SubmitLogin")).click();
					Thread.sleep(25000);
					driver.findElement(By.id("menuTrigger")).click();
					Thread.sleep(5000);
					
				}
				
			}
		}catch(Exception e){
			
		}
		finally{
			if(isExceptionNews == true){
				expNewsRowNumber= rownumber +1;
				isExceptionNews = false;
				
			}
		}
		
	}//Closing the method newsCreation_DWHomePage
	//Declaring the method to read the test data from NewsArticle worksheet of DWHome Page
	public void testData_NewsArticle(XSSFSheet s,int rownumber,DataFormatter formatter) throws Exception{
		runTestcase= formatter.formatCellValue(s.getRow(rownumber).getCell(0));
		testCaseId =formatter.formatCellValue(s.getRow(rownumber).getCell(1));
		URL=formatter.formatCellValue(s.getRow(rownumber).getCell(4));
		UserName=formatter.formatCellValue(s.getRow(rownumber).getCell(5));
		Password=formatter.formatCellValue(s.getRow(rownumber).getCell(6));
		NewsTitle=formatter.formatCellValue(s.getRow(rownumber).getCell(7));
		ShortDesc=formatter.formatCellValue(s.getRow(rownumber).getCell(8));
		NewsContent=formatter.formatCellValue(s.getRow(rownumber).getCell(9));
		IsFeatured=formatter.formatCellValue(s.getRow(rownumber).getCell(10));
		PublishDate=formatter.formatCellValue(s.getRow(rownumber).getCell(11));
		ExpiryDate=formatter.formatCellValue(s.getRow(rownumber).getCell(12));
		Owner=formatter.formatCellValue(s.getRow(rownumber).getCell(13));
		ThumbnailImage=formatter.formatCellValue(s.getRow(rownumber).getCell(14));
		Tags=formatter.formatCellValue(s.getRow(rownumber).getCell(15));
		Mentions=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
		OrganizationalUnits=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
		Country=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
		NewsTopic=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
		NewsType=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
		
	}

}//Closing Class
