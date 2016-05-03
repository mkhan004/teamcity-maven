package com.kaplan.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EMailEngine
{
    public static void main(String[] args)
    {
        TestRunnerUtilities.zip(TestRunnerUtilities.reportPath);

        String emailTo = args[0];
        String emailFrom = args[1];
        String emailHost = args[2];
        String reportFile = args[3]+"/index.html";
        sendMail(emailTo, emailFrom, emailHost, reportFile);
    }

    public static void sendMail(String emailTo, String emailFrom, String emailHost, String reportFile)
    {
        String to = emailTo;
        // Sender's email ID needs to be mentioned
        String from = emailFrom;
        // Assuming you are sending email from localhost
        String host = emailHost;
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try
        {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            // Set Subject: header field
            message.setSubject("Automation Test Report - " + TestRunnerUtilities.projectName + " - "
                    + TestRunnerUtilities.environment + " - "
                    + TestRunnerUtilities.now("yyyy-MM-dd_HH:mm"));
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message

            File indexHtml = new File(reportFile);

            StringBuilder contentBuilder = new StringBuilder();
            try {
                BufferedReader in = new BufferedReader(new FileReader(indexHtml));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
            } catch (IOException e) {
            }
            String htmlContent = contentBuilder.toString();

            messageBodyPart.setContent("Please find the reports attached. \n\n Summary of Test \n Tests Passed : "+ TestRunnerUtilities.passTc + "/"+ TestRunnerUtilities.totalTc  + htmlContent + "\\n\\n Regards\\n Atom API Team", "text/html; charset=utf-8");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.dir") + "/Reports.zip";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Reports.zip");
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport.send(message);
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }
}