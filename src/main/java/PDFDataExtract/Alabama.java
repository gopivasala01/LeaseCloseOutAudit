package PDFDataExtract;

import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import mainPackage.PDFAppConfig;
import mainPackage.RunnerClass;

public class Alabama 
{

	public static void alabama() throws Exception
	{
		try
		{
		File file = RunnerClass.getLastModified();
		FileInputStream fis = new FileInputStream(file);
		PDDocument document = PDDocument.load(fis);
		String text = new PDFTextStripper().getText(document);
		text = text.replaceAll(System.lineSeparator(), " ");
	    text = text.replaceAll(" +", " ");
	    //System.out.println(text);
	    try
	    {
	    String monthlyRent = text.substring(text.indexOf(PDFAppConfig.Alabama_monthlyRent_Format2_Prior)+PDFAppConfig.Alabama_monthlyRent_Format2_Prior.length()).split(" ")[0].trim();
	    System.out.println(monthlyRent);
	    RunnerClass.monthlyRent = monthlyRent;
	    }
	    catch(Exception e)
	    {
	    	RunnerClass.monthlyRent = "Error";
	    }
	    try
	    {
	    String startDate = text.substring(text.indexOf(PDFAppConfig.Alabama_startDate_Format2_Prior)+PDFAppConfig.Alabama_startDate_Format2_Prior.length(),text.indexOf(PDFAppConfig.Alabama_startDate_Format2_After)).trim();
	    System.out.println(startDate);
	    RunnerClass.startDate = startDate;
	    }
	    catch(Exception e) 
	    {
	    	RunnerClass.startDate = "Error";
	    }
		}
		catch(Exception e)
		{
			RunnerClass.monthlyRent = "Error";
			RunnerClass.startDate = "Error";
		}
	}
	
}
