package PDFDataExtract;

import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import mainPackage.AppConfig;
import mainPackage.PDFAppConfig;
import mainPackage.RunnerClass;

public class Florida 
{
	//public static void main(String[] args)
	
	public static void florida() throws Exception
	{
		try
		{
		File file = RunnerClass.getLastModified();
		//File file = new File("C:\\SantoshMurthyP\\Lease Audit Automation\\Lease_1222_1123_2709_Butternut_Ct_FL_Knight.pdf");
		System.out.println(file);
		FileInputStream fis = new FileInputStream(file);
		PDDocument document = PDDocument.load(fis);
		String text = new PDFTextStripper().getText(document);
		text = text.replaceAll(System.lineSeparator(), " ");
	    text = text.replaceAll(" +", " ");
	    String monthlyRent_Prior ="";
	    String startDate_Prior="";
	    String startDate_After="";
	    if(text.contains(AppConfig.PDFFormat2ConfirmationText))
	    {
	    	monthlyRent_Prior = PDFAppConfig.Florida_monthlyRent_Format1_Prior;
	    	startDate_Prior = PDFAppConfig.Florida_startDate_Format1_Prior;
	    	startDate_After = PDFAppConfig.Florida_startDate_Format1_After;
	    }
	    else if(text.contains(AppConfig.PDFFormatConfirmationText))
	    {
	    	monthlyRent_Prior = PDFAppConfig.Florida_monthlyRent_Format2_Prior;
	    	startDate_Prior = PDFAppConfig.Florida_startDate_Format2_Prior;
	    	startDate_After = PDFAppConfig.Florida_startDate_Format2_After;
	    }
	    else 
	    {
	    	
	    }
	    //System.out.println(text);
	    try
	    {
	    String monthlyRent = text.substring(text.indexOf(monthlyRent_Prior)+monthlyRent_Prior.length()).split(" ")[0].trim();
	    System.out.println("Monthly Rent Amount = "+monthlyRent);
	    RunnerClass.monthlyRent = monthlyRent;
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	RunnerClass.monthlyRent = "Error";
	    }
	    try
	    {
	    String startDate = text.substring(text.indexOf(startDate_Prior)+startDate_Prior.length(),text.indexOf(startDate_After)).trim();
	    System.out.println(startDate);
	    RunnerClass.startDate = startDate;
	    RunnerClass.startDate = RunnerClass.convertDate(RunnerClass.startDate);
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    	RunnerClass.startDate = "Error";
	    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			RunnerClass.monthlyRent = "Error";
			RunnerClass.startDate = "Error";
		}
	}
	

}
