package com.ical4jtest.application.service.implementation;

import com.ical4jtest.application.service.SendEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;

@Service
public class SendEmailServiceImpl implements SendEmailService {
    public SendEmailServiceImpl() {
    }

    @Value("${email.username}")
    private String from;

    @Value("${email.host}")
    private String host;

    @Value("${email.password}")
    private String password;

    @Value("${email.host-port}")
    private String port;

    @Override
    public void sendEmailWithAttachment(String emailTo) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.pwd", password);

        // Get the default Session object.
        Session session = Session.getInstance(properties, null);

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailTo));

            // Set Subject: header field
            message.setSubject("Here is your calendar event!");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText("This calendar event was generated in java");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "mymeeting.ics";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart );

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect(host, Integer.parseInt(port), from, password);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
