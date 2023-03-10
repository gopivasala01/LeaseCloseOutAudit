package mainPackage;

public class AppConfig 
{

	public static String username= "mds0418@gmail.com";
	public static String password="HomeRiver1#";
	public static String URL="https://app.propertyware.com/pw/login.jsp";
	public static String[] Buildings= {"SABA2399"};
	public static String[] Names= {"Baxter - Hernandez"};
	
   public static String lastMonthLeases = "Select  Company,buildingabbreviation,LeaseName from LeaseFact_dashboard where DATEDIFF(month, StartDate, GETDATE()) = 1 and Company ='Florida'  order by id asc";
   //public static String lastMonthLeases1 = "Select  Company,buildingabbreviation,LeaseName from LeaseFact_dashboard where DATEDIFF(month, StartDate, GETDATE()) = 1 and Company ='Alabama'  order by id asc";
	//public static String lastMonthLeases = "Select  Company,buildingabbreviation,LeaseName from [Automation].[leaseAuditAutomation] where notes = 'Values did not match'";
	public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
    public static String downloadFilePath = "C:\\SantoshMurthyP\\Lease Audit Automation";
    
    public static  String PDFFormatConfirmationText = "The parties to this lease are:";
	public static  String PDFFormat2ConfirmationText = "THIS RESIDENTIAL LEASE AGREEMENT";
	
	//Mail credentials
	   public static String fromEmail = "bireports@beetlerim.com";
	   public static String fromEmailPassword = "Welcome@123";
	   
	   public static String toEmail ="gopi.v@beetlerim.com,Santosh.p@beetlerim.com";
	   public static String CCEmail = "santosh.t@beetlerim.com";
	   
	   public static String mailSubject = "Lease Audit for the Month of   ";
	   
	   public static String excelFileLocation = "E:\\Automation\\Gopi\\Lease Audit Automation";
	   
	   public static String getBuildingsWithStatusforCurrentDay = "Select Company,[BuildingAbbreviation],MonthlyRentFromLeaseAgreement,MonthlyRentInPW,StartDateFromLeaseAgreement,StartDateInPW,Status,notes from automation.[leaseAuditAutomation] where  FoRMAT(completedDate,'MM-dd-yyyy')=FoRMAT(getdate(),'MM-dd-yyyy')";
	   
	   public static String[] IAGClientList = {"510","AVE","BTH","CAP","FOR","HRG","HS","MAN","MCH","OFF","PIN","RF","SFR3","TH","HH","Lofty.Ai"};
	
   public static String getAutoChargeCode(String company)
   {
	   switch(company)
	   {
	   case "Florida":
		   return "4000 - Rent";
	   case "Alabama":
		   return "4000 - Rent";
	   }
	   return "";
   }
}

