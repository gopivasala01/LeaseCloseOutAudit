package mainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper; 

public class PdfReader 
{
    
	public static boolean readPDFPerMarket(String market) throws Exception  
	{
		
		switch(market)
		{
		case "Alabama":
			PDFDataExtract.Alabama.alabama();
			if(RunnerClass.monthlyRent=="Error"||RunnerClass.startDate=="Error")
			{
				System.out.println("Unable to fetch Monthly Rent and Start Date from Lease Agreement");
				RunnerClass.failedReaonsList.put(RunnerClass.buildingAbbreviation, "Unable to fetch Monthly Rent and Start Date from Lease Agreement");
			    RunnerClass.failedReason = "Unable to fetch Monthly Rent and Start Date from Lease Agreement";
				RunnerClass.updateStatus=1;
				return false;
			}
			else return true;
		case "Florida":
			PDFDataExtract.Florida.florida();
			if(RunnerClass.monthlyRent=="Error"||RunnerClass.startDate=="Error")
			{
				System.out.println("Unable to fetch Monthly Rent and Start Date from Lease Agreement");
				RunnerClass.failedReaonsList.put(RunnerClass.buildingAbbreviation, "Unable to fetch Monthly Rent and Start Date from Lease Agreement");
			    RunnerClass.failedReason = "Unable to fetch Monthly Rent and Start Date from Lease Agreement";
				RunnerClass.updateStatus=1;
				return false;
			}
			else return true;

		}
		return false;
		
		/*
		File file = new File("C:\\SantoshMurthyP\\Company\\Pennsylvania\\Lease_12.21_03.23_310_54th_St_PA_Gomrick_Hairston.pdf");
		FileInputStream fis = new FileInputStream(file);
		PDDocument document = PDDocument.load(fis);
		String text = new PDFTextStripper().getText(document);
		text = text.replaceAll(System.lineSeparator(), " ");
	    text = text.replaceAll(" +", " ");
	    //System.out.println(text);
	    String monthlyRent = text.substring(text.indexOf(PDFAppConfig.Pennsylvania_monthlyRent_Format2_Prior)+PDFAppConfig.Pennsylvania_monthlyRent_Format2_Prior.length()).split(" ")[0].trim();
	    System.out.println(monthlyRent);
	    String startDate = text.substring(text.indexOf(PDFAppConfig.Pennsylvania_startDate_Format2_Prior)+PDFAppConfig.Pennsylvania_startDate_Format2_Prior.length(),text.indexOf(PDFAppConfig.Pennsylvania_startDate_Format2_After)).trim();
	    System.out.println(startDate);
	    */
	}
  
}
   