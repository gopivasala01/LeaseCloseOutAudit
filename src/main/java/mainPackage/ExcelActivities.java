package mainPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelActivities 
{
	public static void createExcelFileWithProcessedData()
	{
		//Get Today's date in MMddyyyy format
		LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String date = dateObj.format(formatter);
        System.out.println(date);
        String filename ;
		try   
		{  
		filename = AppConfig.excelFileLocation+"\\LeaseAuditAutomation_"+date+".xlsx";  
		File file = new File(filename);
		//if file exists, delete and re create it
		if(file.exists())
		{
			file.delete();
		}
		Workbook wb = new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet("Sheet 1");
		Row header = sheet1.createRow(0);
		header.createCell(0).setCellValue("Company");
		header.createCell(1).setCellValue("Building Abbreviation");
		header.createCell(2).setCellValue("Monthly Rent From Lease Agreement");
		header.createCell(3).setCellValue("Monthly Rent From Property Ware");
		header.createCell(4).setCellValue("Start Date From Lease Agreement");
		header.createCell(5).setCellValue("Start Date From Property Ware");
		header.createCell(6).setCellValue("Status");
		header.createCell(7).setCellValue("Failed Notes");
		//int totalCurrentDayBuildings = RunnerClass.successBuildings.size()+RunnerClass.failedBuildings.size();
		//sheet1.createRow(sheet1.getLastRowNum()+totalCurrentDayBuildings);
		boolean getBuildings =  DataBase.getCompletedBuildingsList();
		if(getBuildings==true&&RunnerClass.completedBuildingList!=null)
		{
			for(int i=0;i<RunnerClass.completedBuildingList.length;i++)
			{
				String company = RunnerClass.completedBuildingList[i][0];
				String building = RunnerClass.completedBuildingList[i][1].trim();
				String MonthlyRentFromLeaseAgreement = RunnerClass.completedBuildingList[i][2];
				String MonthlyRentInPW = RunnerClass.completedBuildingList[i][3];
				String StartDateFromLeaseAgreement = RunnerClass.completedBuildingList[i][4];
				String StartDateInPW = RunnerClass.completedBuildingList[i][5];
				String Status = RunnerClass.completedBuildingList[i][6];
				String Notes = RunnerClass.completedBuildingList[i][7];
				
				Row row = sheet1.createRow(1+i);
				row.createCell(0).setCellValue(company);
				row.createCell(1).setCellValue(building);
				row.createCell(2).setCellValue(MonthlyRentFromLeaseAgreement);
				row.createCell(3).setCellValue(MonthlyRentInPW);
				row.createCell(4).setCellValue(StartDateFromLeaseAgreement);
				row.createCell(5).setCellValue(StartDateInPW);
				row.createCell(6).setCellValue(Status);
				row.createCell(7).setCellValue(Notes);
				
			}
		
		}
		
		System.out.println("Last row in the sheet = "+sheet1.getLastRowNum());
		FileOutputStream fileOut = new FileOutputStream(filename);  
		wb.write(fileOut);
		wb.close();
		fileOut.close();  
		System.out.println("Excel file has been generated successfully.");  
		MailActivities.sendMail(filename);
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}  
		
		//Send Email the attachment
	}
	

}
