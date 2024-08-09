package com.example.emr;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.util.*;
import javax.activation.*;

public class EmailUtils {

	private static String SMTP_SERVER = "pnq58.balasai.com";
	private static Integer SMTP_PORT = 25;
	private static String SENDER_EMAIL = "sonam@gamutbizsol.com";
	private static String SENDER_PASSWORD = "29B?*=HE!V;7";
	private static String RECIPIENT_EMAIL1 = "antara@gamutbizsol.com";
	private static String RECIPIENT_EMAIL2 = "nandini@gamutbizsol.com";
	private static String RECIPIENT_EMAIL3 = "sonam@gamutbizsol.com";

		public static void sendEmailWithAttachments(File reportFile, File logFile, File finalScreenshot) {
		String messageText = "Hello ,\nThe automated testing of the User Login functionality has been completed.\nPlease find the attachments.\n\nThank you.";
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", SMTP_SERVER);
		properties.put("mail.smtp.port", SMTP_PORT);

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
			}
		});

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(SENDER_EMAIL));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT_EMAIL1));
	        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(RECIPIENT_EMAIL2));
	        message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(RECIPIENT_EMAIL3));
	        message.setSubject("Login Automation Test Execution Report");

	        MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setText(messageText);
	        
	        // Create MimeBodyPart for log file attachment
	        MimeBodyPart logAttachmentPart = new MimeBodyPart();
	        FileDataSource logFileDataSource = new FileDataSource(logFile);
	        logAttachmentPart.setDataHandler(new DataHandler(logFileDataSource));
	        logAttachmentPart.setFileName(logFileDataSource.getName());

	        MimeBodyPart SSattachmentPart = new MimeBodyPart();
	        FileDataSource fileDataSource = new FileDataSource(finalScreenshot);
	        SSattachmentPart.setDataHandler(new DataHandler(fileDataSource));
	        SSattachmentPart.setFileName(fileDataSource.getName());

	        // Create MimeBodyPart for report file attachment
	        MimeBodyPart reportAttachmentPart = new MimeBodyPart();
	        FileDataSource reportFileDataSource = new FileDataSource(reportFile);
	        reportAttachmentPart.setDataHandler(new DataHandler(reportFileDataSource));
	        reportAttachmentPart.setFileName(reportFileDataSource.getName());
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(textPart);
	        multipart.addBodyPart(reportAttachmentPart);
	        multipart.addBodyPart(logAttachmentPart);
	        multipart.addBodyPart(SSattachmentPart);

	        message.setContent(multipart);

	        Transport.send(message);
	        
			System.out.println("Email sent successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
