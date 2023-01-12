package mainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleClass {

	public static void main(String[] args) throws Exception 
	{
		WebDriverManager.chromedriver().setup();
        WebDriver driver= new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(AppConfig.URL);
        driver.findElement(Locators.username).sendKeys(AppConfig.username);
        driver.findElement(Locators.password).sendKeys(AppConfig.password);
        driver.findElement(Locators.signInButton).click();
        for (int i=0; i< AppConfig.Buildings.length; i++)
  	  {
  	 driver.findElement(Locators.searchBar).sendKeys(AppConfig.Buildings[i]);
  	 
       try 
  	 {
  	 if (driver.findElement(Locators.noItemsFound).isDisplayed())
  	 {
  		 driver.navigate().refresh();
  		 continue;
  		 
  	 }}
  	 catch (Exception e)
  	 {}
       WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(100));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(AppConfig.Buildings[i])));
       driver.findElement(By.partialLinkText(AppConfig.Buildings[i])).click();
       JavascriptExecutor js = (JavascriptExecutor)driver;
       js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
       driver.findElement(Locators.Lease).click();
       List<WebElement>list = driver.findElements(By.xpath("//*[@id='buildingLeaseList']/tbody/tr/td[1]/a"));
       int size=list.size();
       for(int i1=0;i1<size;i1++)
       {
    	   String text=list.get(i).getText();
           if(text.contains("Straughan, M."))
           {
        	   list.get(i).click();
        	   
       }
            js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
           driver.findElement(By.id("ndHeader")).click();
           List<WebElement>pdfList = driver.findElements(By.xpath("//*[@id='documentHolderBody']/tr/td[1]"));
           List<WebElement>pdfList2 = driver.findElements(By.xpath("//*[@id='documentHolderBody']/tr/td[1]"));
           for(int l=0; i<pdfList.size(); l++)
           {
        	   String lease = pdfList.get(l).getText();
        	   String renewal= pdfList.get(l).getText();
        	   if(pdfList.contains(lease))
        	   {
        		   list.get(l).click();
        	   }
        	   else 
        	   {
        		   String lease2 = pdfList2.get(l).getText();
            	   String renewal2= pdfList2.get(l).getText();
            	   if(pdfList.contains(renewal))
            			   {
            		   list.get(l).click();
            			   }
        	   }
        		 
        		   
        	   }
           Thread.sleep(2000);
           driver.findElement(Locators.Edit).click();
           js.executeScript("arguments[0].scrollIntoView();",driver.findElement(Locators.AutoCharges));
           List<WebElement>rent = driver.findElements(By.xpath("//*[@id='autoChargesTable']/tbody/tr/td[1]"));
           List<WebElement>enddate = driver.findElements(By.xpath("//*[@id='autoChargesTable']/tbody/tr/td[6]"));
           for (int k=0;k<rent.size();k++)
           {
        	   String rentCode = rent.get(k).getText();
        	   String date = enddate.get(k).getText();
        	   if(rentCode.toLowerCase().equalsIgnoreCase("4000 - Rent".toLowerCase())&& !date.contains("/"))
               {
        		System.out.println("Matched");  
        		Thread.sleep(2000);
        		String monthlyRent = driver.findElement(By.xpath("//*[@id='autoChargesTable']/tbody/tr/td[3]")).getText();
        		System.out.println(monthlyRent);
        		Thread.sleep(2000);
        		String startDate = driver.findElement(By.xpath("//*[@id='autoChargesTable']/tbody/tr/td[5]")).getText();
        		System.out.println(startDate);
        		String date_s = " 2022-12-01 00:00:00.0"; 
        		String date_p ="12/01/2023";
        		SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd hh:mm:ss"); 
        		Date date2 = dt.parse(date_s); 
        		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        		if(dt.format(date2) != date_s)
        		{
        			System.out.println(date_p);
        			
        		}
        		 
	           }
           }
           

	}}}}


