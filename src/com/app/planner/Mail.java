package com.app.planner;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mail {

    public static void sendMail(String emailAddress, String subject ,String emailMessage) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dietplanner.hello@gmail.com", "hdqrzxffiphxfkrq");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("dietplanner.hello@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(emailAddress));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(emailMessage, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
