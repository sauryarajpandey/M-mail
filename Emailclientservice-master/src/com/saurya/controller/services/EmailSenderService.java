package com.saurya.controller.services;

import com.saurya.controller.EmailSendingResult;
import com.saurya.model.Emailaccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;

public class EmailSenderService extends Service<EmailSendingResult> {
    private Emailaccount emailaccount;
    private String subject;
    private String reciever;
    private String content;
    private List<File> attachments ;

    public EmailSenderService(Emailaccount emailaccount, String subject, String reciever, String content,List<File> attachments) {
        this.emailaccount = emailaccount;
        this.subject = subject;
        this.reciever = reciever;
        this.content = content;
        this.attachments=attachments;
    }




    @Override
    protected Task<EmailSendingResult> createTask() {
        return new Task<EmailSendingResult>() {
            @Override
            protected EmailSendingResult call()  {

                try{
                    // This is used for creating the message or sending it
                    MimeMessage mimeMessage = new MimeMessage(emailaccount.getSession());
                    mimeMessage.setFrom(emailaccount.getAddress());
                    mimeMessage.addRecipients(Message.RecipientType.TO,reciever);
                    mimeMessage.setSubject(subject);
                    // This is for setting the content in the message
                    Multipart multipart = new MimeMultipart();
                    BodyPart messagebodyPart= new MimeBodyPart();
                    messagebodyPart.setContent(content,"text/html");
                    multipart.addBodyPart(messagebodyPart);
                    mimeMessage.setContent(multipart);
                    // This for sending the message with attachment
                    if(attachments.size()>0){
                        for (File file:attachments){
                            MimeBodyPart mimeBodyPart = new MimeBodyPart();
                            DataSource source = new FileDataSource(file.getAbsolutePath());
                            mimeBodyPart.setDataHandler(new DataHandler(source));
                            mimeBodyPart.setFileName(file.getName());
                            multipart.addBodyPart(mimeBodyPart);
                        }
                    }



                    //This is used for sending the message
                    Transport transport = emailaccount.getSession().getTransport();
                    transport.connect(
                            emailaccount.getProperties().getProperty("outgoingHost"),
                            emailaccount.getAddress(),
                            emailaccount.getPassword()
                    );
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();
                    return EmailSendingResult.SUCCESS;

                }
                catch (MessagingException e){
                    e.printStackTrace();
                    return EmailSendingResult.NETWORK_FAIL;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return EmailSendingResult.UNEXPECTED_ERROR;
                }
            }
        };
    }
}
