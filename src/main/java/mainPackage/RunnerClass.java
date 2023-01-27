package mainPackage;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RunnerClass 
{
	public static String[][] lastMonthLeases; 
    public static String company;
    public static String buildingAbbreviation;
    public static String ownerName;
    public static WebDriver driver;
    
	public static ChromeOptions options;
	public static Actions actions;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;
	public static String[][] pendingBuildingList;
	public static boolean saveButtonOnAndOff;
	public static int updateStatus;
	public static String failedReason ="";
	public static ArrayList<String> successBuildings = new ArrayList<String>();
	public static ArrayList<String> failedBuildings = new ArrayList<String>();
	public static String[][] completedBuildingList;
	public static String [] statusList;
	public static String currentDate = "";
	public static HashMap<String,String> failedReaonsList= new HashMap<String,String>();
	public static String leaseStatuses[][];
	public static String UWStatuses[][];
	public static String downloadFilePath;
	public static String monthlyRent;
	public static String startDate;
	public static String monthlyRentInPW;
	public static String startDateInPW;
	public static String portfolioType;
	public static boolean published;
	public static boolean listingAgent;
	public static void main(String[] args) throws Exception 
	{
		
		//Get Leases for Last Month
		//Company,BuildingAbbreviation, LeaseNae
		DataBase.getBuildingsList();
		PropertyWare.login();
		for(int i=0;i<lastMonthLeases.length;i++)
		{
		  company = lastMonthLeases[i][0];
		  buildingAbbreviation = lastMonthLeases[i][1];
		  ownerName = lastMonthLeases[i][2];
		  String insertRecordInTable = "Insert into automation.leaseAuditAutomation (Company,BuildingAbbreviation,LeaseName) values ('"+RunnerClass.company+"','"+RunnerClass.buildingAbbreviation+"','"+RunnerClass.ownerName+"')";
		  DataBase.updateTable(insertRecordInTable);
		  try
		  {
		  //Search building in property Ware
		   if(PropertyWare.searchBuilding(company, buildingAbbreviation)==true)
			{
				//updateStatus=1;
				if(PropertyWare.downloadLeaseAgreement(buildingAbbreviation, ownerName)==true)
				{
					if(PdfReader.readPDFPerMarket(company)==true)
					{
						if(PropertyWare.compareValues(monthlyRent, startDate)==true)
						{
						//successBuildings.add("'"+buildingAbbreviation+"'");
							System.out.println("Values matched");
						String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Completed', completedDate = getdate() where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
				    	DataBase.updateTable(updateSuccessStatus);
						}
						else 			
						{
							System.out.println("Values did not match");
							/*RunnerClass.failedReaonsList.put(buildingAbbreviation, "Values did not match");
						    RunnerClass.failedReason = "Values did not match";
						    failedBuildings.add("'"+buildingAbbreviation+"'");
							RunnerClass.updateStatus=1;
							*/
							String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate() ,Notes = 'Values did not match' where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
					    	DataBase.updateTable(updateSuccessStatus);
						}
						String updateValuesInTable = "Update [Automation].[leaseAuditAutomation] Set MonthlyRentFromLeaseAgreement = '"+monthlyRent+"', StartDateFromLeaseAgreement='"+startDate+"',MonthlyRentInPW='"+monthlyRentInPW+"',StartDateInPW ='"+startDateInPW+"' where BuildingAbbreviation = '"+buildingAbbreviation+"' and Company = '"+company+"'";
						DataBase.updateTable(updateValuesInTable);
					}
					else 
					{
						String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate() ,Notes = 'Unable to Read Lease Agreement' where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
				    	DataBase.updateTable(updateSuccessStatus);
						//failedBuildings.add("'"+buildingAbbreviation+"'");
						//continue;
					}
				}
				else 
				{
					/*
					if(published==false)
					{
						String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate() ,Notes = 'Not a Published Unit' where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
				    	DataBase.updateTable(updateSuccessStatus);
					}
					else if(listingAgent==false)
					{
						String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate() ,Notes = 'Unit marketed by Sovereign' where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
				    	DataBase.updateTable(updateSuccessStatus);
					}
					*/
					//else 
					//{
					String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate() ,Notes = 'Unable to download Lease Agreement' where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
			    	DataBase.updateTable(updateSuccessStatus);
					//}
				//failedBuildings.add("'"+buildingAbbreviation+"'");
				//continue;
				}
				
			}
		    else 
		    {
 		    	String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate() ,Notes = 'Could not find Building' where [BuildingAbbreviation] = '"+buildingAbbreviation+"'";
		    	DataBase.updateTable(updateSuccessStatus);
		    	//failedBuildings.add("'"+buildingAbbreviation+"'");
		    }
		   RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(5));
		   try
			{
				if(RunnerClass.driver.findElement(Locators.renewalPopup).isDisplayed())
				{
					RunnerClass.driver.findElement(Locators.renewalPoupCloseButton).click();
				}

			}
			catch(Exception e) {}
		   RunnerClass.driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(15));
		   driver.navigate().refresh();
		   RunnerClass.js.executeScript("window.scrollBy(document.body.scrollHeight,0)");
		   RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(5));
		   try
			{
				if(RunnerClass.driver.findElement(Locators.renewalPopup).isDisplayed())
				{
					RunnerClass.driver.findElement(Locators.renewalPoupCloseButton).click();
				}

			}
			catch(Exception e) {}
		   RunnerClass.driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(15));
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  driver.navigate().refresh();
			  RunnerClass.js.executeScript("window.scrollBy(document.body.scrollHeight,0)");
		  }
		}
		
		
		
		//Send Email with status attachment
		if(lastMonthLeases.length>0)
			
			
		ExcelActivities.createExcelFileWithProcessedData();
		

	}   

	public static File getLastModified() throws Exception
	{
		
	    File directory = new File(AppConfig.downloadFilePath);
	    File[] files = directory.listFiles(File::isFile);
	    long lastModifiedTime = Long.MIN_VALUE;
	    File chosenFile = null;

	    if (files != null)
	    {
	        for (File file : files)
	        {
	            if (file.lastModified() > lastModifiedTime)
	            {
	                chosenFile = file;
	                lastModifiedTime = file.lastModified();
	            }
	        }
	    }

	    return chosenFile;
	}
	
	public static String convertDate(String dateRaw) throws Exception
	{
		try
		{
		SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd, yyyy");
	    SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
	    Date date = format1.parse(dateRaw.trim().replaceAll(" +", " "));
	    System.out.println(format2.format(date));
		return format2.format(date).toString();
		}
		catch(Exception e)
		{
		return "Error";
		}
	}
	
	    public static String firstDayOfMonth(String date) throws Exception 
	    {
	    	//String string = "02/05/2014"; //assuming input
	        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	        Date dt = sdf .parse(date);
	        Calendar c = Calendar.getInstance();
	        c.setTime(dt);
	        if(RunnerClass.portfolioType=="MCH")
	        c.add(Calendar.MONTH, 1);  //adding a month directly - gives the start of next month.
	        else c.add(Calendar.MONTH, 2);
	        c.set(Calendar.DAY_OF_MONTH, 01);
	        String firstDate = sdf.format(c.getTime());
	        System.out.println(firstDate);
	        return firstDate;
	    }
}

