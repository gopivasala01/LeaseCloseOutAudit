package mainPackage;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PropertyWare 
{
	public static void login()
	{
		RunnerClass.downloadFilePath = AppConfig.downloadFilePath;
		Map<String, Object> prefs = new HashMap<String, Object>();
	    // Use File.separator as it will work on any OS
	    prefs.put("download.default_directory",
	    		RunnerClass.downloadFilePath);
        // Adding cpabilities to ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
		
        
		WebDriverManager.chromedriver().setup();
        RunnerClass.driver= new ChromeDriver(options);
        RunnerClass.driver.manage().window().maximize();
        RunnerClass.driver.get(AppConfig.URL);
        RunnerClass.driver.findElement(Locators.username).sendKeys(AppConfig.username);
        RunnerClass.driver.findElement(Locators.password).sendKeys(AppConfig.password);
        RunnerClass.driver.findElement(Locators.signInButton).click();
        RunnerClass.actions = new Actions(RunnerClass.driver);
        RunnerClass.js = (JavascriptExecutor)RunnerClass.driver;
        RunnerClass.driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(50));
	}
	
	public static boolean searchBuilding(String company, String building)
	{
		try
		{
		RunnerClass.driver.findElement(Locators.searchbox).clear();
		RunnerClass.driver.findElement(Locators.searchbox).sendKeys(building);
			try
			{
			RunnerClass.wait.until(ExpectedConditions.invisibilityOf(RunnerClass.driver.findElement(Locators.searchingLoader)));
			}
			catch(Exception e)
			{}
			Thread.sleep(5000);
			System.out.println(building);
			try
			{
			if(RunnerClass.driver.findElement(Locators.noItemsFound).isDisplayed())
			{
				System.out.println("Building Not Found");
				RunnerClass.failedReaonsList.put(building, "Building Not Found");
			    RunnerClass.failedReason = "Building Not Found";
				RunnerClass.updateStatus=1;
				return false;
			}
			}
			catch(Exception e)
			{
				
			}
		// Select Lease from multiple leases
				List<WebElement> displayedCompanies = RunnerClass.driver.findElements(Locators.searchedLeaseCompanyHeadings);
				boolean leaseSelected = false;
				for(int i =0;i<displayedCompanies.size();i++)
				{
					String companyName = displayedCompanies.get(i).getText();
					if(companyName.contains(company)&&!companyName.contains("Legacy"))
					{
						//RunnerClass.driver.findElement(By.xpath("(//*[@class='searchCat4'])["+(i+1)+"]/a")).click();
						//break;
						//RunnerClass.driver.findElement(By.partialLinkText(leaseName)).click();
						
						List<WebElement> leaseList = RunnerClass.driver.findElements(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li/a"));
						System.out.println(leaseList.size());
						for(int j=0;j<leaseList.size();j++)
						{
							String lease = leaseList.get(j).getText();
							if(lease.toLowerCase().contains(building.toLowerCase()))
							{
								RunnerClass.driver.findElement(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li["+(j+1)+"]/a")).click();
								leaseSelected = true;
								break;
									
							}
						}
						
					}
					if(leaseSelected==true)
					{
					     return true;
					}
					else 
					{
						RunnerClass.failedReaonsList.put(building, "Building Not Found");
					    RunnerClass.failedReason = "Building Not Found";
						RunnerClass.updateStatus=1;
						return false;
					}
				}
	} catch(Exception e) 
		{
		RunnerClass.failedReaonsList.put(building, "Issue in selecting Building");
	    RunnerClass.failedReason = "Issue in selecting Building";
		RunnerClass.updateStatus=1;
		return false;
		}
		return true;
	}
	
	public static boolean downloadLeaseAgreement(String building, String ownerName) throws Exception
	{
		try
		{
		
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
		RunnerClass.driver.findElement(Locators.leasesTab).click();
		try
		{
		RunnerClass.driver.findElement(By.partialLinkText(ownerName.trim())).click();
		}
		catch(Exception e)
		{
			
			System.out.println("Unable to Click Lease Onwer Name");
			RunnerClass.failedReaonsList.put(building, "Unable to Click Lease Onwer Name");
		    RunnerClass.failedReason = "Unable to Click Lease Onwer Name";
			RunnerClass.updateStatus=1;
			return false;
		}

		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		
		RunnerClass.driver.findElement(Locators.notesAndDocs).click();
		
		List<WebElement> documents = RunnerClass.driver.findElements(Locators.documentsList);
		boolean checkLeaseAgreementAvailable = false;
		
		for(int i =0;i<documents.size();i++)
		{
			if(documents.get(i).getText().contains("REVISED_Lease_"))//&&documents.get(i).getText().contains(leaseFirstName))
			{
				documents.get(i).click();
				checkLeaseAgreementAvailable = true;
				break;
			}
		}
		if(checkLeaseAgreementAvailable==false)
		{
		for(int i =0;i<documents.size();i++)
		{
			if(documents.get(i).getText().startsWith("Lease_"))//&&documents.get(i).getText().contains(leaseFirstName))
			{
				documents.get(i).click();
				checkLeaseAgreementAvailable = true;
				break;
			}
		}
		}
		if(checkLeaseAgreementAvailable==false)
		{
		for(int i =0;i<documents.size();i++)
		{
			if(documents.get(i).getText().contains("Lease_"))//&&documents.get(i).getText().contains(leaseFirstName))
			{
				documents.get(i).click();
				checkLeaseAgreementAvailable = true;
				break;
			}
		}
		}
		if(checkLeaseAgreementAvailable==false)
		{
			System.out.println("Unable to download Lease Agreement");
			RunnerClass.failedReaonsList.put(building, "Unable to download Lease Agreement");
		    RunnerClass.failedReason = "Unable to download Lease Agreement";
			RunnerClass.updateStatus=1;
			return false;
		}
		Thread.sleep(20000);
		File file = RunnerClass.getLastModified();
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(RunnerClass.driver).withTimeout(Duration.ofSeconds(25)).pollingEvery(Duration.ofMillis(100));
		wait.until( x -> file.exists());
		Thread.sleep(10000);
		return true;
		}
		catch(Exception e)
		{
			System.out.println("Unable to download Lease Agreement");
			RunnerClass.failedReaonsList.put(building, "Unable to download Lease Agreement");
		    RunnerClass.failedReason = "Unable to download Lease Agreement";
			RunnerClass.updateStatus=1;
			return false;
		}
	}
	
	public static boolean compareValues(String monthlyRent, String startDate) throws Exception
	{
		int availibilityCheck =0;
		RunnerClass.driver.navigate().refresh();
		RunnerClass.js.executeScript("window.scrollBy(document.body.scrollHeight,0)");
		Thread.sleep(3000);
		RunnerClass.driver.findElement(Locators.summaryEditButton).click();
		RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.newAutoCharge)).build().perform();
		Thread.sleep(1000);
		List<WebElement> autoCharges = RunnerClass.driver.findElements(Locators.autoChargeCodesList);
		for(int i=0;i<autoCharges.size();i++)
		{
			String autoCharge = autoCharges.get(i).getText();
			if(autoCharge.equalsIgnoreCase(AppConfig.getAutoChargeCode(RunnerClass.company)))
			{
				String endDate = RunnerClass.driver.findElement(By.xpath("//*[@id='autoChargesTable']/tbody/tr["+(i+1)+"]/td[6]")).getText();
				if(endDate.trim()=="")
				{
					String rent = RunnerClass.driver.findElement(By.xpath("//*[@id='autoChargesTable']/tbody/tr["+(i+1)+"]/td[3]")).getText();
					String date = RunnerClass.driver.findElement(By.xpath("//*[@id='autoChargesTable']/tbody/tr["+(i+1)+"]/td[5]")).getText();
					if(rent.substring(1).equalsIgnoreCase(monthlyRent)&&date.trim().equalsIgnoreCase(startDate))
					{
						availibilityCheck = 1;
						return true;
					}
				}
			}
		}
		if(availibilityCheck==0)
			return false;
		return false;
	}
	
}
