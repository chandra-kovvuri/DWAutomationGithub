package Maven_Automation.Maven_Automation;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewsArticle_Creation {
	
	//Declaring the local variables to store the runtime data.
	String testData,runTestcase,testCaseId,strDesc,strExpres,strActres,strStatus;
	String URL,UserName,Password,NewsTitle,ShortDesc,NewsContent,IsFeatured,PublishDate;
	String ExpiryDate,Owner,ThumbnailImage,Tags,Mentions,OrganizationalUnits,Country,publishTime;
	String NewsTopic,NewsType,checkInComments;
	int expNewsRowNumber =1;
	boolean isExceptionNews = false;
	WebDriver driver;
	int rownumber=0;
	XSSFWorkbook testdatawb;
	int testdatarown=0;
	public void Newscreation_DWHomePage(globalMethods globalobj,String result_Path,String module_Name,String subModule_Name){
		
		try{
			testData=globalobj.testDataPath(module_Name,subModule_Name);
			testdatawb =new  XSSFWorkbook(new FileInputStream(testData));
			DataFormatter formatter = new DataFormatter();
			String sheetName ="NewsArticle";
			XSSFSheet s = testdatawb.getSheet(sheetName);
			for (rownumber = expNewsRowNumber; rownumber <=s.getLastRowNum(); rownumber++) {
				testData_NewsArticle(s,rownumber,formatter);
				if (runTestcase.equalsIgnoreCase("Yes")){
					driver = globalobj.openBrowser(URL);
					//Open the browser by calling the method a method
					driver.findElement(globalobj.getbjectLocator("Username")).sendKeys(UserName);
					driver.findElement(globalobj.getbjectLocator("Password")).sendKeys(Password);
					driver.findElement(globalobj.getbjectLocator("SubmitLogin")).click();
					Thread.sleep(25000);
					driver.findElement(By.id("menuTrigger")).click();
					Thread.sleep(5000);
					driver.findElement(By.linkText("News Centre")).click();
					WebDriverWait driverWait=new WebDriverWait(driver, 40);
					driverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[@id='btn']"))));
					driver.findElement(By.xpath(".//*[@id='btn']")).click();
					driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(1));
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
					NewsTitle=NewsTitle.concat(sdf.format(date));
					String ms= String.valueOf(System.currentTimeMillis());
					NewsTitle=NewsTitle.concat(ms.substring(7));
					driver.findElement(By.xpath(".//*[@id='ctl00_PlaceHolderMain_nameInput']")).sendKeys(NewsTitle);
					driver.findElement(By.xpath(".//*[@id='ctl00_PlaceHolderMain_createButton']")).click();
					driver.switchTo().defaultContent();
					driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='ctl00_PlaceHolderMain_ctl04_ctl00_ctl00_TextField']")));
					DWHomePage_NewsDetails_DataInputs(driver); 
					
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error message is  " +e.getMessage());
		}
		/*finally{
			if(isExceptionNews == true){
				expNewsRowNumber= rownumber +1;
				isExceptionNews = false;
				
			}
		}*/
		
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
		publishTime =formatter.formatCellValue(s.getRow(rownumber).getCell(12));
		ExpiryDate=formatter.formatCellValue(s.getRow(rownumber).getCell(13));
		Owner=formatter.formatCellValue(s.getRow(rownumber).getCell(14));
		ThumbnailImage=formatter.formatCellValue(s.getRow(rownumber).getCell(15));
		Tags=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
		Mentions=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
		OrganizationalUnits=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
		Country=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
		NewsTopic=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
		NewsType=formatter.formatCellValue(s.getRow(rownumber).getCell(21));
		checkInComments=formatter.formatCellValue(s.getRow(rownumber).getCell(22));
		
	}//Closing the method testData_NewsArticle
	/*Declaring the method to enter the inputs values into News detail page as per the test data sheet*/
	public void DWHomePage_NewsDetails_DataInputs(WebDriver driver) throws Exception{
		//enter the test data inputs into news detail page
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl04_ctl01_ctl00_ctl00_TextField")).sendKeys(ShortDesc);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl05_RichHtmlField_displayContent")).sendKeys(NewsContent);
		Thread.sleep(2000);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl05_RichHtmlField_displayContent")).sendKeys(Keys.TAB);
		Select featuredflag = new Select(driver.findElement(By.id("ctl00_PlaceHolderMain_ctl06_ctl00_DropDownChoice")));
		featuredflag.selectByValue(IsFeatured);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl07_ctl00_ctl00_DateTimeField_DateTimeFieldDate")).sendKeys(PublishDate);
		Select timeUnits = new Select (driver.findElement(By.id("ctl00_PlaceHolderMain_ctl07_ctl00_ctl00_DateTimeField_DateTimeFieldDateHours")));
		timeUnits.selectByValue(publishTime);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl09_ctl00_ctl00_DateTimeField_DateTimeFieldDate")).sendKeys(ExpiryDate);
		//driver.findElement(By.id("ctl00_PlaceHolderMain_ctl10_ctl00_ctl00_UserField_upLevelDiv")).sendKeys(Owner);
		driver.findElement(By.linkText("Click here to insert a picture from SharePoint.")).click();
		Thread.sleep(4000);
		driver.switchTo().frame(4);
		driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(ThumbnailImage);
		driver.findElement(By.xpath(".//*[@id='ctl00_OkButton']")).click();
		Thread.sleep(8000);
		int taglen=Tags.length();
		if (taglen>0){
			driver.findElement(By.id("ctl00_PlaceHolderMain_ctl14_ctl00_ctl02editableRegion")).sendKeys(Tags);
		}
		int mentlen=Mentions.length();
		if (mentlen>0){
			driver.findElement(By.id("ctl00_PlaceHolderMain_ctl15_ctl00_ctl00_UserField_upLevelDiv")).sendKeys(Mentions);
		}
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl16_ctl00_ctl02editableRegion")).sendKeys(OrganizationalUnits);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl18_ctl00_ctl02editableRegion")).sendKeys(Country);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl19_ctl00_ctl02editableRegion")).sendKeys(NewsTopic);
		driver.findElement(By.id("ctl00_PlaceHolderMain_ctl20_ctl00_ctl02editableRegion")).sendKeys(NewsType);
		driver.findElement(By.linkText("Check it in")).click();
		Thread.sleep(2000);
		List<WebElement> fr = driver.findElements(By.tagName("iframe"));
		 System.out.println("Frame Count:  " +fr.size());
		 for (WebElement frli: fr){
			 System.out.println("Title: "+frli.getAttribute("title") + "-- Id: "+frli.getAttribute("id")+ "-- Name: "+frli.getAttribute("name"));
			 } 
		driver.switchTo().frame(3);
		driver.findElement(By.id("checkincomments")).sendKeys(checkInComments);
		driver.findElement(By.id("statechangedialog_okbutton")).click();
		
	}

}//Closing Class
