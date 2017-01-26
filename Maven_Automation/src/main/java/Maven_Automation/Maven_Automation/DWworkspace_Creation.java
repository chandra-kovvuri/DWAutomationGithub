package Maven_Automation.Maven_Automation;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.xmlbeans.impl.store.Locale;

//import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class DWworkspace_Creation {
	//Declaring the local variables to store the runtime data.
	String testData,runTestcase,testCaseId,strDesc,strExpres,strActres,strStatus;
	String URL,UserName,Password,WsTitle,WsDescription,WsUrl,WsAvatar,WsImage,Wstags;
	String Wstype,WsOrgUnit,Wscountry,WsOwners,WsMembers,Wsstatus,Wscomments;
	int expRowNumber =1;
	boolean isException = false;
	WebDriver driver;
	int rownumber=0;
	XSSFWorkbook testdatawb;
	int testdatarown=0;

	public void Workspace_Creation_Alltypes(globalMethods globalobj,String result_Path,String module_Name,String subModule_Name,String workspaceName)
	{
		try{
			//Creating the objects 
			testData=globalobj.testDataPath(module_Name,subModule_Name);
			testdatawb =new  XSSFWorkbook(new FileInputStream(testData));
			DataFormatter formatter = new DataFormatter();
			String sheetName="wssheet";
			//Extract the data from Excel file and parameterize the data
			switch (workspaceName){
			case "PROJECT":
				sheetName = "Project";
				break;
			case "TEAM":
				sheetName = "Team";
				break;
			case "BID":
				sheetName = "Bid";
				break;
			case "COMMUNITY":
				sheetName = "Community";
				break;
			case "CONTRACT":
				sheetName = "Contract";
				break;
			}
			XSSFSheet s = testdatawb.getSheet(sheetName);
			for (rownumber = expRowNumber; rownumber <=s.getLastRowNum(); rownumber++) {
				dataRead_Common_WS_Fields(s,rownumber,formatter);
				if (runTestcase.equalsIgnoreCase("Yes")){
					driver = globalobj.openBrowser(URL);
					//Open the browser by calling the method a method
					driver.findElement(globalobj.getbjectLocator("Username")).sendKeys(UserName);
					driver.findElement(globalobj.getbjectLocator("Password")).sendKeys(Password);
					driver.findElement(globalobj.getbjectLocator("SubmitLogin")).click();
					Thread.sleep(25000);
					driver.findElement(By.id("menuTrigger")).click();
					Thread.sleep(5000);
					driver.findElement(By.xpath(".//*[@id='mainMenu']/li[6]/a")).click();
					Thread.sleep(5000);
					driver.findElement(By.xpath(".//*[@id='btnTreeView']/div[1]/a")).click();
					switch (workspaceName){
					case "PROJECT":
						 workspace_Details_Project(driver,s,rownumber,formatter);
						 break;
					case "TEAM":
						workspace_Details_Team(driver,s,rownumber,formatter);
						 break;
					case "BID":
						workspace_Details_Bid(driver,s,rownumber,formatter);
						 break;
					case "COMMUNITY":
						workspace_Details_Community(driver,s,rownumber,formatter);
						break;
					case "CONTRACT":
						workspace_Details_Contract(driver,s,rownumber,formatter);
						 break;
					}
					enter_Wsdetails_Common(driver);
					String results=saveWSDetails(driver,WsTitle);
					if (results=="Pass"){
						strDesc="Creating the"+" "+workspaceName+" "+"Workspace";
						strExpres=workspaceName+" "+"Workspace Should be created successfully and approved";
						strActres=workspaceName+" "+"workspace is created successfully and approved";
						strStatus="Pass";
						Approve_Reject_WS(driver,Wsstatus,Wscomments);
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
					}else{
						strDesc="Creating the"+" "+workspaceName+" "+"Workspace";
						strExpres=workspaceName+" "+"Workspace Should be created successfully";
						strActres=workspaceName+" "+"workspace is not created successfully";
						strStatus="Fail";
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
					}
					
				}//closing if condition.
			}//closing for loop
			
		}
		catch (Exception e){
			isException = true;
			globalobj.closeBrowser(driver);
			strActres=e.getMessage();
			strDesc="Creating the"+" "+workspaceName+" "+"Workspace";
			strExpres=workspaceName+" "+"Workspace Should be created successfully";
			strStatus="Fail";
			globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		}
		finally{
			if(isException == true){
				expRowNumber= rownumber +1;
				isException = false;
				Workspace_Creation_Alltypes(globalobj,result_Path,module_Name,subModule_Name,workspaceName);
			}
		}
		
	}//closing Workspace_Creation_Alltypes method.
	
	//Defining another method to enter the project workspace specific details.
	public void workspace_Details_Project(WebDriver driver,XSSFSheet s,int rownumber,DataFormatter formatter)throws Exception{
		//Declaring the local variables
		String Wssponsor,WsProMember,WsProcode,WsProManager,WsProstage;
		String WsProstate,WsStartDate,WsEndDate;
		//Extract the data from Project details test data sheet.
		Wssponsor=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
		WsProMember=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
		WsProcode=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
		WsProManager=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
		WsProstage=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
		WsProstate=formatter.formatCellValue(s.getRow(rownumber).getCell(21));
		WsStartDate=formatter.formatCellValue(s.getRow(rownumber).getCell(22));
		WsEndDate=formatter.formatCellValue(s.getRow(rownumber).getCell(23));
		WsOwners=formatter.formatCellValue(s.getRow(rownumber).getCell(24));
		WsMembers=formatter.formatCellValue(s.getRow(rownumber).getCell(25));
		Wsstatus=formatter.formatCellValue(s.getRow(rownumber).getCell(26));
		Wscomments=formatter.formatCellValue(s.getRow(rownumber).getCell(27));
		driver.findElement(By.id("Project")).click();
		driver.findElement(By.id("btnNext")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='WkspcSponsor_8e069a0e-6eca-4342-9e1f-8887af3c4b33_$ClientPeoplePicker_EditorInput']")).sendKeys(Wssponsor);
		driver.findElement(By.xpath(".//*[@id='WkspcProjectBoard_a4efecb3-45ce-49bf-ba18-cd1a2d1e184c_$ClientPeoplePicker_EditorInput']")).sendKeys(WsProMember);
		driver.findElement(By.xpath(".//*[@id='DWProjectCode_eedefd96-fe78-4b15-8f80-4cecef236040_$TextField']")).sendKeys(WsProcode);
		driver.findElement(By.xpath(".//*[@id='WkspcProjectManager_28859254-5ef9-46dd-9b4b-7f0ef40342dd_$ClientPeoplePicker_EditorInput']")).sendKeys(WsProManager);
		driver.findElement(By.xpath(".//*[@id='WkspcProjectStage_$containereditableRegion']")).sendKeys(WsProstage);
		driver.findElement(By.xpath(".//*[@id='WkspcProjectState_$containereditableRegion']")).sendKeys(WsProstate);
		driver.findElement(By.xpath(".//*[@id='WkspcProjectState_$containereditableRegion']")).click();
		driver.findElement(By.xpath(".//*[@id='WkspcStartDate_d151e103-b216-4070-8381-7db32a1c9694_$DateTimeFieldDate']")).sendKeys(WsStartDate);
		driver.findElement(By.xpath(".//*[@id='WkspcEndDate_a72d0f29-7d5f-487e-86ce-f863a9a4fa14_$DateTimeFieldDate']")).sendKeys(WsEndDate);
		driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink")).click();
		Thread.sleep(4000);
		driver.switchTo().frame(3);
		Thread.sleep(3000);
		driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsAvatar);
		driver.findElement(By.id("ctl00_OkButton")).click();
		driver.switchTo().defaultContent();
		Thread.sleep(9000);
		driver.findElement(By.linkText("Click here to insert a picture from SharePoint.")).click();
		Thread.sleep(3000);
		driver.switchTo().frame(3);
		driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsImage);
		driver.findElement(By.id("ctl00_OkButton")).click();
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
	}
	
	//Defining another method to enter the Team workspace specific details.
	public void workspace_Details_Team(WebDriver driver,XSSFSheet s,int rownumber,DataFormatter formatter)throws Exception{
		String WsBannerImage;
		WsBannerImage=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
		WsOwners=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
		WsMembers=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
		Wsstatus=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
		Wscomments=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
		driver.findElement(By.id("Team")).click();
		driver.findElement(By.id("btnNext")).click();
		Thread.sleep(4000);
		//driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink)")).click();
		driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink")).click();
		Thread.sleep(3000);
		driver.switchTo().frame(1);
		Thread.sleep(3000);
		driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsAvatar);
		driver.findElement(By.id("ctl00_OkButton")).click();
		driver.switchTo().defaultContent();
		Thread.sleep(4000);
		List <WebElement> ImageList=driver.findElements(By.tagName("a"));
		int count=ImageList.size();
		int imgcnt=0;
		for (int i=0;i<count;i++){
			String Imgtext=ImageList.get(i).getText();
		    if (Imgtext.equalsIgnoreCase("Click here to insert a picture from SharePoint.")){
			   imgcnt=1;
			   if (imgcnt>=2){
				   ImageList.get(i).click();
				   Thread.sleep(4000);
				   driver.switchTo().frame(1);
				   Thread.sleep(3000);
				   driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsBannerImage);
				   driver.findElement(By.id("ctl00_OkButton")).click();
				   driver.switchTo().defaultContent();
				   Thread.sleep(4000);
				   break;
			   }else{
				   ImageList.get(i).click();
				   Thread.sleep(4000);
				   driver.switchTo().frame(1);
				   Thread.sleep(3000);
				   driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsImage);
				   driver.findElement(By.id("ctl00_OkButton")).click();
				   driver.switchTo().defaultContent();
				   Thread.sleep(4000);
				   
			   }//closing else condition.
			   
		   }//closing if condition
		}//closing for loop
	}//closing method.
	
	//Defining another method to enter the Community workspace specific details.
		public void workspace_Details_Community(WebDriver driver,XSSFSheet s,int rownumber,DataFormatter formatter)throws Exception{
			String WsBannerImage,CommObjective;
			WsBannerImage=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
			CommObjective=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
			WsOwners=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
			WsMembers=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
			Wsstatus=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
			Wscomments=formatter.formatCellValue(s.getRow(rownumber).getCell(21));
			driver.findElement(By.id("Community")).click();
			driver.findElement(By.id("btnNext")).click();
			Thread.sleep(4000);
			//driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink)")).click();
			driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink")).click();
			Thread.sleep(3000);
			driver.switchTo().frame(1);
			Thread.sleep(3000);
			driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsAvatar);
			driver.findElement(By.id("ctl00_OkButton")).click();
			driver.switchTo().defaultContent();
			Thread.sleep(4000);
			List <WebElement> ImageList=driver.findElements(By.tagName("a"));
			int count=ImageList.size();
			int imgcnt=0;
			for (int i=0;i<count;i++){
				String Imgtext=ImageList.get(i).getText();
			    if (Imgtext.equalsIgnoreCase("Click here to insert a picture from SharePoint.")){
				   imgcnt=1;
				   if (imgcnt>=2){
					   ImageList.get(i).click();
					   Thread.sleep(4000);
					   driver.switchTo().frame(1);
					   Thread.sleep(3000);
					   driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsBannerImage);
					   driver.findElement(By.id("ctl00_OkButton")).click();
					   driver.switchTo().defaultContent();
					   Thread.sleep(4000);
					   break;
				   }else{
					   ImageList.get(i).click();
					   Thread.sleep(4000);
					   driver.switchTo().frame(1);
					   Thread.sleep(3000);
					   driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsImage);
					   driver.findElement(By.id("ctl00_OkButton")).click();
					   driver.switchTo().defaultContent();
					   Thread.sleep(4000);
					   
				   }//closing else condition.
				   
			   }//closing if condition
			}//closing for loop
			driver.findElement(By.id("WkspcCommunityObjective_4d9d1cfc-78c2-4199-8c2a-c3a2caef5817_$TextField")).sendKeys(CommObjective);
		}//closing method.
		
		//Defining another method to enter the Bid workspace specific details.
		public void workspace_Details_Bid(WebDriver driver,XSSFSheet s,int rownumber,DataFormatter formatter)throws Exception{
			//Declaring the local variables
			String Bidcode,Client,Biddirector,Bidstage,BidStartdate,BidEnddate;
			//Extract the data from Project details test data sheet.
			Bidcode=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
			Client=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
			Biddirector=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
			Bidstage=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
			BidStartdate=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
			BidEnddate=formatter.formatCellValue(s.getRow(rownumber).getCell(21));
			WsOwners=formatter.formatCellValue(s.getRow(rownumber).getCell(22));
			WsMembers=formatter.formatCellValue(s.getRow(rownumber).getCell(23));
			Wsstatus=formatter.formatCellValue(s.getRow(rownumber).getCell(24));
			Wscomments=formatter.formatCellValue(s.getRow(rownumber).getCell(25));
			driver.findElement(By.id("Bid")).click();
			driver.findElement(By.id("btnNext")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath(".//*[@id='DWBidCode_ecfcffa6-8ad9-4da4-a81c-cf2ac2da6f84_$TextField']")).sendKeys(Bidcode);
			driver.findElement(By.xpath(".//*[@id='Client_72f5e0e6-5161-4a45-9902-1a82a6473d08_$TextField']")).sendKeys(Client);
			driver.findElement(By.xpath(".//*[@id='WkspcBidDirector_ba63cc8b-f746-408f-94dd-d7f29afcd823_$ClientPeoplePicker_EditorInput']")).sendKeys(Biddirector);
			driver.findElement(By.xpath(".//*[@id='WkspcBidStage_$containereditableRegion']")).sendKeys(Bidstage);
			driver.findElement(By.xpath(".//*[@id='WkspcStartDate_d151e103-b216-4070-8381-7db32a1c9694_$DateTimeFieldDate']")).sendKeys(BidStartdate);
			driver.findElement(By.xpath(".//*[@id='WkspcEndDate_a72d0f29-7d5f-487e-86ce-f863a9a4fa14_$DateTimeFieldDate']")).sendKeys(BidEnddate);
			driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink")).click();
			Thread.sleep(4000);
			driver.switchTo().frame(3);
			Thread.sleep(3000);
			driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsAvatar);
			driver.findElement(By.id("ctl00_OkButton")).click();
			driver.switchTo().defaultContent();
			Thread.sleep(9000);
			driver.findElement(By.linkText("Click here to insert a picture from SharePoint.")).click();
			Thread.sleep(3000);
			driver.switchTo().frame(3);
			driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsImage);
			driver.findElement(By.id("ctl00_OkButton")).click();
			driver.switchTo().defaultContent();
			Thread.sleep(5000);
		}
		
		//Defining another method to enter the Contract workspace specific details.
		public void workspace_Details_Contract(WebDriver driver,XSSFSheet s,int rownumber,DataFormatter formatter)throws Exception{
			String WsBannerImage,WscontClient,WscontManager,WsContLocation,WsContStartDate,WsContEndDate;
			WsBannerImage=formatter.formatCellValue(s.getRow(rownumber).getCell(16));
			WscontClient=formatter.formatCellValue(s.getRow(rownumber).getCell(17));
			WscontManager=formatter.formatCellValue(s.getRow(rownumber).getCell(18));
			WsContLocation=formatter.formatCellValue(s.getRow(rownumber).getCell(19));
			WsContStartDate=formatter.formatCellValue(s.getRow(rownumber).getCell(20));
			WsContEndDate=formatter.formatCellValue(s.getRow(rownumber).getCell(21));
			WsOwners=formatter.formatCellValue(s.getRow(rownumber).getCell(22));
			WsMembers=formatter.formatCellValue(s.getRow(rownumber).getCell(23));
			Wsstatus=formatter.formatCellValue(s.getRow(rownumber).getCell(24));
			Wscomments=formatter.formatCellValue(s.getRow(rownumber).getCell(25));
			driver.findElement(By.id("Contract")).click();
			driver.findElement(By.id("btnNext")).click();
			Thread.sleep(4000);
			driver.findElement(By.id(".//*[@id='WkspcClient_$containereditableRegion']")).sendKeys(WscontClient);
			driver.findElement(By.id(".//*[@id='WkspcContractManager_687e3103-eb73-4bb6-9422-7161db1e2717_$ClientPeoplePicker_EditorInput']")).sendKeys(WscontManager);
			driver.findElement(By.id(".//*[@id='WkspcLocations_99f47843-1888-42bc-8c47-7cdce5259aab_$TextField']")).sendKeys(WsContLocation);
			driver.findElement(By.id(".//*[@id='WkspcStartDate_d151e103-b216-4070-8381-7db32a1c9694_$DateTimeFieldDate']")).sendKeys(WsContStartDate);
			driver.findElement(By.id(".//*[@id='WkspcEndDate_a72d0f29-7d5f-487e-86ce-f863a9a4fa14_$DateTimeFieldDate']")).sendKeys(WsContEndDate);
			Thread.sleep(2000);
			driver.findElement(By.id("ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_ctl05_ctl03_ctl00_ctl00_ctl04_ctl00_ctl00_RichImageField____AddAssetPromptLink")).click();
			Thread.sleep(3000);
			driver.switchTo().frame(1);
			Thread.sleep(3000);
			driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsAvatar);
			driver.findElement(By.id("ctl00_OkButton")).click();
			driver.switchTo().defaultContent();
			Thread.sleep(4000);
			List <WebElement> ImageList=driver.findElements(By.tagName("a"));
			int count=ImageList.size();
			int imgcnt=0;
			for (int i=0;i<count;i++){
				String Imgtext=ImageList.get(i).getText();
			    if (Imgtext.equalsIgnoreCase("Click here to insert a picture from SharePoint.")){
				   imgcnt=1;
				   if (imgcnt>=2){
					   ImageList.get(i).click();
					   Thread.sleep(4000);
					   driver.switchTo().frame(1);
					   Thread.sleep(3000);
					   driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsBannerImage);
					   driver.findElement(By.id("ctl00_OkButton")).click();
					   driver.switchTo().defaultContent();
					   Thread.sleep(4000);
					   break;
				   }else{
					   ImageList.get(i).click();
					   Thread.sleep(4000);
					   driver.switchTo().frame(1);
					   Thread.sleep(3000);
					   driver.findElement(By.id("ctl00_PlaceHolderDialogBodySection_PlaceHolderDialogBodyMainSection_ctl01_assetSelectedImage_AssetUrlInput")).sendKeys(WsImage);
					   driver.findElement(By.id("ctl00_OkButton")).click();
					   driver.switchTo().defaultContent();
					   Thread.sleep(4000);
					   
				   }//closing else condition.
				   
			   }//closing if condition
			}//closing for loop
		}//closing method.
		
		
	//This method is useful to read the data from excel which are common for all for workspace types.
	//This method is resualbe method.
    public void dataRead_Common_WS_Fields(XSSFSheet s,int rownumber,DataFormatter formatter)throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
    	runTestcase= formatter.formatCellValue(s.getRow(rownumber).getCell(0));
		testCaseId =formatter.formatCellValue(s.getRow(rownumber).getCell(1));
		URL=formatter.formatCellValue(s.getRow(rownumber).getCell(4));
		UserName=formatter.formatCellValue(s.getRow(rownumber).getCell(5));
		Password=formatter.formatCellValue(s.getRow(rownumber).getCell(6));
		WsTitle = formatter.formatCellValue(s.getRow(rownumber).getCell(7));
		WsTitle=WsTitle.concat(sdf.format(date));
		String ms= String.valueOf(System.currentTimeMillis());
		WsTitle=WsTitle.concat(ms.substring(7));
		WsDescription= formatter.formatCellValue(s.getRow(rownumber).getCell(8));
		WsUrl= formatter.formatCellValue(s.getRow(rownumber).getCell(9));
		WsUrl=WsUrl.concat(sdf.format(date));
		WsUrl=WsUrl.concat(ms.substring(7));
		WsAvatar= formatter.formatCellValue(s.getRow(rownumber).getCell(10));
		WsImage= formatter.formatCellValue(s.getRow(rownumber).getCell(11));
		Wstags=formatter.formatCellValue(s.getRow(rownumber).getCell(12));
		Wstype=formatter.formatCellValue(s.getRow(rownumber).getCell(13));
		WsOrgUnit=formatter.formatCellValue(s.getRow(rownumber).getCell(14));
		Wscountry=formatter.formatCellValue(s.getRow(rownumber).getCell(15));
	}
    //This method is useful to enter the workspace details which are common for all workspace types.
    public void enter_Wsdetails_Common(WebDriver driver)throws Exception{
    	driver.findElement(By.xpath(".//*[contains(@id,'Title_')]")).sendKeys(WsTitle);
		driver.findElement(By.xpath(".//*[contains(@id,'WkspcDesc_')]")).sendKeys(WsDescription);
		driver.findElement(By.xpath(".//*[contains(@id,'WkspcURL_')]")).sendKeys(WsUrl);
		driver.findElement(By.xpath(".//*[@id='TaxKeyword_$containereditableRegion']")).sendKeys(Wstags);
		Select isPrivate=new Select(driver.findElement(By.xpath(".//*[@id='DWPrivateFlag_b4aa0c92-c2c0-448b-8606-5780c71c9457_$DropDownChoice']")));
		isPrivate.selectByValue(Wstype);
		driver.findElement(By.xpath(".//*[@id='DWOrganisationUnit_$containereditableRegion']")).sendKeys(WsOrgUnit);
		driver.findElement(By.xpath(".//*[@id='DWOrganisationUnit_$containereditableRegion']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='DWCountry_$containereditableRegion']")).sendKeys(Wscountry);
		driver.findElement(By.xpath(".//*[@id='WkspcWorkspaceOwners_cc05c3c3-85e8-4b24-90f3-b68a180044e9_$ClientPeoplePicker_EditorInput']")).sendKeys(WsOwners);
		driver.findElement(By.xpath(".//*[@id='WkspcWorkspaceMembers_f67a59b4-6d31-4559-95d2-b95c8830f9d2_$ClientPeoplePicker_EditorInput']")).sendKeys(WsMembers);
	}
  //Declaring a method to save the workSpae details and validate workspace is created or not.
  	//pass or fail status will be returned by this method.
  	public String saveWSDetails(WebDriver driver,String WsTitle)throws Exception{
  		//clicking on save button and creating the workspace
  		driver.findElement(By.xpath(".//*[@id='ctl00_ctl45_g_3d8eb4db_7257_4b63_8b3e_a95ca84630a8_ctl00_toolBarTbl_RightRptControls_ctl00_ctl00_diidIOSaveItem']")).click();
  		Thread.sleep(5000);
  	   //Clicking on workspace request tab
  		driver.findElement(By.xpath(".//*[@id='workSpaceRequest-tab']")).click();
  		Thread.sleep(1000);
  		String wstitle=driver.findElement(By.xpath(".//*[@id='wsGrid3']/div[1]/div[1]/div[2]/div/div[1]/div/div[1]/div/a/span")).getText();
  		if (wstitle.equalsIgnoreCase(WsTitle)){
  			return "Pass";	
  		}else{
  			return "Fail";
  			
  		}
  	}
    //Declaring the method to Approve or Reject the workspace based on the input data.
    public void Approve_Reject_WS(WebDriver driver,String Wsstatus,String Wscomments)throws Exception{
    	Thread.sleep(7000);
  		driver.findElement(By.id("menuTrigger")).click();
  		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='mainMenu']/li[6]/a")).click();
		Thread.sleep(5000);
  		//Clicking on workspace request tab
  		driver.findElement(By.xpath(".//*[@id='workSpaceRequest-tab']")).click();
  		Thread.sleep(3000);
    	driver.findElement(By.xpath(".//*[@id='wsGrid3']/div[1]/div[1]/div[2]/div/div[1]/div/div[8]/div/a/span")).click();
    	Thread.sleep(5000);
    	/*List<WebElement> fr = driver.findElements(By.tagName("iframe"));
		 System.out.println("Frame Count:  " +fr.size());
		 for (WebElement frli: fr){
			 System.out.println("Title: "+frli.getAttribute("title") + "-- Id: "+frli.getAttribute("id")+ "-- Name: "+frli.getAttribute("name"));
		 }*/
		driver.switchTo().frame(1);
		Wsstatus=Wsstatus.toUpperCase();
    	if(Wsstatus=="APPROVE"){
    		driver.findElement(By.id("ctl00_PlaceHolderMain_approveDescription_ctl01_RadioBtnApprovalStatus_0")).click();
    		driver.findElement(By.name("ctl00$PlaceHolderMain$approveComment$ctl01$Comments")).sendKeys(Wscomments);
    		driver.findElement(By.name("ctl00$PlaceHolderMain$ctl00$RptControls$BtnSubmit")).click();
    	}else{
    		driver.findElement(By.id("ctl00_PlaceHolderMain_approveDescription_ctl01_RadioBtnApprovalStatus_1")).click();
    		driver.findElement(By.name("ctl00$PlaceHolderMain$approveComment$ctl01$Comments")).sendKeys(Wscomments);
    		driver.findElement(By.name("ctl00$PlaceHolderMain$ctl00$RptControls$BtnSubmit")).click();
    	}
    	
    }
}//closing class