/*
String success = String.join(",",successBuildings);
String failed = String.join(",",failedBuildings);
try
{
	if(successBuildings.size()>0)
	{
	String updateSuccessStatus = "update [Automation].[leaseAuditAutomation] Set Status ='Completed', completedDate = getdate() where [BuildingAbbreviation] in ("+success+")";
	DataBase.updateTable(updateSuccessStatus);
	}
	if(failedBuildings.size()>0)
	{
	String failedReasons = String.join(",",failedReaonsList.values());
	String failedBuildings = String.join(",",failedReaonsList.keySet());
	String failedBuildingsUpdateQuery = "";
	for(int i=0;i<failedReaonsList.size();i++)
	{
		String buildingAbbr = failedBuildings.split(",")[i].trim();
		String failedReason = failedReasons.split(",")[i].trim();
		failedBuildingsUpdateQuery =failedBuildingsUpdateQuery+"\nupdate [Automation].[leaseAuditAutomation] Set Status ='Failed', completedDate = getdate(),Notes='"+failedReason+"' where [BuildingAbbreviation] ='"+buildingAbbr+"'";
		
	}
	//String updateFailedStatus = "update automation.TargetRent Set Status ='Failed', completedOn = getdate(),Notes='"+failedReason+"' where [Building/Unit Abbreviation] in ("+failed+")";
	DataBase.updateTable(failedBuildingsUpdateQuery);
	}
}

catch(Exception e) {}
*/