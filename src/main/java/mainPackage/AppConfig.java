package mainPackage;

public class AppConfig 
{

	public static String username= "mds0418@gmail.com";
	public static String password="HomeRiver1#";
	public static String URL="https://app.propertyware.com/pw/login.jsp";
	public static String[] Buildings= {"SABA2399"};
	public static String[] Names= {"Baxter - Hernandez"};
	
	public static String lastMonthLeases = "Select  Company,buildingabbreviation,LeaseName from LeaseFact_dashboard where DATEDIFF(month, StartDate, GETDATE()) = 1 and Company ='Florida'  order by id asc";
	public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
    public static String downloadFilePath = "C:\\SantoshMurthyP\\Lease Audit Automation";
    
    public static  String PDFFormatConfirmationText = "The parties to this lease are:";
	public static  String PDFFormat2ConfirmationText = "THIS RESIDENTIAL LEASE AGREEMENT";
	
   public static String getAutoChargeCode(String company)
   {
	   switch(company)
	   {
	   case "Florida":
		   return "4000 - Rent";
	   }
	   return "";
   }
}

