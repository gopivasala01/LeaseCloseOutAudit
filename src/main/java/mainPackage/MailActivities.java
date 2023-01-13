package mainPackage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailActivities 
{

	public static void sendMail(String FileName)
	{
		
		Calendar cal =  Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		//format it to MMM-yyyy // January-2012
		String previousMonthYear  = new SimpleDateFormat("MMM yyyy").format(cal.getTime());
        System.out.println(previousMonthYear);
		
		File fileName = new File(FileName);
		
		// Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtpout.asia.secureserver.net";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      //props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "80");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(AppConfig.fromEmail, AppConfig.fromEmailPassword);
	            }
	         });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(AppConfig.fromEmail));

	         InternetAddress[] iAdressArray = InternetAddress.parse(AppConfig.toEmail);
	         // Set To: header field of the header.
	        message.setRecipients(Message.RecipientType.TO,
	        		iAdressArray);

	         // Set CC: header field of the header.
	         message.setRecipients(Message.RecipientType.CC,
	            InternetAddress.parse(AppConfig.CCEmail));
	         
	         // Set CC: header field of the header.
	         message.setRecipients(Message.RecipientType.BCC,
	        		 InternetAddress.parse("sujana.t@beetlerim.com"));
	         
	         // Set Subject: header field
	        String subject = AppConfig.mailSubject+previousMonthYear;
	        message.setSubject(subject);

	         // Create the message part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Now set the actual message
	         String messageInBody = "Hi All,\n Please find the attachment.\n\n Regards,\n HomeRiver Group.";
	         messageBodyPart.setText(messageInBody);

	         // Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	        // String filename = "C:\\PropertyWare\\externalFiles\\downloadFiles\\"+"Operations-Marketing.xlsx";
	         System.out.println("FileName sending in mail"+fileName);
	         messageBodyPart.setFileName(fileName.getName());
	         DataSource source = new FileDataSource(fileName);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	        // messageBodyPart.setFileName(filename);
	         messageBodyPart.setFileName(fileName.getName());
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         message.setContent(multipart);

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
	  
	         //wait until file is downloaded
	         /*
	         File dir = new File("DownloadPath");
	         //String partialName = downloaded_report.split("_")[0].concat("_"); //get cancelled and add underscore
	        // FluentWait<WebDriver> wait = new FluentWait<WebDriver>(RunnerClass.driver);
	                 //wait.pollingEvery(1, TimeUnit.SECONDS);
	                 //wait.withTimeout(15, TimeUnit.SECONDS);
	                 RunnerClass.wait.until(x -> {
	                     File[] filesInDir = dir.listFiles();
	                     for (File fileInDir : filesInDir) {
	                         if (fileInDir.getName().startsWith("Marketing")) {
	                             return true;
	                         }
	                     }
	                     return false;
	                 });
	         */
	         //delete the current file
	       // File file = new File(fileName);
	         fileName.delete();
	      } catch (MessagingException e) 
	      {
	    	  e.printStackTrace();
	         throw new RuntimeException(e);
	      }
	}
	
}
