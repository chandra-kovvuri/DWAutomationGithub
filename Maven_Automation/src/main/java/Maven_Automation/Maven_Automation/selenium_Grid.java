package Maven_Automation.Maven_Automation;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;

public class selenium_Grid {
	public static void main(String[]args)throws Exception{
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		//System.setProperty("webdriver.chrome.driver","F:/Selenium_Automation/DataDrivernTestProject/chromedriver.exe");
		cap.setPlatform(Platform.VISTA);
		//cap.setVersion("55.0");
		URL UrlNode=null;
		try{
			UrlNode = new URL("http://172.16.0.8:5555/wd/hub");//node url has to give
		}
		catch (MalformedURLException e){
			e.printStackTrace();
		}
	    RemoteWebDriver driver = new RemoteWebDriver(UrlNode,cap);
	    driver.navigate().to("http://selenium-suresh.blogspot.com");
	    Thread.sleep(2000);
	    System.out.println(driver.getTitle());
	    driver.quit();
				
  }

}

//To run the grid use the following path
/*
* java -Dwebdriver.gecko.driver=F:\Selenium_Automation\DataDrivernTestProject\geckodriver.exe -Dwebdriver.chrome.driver=F:\Selenium_Automation\DataDrivernTestProject\chromedriver.exe -jar F:\Selenium_Automation\DataDrivernTestProject\lib\selenium-server-standalone-3.0.1.jar -role node -hub http://172.16.0.8:4444/grid/register -port 8888
* 
*/
