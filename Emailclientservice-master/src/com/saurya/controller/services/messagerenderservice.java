package com.saurya.controller.services;

import com.saurya.model.Emailmessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

public class messagerenderservice extends Service {
    private Emailmessage emailmessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    public messagerenderservice(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(event -> {
            displaymessage();
        });
    }

    public void setEmailmessage(Emailmessage emailmessage){
        this.emailmessage=emailmessage;
    }

    private  void displaymessage(){
        webEngine.loadContent(stringBuffer.toString());
    }
    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    loadmessage();

                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
    private void loadmessage() throws MessagingException, IOException {
        stringBuffer.setLength(0); // this helps in clearing the stringbuffer
        Message message =emailmessage.getMessage();
        String contentType=message.getContentType();

        if(isSimpleType(contentType)){
            stringBuffer.append(message.getContent().toString());
        }
        else if (ismultiplepartType(contentType)){
            Multipart multipart = (Multipart) message.getContent();
          loadMultipart(multipart,stringBuffer);
        }
    }
    private void loadMultipart(Multipart multipart,StringBuffer stringBuffer) throws MessagingException, IOException {
        for(int i = multipart.getCount() - 1; i>=0 ; i--){
            BodyPart bodyPart = multipart.getBodyPart(i);
            String contentType= bodyPart.getContentType();
            if(isSimpleType(contentType)){
                stringBuffer.append(bodyPart.getContent().toString());
            }
            else if(ismultiplepartType(contentType)){
                Multipart multipart2= (Multipart) bodyPart.getContent();
                loadMultipart(multipart2,stringBuffer);
            }   else if(!isTextPlain(contentType)){
                // this field is where you attach your files
                MimeBodyPart mbp = (MimeBodyPart) bodyPart;
                emailmessage.addAttachment(mbp);

            }
        }
    }

    private  boolean isTextPlain(String contentType){
        return  contentType.contains("TEXT/PLAIN");
    }
    private  boolean isSimpleType(String contentType){
        if(contentType.contains("TEXT/HTML") ||
                contentType.contains("mixed") ||
                contentType.contains("text")){
            return  true;
        }
        else {
            return false;
        }
    }
    private boolean ismultiplepartType(String contentType){
        if(contentType.contains("multipart")){
            return  true;
        }
        else{
            return false;
        }
    }
}
