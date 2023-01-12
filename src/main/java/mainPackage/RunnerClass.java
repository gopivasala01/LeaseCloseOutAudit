package mainPackage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
	public static void main(String[] args) throws Exception 
	{
		
		//Get Leases for Last Month
		//Company,BuildingAbbreviation, LeaseNae
		DataBase.getBuildingsList();
		PropertyWare.login();
		for(int i=0;i<2;i++)
		{
		  company = lastMonthLeases[i][0];
		  buildingAbbreviation = lastMonthLeases[i][1];
		  ownerName = lastMonthLeases[i][2];
		  String insertRecordInTable = "Insert into automation.leaseAuditAutomation (Company,BuildingAbbreviation,LeaseName) values ('"+RunnerClass.company+"','"+RunnerClass.buildingAbbreviation+"','"+RunnerClass.ownerName+"')";
		  DataBase.updateTable(insertRecordInTable);
		  //Search building in property Ware
		   if(PropertyWare.searchBuilding(company, buildingAbbreviation)==true)
			{
				//updateStatus=1;
				if(PropertyWare.downloadLeaseAgreement(buildingAbbreviation, ownerName)==true)
				{
					if(PdfReader.readPDFPerMarket(company)==true)
					{
						if(PropertyWare.compareValues(monthlyRent, startDate)==true)
							successBuildings.add("'"+buildingAbbreviation+"'");
						else 			
						{
							System.out.println("Values did not match");
							RunnerClass.failedReaonsList.put(buildingAbbreviation, "Values did not match");
						    RunnerClass.failedReason = "Values did not match";
							RunnerClass.updateStatus=1;
						}
					}
					else 
					{
						failedBuildings.add("'"+buildingAbbreviation+"'");
						continue;
					}
				}
				else 
				{
				failedBuildings.add("'"+buildingAbbreviation+"'");
				continue;
				}
				
			}
		    else 
		    {
		    	failedBuildings.add("'"+buildingAbbreviation+"'");
		    }
		   driver.navigate().refresh();
		   RunnerClass.js.executeScript("window.scrollBy(document.body.scrollHeight,0)");
		   
		}
		
		String success = String.join(",",successBuildings);
		String failed = String.join(",",failedBuildings);
		try
		{
			if(successBuildings.size()>0)
			{
			String updateSuccessStatus = "update automation.TargetRent Set Status ='Completed',StatusID=4, completedOn = getdate() where [Building/Unit Abbreviation] in ("+success+")";
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
				failedBuildingsUpdateQuery =failedBuildingsUpdateQuery+"\nupdate automation.TargetRent Set Status ='Failed',StatusID=3, completedOn = getdate(),Notes='"+failedReason+"' where [Building/Unit Abbreviation] ='"+buildingAbbr+"'";
				
			}
	    	//String updateFailedStatus = "update automation.TargetRent Set Status ='Failed', completedOn = getdate(),Notes='"+failedReason+"' where [Building/Unit Abbreviation] in ("+failed+")";
			DataBase.updateTable(failedBuildingsUpdateQuery);
			}
		}
		catch(Exception e) {}
		
		//Send Email with status attachment
		//if(pendingBuildingList.length>0)
		//CommonMethods.createExcelFileWithProcessedData();
		
		
		// Start automation
		/* Login
		 * Search Building
		 * Click Building
		 * Scroll bottom
		 * Select LeaseName
		 * Notes & Docs - Download PDF
		 * Read PDF
		 * Click Edit button
		 * Fetch Auto Charges List
		 * Check if 4000 - Rent and should not have an End Date
		 * Verify if StartDate and MonthlyRent Matches
		 */

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
		return date.toString();
		}
		catch(Exception e)
		{
		return "Error";
		}
	}
	
}
