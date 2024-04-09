/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.util.Properties;
import java.sql.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mail {
    private String fromEmail ; 
    private String password;
    private String toEmail ; 
    private String subject ; 
    private String message ; 

    public Mail(String fromEmail, String password, String toEmail, String subject, String message) {
        this.fromEmail = fromEmail;
        this.password = password;
        this.toEmail = toEmail;
        this.subject = subject;
        this.message = message;
    }
    
    

    public void sendEmail() throws MessagingException {
        // Configuration des propriétés SMTP session mail 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Configuration de l'authentification
        Session session = Session.getInstance(props,
                new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        try{
        // Création du message
        Message e_message = new MimeMessage(session);
        e_message.setFrom(new InternetAddress(fromEmail));
        e_message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        e_message.setSubject(subject);
        e_message.setText(this.message);

        // Envoi du message
        Transport.send(e_message);
        System.out.println("message envoyé ");
        }
        catch (MessagingException e){
    throw new RuntimeException("Failed to send email: " + e.getMessage()); 
}

    }
     public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
      public void setSubject(String subject) {
        this.subject = subject;
    }
       public void setMessage(String message) {
        this.message = message;
    }
    
}
