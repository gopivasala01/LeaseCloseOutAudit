package mainPackage;

import org.openqa.selenium.By;

public class Locators 
{
	public static By username= By.id("loginEmail");
	public static By password= By.name("password");
	public static By signInButton= By.xpath("//*[@class=\"button login-button\"]");
	public static By searchBar= By.id("eqsSearchText");
	public static By noItemsFound= By.xpath("//*[text()='No Items Found']");
	public static By Lease= By.xpath("//*[@class='tabbedSection']/a[4]");
	public static By Edit= By.xpath("//*[@value='Edit']");
	public static By AutoCharges=By.xpath("//*[text()=\"Auto Charges\"]");
	
	public static By searchbox = By.name("eqsSearchText");
	public static By dashboardsTab = By.linkText("Dashboards");
	public static By searchingLoader = By.xpath("//*[@id='eqsResult']/h1");
	public static By searchedLeaseCompanyHeadings = By.xpath("//*[@id='eqsResult']/div/div/h1");
	//public static By selectSearchedLease = By.partialLinkText(RunnerClass.buildingAbbreviation);
	
	public static By leasesTab = By.xpath("//*[@class='tabbedSection']/a[4]");	
    public static By RCDetails = By.xpath("//*[contains(text(),'Resident Coordinator [Name/Phone/Email]')]/following::td[1]/div");
    public static By leaseStartDate_PW = By.xpath("//*[@id='infoTable']/tbody/tr[3]/td[1]");
    public static By leaseEndDate_PW = By.xpath("//*[@id='infoTable']/tbody/tr[3]/td[2]");
    public static By popUpAfterClickingLeaseName = By.id("viewStickyNoteForm");
    public static By popupClose = By.xpath("//*[@id='editStickyBtnDiv']/input[2]");
    public static By notesAndDocs = By.id("notesAndDocuments");
    public static By documentsList = By.xpath("//*[@id='documentHolderBody']/tr/td[1]/a"); 
    
    public static By summaryEditButton = By.xpath("//*[@value='Edit']");
    public static By newAutoCharge = By.xpath("//*[@value='New Auto Charge']");
    
    public static By autoChargeCodesList = By.xpath("//*[@id='autoChargesTable']/tbody/tr/td[1]");
    public static By checkPortfolioType = By.xpath("//*[@title='Click to jump to portfolio']");
}
