package Maven_Automation.Maven_Automation;

/*import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Hello world!
 *
 */
/*public class DriverClass{
	//Declaring variables to store the run time values
		int k=0;
		String module_Name;
		String subModule_Name,execute;
		String result_Path;
		String workspaceName;
		boolean isExceptiondriver=false;
		int expRowNumberdriver=1;
		DataFormatter formatter1;
		//String URL="https://sit-thehub-au.dw.oaktonapps.com";
		//creating the object for the globalMethods class which is implemented the reusableMethods interface.
		globalMethods globalobj = new globalMethods();
		public void load_Module_Drive() throws Exception{
			System.out.println("In load module method");
		}
    public static void main( String[] args )throws Exception
    {
        System.out.println( "In main method" );
        DriverClass d=new DriverClass();
        d.load_Module_Drive();
        //System.setProperty("webdriver.chrome.driver", "");
        WebDriver driver1 = new ChromeDriver();
		driver1.manage().window().maximize();
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver1.navigate().to("https://sit-thehub-au.dw.oaktonapps.com");
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}*/
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.openqa.selenium.server.SeleniumServer;
//import org.testng.annotations.Test;

//This is a main driver class will control entire test execution. This class is responsible
//of loading all the components of framework.
public class DriverClass {
	
	//Declaring variables to store the run time values
	int k=0;
	String module_Name;
	String subModule_Name,execute;
	String result_Path;
	String workspaceName;
	boolean isExceptiondriver=false;
	int expRowNumberdriver=1;
	DataFormatter formatter1;
	String testdatadriver;
	//creating the object for the globalMethods class which is implemented the reusableMethods interface.
	globalMethods globalobj = new globalMethods();
	//public SeleniumServer seleniumserver;
	//This method is responsible for loading main module, submodule from Driver.xls sheet based on the
	//condition in the Execute column.  It will also create the result folder and result file, and load the
	//reusable action class to execute the corresponding module test cases.
	public void load_Module_Drive() throws Exception{
		//Loading driver.xls to decide the modules which user wants to run
		try{
			testdatadriver=System.getProperty("user.dir");
			testdatadriver=testdatadriver+"\\TestData\\Driver.xlsx";
			System.out.println("the path of the driver sheet is  "+testdatadriver);
		//This block of code is implemented using Apache POI api as Microsoft 2010 is used as a test data sheet.
			XSSFWorkbook wb = new  XSSFWorkbook(new FileInputStream(testdatadriver));
		    XSSFSheet sheet1 = wb.getSheetAt(0);
		    DataFormatter formatter1=new DataFormatter();
		    for (k = expRowNumberdriver; k <=sheet1.getLastRowNum(); k++) {
		    	module_Name=formatter1.formatCellValue(sheet1.getRow(k).getCell(0));
		    	subModule_Name=formatter1.formatCellValue(sheet1.getRow(k).getCell(1));
		    	execute=formatter1.formatCellValue(sheet1.getRow(k).getCell(2));
		    	workspaceName=formatter1.formatCellValue(sheet1.getRow(k).getCell(3));
		    	if (execute.equalsIgnoreCase("Yes")){
		    		System.out.println("Userwants to load the module"+" "+module_Name+"and sub Module "+subModule_Name);
		    		result_Path=globalobj.resultFile(module_Name,subModule_Name);
		    	    Reusable_Action ra = new Reusable_Action();
		    		ra.load_Testcase_Method(globalobj,result_Path,module_Name,subModule_Name,workspaceName);
		    		
		    	}else
					System.out.println("Please open the Driver.xls sheet and select the Execute value as Yes to load the module"+" "+module_Name);
		    }
		}catch (Exception e){
			isExceptiondriver=true;
			System.out.println("Not able to read the data from Driver.xls file.");
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		}
		finally{
			if(isExceptiondriver == true){
				expRowNumberdriver= k +1;
				isExceptiondriver = false;
				load_Module_Drive();
			}
		}
		
	}
	public static void main (String args[]){
		try{
		 DriverClass d = new DriverClass();
		d.load_Module_Drive();
		System.out.println("Change made by Siva Ram");

		System.out.println("working on same file to understand git hub version control");

		System.out.println("Change made by Siva Ram 2nd Time");

	  	}catch(Exception e){
	  		System.out.println("In catch block");
	  		e.printStackTrace();
			System.out.println("committing changes into github");
		}
	}

	
	
}
